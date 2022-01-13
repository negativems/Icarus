package net.zargum.plugin.icarus.npc.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.npc.NPCManager;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NPCSetHandArgument extends ServerCommandArgument<Icarus> {

    private final NPCManager manager;

    public NPCSetHandArgument(ServerCommand<Icarus> command) {
        super(command, "sethand", "Put item on NPC's hand", "<id>");
        manager = plugin.getNpcManager();
        addToTablistCompletion(0, manager.getNPCsId().toArray(new String[0]));
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        String npcId = args[0];
        Player player = (Player) sender;
        ItemStack item = player.getItemInHand();
        if (item == null || item.getType() == Material.AIR) {
            manager.setHand(npcId, null);
            player.sendMessage(Messages.NPC_DELETED_HAND.toString(npcId));
            StaffUtil.log(Messages.LOG_NPC_HAND_DELETE.toString(player.getName(), npcId), player);
            return;
        }
        manager.setHand(npcId, item);
        String itemName = item.getType().name();
        itemName = itemName.toLowerCase().replaceAll("_", " ");
        player.sendMessage(Messages.NPC_SET_HAND.toString(itemName, npcId));
        StaffUtil.log(Messages.LOG_NPC_UPDATE.toString(player.getName(), "hand item", npcId), player);
    }
}
