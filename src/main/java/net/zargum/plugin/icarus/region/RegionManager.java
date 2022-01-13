package net.zargum.plugin.icarus.region;

import lombok.Getter;
import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.region.listeners.*;
import net.zargum.plugin.icarus.region.managers.RegionPriorityManager;
import net.zargum.plugin.icarus.world.WorldManager;
import net.zargum.zlib.command.ServerCommand;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class RegionManager {

    public final Icarus plugin;
    private final RegionConfig handler;
    private final WorldManager worldManager;
    private final RegionPriorityManager regionPriorityManager;
    private final List<UUID> logPlayers = new ArrayList<>();

    private final String[] tabulatedArguments = new String[]{
            "delete", "flagstate", "info", "rename",
            "select", "setflag", "setpriority"
    };

    private void loadListeners() {
        plugin.getServer().getPluginManager().registerEvents(new RegionEntitySpawnListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new RegionFrameListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new RegionBreakListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new RegionPlaceListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new RegionCombatListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new RegionDropListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new RegionEnderpearlListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new RegionGetDamageListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new RegionInteractListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new RegionHungerListener(this), plugin);
        plugin.getServer().getPluginManager().registerEvents(new RegionFallDamageListener(this), plugin);
    }

    public RegionManager(Icarus plugin) {
        this.plugin = plugin;
        handler = new RegionConfig(plugin);
        worldManager = plugin.getWorldManager();
        regionPriorityManager = new RegionPriorityManager(this);
        loadListeners();
    }

    public boolean hasLogsEnabled(Player player) {
        return logPlayers.contains(player.getUniqueId());
    }

    public FlagState getFinalFlagState(Location location, Flag flag) {
        FlagState flagState = FlagState.DEFAULT;
        List<Region> regions = new ArrayList<>();

        // Set the world flag state if it is modified
        if (worldManager.hasFlag(location.getWorld(), flag)) {
            for (Flag worldFlag : worldManager.getFlags(location.getWorld()).keySet()) {
                if (worldFlag == flag) {
                    FlagState worldFlagState = worldManager.getFlagState(location.getWorld(), flag);
                    if (worldFlagState != FlagState.DEFAULT) {
                        flagState = worldFlagState;
                    }
                }
            }
        }

        // Add all regions from location with that flag
        for (Region region : getRegionsFromLocation(location)) {
            if (region.getFlags().containsKey(flag) && region.getFlags().get(flag) != FlagState.DEFAULT) {
                regions.add(region);
            }
        }

        if (regions.size() > 0) {
            // Gets the highest region by priority
            Region mostPriorityRegion = regions.get(0);
            for (Region region : regions) {
                if (region.getPriority() > mostPriorityRegion.getPriority()) {
                    mostPriorityRegion = region;
                }
            }
            // Returns highest region state
            for (Flag mostPriorityRegionFlag : mostPriorityRegion.getFlags().keySet()) {
                if (mostPriorityRegionFlag == flag) {
                    return mostPriorityRegion.getFlags().get(flag);
                }
            }
        }

        return flagState;
    }

    public String getRegionsFormatted(List<Region> regions) {
        List<String> regionsName = new ArrayList<>();
        for (Region region : regions) {
            regionsName.add(region.getName());
        }
        return String.join(", ", regionsName);
    }

    public void create(String regionName, Location firstLocation, Location secondLocation) {
        Region region = new Region(regionName, firstLocation, secondLocation);
        handler.getRegions().put(regionName, region);
    }

    public void delete(String regionName) {
        if (!handler.getRegions().containsKey(regionName)) {
            throw new NullPointerException("Region " + regionName + " not found.");
        }
        handler.getRegions().remove(regionName);
    }

    public Region getRegion(String regionName) {
        if (!exist(regionName)) return null;
        return handler.getRegions().get(regionName);
    }

    public List<Region> getAllRegions() {
        return new ArrayList<>(handler.getRegions().values());
    }

    public List<String> getAllRegionsName() {
        Collection<Region> regions = handler.getRegions().values();
        List<String> result = new ArrayList<>();
        for (Region region : regions) result.add(region.getName());
        return result;
    }

    public boolean exist(String region) {
        return handler.getRegions().containsKey(region);
    }

    public boolean isLocationContainRegions(Location location) {
        return getRegionsFromLocation(location).size() > 0;
    }

    public List<Region> getRegionsFromLocation(Location location) {
        List<Region> result = new ArrayList<>();
        for (Region region : handler.getRegions().values()) {
            boolean isBetweenX = location.getX() < region.getMaxX() && location.getX() > region.getMinX();
            boolean isBetweenY = location.getY() < region.getMaxY() && location.getY() > region.getMinY();
            boolean isBetweenZ = location.getZ() < region.getMaxZ() && location.getZ() > region.getMinZ();
            if (isBetweenX && isBetweenY && isBetweenZ) result.add(region);
        }
        return result;
    }

    public List<Region> getRegionsFromLocations(Location... locations) {
        List<Region> result = new ArrayList<>();
        for (Location location : locations) {
            result.addAll(getRegionsFromLocation(location));
        }
        return result;
    }

    public List<Region> getRegionsByPlayer(Player player) {
        return getRegionsFromLocation(player.getLocation());
    }

    public List<String> getRegionsNameByLocation(Location location) {
        List<Region> regions = getRegionsFromLocation(location);
        List<String> result = new ArrayList<>();
        for (Region region : regions) {
            result.add(region.getName());
        }
        return result;
    }

    public boolean checkRegionByLocation(String regionName, Location location) {
        if (!exist(regionName)) return false;
        Region region = handler.getRegions().get(regionName);
        return getRegionsFromLocation(location).contains(region);
    }
}