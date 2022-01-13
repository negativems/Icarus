package net.zargum.plugin.icarus.hologram;

import lombok.Getter;
import net.zargum.plugin.icarus.Icarus;
import net.zargum.zlib.hologram.Hologram;
import net.zargum.zlib.hologram.HologramAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class HologramManager extends HologramAPI<Icarus> {

    private final Icarus plugin;
    private final HologramConfig config;
    private final HologramTask task;

    public HologramManager(Icarus plugin) {
        super(plugin);
        this.plugin = plugin;
        this.config = new HologramConfig(this);
        task = new HologramTask(this);
    }

    public List<String> getHologramsId() {
        return new ArrayList<>(hologramsMap.keySet());
    }

    public Map<String, Hologram> getHolograms() {
        return hologramsMap;
    }

    public List<Hologram> getHologramList() {
        return new ArrayList<>(hologramsMap.values());
    }

    public Hologram getHologram(String hologramId) {
        return hologramsMap.get(hologramId);
    }

    public void addHologram(Hologram hologram) {
        hologramsMap.put(hologram.getId(), hologram);
    }

    public String[] getHologramsIdsByDistance(Location location, double distance) {
        List<String> result = new ArrayList<>();
        for (Hologram hologram : hologramsMap.values()) {
            if (hologram.getLocation().distance(location) <= distance) {
                result.add(hologram.getId());
            }
        }
        return result.toArray(new String[0]);
    }

    public List<Hologram> getAutoUpdatedHolograms() {
        List<Hologram> holograms = new ArrayList<>();
        for (Hologram hologram : getHologramList()) {
            if (hologram.isAutoUpdate()) {
                holograms.add(hologram);
            }
        }
        return holograms;
    }
}
