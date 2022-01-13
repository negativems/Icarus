package net.zargum.plugin.icarus.player.profile;

import com.mongodb.client.model.Filters;
import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class ProfileListener implements Listener {

    private final Icarus plugin;
    private final ProfileManager profileManager;

    public ProfileListener(Icarus plugin) {
        this.plugin = plugin;
        profileManager = plugin.getProfileManager();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void asyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        UserProfile profile;
        UUID uniqueId = event.getUniqueId();
        String username = event.getName();
        Document document = plugin.getProfilesCollection().find(new Document("_id", uniqueId)).first();

        profile = document != null ? new UserProfile(document) : new UserProfile(uniqueId);
        profile.setIpAddress(event.getAddress().getHostAddress());

        if (profile.getUsername() == null || !profile.getUsername().equals(username)) {
            profile.setUsername(username);
            profileManager.save(profile);
        }

        profileManager.getUserProfiles().put(uniqueId, profile);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void playerLogin(PlayerLoginEvent event) {
        UserProfile profile = profileManager.getProfile(event.getPlayer().getUniqueId());
        if (profile == null) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Profile data could not be loaded. Please try again later.");
        }

        // Staff Profile
        Player player = event.getPlayer();
        if (player.hasPermission("staff")) {
            StaffProfile staffProfile;
            UUID uniqueId = player.getUniqueId();
            String username = player.getName();
            Document document = plugin.getStaffProfilesCollection().find(Filters.eq(uniqueId)).first();

            staffProfile = document != null ? new StaffProfile(document) : new StaffProfile(uniqueId);

            if (staffProfile.getUsername() == null || !staffProfile.getUsername().equals(username)) {
                staffProfile.setUsername(username);
                staffProfile.save();
            }

            profileManager.getStaffProfiles().put(uniqueId, staffProfile);
            if (staffProfile.isVanished()) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (!players.hasPermission("vanish.bypass")) {
                        players.hidePlayer(player);
                    }
                }
                player.sendMessage(Messages.VANISH_ENABLED.toString());
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        UserProfile profile = profileManager.getUserProfiles().remove(event.getPlayer().getUniqueId());
        if (profile != null) profile.saveAsync();

        if (event.getPlayer().hasPermission("staff")) {
            if (!profileManager.hasStaffProfile(event.getPlayer().getUniqueId())) return;
            StaffProfile staffProfile = profileManager.getStaffProfile(event.getPlayer().getUniqueId());
            staffProfile.saveAsync();
        }
    }

}
