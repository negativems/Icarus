package net.zargum.plugin.icarus.npc.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.npc.NPCManager;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import net.zargum.zlib.utils.ColorUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class NPCAddLineArgument extends ServerCommandArgument<Icarus> {

    private final NPCManager manager;

    public NPCAddLineArgument(ServerCommand<Icarus> command) {
        super(command, "addline", "Adds hologram line over of an npc", "<id> [text]");
        manager = plugin.getNpcManager();
        addToTablistCompletion(0, manager.getNPCsId().toArray(new String[0]));
    }

    @Override
    public void execute(CommandSender commandSender, String label, String[] args) {
        Player player = (Player) commandSender;
        String npcId = args[0];
        if (manager.get(npcId) == null) {
            player.sendMessage(Messages.NPC_NOT_EXISTS.toString());
            return;
        }

        List<String> textArray = Arrays.asList(args).subList(1, args.length);
        String line = StringUtils.join(textArray.toArray(), ' ');
        line = ColorUtils.translate(line);

        manager.addLine(npcId, line);
        player.sendMessage(Messages.NPC_LINE_ADDED.toString(line, npcId));
        StaffUtil.log(Messages.LOG_NPC_ADDLINE.toString(player.getName(), line, npcId), player);
    }
}
