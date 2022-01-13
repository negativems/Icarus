package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.command.ServerCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand extends ServerCommand<Icarus> {

    public FlyCommand() {
        super("fly", "Allow/disallow you to fly.", "[(player)|on|off] [on|off]");
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    private boolean isBoolean(String s) {
        return (s.equalsIgnoreCase("true")) || (s.equalsIgnoreCase("false"));
    }

    private String getStatus(boolean status) {
        return status ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled";
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            boolean flight = player.getAllowFlight();
            player.setAllowFlight(!flight);
            player.setFlying(!flight);
            player.sendMessage(Messages.COMMAND_FLY.toString(getStatus(!flight)));
            return;
        }

        if (args.length == 1) {
            if (Bukkit.getPlayer(args[0]) != null) {
                Player target = Bukkit.getPlayer(args[0]);
                boolean flight = target.getAllowFlight();
                target.setAllowFlight(!flight);
                target.setFlying(!flight);
                target.sendMessage(Messages.COMMAND_FLY.toString(getStatus(!flight)));
                player.sendMessage(Messages.COMMAND_FLY_TO_OTHER.toString(getStatus(!flight), target.getName()));
                return;
            }
            player.sendMessage();
        }

        if (args.length == 2 && isBoolean(args[1])) {
            Player target = Bukkit.getPlayer(args[0]);
            boolean flight = Boolean.parseBoolean(args[1]);
            target.setAllowFlight(!flight);
            target.setFlying(!flight);
            target.sendMessage(Messages.COMMAND_FLY.toString(getStatus(!flight)));
            player.sendMessage(Messages.COMMAND_FLY_TO_OTHER.toString(getStatus(!flight), target.getName()));
        }

    }
}
