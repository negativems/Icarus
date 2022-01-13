package net.zargum.plugin.icarus.npc;

import com.mojang.authlib.properties.Property;
import lombok.Getter;
import net.zargum.plugin.icarus.Icarus;
import net.zargum.zlib.hologram.Hologram;
import net.zargum.zlib.npc.NPC;
import net.zargum.zlib.npc.NPCAPI;
import net.zargum.zlib.skin.SkinUtil;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
public class NPCManager extends NPCAPI<Icarus> {

    private final Icarus plugin;
    private final NPCConfig config;

    public NPCManager(Icarus plugin) {
        super(plugin);
        this.plugin = plugin;
        this.config = new NPCConfig(this);
    }

    public List<String> getNPCsId() {
        return new ArrayList<>(NPCs.keySet());
    }

    public List<NPC> getAllNPCs() {
        return new ArrayList<>(NPCs.values());
    }

    public NPC get(String npcName) {
        return NPCs.get(npcName);
    }

    public void delete(String id) {
        NPC npc = NPCs.get(id);
        if (npc == null) return;
        npc.delete();
        NPCs.remove(id);
    }

    public void create(NPC npc) {
        NPCs.put(npc.getId(), npc);
    }

    public void addLine(String id, String text) {
        NPC npc = NPCs.get(id);
        if (npc == null) throw new NullPointerException("Npc not exists");
        Hologram hologram = npc.getHologram();
        if (hologram == null) {
            hologram = new Hologram(plugin, "NPC-" + id, npc.getLocation());
            hologram.create();
            npc.attachHologram(hologram);
            hologram.show();
        }

        hologram.addLine(text);
        hologram.update();
    }

    public void removeLine(String id, int linePosition) {
        Hologram hologram = NPCs.get(id).getHologram();
        if (hologram == null) throw new NullPointerException("Npc not exists");
        if (hologram.getLines().size() == 1) {
            NPCs.get(id).attachHologram(null);
            hologram.delete();
            System.out.println("npc hologram deleted");
            return;
        }
        hologram.removeLine(linePosition);
        hologram.update();
    }

    public void setHologramLine(String id, int linePosition, String line) {
        NPC npc = NPCs.get(id);
        if (npc == null) throw new NullPointerException("Npc not exists");
        if (npc.getHologram() == null) throw new NullPointerException("Npc not exists");
        npc.getHologram().setLine(linePosition, line);
        npc.getHologram().update();
    }

    public void setSkin(String ID, String skinName) {
        NPC npc = NPCs.get(ID);
        if (skinName.equals("#self#")) {
            npc.setSkinProperty(null);
            npc.setSelfSkin(true);
        }else{
            npc.setSelfSkin(false);
            Property property = SkinUtil.getProperty(skinName);
            npc.setSkinProperty(property);
        }
        npc.update();
    }

    public void setHand(String ID, ItemStack item) {
        NPC npc = NPCs.get(ID);
        npc.setItemInHand(item);
        npc.update();
    }

    public void setArmor(String ID, ItemStack[] items) {
        NPC npc = NPCs.get(ID);
        npc.setArmor(items);
        npc.update();
    }

    public void setLocation(String ID, Location location) {
        NPC npc = NPCs.get(ID);
        npc.updateLocation(location);
        npc.update();
    }

    public void setDisplayName(String ID, String displayname) {
        NPC npc = NPCs.get(ID);
        npc.setDisplayName(displayname);
        npc.update();
    }
}
