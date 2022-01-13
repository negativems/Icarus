package net.zargum.plugin.icarus.hologram;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.zlib.configuration.ConfigYML;
import net.zargum.zlib.hologram.Hologram;
import net.zargum.zlib.utils.ColorUtils;
import net.zargum.zlib.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HologramConfig extends ConfigYML {

    private final Icarus plugin;
    private final HologramManager manager;

    public HologramConfig(HologramManager manager) {
        super(manager.getPlugin().getDataFolder(), "holograms");
        this.plugin = manager.getPlugin();
        this.manager = manager;
        load();
    }

    @Override
    public void onLoad() {
        plugin.log("Loading holograms...");
        Set<String> hologramSet = config.getValues(false).keySet();
        int count = hologramSet.size();
        if (count == 0) {
            plugin.log("There is no holograms to load.");
            return;
        }

        for (String hologramId : hologramSet) {
            List<String> lines = config.getStringList(hologramId + ".content");
            Location location = getLocationFromConfig(hologramId);
            Hologram hologram = new Hologram(plugin, hologramId, location.clone());
            hologram.setAutoUpdate(config.contains(hologramId + ".auto-update") && config.getBoolean(hologramId + ".auto-update"));
            hologram.setLines(ColorUtils.translate(lines));
            hologram.create();
            manager.addHologram(hologram);
        }
        plugin.log(ChatColor.GREEN + "Loaded " + count + " holograms.");
    }

    @Override
    public void beforeSave() {
        plugin.log("Saving holograms...");

        // Remove all the holograms from the config file
        for(String key : config.getKeys(false)) config.set(key, null);

        // Set all the holograms from the hashmap to the config file
        List<Hologram> holograms = manager.getHologramList();
        for (Hologram hologram : holograms) {
            List<String> lines = new ArrayList<>();
            for (String line : hologram.getLines()) lines.add(line.replaceAll(ChatColor.COLOR_CHAR + "", "&"));
            String hologramId = hologram.getId();
            LocationUtils.toConfig(config, hologramId + ".location", hologram.getLocation());
            if (hologram.isAutoUpdate()) config.set(hologramId + ".auto-update", true);
            config.set(hologramId + ".content", lines);
        }

        try {
            config.save(file);
            plugin.log(ChatColor.GREEN + "" + holograms.size() + " holograms saved.");
        } catch (IOException e) {
            plugin.log(ChatColor.RED + "Error saving holograms.");
            e.printStackTrace();
        }
    }

    @Override
    public void onSave() {

    }

    private Location getLocationFromConfig(String ID) {
        double x = config.getDouble(ID + ".location.x");
        double y = config.getDouble(ID + ".location.y");
        double z = config.getDouble(ID + ".location.z");
        float yaw = (float) config.getDouble(ID + ".location.yaw");
        float pitch = (float) config.getDouble(ID + ".location.pitch");
        String world = config.getString(ID + ".location.world");
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

}
