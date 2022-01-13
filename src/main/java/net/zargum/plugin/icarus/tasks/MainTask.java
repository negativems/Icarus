package net.zargum.plugin.icarus.tasks;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.hologram.HologramConfig;
import net.zargum.plugin.icarus.hologram.HologramManager;
import net.zargum.plugin.icarus.npc.NPCManager;
import net.zargum.zlib.hologram.Hologram;
import net.zargum.zlib.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MainTask extends BukkitRunnable {

    private final Icarus plugin;
    private final NPCManager npcManager;
    private final HologramManager hologramManager;

    public MainTask(Icarus plugin) {
        this.plugin = plugin;
        npcManager = plugin.getNpcManager();
        hologramManager = plugin.getHologramManager();
    }

    @Override
    public void run() {
        int i = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                for (NPC npc : npcManager.getAllNPCs()) {
                    if (npc.isSpawnedTo(player) && !npc.isNear(player)) npc.unshow(player);
                    if (!npc.isSpawnedTo(player) && npc.isNear(player)) npc.show(player);
                }
                for (Hologram hologram : hologramManager.getHolograms().values()) {
                    if (hologram.isSpawnedTo(player) && !hologram.isNear(player)) hologram.unshow(player);
                    if (!hologram.isSpawnedTo(player) && hologram.isNear(player)) hologram.show(player);
                }
            }, i * 3L);
            i++;
        }
    }

}
