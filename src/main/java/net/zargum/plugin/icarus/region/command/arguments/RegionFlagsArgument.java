package net.zargum.plugin.icarus.region.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.region.RegionManager;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.bukkit.command.CommandSender;

public class RegionFlagsArgument extends ServerCommandArgument<Icarus> {

    private final RegionManager manager;

    public RegionFlagsArgument(ServerCommand<Icarus> command) {
        super(command, "flags", "Lists all flags");
        manager = plugin.getRegionManager();
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        String flags = plugin.getFlagManager().getFormattedFlags();
        sender.sendMessage(Messages.FLAG_LIST.toString(flags));
    }
}
