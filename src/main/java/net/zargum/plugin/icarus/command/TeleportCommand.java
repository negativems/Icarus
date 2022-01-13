package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.utils.JavaUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand extends ServerCommand<Icarus> {

    public TeleportCommand() {
        super("teleport", "Teleport to a destination.", "<[player], [x]> [y] [z]", "tp, tphere, tpall");
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        if (label.equalsIgnoreCase("tp") || label.equalsIgnoreCase("teleport")) {
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    player.teleport(target);
                    player.sendMessage(Messages.COMMAND_TELEPORT_SUCCESS.toString(target.getName()));
                    return;
                }
                player.sendMessage(Messages.PLAYER_NOT_ONLINE.toString());
                return;
            }
            if (args.length == 2) {
                if (Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[1]) != null){
                    Player first = Bukkit.getPlayer(args[0]);
                    Player second = Bukkit.getPlayer(args[1]);
                    first.teleport(second);
                    return;
                }
                player.sendMessage(Messages.PLAYER_NOT_ONLINE.toString());
                return;
            }
            if (args.length == 3) {
                if (JavaUtils.isDouble(args[0]) && JavaUtils.isDouble(args[1]) && JavaUtils.isDouble(args[2])) {
                    double x = Double.parseDouble(args[0]);
                    double y = Double.parseDouble(args[1]);
                    double z = Double.parseDouble(args[2]);
                    Location location = new Location(player.getWorld(), x+0.5, y+0.5, z+0.5, player.getLocation().getYaw(), player.getLocation().getPitch());
                    player.teleport(location);
                    return;
                }
                player.sendMessage(Messages.COMMAND_TELEPORT_LOCATION_ERROR.toString());
                return;
            }
            if (args.length == 4) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    if (JavaUtils.isInt(args[1]) && JavaUtils.isInt(args[2]) && JavaUtils.isInt(args[3])) {
                        int x = Integer.parseInt(args[1]);
                        int y = Integer.parseInt(args[2]);
                        int z = Integer.parseInt(args[3]);
                        Location location = new Location(player.getWorld(), x, y, z, player.getLocation().getYaw(), player.getLocation().getPitch());
                        player.teleport(location);
                        return;
                    }
                    player.sendMessage(Messages.COMMAND_TELEPORT_LOCATION_ERROR.toString());
                    return;
                }
                player.sendMessage(Messages.PLAYER_NOT_ONLINE.toString());
                return;
            }
        }

        if (label.equalsIgnoreCase("tphere")) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                target.teleport(player);
                player.sendMessage(Messages.COMMAND_TELEPORT_HERE_SUCCESS.toString(target.getName()));
                return;
            }
            player.sendMessage(Messages.PLAYER_NOT_ONLINE.toString());
            return;
        }

        if (label.equalsIgnoreCase("tpall")) {
            if (Bukkit.getOnlinePlayers().size() > 1) {
                for (Player target : Bukkit.getOnlinePlayers()) {
                    target.teleport(player);
                    target.sendMessage(Messages.TELEPORTED.toString(player.getName()));
                }
                player.sendMessage(Messages.COMMAND_TELEPORT_ALL_SUCCESS.toString());
                StaffUtil.log(ChatColor.LIGHT_PURPLE + player.getName() + " &7teleport all the server.", player);
                return;
            }
            player.sendMessage(Messages.COMMAND_TELEPORT_ALL_NO_PLAYERS.toString());
        }
    }
}
