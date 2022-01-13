package net.zargum.plugin.icarus.region.listeners;

import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.region.Flag;
import net.zargum.plugin.icarus.region.FlagState;
import net.zargum.plugin.icarus.region.Region;
import net.zargum.plugin.icarus.region.RegionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class RegionFallDamageListener implements Listener {

    private final RegionManager manager;

    public RegionFallDamageListener(RegionManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onGetDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return;
            Player player = (Player) event.getEntity();
            if (manager.hasLogsEnabled(player)) {
                List<Region> regionsFromFrameLocation = manager.getRegionsFromLocation(player.getLocation());
                player.sendMessage(Messages.REGION_LOG.toString("fall-damage", manager.getRegionsFormatted(regionsFromFrameLocation)));
            }
            if (manager.getFinalFlagState(player.getLocation(),Flag.FALL_DAMAGE) == FlagState.DENY) {
                event.setCancelled(true);
            }
        }
    }
}
