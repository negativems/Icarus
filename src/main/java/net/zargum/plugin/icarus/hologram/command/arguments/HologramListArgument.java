package net.zargum.plugin.icarus.hologram.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.hologram.HologramManager;
import net.zargum.plugin.icarus.hologram.command.HologramCommand;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.command.ServerCommandArgument;
import org.bukkit.command.CommandSender;

public class HologramListArgument extends ServerCommandArgument<Icarus> {

    private final HologramManager manager;

    public HologramListArgument(HologramCommand command) {
        super(command, "list", "Shows list of all holograms created");
        manager = getPlugin().getHologramManager();
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (manager.getHolograms().size() == 0) {
            sender.sendMessage(Messages.HOLOGRAM_LIST_NONE.toString());
            return;
        }
        String hologramListId = String.join(", ", manager.getHologramsId());
        sender.sendMessage(Messages.HOLOGRAM_LIST.toString(manager.getHologramsId().size() + "", hologramListId));
    }
}
