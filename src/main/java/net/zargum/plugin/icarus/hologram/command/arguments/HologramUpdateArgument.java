package net.zargum.plugin.icarus.hologram.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.hologram.HologramManager;
import net.zargum.plugin.icarus.hologram.command.HologramCommand;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.npc.NPCManager;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HologramUpdateArgument extends ServerCommandArgument<Icarus> {

    private final HologramManager manager;

    public HologramUpdateArgument(HologramCommand command) {
        super(command, "update", "Update an hologram", "<id>");
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

        manager.getHologram(hologramId).update();
        sender.sendMessage(Messages.HOLOGRAM_UPDATE.toString(hologramId));
        StaffUtil.log(Messages.LOG_HOLOGRAM_UPDATE.toString(sender.getName(), hologramId), (sender instanceof Player) ? (Player) sender : null);
    }
}
