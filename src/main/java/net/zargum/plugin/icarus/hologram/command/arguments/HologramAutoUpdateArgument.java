package net.zargum.plugin.icarus.hologram.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.hologram.HologramManager;
import net.zargum.plugin.icarus.hologram.command.HologramCommand;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommandArgument;
import net.zargum.zlib.hologram.Hologram;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HologramAutoUpdateArgument extends ServerCommandArgument<Icarus> {

    private final HologramManager manager;

    public HologramAutoUpdateArgument(HologramCommand command) {
        super(command, "autoupdate", "Automatically update an hologram every interval", "<id>");
        manager = plugin.getHologramManager();
        addToTablistCompletion(0, manager.getHologramsId().toArray(new String[0]));
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        String hologramId = args[0];
        if (manager.getHologram(hologramId) == null) {
            sender.sendMessage(Messages.HOLOGRAM_NOT_EXISTS.toString(hologramId));
            return;
        }

        Hologram hologram = manager.getHologram(hologramId);
        hologram.setAutoUpdate(!hologram.isAutoUpdate());
        sender.sendMessage(Messages.HOLOGRAM_AUTOUPDATE.toString(hologramId, hologram.isAutoUpdate() ? "enabled" : "disabled"));
        StaffUtil.log(Messages.LOG_HOLOGRAM_AUTOUPDATE.toString(sender.getName(), hologram.isAutoUpdate() ? "enabled" : "disabled", hologramId), (sender instanceof Player) ? (Player) sender : null);
    }
}
