package net.zargum.plugin.icarus.world.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.region.Flag;
import net.zargum.plugin.icarus.region.FlagState;
import net.zargum.plugin.icarus.region.managers.FlagManager;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.plugin.icarus.world.WorldManager;
import net.zargum.plugin.icarus.world.command.WorldCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class WorldSetFlagArgument extends ServerCommandArgument<Icarus> {

    private final WorldManager worldManager;
    private final FlagManager flagManager;
    private final String formattedFlags;

    public WorldSetFlagArgument(WorldCommand command) {
        super(command, "setflag", "Defines a flag to the current world", "[world] <flag> <flag state>");
        worldManager = plugin.getWorldManager();
        flagManager = plugin.getFlagManager();
        formattedFlags = flagManager.getFormattedFlags();
        addToTablistCompletion(0, flagManager.getFlagsName().toArray(new String[0]));
        addToTablistCompletion(1, flagManager.getFlagStatesName().toArray(new String[0]));
    }

    @Override
    public String getUsage() {
        return net.zargum.zlib.messages.Messages.USAGE_COMMAND.toString(getCommand().getName() + " " + getArgumentName(), this.getUsageFormat()) + "\n" + Messages.FLAG_LIST.toString(formattedFlags);
    }

    public void setFlag(CommandSender sender, String worldName, String flagArg, String flagStateArg) {
        if (plugin.getServer().getWorld(worldName) == null) {
            sender.sendMessage(Messages.WORLD_NOT_EXIST.toString(worldName));
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
        String stateFormatted = (flagState == FlagState.ALLOW) ? "allowed" : ((flagState == FlagState.DENY) ? "denied" : "removed");
        if (worldManager.hasFlag(worldName, flag) && worldManager.getFlagState(worldName, flag) == flagState) {
            sender.sendMessage(Messages.WORLD_SETFLAG_ALREADY_EXISTS.toString(stateFormatted));
            return;
        }

        flagManager.addFlagToWorld(worldName, flag, flagState);
        sender.sendMessage(Messages.WORLD_SETFLAG.toString(flagArg, flagStateArg, worldName));

        StaffUtil.log(Messages.LOG_WORLD_SET_FLAG.toString(sender.getName(), stateFormatted, flagArg, worldName), (sender instanceof Player) ? (Player) sender : null);
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 2) {
            if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(Messages.ARG_ONLY_PLAYER.toString());
                return;
            }

            Player player = (Player) sender;
            String flagArg = args[0].toUpperCase().replaceAll("-", "_");
            String flagStateArg = args[1].toUpperCase();
            setFlag(player, player.getWorld().getName(), flagArg, flagStateArg);
        } else if (args.length == 3) {
            String worldName = args[0];
            String flagArg = args[1].toUpperCase().replaceAll("-", "_");
            String flagStateArg = args[2].toUpperCase();
            setFlag(sender, worldName, flagArg, flagStateArg);
        }

    }
}
