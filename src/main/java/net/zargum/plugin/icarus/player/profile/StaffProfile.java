package net.zargum.plugin.icarus.player.profile;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;
import lombok.Getter;
import lombok.Setter;
import net.zargum.plugin.icarus.Icarus;
import net.zargum.zlib.profile.Profile;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter @Setter
public class StaffProfile extends Profile {

    private final Icarus plugin = Icarus.getInstance();

    private UUID uniqueId;
    private String username;
    private boolean vanished;
    private boolean logs;

    StaffProfile(Document document) {
        this.uniqueId = (UUID) document.get("_id");
        this.username = document.getString("username");
        this.vanished = document.getBoolean("vanished");
        this.logs = document.getBoolean("logs");
    }

    StaffProfile(UUID uuid) {
        this.uniqueId = uuid;
    }

    @Override
    public Document serialize() {
        return
                new Document("_id", uniqueId)
                        .append("username", username)
                        .append("vanished", vanished)
                        .append("logs", logs);
    }

    @Override
    public void save() {
        MongoCollection<Document> collection = plugin.getStaffProfilesCollection();
        collection.replaceOne(Filters.eq(uniqueId), serialize(), new ReplaceOptions().upsert(true));
    }

    @Override
    public void saveAsync() {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, this::save);
    }

    @Override
    public Player asPlayer() {
        return Bukkit.getPlayer(uniqueId);
    }

}
