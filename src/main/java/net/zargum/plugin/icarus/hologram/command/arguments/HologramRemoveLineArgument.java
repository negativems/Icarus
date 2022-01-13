package net.zargum.plugin.icarus.hologram.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.hologram.HologramManager;
import net.zargum.plugin.icarus.hologram.command.HologramCommand;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import net.zargum.zlib.hologram.Hologram;
import net.zargum.zlib.utils.JavaUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HologramRemoveLineArgument extends ServerCommandArgument<Icarus> {

    private final HologramManager manager;

    public HologramRemoveLineArgument(HologramCommand command) {
        super(command, "removeline", "Removes line from an hologram", "<id> <line>");
        manager = plugin.getHologramManager();
        addToTablistCompletion(0, manager.getHologramsId().toArray(new String[0]));
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        String hologramId = args[0];
        String lineArg = args[1];
        if (!JavaUtils.isInt(lineArg)) {
            sender.sendMessage(Messages.NOT_NUMBER.toString());
            return;
        }
        if (manager.getHologram(hologramId) == null) {
            sender.sendMessage(Messages.HOLOGRAM_NOT_EXISTS.toString(hologramId));
            return;
        }

        int lineIndex = Integer.parseInt(lineArg);
        if (lineIndex == 1 && manager.getHologram(hologramId).getLines().size() == 1) {
            sender.sendMessage(Messages.HOLOGRAM_REMOVE_LAST_LINE.toString(hologramId));
            return;
        }
        if (lineIndex > manager.getHologram(hologramId).getLines().size() || lineIndex < 1) {
            sender.sendMessage(Messages.HOLOGRAM_LINE_NOT_EXISTS.toString());
            return;
        }

        Hologram hologram = manager.getHologram(hologramId);
        hologram.removeLine(lineIndex - 1);
        hologram.update();

        player.sendMessage(Messages.HOLOGRAM_LINE_REMOVED.toString(lineArg, hologramId));
        StaffUtil.log(Messages.LOG_HOLOGRAM_REMOVELINE.toString(player.getName(), lineArg, hologramId), player);
    }
}
