package net.zargum.plugin.icarus.hologram;

import net.zargum.zlib.hologram.Hologram;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class HologramTask extends BukkitRunnable {

    private final HologramManager manager;

    public HologramTask(HologramManager manager) {
        this.manager = manager;
        runTaskTimerAsynchronously(manager.getPlugin(), 0L, 20L);
    }

    @Override
    public void run() {
        List<Hologram> updateHologramList = manager.getAutoUpdatedHolograms();
        for (Hologram hologram : updateHologramList) {
            hologram.update();
        }

    }
}
