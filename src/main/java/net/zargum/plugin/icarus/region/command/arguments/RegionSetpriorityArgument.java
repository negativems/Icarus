package net.zargum.plugin.icarus.region.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.region.RegionManager;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import net.zargum.zlib.utils.JavaUtils;
import org.bukkit.command.CommandSender;

public class RegionSetpriorityArgument extends ServerCommandArgument<Icarus> {

    private final RegionManager manager;

    public RegionSetpriorityArgument(ServerCommand<Icarus> command) {
        super(command, "setpriority", "Sets priority to a region", "<region> <priority>");
        manager = plugin.getRegionManager();
        addToTablistCompletion(0, manager.getAllRegionsName().toArray(new String[0]));
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        String regionName = args[0];
        String priorityArg = args[1];

        if (!manager.exist(regionName)) {
            sender.sendMessage(Messages.REGION_NOT_EXISTS.toString(regionName));
            return;
        }
        if (!JavaUtils.isInt(priorityArg)) {
            sender.sendMessage(Messages.NOT_NUMBER.toString());
            return;
        }

        int priority = Integer.parseInt(priorityArg);
        manager.getRegion(regionName).setPriority(priority);
        sender.sendMessage(Messages.REGION_SETPRIORITY.toString(priorityArg, regionName));
    }
}
