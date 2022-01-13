package net.zargum.plugin.icarus.region.listeners;

import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.region.Flag;
import net.zargum.plugin.icarus.region.FlagState;
import net.zargum.plugin.icarus.region.Region;
import net.zargum.plugin.icarus.region.RegionManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.List;

public class RegionFrameListener implements Listener {

    private final RegionManager manager;

    public RegionFrameListener(RegionManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onFrameInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof ItemFrame) {
            Player player = event.getPlayer();
            Entity frame = event.getRightClicked();
            if (manager.hasLogsEnabled(player)) {
                List<Region> regionsFromFrameLocation = manager.getRegionsFromLocation(frame.getLocation());
                player.sendMessage(Messages.REGION_LOG.toString("frame-interact", manager.getRegionsFormatted(regionsFromFrameLocation)));
            }
            if (player.hasPermission("region.bypass") && player.getGameMode().equals(GameMode.CREATIVE)) return;
            if (manager.getFinalFlagState(frame.getLocation(), Flag.FRAME_INTERACT) == FlagState.DENY) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onFrameDestroy(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof ItemFrame) {
            Entity frame = event.getEntity();
            Entity damager = event.getDamager();
            if (damager instanceof Player) {
                Player player = (Player) damager;
                if (manager.hasLogsEnabled(player)) {
                    List<Region> regionsFromFrameLocation = manager.getRegionsFromLocation(frame.getLocation());
                    player.sendMessage(Messages.REGION_LOG.toString("frame-destroy", manager.getRegionsFormatted(regionsFromFrameLocation)));
                }
                if (player.hasPermission("region.bypass") && player.getGameMode().equals(GameMode.CREATIVE)) return;
            }
            if (manager.getFinalFlagState(frame.getLocation(), Flag.FRAME_DESTROY) == FlagState.DENY) {
                event.setCancelled(true);
            }
        }
    }
}
