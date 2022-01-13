package net.zargum.plugin.icarus.npc.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.npc.NPCManager;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import net.zargum.zlib.npc.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCHideNameArgument extends ServerCommandArgument<Icarus> {

    private final NPCManager manager;

    public NPCHideNameArgument(ServerCommand<Icarus> command) {
        super(command, "hidename", "Hide name of an npc", "<id>");
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
        if (npc.isHideName()) {
            sender.sendMessage(Messages.NPC_NAME_ALREADY_HIDDEN.toString(npcId));
            return;
        }
        npc.hideName();
        npc.update();
        sender.sendMessage(Messages.NPC_NAME_HIDDEN.toString(npcId));
        StaffUtil.log(Messages.LOG_NPC_HIDE.toString(sender.getName(), "name", npcId), (sender instanceof Player) ? (Player) sender : null);
    }
}
