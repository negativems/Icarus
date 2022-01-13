package net.zargum.plugin.icarus.world.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.world.command.arguments.*;
import net.zargum.zlib.command.HelpCommandArgument;
import net.zargum.zlib.command.ServerCommand;
import org.bukkit.command.CommandSender;

public class WorldCommand extends ServerCommand<Icarus> {

    private final Icarus plugin;

    public WorldCommand(Icarus plugin) {
        super("world", "Manage worlds");
        this.plugin = plugin;
        addArgument(new WorldCreateArgument(this));
        addArgument(new WorldDeleteArgument(this));
        addArgument(new WorldSetFlagArgument(this));
        addArgument(new WorldInfoArgument(this));
        addArgument(new WorldTpArgument(this));
        addArgument(new WorldPregenArgument(this));
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
