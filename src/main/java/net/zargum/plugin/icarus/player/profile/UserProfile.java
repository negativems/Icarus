package net.zargum.plugin.icarus.player.profile;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import net.zargum.plugin.icarus.Icarus;
import net.zargum.zlib.profile.Profile;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter @Getter
public class UserProfile extends Profile {
    private Icarus plugin = Icarus.getInstance();

    private UUID uniqueId, replyPlayer;
    private String username, ipAddress;
    private List<UUID> ignoredPlayers = new ArrayList<>();
    private long firstJoin, voted;
    private int credits = 0;

    UserProfile(Document document) {
        uniqueId = (UUID) document.get("_id");
        username = document.getString("username");
        ignoredPlayers = document.getList("ignored", UUID.class);
        ipAddress = document.getString("ip");
        firstJoin = document.getLong("firstJoin");
        credits = document.getInteger("credits");
        voted = document.getLong("voted");
    }

    UserProfile(UUID uuid) {
        firstJoin = System.currentTimeMillis();
        uniqueId = uuid;
    }

    public void vote() {
        this.voted = System.currentTimeMillis();

        // TODO: Rewards
    }

    public void subtractCredits(int amount) {
        credits = credits - amount;
    }

    @Override
    public Player asPlayer() {
        return Bukkit.getPlayer(uniqueId);
    }

    @Override
    public Document serialize() {
        return new Document("_id", uniqueId)
                .append("username", username)
                .append("ignored", ignoredPlayers)
                .append("ip", ipAddress)
                .append("firstJoin", firstJoin)
                .append("voted", voted)
                .append("credits", credits)
                .append("replyPlayer", replyPlayer == null ? null : replyPlayer.toString());
    }

    @Override
    public void save() {
        // TODO: Save profile through redis to prevent multi-hub errors.
        MongoCollection<Document> collection = plugin.getProfilesCollection();
        collection.replaceOne(Filters.eq(uniqueId), serialize(), new ReplaceOptions().upsert(true));
    }

    @Override
    public void saveAsync() {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, this::save);
    }
}
