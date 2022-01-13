package net.zargum.plugin.icarus.npc.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.npc.NPCManager;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import net.zargum.zlib.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class NPCInfoArgument extends ServerCommandArgument<Icarus> {

    private final NPCManager manager;

    public NPCInfoArgument(ServerCommand<Icarus> command) {
        super(command, "info", "Shows info of an npc", "<id>", "i");
        manager = plugin.getNpcManager();
        addToTablistCompletion(0, manager.getNPCsId().toArray(new String[0]));
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        String npcId = args[0];
        if (manager.get(npcId) == null) {
            sender.sendMessage(Messages.NPC_NOT_EXISTS.toString(npcId));
            return;
        }
        NPC npc = manager.get(npcId);
        String world = npc.getLocation().getWorld().getName();
        String x = npc.getLocation().getBlockX()+"";
        String y = npc.getLocation().getBlockY()+"";
        String z = npc.getLocation().getBlockZ()+"";
        String selfskin = npc.isSelfSkin()+"";
        String handItem = "none";
        if (npc.getItemInHand() != null) {
            handItem = npc.getItemInHand().getType().name();
            handItem = handItem.toLowerCase().replaceAll("_", " ");
        }
        String nametag = npc.isHideName() + "";
        String tablisted = npc.isTablisted() + "";
        String holograms = ChatColor.RED + "No holograms";
        if (npc.getHologram() != null) {
            StringBuilder hologramsBuilder = new StringBuilder("\n");
            int i = 1;
            for (String line : npc.getHologram().getLines()) {
                hologramsBuilder.append(i).append(". ").append(line).append("\n");
                i++;
            }
            holograms = hologramsBuilder.toString();
        }
        sender.sendMessage(Messages.NPC_INFO.toString(npcId, world, x, y, z, selfskin, handItem, nametag, tablisted, holograms));
    }
}
