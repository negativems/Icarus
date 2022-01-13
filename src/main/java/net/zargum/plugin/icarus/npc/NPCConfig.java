package net.zargum.plugin.icarus.npc;

import com.mojang.authlib.properties.Property;
import net.zargum.plugin.icarus.Icarus;
import net.zargum.zlib.configuration.ConfigYML;
import net.zargum.zlib.hologram.Hologram;
import net.zargum.zlib.npc.NPC;
import net.zargum.zlib.utils.LocationUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NPCConfig extends ConfigYML {

    private final Icarus plugin;
    private final NPCManager manager;

    public NPCConfig(NPCManager manager) {
        super(manager.getPlugin().getDataFolder(), "npcs");
        this.plugin = manager.getPlugin();
        this.manager = manager;
        load();
    }

    @Override
    public void onLoad() {
        plugin.log("Loading NPCs...");
        Set<String> npcs = config.getValues(false).keySet();
        int count = npcs.size();
        for (String id : npcs) {
            Location location = LocationUtils.getFromConfig(config, id + ".location");
            NPC npc = new NPC(plugin, id, location);
            npc.removeFromTablist();
            if (isNameHiddenFromConfig(id)) npc.hideName();
            if (getSelfSkinFromConfig(id)) npc.setSelfSkin(getSelfSkinFromConfig(id));
            if (getSkinProperties(id) != null) npc.setSkinProperty(getSkinProperties(id));
            if (getHologramsFromConfig(id) != null) {
                Hologram hologram = new Hologram(plugin, "NPC-" + id, location.clone().add(0, 1.975 - 0.9875, 0));
                hologram.setLines(getHologramsFromConfig(id));
                hologram.create();
                npc.attachHologram(hologram);
            }
            if (getItemInHandFromConfig(id) != null) npc.setItemInHand(getItemInHandFromConfig(id));
            ItemStack[] armor = new ItemStack[]{null, null, null, null};
            if (getHelmetFromConfig(id) != null) armor[3] = getHelmetFromConfig(id);
            if (getChestplateFromConfig(id) != null) armor[2] = getChestplateFromConfig(id);
            if (getLeggingsFromConfig(id) != null) armor[1] = getLeggingsFromConfig(id);
            if (getBootsFromConfig(id) != null) armor[0] = getBootsFromConfig(id);
            npc.setArmor(armor);
            npc.create();
            manager.getNPCs().put(id, npc);
        }
        if (count > 0) {
            plugin.log(ChatColor.GREEN + "Loaded " + count + " NPCs.");
            return;
        }
        plugin.log("No NPCs to load.");
    }

    @Override
    public void beforeSave() {
        plugin.log("Saving NPCs...");

        // Remove all the NPCs from the config file
        for(String key : config.getKeys(false)) config.set(key, null);

        // Set all the npcs from the hashmap to the config file
        for (NPC npc : manager.getNPCs().values()) {
            // Skin
            if (npc.getSkinProperty() != null && !npc.isSelfSkin()) {
                config.set(npc.getId() + ".skin_value", npc.getSkinProperty().getValue());
                config.set(npc.getId() + ".skin_signature", npc.getSkinProperty().getSignature());
            } else if (npc.isSelfSkin()) {
                config.set(npc.getId() + ".skin_value", null);
                config.set(npc.getId() + ".skin_signature", null);
                config.set(npc.getId() + ".selfskin", npc.isSelfSkin());
            }

            // Holograms
            if (npc.getHologram() != null && npc.getHologram().getLines().size() > 0) {
                List<String> lines = new ArrayList<>();
                for (String line : npc.getHologram().getLines()) lines.add(line.replaceAll("§", "&"));
                config.set(npc.getId() + ".holograms", lines);
            }

            // Hand
            if (npc.getItemInHand() != null) {
                config.set(npc.getId() + ".hand", npc.getItemInHand().serialize());
            }

            // Armor
            if (npc.getArmor() != null) {
                if (npc.getArmor()[3] != null) {
                    config.set(npc.getId() + ".helmet", npc.getArmor()[3].serialize());
                }
                if (npc.getArmor()[2] != null) {
                    config.set(npc.getId() + ".chestplate", npc.getArmor()[2].serialize());
                }
                if (npc.getArmor()[1] != null) {
                    config.set(npc.getId() + ".leggings", npc.getArmor()[1].serialize());
                }
                if (npc.getArmor()[0] != null) {
                    config.set(npc.getId() + ".boots", npc.getArmor()[0].serialize());
                }
            }

            // Hidden name
            if (npc.isHideName()) {
                config.set(npc.getId() + ".name-hidden", true);
            }

            // Location
            config.set(npc.getId() + ".location.x", npc.getLocation().getX());
            config.set(npc.getId() + ".location.y", npc.getLocation().getY());
            config.set(npc.getId() + ".location.z", npc.getLocation().getZ());
            config.set(npc.getId() + ".location.yaw", npc.getLocation().getYaw());
            config.set(npc.getId() + ".location.pitch", npc.getLocation().getPitch());
            config.set(npc.getId() + ".location.world", npc.getLocation().getWorld().getName());

            try {
                config.save(file);
                plugin.log(ChatColor.GREEN + "" + manager.getNPCs().size() + " npcs saved.");
            } catch (IOException e) {
                plugin.log(ChatColor.RED + "Error saving npcs.");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSave() {

    }

    public boolean isNameHiddenFromConfig(String id) {
        return config.getBoolean(id + ".name-hidden", false);
    }

    private ItemStack getItemInHandFromConfig(String ID) {
        if (!config.contains(ID + ".hand")) return null;
        return ItemStack.deserialize(config.getConfigurationSection(ID + ".hand").getValues(false));
    }

    private ItemStack getHelmetFromConfig(String ID) {
        if (!config.contains(ID + ".helmet")) return null;
        return ItemStack.deserialize(config.getConfigurationSection(ID + ".helmet").getValues(false));
    }

    private ItemStack getChestplateFromConfig(String ID) {
        if (!config.contains(ID + ".chestplate")) return null;
        return ItemStack.deserialize(config.getConfigurationSection(ID + ".chestplate").getValues(false));
    }

    private ItemStack getLeggingsFromConfig(String ID) {
        if (!config.contains(ID + ".leggings")) return null;
        return ItemStack.deserialize(config.getConfigurationSection(ID + ".leggings").getValues(false));
    }

    private ItemStack getBootsFromConfig(String ID) {
        if (!config.contains(ID + ".boots")) return null;
        return ItemStack.deserialize(config.getConfigurationSection(ID + ".boots").getValues(false));
    }

    private List<String> getHologramsFromConfig(String ID) {
        if (!config.contains(ID + ".holograms")) return null;
        List<String> holograms = config.getStringList(ID + ".holograms");
        List<String> result = new ArrayList<>();
        for (String line : holograms) result.add(ChatColor.translateAlternateColorCodes('&', line));
        return result;
    }

    private Property getSkinProperties(String ID) {
        if (config.contains(ID + ".selfskin")) return null;
        if (!config.contains(ID + ".skin_value")) return null;
        if (!config.contains(ID + ".skin_signature")) return null;
        String value = config.getString(ID + ".skin_value");
        String signature = config.getString(ID + ".skin_signature");
        return new Property("textures", value, signature);
    }

    private boolean getSelfSkinFromConfig(String ID) {
        if (!config.contains(ID + ".selfskin")) return false;
        return config.getBoolean(ID + ".selfskin");
    }

}
