package net.zargum.plugin.icarus.hologram.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.hologram.HologramManager;
import net.zargum.plugin.icarus.hologram.command.HologramCommand;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommandArgument;
import net.zargum.zlib.hologram.Hologram;
import net.zargum.zlib.utils.ColorUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class HologramAddLineArgument extends ServerCommandArgument<Icarus> {

    private final HologramManager manager;

    public HologramAddLineArgument(HologramCommand command) {
        super(command, "addline", "Adds new hologram line under the last one", "<id> [text]");
        manager = plugin.getHologramManager();
        addToTablistCompletion(0, manager.getHologramsId().toArray(new String[0]));
    }

    @Override
    public void execute(CommandSender commandSender, String label, String[] args) {
        Player player = (Player) commandSender;
        String hologramId = args[0];
        if (manager.getHologram(hologramId) == null) {
            player.sendMessage(Messages.HOLOGRAM_NOT_EXISTS.toString());
            return;
        }

        List<String> textArray = Arrays.asList(args).subList(1, args.length);
        String line = StringUtils.join(textArray.toArray(), ' ');
        line = ColorUtils.translate(line);

        Hologram hologram = manager.getHologram(hologramId);
        hologram.addLine(line);
        hologram.update();

        player.sendMessage(Messages.HOLOGRAM_LINE_ADDED.toString(line, hologramId));
        StaffUtil.log(Messages.LOG_HOLOGRAM_ADDLINE.toString(player.getName(), line, hologramId), player);
    }
}
