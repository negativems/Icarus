package net.zargum.plugin.icarus.region.listeners;

import net.zargum.plugin.icarus.region.Flag;
import net.zargum.plugin.icarus.region.FlagState;
import net.zargum.plugin.icarus.region.RegionManager;
import org.bukkit.Location;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class RegionEntitySpawnListener implements Listener {

    private final RegionManager manager;

    public RegionEntitySpawnListener(RegionManager regionManager) {
        this.manager = regionManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntitySpawn(EntitySpawnEvent event) {
        Location location = event.getLocation();
        if (event.getEntity() instanceof Animals && manager.getFinalFlagState(location, Flag.SPAWN_ANIMALS) == FlagState.DENY) {
            event.setCancelled(true);
            return;
        }
        if (event.getEntity() instanceof Monster && manager.getFinalFlagState(location, Flag.SPAWN_MONSTERS) == FlagState.DENY) {
            event.setCancelled(true);
        }
    }
}
