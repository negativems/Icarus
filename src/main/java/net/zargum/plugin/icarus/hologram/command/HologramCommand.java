package net.zargum.plugin.icarus.hologram.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.hologram.command.arguments.*;
import net.zargum.zlib.command.HelpCommandArgument;
import net.zargum.zlib.command.ServerCommand;
import org.bukkit.command.CommandSender;

public class HologramCommand extends ServerCommand<Icarus> {

    public HologramCommand() {
        super("hologram","Creates a holograms", "<create, delete, edit, setlocation, view>", "hd");

        // Arguments
        addArgument(new HologramCreateArgument(this));
        addArgument(new HologramDeleteArgument(this));
        addArgument(new HologramAutoUpdateArgument(this));
        addArgument(new HologramSetLineArgument(this));
        addArgument(new HologramAddLineArgument(this));
        addArgument(new HologramRemoveLineArgument(this));
        addArgument(new HologramInfoArgument(this));
        addArgument(new HologramListArgument(this));
        addArgument(new HologramNearArgument(this));
        addArgument(new HologramMoveHereArgument(this));
        addArgument(new HologramUpdateArgument(this));
        addArgument(new HelpCommandArgument<>(this));
    }

    @Override
    public int getMinArgRequired() {
        return 1;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {



    }
}
