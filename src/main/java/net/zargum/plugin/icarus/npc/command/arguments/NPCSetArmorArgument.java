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

public class NPCSetArmorArgument extends ServerCommandArgument<Icarus> {

    private final NPCManager manager;

    public NPCSetArmorArgument(ServerCommand<Icarus> command) {
        super(command, "setarmor", "Set armor of an npc", "<id>");
        manager = getPlugin().getNpcManager();
        addToTablistCompletion(0, manager.getNPCsId().toArray(new String[0]));
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        String npcId = args[0];
        if (manager.get(npcId) == null) {
            sender.sendMessage(Messages.NPC_NOT_EXISTS.toString(npcId));
            return;
        }

        Player player = (Player) sender;
        ItemStack[] armor = player.getInventory().getArmorContents();
        int i = 0;
        for (ItemStack item : armor) {
            if (item == null || item.getType().equals(Material.AIR)) armor[i] = null;
            i++;
        }
        manager.setArmor(npcId, armor);
        player.sendMessage(Messages.NPC_SET_ARMOR.toString(npcId));
        StaffUtil.log(Messages.LOG_NPC_UPDATE.toString(player.getName(), "armor", npcId), player);
    }
}
