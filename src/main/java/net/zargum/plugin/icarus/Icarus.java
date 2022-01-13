package net.zargum.plugin.icarus;

import com.mongodb.client.MongoCollection;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import lombok.Getter;
import net.zargum.plugin.icarus.command.manager.CommandManager;
import net.zargum.plugin.icarus.hologram.HologramManager;
import net.zargum.plugin.icarus.managers.LocationManager;
import net.zargum.plugin.icarus.managers.ServerManager;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.npc.NPCManager;
import net.zargum.plugin.icarus.player.PlayerListener;
import net.zargum.plugin.icarus.player.profile.ProfileListener;
import net.zargum.plugin.icarus.player.profile.ProfileManager;
import net.zargum.plugin.icarus.region.RegionManager;
import net.zargum.plugin.icarus.region.managers.FlagManager;
import net.zargum.plugin.icarus.tasks.MainTask;
import net.zargum.plugin.icarus.world.WorldManager;
import net.zargum.zlib.database.MongoConnection;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Icarus extends JavaPlugin {

    @Getter private static Icarus instance;
    private MongoCollection<Document> profilesCollection;
    private MongoCollection<Document> staffProfilesCollection;
    private MongoCollection<Document> regionsCollection;

    private WorldEditPlugin worldEdit;

    private ProfileManager profileManager;
    private CommandManager commandManager;
    private LocationManager locationManager;
    private ServerManager serverManager;
    private FlagManager flagManager;
    private WorldManager worldManager;
    private NPCManager npcManager;
    private HologramManager hologramManager;
    private RegionManager regionManager;

    private MainTask mainTask;

    @Override
    public void onEnable() {
        instance = this;

        // Load config
        saveDefaultConfig();

        // Database connection
        log("Connecting to mongo database...");
        ConfigurationSection mongoConfig = getConfig().getConfigurationSection("mongodb");
        MongoConnection mongoConnection = new MongoConnection(this, mongoConfig);
        this.profilesCollection = mongoConnection.getDatabase().getCollection("UserProfiles");
        this.staffProfilesCollection = mongoConnection.getDatabase().getCollection("StaffProfiles");
        this.regionsCollection = mongoConnection.getDatabase().getCollection("regions");

        // Load managers
        log("Loading constructors...");
        profileManager = new ProfileManager(this);
        serverManager = new ServerManager(this);
        worldManager = new WorldManager(this);
        regionManager = new RegionManager(this);
        flagManager = new FlagManager(this);
        locationManager = new LocationManager(this);
        npcManager = new NPCManager(this);
        hologramManager = new HologramManager(this);
        mainTask = new MainTask(this);
        commandManager = new CommandManager(this);

        // Loading APIs
        worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        if (worldEdit == null) {
            log(ChatColor.RED + "Error loading worldedit!");
            log(ChatColor.RED + "Disabling Icarus...");
            getServer().getPluginManager().disablePlugin(this);
        }

        // Start tasks
        log("Starting tasks...");
        mainTask.runTaskTimerAsynchronously(this, 0L, 20L);

        // Load Commands and Listeners
        log("Loading commands and listeners...");
        loadListeners();

        this.log(ChatColor.GREEN + getDescription().getName() + " has been enabled.");
    }

    @Override
    public void onDisable() {
        profileManager.saveAll();
        regionManager.getHandler().save();
        worldManager.getHandler().save();
        locationManager.saveSpawn();
        npcManager.getConfig().save();
        hologramManager.getConfig().save();

        log("Saving configuration...");
        reloadConfig();
        saveConfig();

        this.log(ChatColor.RED + getDescription().getName() + " has been disabled.");
    }

    private void loadListeners() {
        getServer().getPluginManager().registerEvents(new ProfileListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    public void log(String s) {
        Bukkit.getConsoleSender().sendMessage(Messages.PREFIX.toString() + ChatColor.YELLOW + s);
    }
}
