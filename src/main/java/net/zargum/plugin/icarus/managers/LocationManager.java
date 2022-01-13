package net.zargum.plugin.icarus.managers;

import lombok.Getter;
import lombok.Setter;
import net.zargum.plugin.icarus.Icarus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

public class LocationManager {

    private final Icarus plugin;
    @Getter private final ConfigurationSection config;
    @Getter @Setter private Location spawn;

    public LocationManager(Icarus plugin){
        this.plugin = plugin;
        config = plugin.getConfig().getConfigurationSection("spawn");
        spawn = getConfigSpawn();
    }

    public void saveSpawn() {
        config.set("world", spawn.getWorld().getName());
        config.set("x", spawn.getX());
        config.set("y", spawn.getY());
        config.set("z", spawn.getZ());
        config.set("yaw", spawn.getYaw());
        config.set("pitch", spawn.getPitch());
        plugin.saveConfig();
    }

    public void resetSpawnLocation() {
        Location location = Bukkit.getWorlds().get(0).getSpawnLocation();
        config.set("world", location.getWorld().getName());
        config.set("x", location.getX() + 0.5D);
        config.set("y", location.getY());
        config.set("z", location.getZ() + 0.5D);
        config.set("yaw", location.getYaw());
        config.set("pitch", location.getPitch());
        plugin.saveConfig();
    }

    private Location getConfigSpawn() {
        String worldName = config.getString("world");
        World world = plugin.getServer().getWorld(worldName);
        if (world == null) {
            plugin.log(ChatColor.RED + "Can not found world " + worldName + " from the config.");
            world = plugin.getServer().getWorlds().get(0);
        }
        return new Location(
                world,
                config.getDouble("x", 0.5D),
                config.getDouble("y", 80.0D),
                config.getDouble("z", 0.5D),
                (float) config.getDouble("yaw", 0),
                (float) config.getDouble("pitch", 0)
        );
    }

}
