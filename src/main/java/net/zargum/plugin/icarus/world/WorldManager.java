package net.zargum.plugin.icarus.world;

import lombok.Getter;
import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.region.Flag;
import net.zargum.plugin.icarus.region.FlagState;
import org.bukkit.ChatColor;
import org.bukkit.World;

import java.util.Map;

@Getter
public class WorldManager {

    private final Icarus plugin;
    private final WorldConfig handler;

    public WorldManager(Icarus plugin) {
        this.plugin = plugin;
        handler = new WorldConfig(plugin);
        plugin.getServer().getPluginManager().registerEvents(new WorldListener(this), plugin);
    }

    public boolean existsWorld(String worldName) {
        return handler.getWorlds().contains(worldName);
    }

    public boolean isLoaded(String worldName) {
        return plugin.getServer().getWorld(worldName) != null;
    }

    public boolean hasFlag(World world, Flag flag) {
        if (!handler.getFlags().containsKey(world.getName())) {
            plugin.log(ChatColor.RED + "[WorldManager #hasFlag] Can not found world " + world.getName());
            return false;
        }
        return handler.getFlags().get(world.getName()).containsKey(flag);
    }

    public boolean hasFlag(String worldName, Flag flag) {
        return handler.getFlags().get(worldName).containsKey(flag);
    }

    public FlagState getFlagState(World world, Flag flag) {
        if (!handler.getFlags().containsKey(world.getName())) return null;
        return handler.getFlags().get(world.getName()).get(flag);
    }

    public FlagState getFlagState(String worldName, Flag flag) {
        if (!handler.getFlags().containsKey(worldName)) return null;
        return handler.getFlags().get(worldName).get(flag);
    }

    public Map<Flag, FlagState> getFlags(World world) {
        if (!handler.getFlags().containsKey(world.getName())) return null;
        return handler.getFlags().get(world.getName());
    }
    public Map<Flag, FlagState> getFlags(String worldName) {
        if (!existsWorld(worldName)) return null;
        return handler.getFlags().get(worldName);
    }
}
