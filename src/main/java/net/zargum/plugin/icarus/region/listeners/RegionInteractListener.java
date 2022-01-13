package net.zargum.plugin.icarus.region.listeners;

import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.region.Flag;
import net.zargum.plugin.icarus.region.FlagState;
import net.zargum.plugin.icarus.region.Region;
import net.zargum.plugin.icarus.region.RegionManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class RegionInteractListener implements Listener {

    private final RegionManager manager;

    public RegionInteractListener(RegionManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("region.bypass") && player.getGameMode().equals(GameMode.CREATIVE)) return;
        if (manager.hasLogsEnabled(player)) {
            List<Region> regionsFromFrameLocation = manager.getRegionsFromLocation(player.getLocation());
            player.sendMessage(Messages.REGION_LOG.toString("interact", manager.getRegionsFormatted(regionsFromFrameLocation)));
        }
        if (manager.getFinalFlagState(event.getClickedBlock().getLocation(), Flag.INTERACT) == FlagState.DENY) {
            event.setCancelled(true);
        }
    }
}
