package net.zargum.plugin.icarus.region.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.region.command.arguments.*;
import net.zargum.zlib.command.HelpCommandArgument;
import net.zargum.zlib.command.ServerCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class RegionCommand extends ServerCommand<Icarus> {

    public RegionCommand() {
        super("region", "Manage world regions.", "<help, create, delete, setflags, list, flags, rename, info>", "rg");
        addArgument(new RegionCreateArgument(this));
        addArgument(new RegionDeleteArgument(this));
        addArgument(new RegionFlagsArgument(this));
        addArgument(new RegionFlagStateArgument(this));
        addArgument(new RegionInfoArgument(this));
        addArgument(new RegionSelectArgument(this));
        addArgument(new RegionListArgument(this));
        addArgument(new RegionLogsArgument(this));
        addArgument(new RegionRenameArgument(this));
        addArgument(new RegionSetFlagArgument(this));
        addArgument(new RegionSetpriorityArgument(this));
        addArgument(new HelpCommandArgument<>(this, ChatColor.GOLD, ChatColor.YELLOW));
    }

    @Override
    public int getMinArgRequired() {
        return 1;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {

    }

}
