package net.zargum.plugin.icarus.npc.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.npc.NPCManager;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCSetDisplayNameArgument extends ServerCommandArgument<Icarus> {

    private final NPCManager manager;

    public NPCSetDisplayNameArgument(ServerCommand<Icarus> command) {
        super(command, "setdisplayname", "Change display name of an npc", "<id> <displayname>");
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

        String displayname = args[2];
        manager.setDisplayName(npcId, displayname);
        sender.sendMessage(Messages.NPC_SET_DISPLAYNAME.toString(npcId, displayname));
        StaffUtil.log(Messages.LOG_NPC_UPDATE.toString(sender.getName(), "display name", npcId), (sender instanceof Player) ? (Player) sender : null);
    }
}
