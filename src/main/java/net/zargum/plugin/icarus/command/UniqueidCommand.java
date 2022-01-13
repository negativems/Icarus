package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.command.ServerCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UniqueidCommand extends ServerCommand<Icarus> {

    public UniqueidCommand() {
        super("uniqueid", "Shows unique id from a player", "<player>", "uuid");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Messages.PLAYER_NOT_ONLINE.toString());
            return;
        }
        sender.sendMessage(Messages.COMMAND_UNIQUEID_MESSAGE.toString(target.getName(), target.getUniqueId().toString()));
    }
}
