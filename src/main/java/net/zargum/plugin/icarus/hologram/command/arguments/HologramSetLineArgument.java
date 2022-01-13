package net.zargum.plugin.icarus.hologram.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.hologram.HologramManager;
import net.zargum.plugin.icarus.hologram.command.HologramCommand;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommandArgument;
import net.zargum.zlib.hologram.Hologram;
import net.zargum.zlib.utils.ColorUtils;
import net.zargum.zlib.utils.JavaUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class HologramSetLineArgument extends ServerCommandArgument<Icarus> {

    private final HologramManager manager;

    public HologramSetLineArgument(HologramCommand command) {
        super(command, "setline", "Changes line from an hologram", "<id> <line> [text]");
        manager = plugin.getHologramManager();
        addToTablistCompletion(0, manager.getHologramsId().toArray(new String[0]));
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        String hologramId = args[0];
        if (manager.getHologram(hologramId) == null) {
            player.sendMessage(Messages.HOLOGRAM_NOT_EXISTS.toString());
            return;
        }
        String lineArg = args[1];
        if (!JavaUtils.isInt(lineArg)) {
            sender.sendMessage(Messages.NOT_NUMBER.toString());
            return;
        }
        int linePosition = Integer.parseInt(lineArg);

        int linesAmount = manager.getHologram(hologramId).getLines().size();
        if (linePosition > linesAmount || linePosition < 1) {
            sender.sendMessage(Messages.HOLOGRAM_SETLINE_INVALID_INDEX.toString(linesAmount));
            return;
        }

        List<String> textArray = Arrays.asList(args).subList(2, args.length);
        String line = StringUtils.join(textArray.toArray(), ' ');
        line = ColorUtils.translate(line);

        Hologram hologram = manager.getHologram(hologramId);
        hologram.setLine(linePosition - 1, line);
        hologram.update();

        player.sendMessage(Messages.HOLOGRAM_LINE_SETTED.toString(line, hologramId));
        StaffUtil.log(Messages.LOG_HOLOGRAM_ADDLINE.toString(player.getName(), line, hologramId), player);
    }
}
