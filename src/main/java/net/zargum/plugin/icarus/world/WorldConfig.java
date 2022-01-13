package net.zargum.plugin.icarus.world;

import lombok.Getter;
import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.region.Flag;
import net.zargum.plugin.icarus.region.FlagState;
import net.zargum.zlib.configuration.ConfigYML;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class WorldConfig extends ConfigYML {

    private final Icarus plugin;

    @Getter private final List<String> worlds = new ArrayList<>();
    @Getter private final List<String> deletedWorlds = new ArrayList<>();
    @Getter private final Map<String, Map<Flag, FlagState>> flags = new HashMap<>();

    public WorldConfig(Icarus plugin) {
        super(plugin.getDataFolder(), "worlds");
        this.plugin = plugin;
        load();
    }

    @Override
    public void onLoad() {

        // Loads worlds from main folder
        String[] worldsFromFolder = plugin.getServer().getWorldContainer().list((file, name) -> {
            File folder = new File(file, name);
            return folder.isDirectory() && new File(folder, "level.dat").isFile();
        });

        if (worldsFromFolder != null) {
            plugin.log("Worlds from folder [" + String.join(", ", worldsFromFolder) + "]");
            worlds.addAll(Arrays.asList(worldsFromFolder));
        } else {
            plugin.log(ChatColor.RED + "ERROR: There is no world.");
        }

        for (String worldName : worlds) {
            WorldCreator wc = new WorldCreator(worldName);
            wc.createWorld();
            plugin.log(ChatColor.GREEN + "World " + worldName + " loaded.");
        }

        for (World world : plugin.getServer().getWorlds()) {
            worlds.add(world.getName());
            flags.put(world.getName(), new HashMap<>());
        }
        plugin.log(ChatColor.GREEN + "Loaded " + worlds.size() + " worlds successfully.");


        // Loads worlds flags
        Set<String> configWorlds = config.getValues(false).keySet();
        for (String worldName : configWorlds) {
            if (!worlds.contains(worldName)) {
                plugin.log(ChatColor.RED + "Error loading config world " + worldName + ": this world not exists.");
                continue;
            }
            flags.put(worldName, new HashMap<>());
            Set<String> worldFlags = config.getConfigurationSection(worldName + ".flags").getValues(false).keySet();
            for (String flagName : worldFlags) {
                String flagState = config.getString(worldName + ".flags." + flagName);
                if (!EnumUtils.isValidEnum(Flag.class, flagName.toUpperCase().replaceAll("-", "_"))) {
                    plugin.log(ChatColor.RED + "Error loading worlds: not valid flag " + flagName + " on the world " + worldName);
                    continue;
                }
                if (!EnumUtils.isValidEnum(FlagState.class, flagState.toUpperCase())) {
                    plugin.log(ChatColor.RED + "Error loading worlds: not valid flag state " + flagState + " on the world " + worldName);
                    continue;
                }
                flags.get(worldName).put(Flag.valueOf(flagName.toUpperCase().replaceAll("-", "_")), FlagState.valueOf(flagState.toUpperCase()));
            }

            File file = new File(plugin.getServer().getWorldContainer().getAbsolutePath(), worldName);
            if (!file.exists()) {
                plugin.log(ChatColor.RED + "World '" + ChatColor.YELLOW + worldName + ChatColor.RED + "' can not be loaded." + ChatColor.DARK_RED + "(Folder not exists)");
                plugin.log(ChatColor.RED + "Removing world '" + ChatColor.YELLOW + worldName + ChatColor.RED + "' from the config.");
                config.set(worldName, null);
            }
        }

        // Checking if spawn location is not on a deleted world.
        String spawnWorld = plugin.getConfig().getString("spawn.world");
        if (plugin.getServer().getWorld(spawnWorld) == null) {
            plugin.getLocationManager().resetSpawnLocation();
            plugin.log(ChatColor.RED + "Spawn reseted");
        }
    }

    @Override
    public void beforeSave() {
        plugin.log("Saving worlds...");

        // Remove all the worlds from the config file
        for(String key : config.getKeys(false)) config.set(key, null);

        if (worlds.size() > 0) {
            for (String worldName : worlds) {
                for (Flag flag : flags.get(worldName).keySet()) {
                    config.set(worldName + ".flags." + flag.name().toLowerCase().replaceAll("_", "-"), flags.get(worldName).get(flag).name().toLowerCase());
                }
            }
            try {
                config.save(file);
                plugin.log(ChatColor.GREEN + "" + worlds.size() + " regions saved.");
            } catch (IOException e) {
                plugin.log(ChatColor.RED + "Error saving regions.");
                e.printStackTrace();
            }
            return;
        }
        plugin.log(ChatColor.GRAY + "No worlds to save.");
    }

    @Override
    public void onSave() {

    }
}
