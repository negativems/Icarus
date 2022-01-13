package net.zargum.plugin.icarus.region;

import lombok.Getter;
import net.zargum.plugin.icarus.Icarus;
import net.zargum.zlib.configuration.ConfigYML;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RegionConfig extends ConfigYML {

    private final Icarus plugin;
    @Getter private final Map<String, Region> regions = new HashMap<>();

    public RegionConfig(Icarus plugin) {
        super(plugin.getDataFolder(), "regions");
        this.plugin = plugin;
        load();
    }

    @Override
    public void onLoad() {
        plugin.log("Loading regions...");

        Set<String> regionsConfig = config.getValues(false).keySet();
        for (String regionName : regionsConfig) {

            String worldName = config.getString(regionName + ".world");
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                plugin.log(ChatColor.RED + "Regions: World " + worldName + " is not exists.");
                continue;
            }

            int x1 = config.getInt(regionName + ".first-location.x");
            int y1 = config.getInt(regionName + ".first-location.y");
            int z1 = config.getInt(regionName + ".first-location.z");
            int x2 = config.getInt(regionName + ".second-location.x");
            int y2 = config.getInt(regionName + ".second-location.y");
            int z2 = config.getInt(regionName + ".second-location.z");

            // Flags
            Location firstLocation = new Location(world, x1,y1,z1);
            Location secondLocation = new Location(world, x2,y2,z2);
            Region region = new Region(regionName, firstLocation, secondLocation);
            if (config.contains(regionName + ".flags")) {
                Map<Flag, FlagState> flags = new HashMap<>();
                Set<String> flagsSection = config.getConfigurationSection(regionName + ".flags").getValues(false).keySet();
                for (String flagName : flagsSection) {
                    if (!EnumUtils.isValidEnum(Flag.class, flagName.toUpperCase().replaceAll("-", "_"))) {
                        plugin.log(ChatColor.RED + "(Config) Regions: " + flagName + " is not a valid flag.");
                        continue;
                    }
                    String configFlagState = config.getString(regionName + ".flags." + flagName).toUpperCase().replaceAll("-", "_");
                    if (!EnumUtils.isValidEnum(FlagState.class, configFlagState)) {
                        plugin.log(ChatColor.RED + "(Config) Regions: " + flagName + " is not a valid flag state.");
                        continue;
                    }

                    flags.put(Flag.valueOf(flagName.toUpperCase().replaceAll("-", "_")), FlagState.valueOf(configFlagState));
                }
                region.setFlags(flags);
            }
            regions.put(regionName, region);
        }
        if (regions.size() > 0) {
            plugin.log(ChatColor.GREEN + "Loaded " + regions.size() + " regions.");
            return;
        }
        plugin.log("No regions to load.");
    }

    @Override
    public void beforeSave() {
        plugin.log("Saving regions...");

        // Remove all the regions from the config file
        for(String key : config.getKeys(false)) config.set(key, null);

        // Save loaded regions
        for (String regionName : regions.keySet()) {
            Region region = regions.get(regionName);
            config.set(regionName + ".world", region.getFirstLocation().getWorld().getName());
            config.set(regionName + ".first-location.x", region.getFirstLocation().getBlockX());
            config.set(regionName + ".first-location.y", region.getFirstLocation().getBlockY());
            config.set(regionName + ".first-location.z", region.getFirstLocation().getBlockZ());
            config.set(regionName + ".second-location.x", region.getSecondLocation().getBlockX());
            config.set(regionName + ".second-location.y", region.getSecondLocation().getBlockY());
            config.set(regionName + ".second-location.z", region.getSecondLocation().getBlockZ());
            for (Flag flag : region.getFlags().keySet()) {
                String flagName = flag.name().replaceAll("_", "-").toLowerCase();
                String flagStateName = region.getFlags().get(flag).name().replaceAll("_", "-").toLowerCase();
                config.set(regionName + ".flags." + flagName, flagStateName);
            }
        }
        try {
            config.save(file);
            plugin.log(ChatColor.GREEN + "" + regions.size() + " regions saved.");
        } catch (IOException e) {
            plugin.log(ChatColor.RED + "Error saving regions.");
            e.printStackTrace();
        }
    }

    @Override
    public void onSave() {

    }
}
