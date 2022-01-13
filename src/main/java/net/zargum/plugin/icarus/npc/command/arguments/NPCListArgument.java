package net.zargum.plugin.icarus.npc.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.npc.NPCManager;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.bukkit.command.CommandSender;

public class NPCListArgument extends ServerCommandArgument<Icarus> {

    private final NPCManager manager;

    public NPCListArgument(ServerCommand<Icarus> command) {
        super(command, "list", "Shows list of all npcs created");
        manager = getPlugin().getNpcManager();
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (manager.getAllNPCs().size() == 0) {
            sender.sendMessage(Messages.NPC_LIST_NONE.toString());
            return;
        }
        String npclist = String.join(", ", manager.getNPCsId());
        sender.sendMessage(Messages.NPC_LIST.toString(manager.getNPCsId().size()+"", npclist));
    }
}
