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

public class RegionGetDamageListener implements Listener {

    private final RegionManager manager;

    public RegionGetDamageListener(RegionManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onGetDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (manager.hasLogsEnabled(player)) {
            List<Region> regionsFromFrameLocation = manager.getRegionsFromLocation(player.getLocation());
            player.sendMessage(Messages.REGION_LOG.toString("get-damage", manager.getRegionsFormatted(regionsFromFrameLocation)));
        }
        if (manager.getFinalFlagState(player.getLocation(),Flag.GET_DAMAGE) == FlagState.DENY) {
            event.setCancelled(true);
        }
    }
}
