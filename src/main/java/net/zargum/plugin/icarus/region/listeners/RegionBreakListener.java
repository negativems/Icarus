package net.zargum.plugin.icarus.region.listeners;

import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.region.Flag;
import net.zargum.plugin.icarus.region.FlagState;
import net.zargum.plugin.icarus.region.Region;
import net.zargum.plugin.icarus.region.RegionManager;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

public class RegionBreakListener implements Listener {

    private final RegionManager manager;

    public RegionBreakListener(RegionManager regionManager) {
        this.manager = regionManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (player.hasPermission("region.bypass") && player.getGameMode().equals(GameMode.CREATIVE)) return;
        if (manager.hasLogsEnabled(player)) {
            List<Region> regionsFromFrameLocation = manager.getRegionsFromLocation(block.getLocation());
            player.sendMessage(Messages.REGION_LOG.toString("break", manager.getRegionsFormatted(regionsFromFrameLocation)));
        }
        if (manager.getFinalFlagState(block.getLocation(),Flag.BREAK) == FlagState.DENY) {
            event.setCancelled(true);
        }
    }
}
