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

public class NPCShowNameArgument extends ServerCommandArgument<Icarus> {

    private final NPCManager manager;

    public NPCShowNameArgument(ServerCommand<Icarus> command) {
        super(command, "showname", "Shows name of an npc", "<id>");
        manager = plugin.getNpcManager();
        addToTablistCompletion(0, manager.getNPCsId().toArray(new String[0]));
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        String npcId = args[0];
        NPC npc = manager.get(npcId);
        if (!npc.isHideName()) {
            sender.sendMessage(Messages.NPC_NAME_ALREADY_UNHIDDEN.toString(npcId));
            return;
        }
        npc.unhideName();
        npc.update();
        sender.sendMessage(Messages.NPC_NAME_UNHIDDEN.toString(npcId));
        StaffUtil.log(Messages.LOG_NPC_SHOW.toString(sender.getName(), "display name", npcId), (sender instanceof Player) ? (Player) sender : null);
    }
}
