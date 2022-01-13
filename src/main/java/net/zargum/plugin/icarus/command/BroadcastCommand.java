package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.command.ServerCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class BroadcastCommand extends ServerCommand<Icarus> {

    public BroadcastCommand() {
        super("broadcast", "Send a broadcast message.", "<message>", "bc");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        StringBuilder message = new StringBuilder();
        for (String arg : args) message.append(arg).append(" ");
        Bukkit.broadcastMessage(Messages.COMMAND_BROADCAST.toString(message.toString()));
    }
}
