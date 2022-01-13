package net.zargum.plugin.icarus.player.profile;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import net.zargum.plugin.icarus.Icarus;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileManager {

    private final Icarus plugin;
    @Getter private final Map<UUID, UserProfile> userProfiles;
    @Getter private final Map<UUID, StaffProfile> staffProfiles;

    public ProfileManager(Icarus plugin) {
        this.plugin = plugin;
        userProfiles = new HashMap<>();
        staffProfiles = new HashMap<>();
    }

    public boolean hasStaffProfile(UUID uniqueId) {
        return staffProfiles.containsKey(uniqueId);
    }

    public UserProfile getProfile(UUID uuid) {
        return userProfiles.get(uuid);
    }

    public StaffProfile getStaffProfile(UUID uuid) {
        return staffProfiles.get(uuid);
    }

    public void save(UserProfile profile) {
        UUID uniqueId = profile.getUniqueId();

        // TODO: Save profile throught redis to prevent multi-hub errors.
        MongoCollection<Document> collection = plugin.getProfilesCollection();
        collection.replaceOne(Filters.eq(uniqueId), profile.serialize(), new ReplaceOptions().upsert(true));
    }

    public void saveAll() {
        plugin.log("Saving all profiles...");
        userProfiles.forEach((uuid, userProfile) -> save(userProfile));
    }
}
