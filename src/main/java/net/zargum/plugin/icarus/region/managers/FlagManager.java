package net.zargum.plugin.icarus.region.managers;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.region.*;
import net.zargum.plugin.icarus.world.WorldConfig;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FlagManager {

    private final Icarus plugin;
    private final WorldConfig worldHandler;
    private final RegionConfig regionHandler;

    public FlagManager(Icarus plugin) {
        this.plugin = plugin;
        this.worldHandler = plugin.getWorldManager().getHandler();
        this.regionHandler = plugin.getRegionManager().getHandler();
    }

    public boolean isFlagOnWorld(String worldName, Flag flag) {
        if (worldHandler.getFlags().containsKey(worldName)) throw new NullPointerException("World " + worldName + " not exists");
        return worldHandler.getFlags().get(worldName).containsKey(flag);
    }

    public void addFlagToWorld(String worldName, Flag flag, FlagState state) {
        worldHandler.getFlags().get(worldName).put(flag, state);
    }

    public void addFlagToRegion(String regionName, Flag flag, FlagState state) {
        regionHandler.getRegions().get(regionName).getFlags().put(flag, state);
    }

    public void addFlagToRegion(Region region, Flag flag, FlagState state) {
        regionHandler.getRegions().get(region.getName()).getFlags().put(flag, state);
    }

    public void removeFlagFromRegion(Region region, Flag flag) {
        regionHandler.getRegions().get(region.getName()).getFlags().remove(flag);
    }
    public void removeFlagFromRegion(String region, Flag flag) {
        regionHandler.getRegions().get(region).getFlags().remove(flag);
    }

    public boolean isFlagAllowedOnRegion(Region region, Flag flag) {
        return region.getFlags().containsKey(flag) && region.getFlags().get(flag) == FlagState.ALLOW;
    }

    public boolean isFlagDeniedOnRegion(Region region, Flag flag) {
        return region.getFlags().containsKey(flag) && region.getFlags().get(flag) == FlagState.DENY;
    }

    public boolean isFlagDefaultOnRegion(Region region, Flag flag) {
        return region.getFlags().containsKey(flag) && region.getFlags().get(flag) == FlagState.DEFAULT;
    }

    public List<String> getFlagStatesName() {
        List<String> result = new ArrayList<>();
        for (FlagState flagState : FlagState.values()) {
            result.add(flagState.name().toLowerCase().replaceAll("_", "-"));
        }
        return result;
    }

    public List<String> getFlagsName() {
        List<String> result = new ArrayList<>();
        for (Flag flag : Flag.values()) {
            result.add(flag.name().toLowerCase().replaceAll("_", "-"));
        }
        return result;
    }

    public String getFormattedFlags() {
        StringBuilder result = new StringBuilder();
        for (Flag flag : Flag.values()) {
            String flagName = ChatColor.YELLOW + flag.name().toLowerCase().replaceAll("_", "-");
            result.append(flagName).append(ChatColor.WHITE).append(",").append(ChatColor.YELLOW).append(" ");
        }
        return result.toString();
    }

    public String getFormattedFlags(Map<Flag, FlagState> flags) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        for (Flag flag : flags.keySet()) {
            String flagName = flag.name().toLowerCase().replaceAll("_", "-");
            FlagState flagState = flags.get(flag);
            ChatColor color = flagState != FlagState.DEFAULT ? (flagState == FlagState.ALLOW) ? ChatColor.GREEN : ChatColor.RED : ChatColor.GRAY;
            if (i == flags.keySet().size()-1) {
                result.append(color).append(flagName);
                continue;
            }
            result.append(color).append(flagName).append(ChatColor.WHITE).append(",").append(ChatColor.YELLOW).append(" ");
        }
        return result.toString();
    }

}
