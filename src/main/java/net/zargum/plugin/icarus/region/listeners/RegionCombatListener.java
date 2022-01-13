package net.zargum.plugin.icarus.region.listeners;

import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.region.Flag;
import net.zargum.plugin.icarus.region.FlagState;
import net.zargum.plugin.icarus.region.Region;
import net.zargum.plugin.icarus.region.RegionManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class RegionCombatListener implements Listener {

    private final RegionManager manager;

    public RegionCombatListener(RegionManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player victim = (Player) event.getEntity();
            if (manager.hasLogsEnabled(damager)) {
                List<Region> regionsFromFrameLocation = manager.getRegionsFromLocation(damager.getLocation());
                damager.sendMessage(Messages.REGION_LOG.toString("combat", manager.getRegionsFormatted(regionsFromFrameLocation)));
            }
            if (manager.hasLogsEnabled(victim)) {
                List<Region> regionsFromFrameLocation = manager.getRegionsFromLocation(victim.getLocation());
                victim.sendMessage(Messages.REGION_LOG.toString("combat", manager.getRegionsFormatted(regionsFromFrameLocation)));
            }
            if (manager.getFinalFlagState(damager.getLocation(), Flag.COMBAT) == FlagState.DENY || manager.getFinalFlagState(victim.getLocation(), Flag.COMBAT) == FlagState.DENY) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamageMob(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Animals) || !(event.getEntity() instanceof Monster)) return;
        Player damager = (Player) event.getDamager();
        Entity mob = event.getEntity();
        if (damager.hasPermission("region.bypass") && damager.getGameMode().equals(GameMode.CREATIVE)) return;

        if (mob instanceof Animals) {
            if (manager.hasLogsEnabled(damager)) {
                List<Region> regionsFromFrameLocation = manager.getRegionsFromLocation(damager.getLocation());
                damager.sendMessage(Messages.REGION_LOG.toString("hit-animals", manager.getRegionsFormatted(regionsFromFrameLocation)));
            }
            if (manager.getFinalFlagState(damager.getLocation(), Flag.HIT_ANIMALS) == FlagState.DENY || manager.getFinalFlagState(mob.getLocation(), Flag.HIT_ANIMALS) == FlagState.DENY) {
                event.setCancelled(true);
            }
        }

        if (mob instanceof Monster) {
            if (manager.hasLogsEnabled(damager)) {
                List<Region> regionsFromFrameLocation = manager.getRegionsFromLocation(damager.getLocation());
                damager.sendMessage(Messages.REGION_LOG.toString("hit-monsters", manager.getRegionsFormatted(regionsFromFrameLocation)));
            }
            if (manager.getFinalFlagState(damager.getLocation(), Flag.HIT_MONSTERS) == FlagState.DENY || manager.getFinalFlagState(mob.getLocation(), Flag.HIT_MONSTERS) == FlagState.DENY) {
                event.setCancelled(true);
            }
        }

    }
}
