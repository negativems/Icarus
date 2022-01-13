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

public class HologramDeleteArgument extends ServerCommandArgument<Icarus> {

    private final HologramManager manager;

    public HologramDeleteArgument(HologramCommand command) {
        super(command, "delete", "Deletes an hologram", "<id>");
        manager = command.getPlugin().getHologramManager();
        addToTablistCompletion(0, manager.getHologramsId().toArray(new String[0]));
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        String hologramId = args[0];
        if (manager.getHologram(hologramId) == null) {
            sender.sendMessage(Messages.HOLOGRAM_NOT_EXISTS.toString());
            return;
        }

        Hologram hologram = manager.getHologram(hologramId);
        hologram.delete();
        manager.getHologramsMap().remove(hologramId);

        removeFromTablistCompletion(0, hologramId);

        sender.sendMessage(Messages.HOLOGRAM_DELETED.toString(hologramId));
        StaffUtil.log(Messages.LOG_HOLOGRAM_DELETE.toString(sender.getName(), hologramId), (sender instanceof Player) ? (Player) sender : null);
    }
}
