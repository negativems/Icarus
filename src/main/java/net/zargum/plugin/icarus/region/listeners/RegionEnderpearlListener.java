package net.zargum.plugin.icarus.region.listeners;

import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.region.Flag;
import net.zargum.plugin.icarus.region.FlagState;
import net.zargum.plugin.icarus.region.Region;
import net.zargum.plugin.icarus.region.RegionManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class RegionEnderpearlListener implements Listener {

    private final RegionManager manager;

    public RegionEnderpearlListener(RegionManager manager) {
        this.manager = manager;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onThrowEnderpearl(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.hasItem() && event.getItem().getType() == Material.ENDER_PEARL) {
            Player player = event.getPlayer();
            if (manager.hasLogsEnabled(player)) {
                List<Region> regionsFromFrameLocation = manager.getRegionsFromLocation(player.getLocation());
                player.sendMessage(Messages.REGION_LOG.toString("enderpearl", manager.getRegionsFormatted(regionsFromFrameLocation)));
            }
            if (manager.getFinalFlagState(player.getLocation(),Flag.ENDERPEARL) == FlagState.DENY) {
                event.setCancelled(true);
            }
        }
    }
}
