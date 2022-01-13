package net.zargum.plugin.icarus.region.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.region.Flag;
import net.zargum.plugin.icarus.region.FlagState;
import net.zargum.plugin.icarus.region.RegionManager;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.command.CommandSender;

public class RegionSetFlagArgument extends ServerCommandArgument<Icarus> {

    private final RegionManager manager;
    private final String formattedFlags;

    public RegionSetFlagArgument(ServerCommand<Icarus> command) {
        super(command, "setflag", "Define flag to a region", "<region> <flag> <flag state>");
        manager = plugin.getRegionManager();
        formattedFlags = plugin.getFlagManager().getFormattedFlags();
        addToTablistCompletion(0, manager.getAllRegionsName().toArray(new String[0]));
        addToTablistCompletion(1, plugin.getFlagManager().getFlagsName().toArray(new String[0]));
        addToTablistCompletion(2, plugin.getFlagManager().getFlagStatesName().toArray(new String[0]));
    }

    @Override
    public String getUsage() {
        return net.zargum.zlib.messages.Messages.USAGE_COMMAND.toString(getCommand().getName() + " " + getArgumentName(), this.getUsageFormat()) + "\n" + Messages.FLAG_LIST.toString(formattedFlags);
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        String regionName = args[0];
        String flagArg = args[1].toUpperCase().replaceAll("-", "_");
        String flagStateArg = args[2].toUpperCase();

        if (!manager.exist(regionName)) {
            sender.sendMessage(Messages.REGION_NOT_EXISTS.toString(regionName));
            return;
        }
        if (!EnumUtils.isValidEnum(Flag.class, flagArg)) {
            sender.sendMessage(Messages.FLAG_NOT_EXISTS.toString());
            sender.sendMessage(Messages.FLAG_LIST.toString(formattedFlags));
            return;
        }
        if (!EnumUtils.isValidEnum(FlagState.class, flagStateArg)) {
            sender.sendMessage(Messages.FLAG_STATE_NOT_EXISTS.toString());
            return;
        }

        Flag flag = Flag.valueOf(flagArg);
        FlagState flagState = FlagState.valueOf(flagStateArg);
        plugin.getFlagManager().addFlagToRegion(regionName, flag, flagState);
        sender.sendMessage(Messages.REGION_SETFLAG.toString(flagArg, flagStateArg, regionName));
    }
}
