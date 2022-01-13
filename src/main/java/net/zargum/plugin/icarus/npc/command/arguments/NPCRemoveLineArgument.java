package net.zargum.plugin.icarus.npc.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.npc.NPCManager;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import net.zargum.zlib.npc.NPC;
import net.zargum.zlib.utils.JavaUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCRemoveLineArgument extends ServerCommandArgument<Icarus> {

    private final NPCManager manager;

    public NPCRemoveLineArgument(ServerCommand<Icarus> command) {
        super(command, "removeline", "Removes line from the hologram over of the npc", "<id> <line>");
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
        String lineArg = args[1];
        if (!JavaUtils.isInt(lineArg)) {
            sender.sendMessage(Messages.NOT_NUMBER.toString());
            return;
        }
        NPC npc = manager.get(npcId);
        if (npc.getHologram() == null) {
            sender.sendMessage(Messages.NPC_NOT_HOLOGRAM.toString());
            return;
        }

        int linePosition = Integer.parseInt(lineArg);
        int hologramLinesSize = npc.getHologram().getLines().size();
        if (linePosition > hologramLinesSize || linePosition < 1) {
            sender.sendMessage(Messages.HOLOGRAM_LINE_NOT_EXISTS.toString());
            return;
        }

        String hologramId = npc.getHologram().getId();
        manager.removeLine(npcId, linePosition - 1);
        if (hologramLinesSize == 1) {
            sender.sendMessage(Messages.HOLOGRAM_DELETED.toString(lineArg, npcId));
            StaffUtil.log(Messages.LOG_HOLOGRAM_DELETE.toString(sender.getName(), hologramId), (sender instanceof Player) ? (Player) sender : null);
            return;
        }
        sender.sendMessage(Messages.NPC_LINE_REMOVED.toString(lineArg, npcId));
        StaffUtil.log(Messages.LOG_NPC_REMOVELINE.toString(sender.getName(), lineArg, npcId), (sender instanceof Player) ? (Player) sender : null);
    }
}
