package net.zargum.plugin.icarus.player;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.events.PlayerCooldownTeleport;
import net.zargum.zlib.events.PlayerFirstJoinEvent;
import net.zargum.zlib.events.TeleportCancelledEvent;
import net.zargum.zlib.permissions.PermissionsManager;
import net.zargum.zlib.sounds.SoundUtil;
import net.zargum.zlib.sounds.Sounds;
import net.zargum.zlib.tab.Tablist;
import net.zargum.zlib.utils.ColorUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {

    private final Icarus plugin;

    public PlayerListener(Icarus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFirstJoin(PlayerFirstJoinEvent event) {
        event.setSpawnLocation(plugin.getLocationManager().getSpawn());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();

        // Auto Teleport
        if (plugin.getLocationManager().getConfig().getBoolean("teleport-on-join")) {
            Location spawnLocation = plugin.getLocationManager().getSpawn();
            if (spawnLocation == null) {
                plugin.log(ChatColor.RED + "Error: spawn is null.");
            } else {
                player.teleport(spawnLocation);
            }
        }

        // Staff connected
        if (player.hasPermission("staff")) {
            StaffUtil.log(Messages.STAFF_CONNECTED.toString(player.getName()), player);
        }

        // Tablist
        String header = "\n" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Zargum Network" + "\n" + ChatColor.GRAY + "[" + plugin.getServer().getServerName() + "]" + "\n";
        String footer = "\n" + ChatColor.DARK_PURPLE + "play.zargum.net" + "\n";
        new Tablist(3).setHeader(header).setFooter(footer).sendTo(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        Player player = event.getPlayer();
        if (player.hasPermission("staff")) {
            StaffUtil.log(Messages.STAFF_DISCONNECTED.toString(player.getName()), player);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String format = PermissionsManager.getUserPrefix(player) + "%s"  + PermissionsManager.getUserSuffix(player) + ChatColor.GRAY + ": " + ChatColor.WHITE + "%s";
        format = ChatColor.translateAlternateColorCodes(format);
        if (player.hasPermission("chat.color")) {
            event.setMessage(ColorUtils.translate(event.getMessage()));
            event.setFormat(format);
            return;
        }
        event.setFormat(format);

        // Tag Sound TODO: settings menu
//        for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
//            if (event.getMessage().contains(otherPlayer.getName())) {
//                otherPlayer.playSound(otherPlayer.getLocation(), Sound.valueOf("mob.guardian.flop"), 5F, 10F);
//            }
//        }
    }

    @EventHandler
    public void onCooldownTeleport(PlayerCooldownTeleport event) {
        Player player = event.getPlayer();
        SoundUtil.play(player, Sounds.SUCCESS);
    }

    @EventHandler
    public void onTeleportCancelled(TeleportCancelledEvent event) {
        Player player = event.getPlayer();
        SoundUtil.play(player, Sounds.ERROR);
        player.sendMessage(Messages.TELEPORT_CANCELLED.toString());
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        event.setRespawnLocation(plugin.getLocationManager().getSpawn());

        boolean autoRespawn = plugin.getConfig().getBoolean("auto-respawn");
        if (autoRespawn) {
            Player player = event.getPlayer();
            plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> player.spigot().respawn(), 10L);
        }
    }

}