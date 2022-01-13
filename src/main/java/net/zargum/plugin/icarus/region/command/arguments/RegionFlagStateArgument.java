package net.zargum.plugin.icarus.region.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.region.Flag;
import net.zargum.plugin.icarus.region.FlagState;
import net.zargum.plugin.icarus.region.RegionManager;
import net.zargum.plugin.icarus.region.command.RegionCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegionFlagStateArgument extends ServerCommandArgument<Icarus> {

    private final RegionManager manager;

    public RegionFlagStateArgument(RegionCommand command) {
        super(command, "flagstate", "Gets the final status of a flag in a location", "<flag>");
        manager = plugin.getRegionManager();
        addToTablistCompletion(0, plugin.getFlagManager().getFlagsName().toArray(new String[0]));
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        String flagArg = args[0];
        if (!EnumUtils.isValidEnum(Flag.class, flagArg.toUpperCase().replaceAll("-", "_"))) {
            player.sendMessage(Messages.FLAG_NOT_EXISTS.toString());
            return;
        }

        Flag flag = Flag.valueOf(flagArg.toUpperCase().replaceAll("-", "_"));
        FlagState flagState = manager.getFinalFlagState(player.getLocation(), flag);
        String flagStateName = flagState.name().toLowerCase().replaceAll("_", "-");
        player.sendMessage(Messages.REGION_FLAGSTATE.toString(flagArg, flagStateName));
    }
}
