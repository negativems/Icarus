package net.zargum.plugin.icarus.npc.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.npc.NPCManager;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCSetSkinArgument extends ServerCommandArgument<Icarus> {

    private final NPCManager manager;

    public NPCSetSkinArgument(ServerCommand<Icarus> command) {
        super(command, "setskin", "Update skin of an npc", "<id> <username/#self#>");
        manager = Icarus.getInstance().getNpcManager();
        addToTablistCompletion(0, manager.getNPCsId().toArray(new String[0]));
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        String npcId = args[0];
        if (manager.get(npcId) == null) {
            sender.sendMessage(Messages.NPC_NOT_EXISTS.toString(npcId));
            return;
        }

        String skin = args[1];
        manager.setSkin(npcId, skin);
        sender.sendMessage(Messages.NPC_SET_SKIN.toString(skin, npcId));
        StaffUtil.log(Messages.LOG_NPC_UPDATE.toString(sender.getName(), "skin", npcId), (sender instanceof Player) ? (Player) sender : null);
    }
}
