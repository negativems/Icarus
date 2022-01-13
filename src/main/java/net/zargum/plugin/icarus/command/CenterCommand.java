package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.command.ServerCommand;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CenterCommand extends ServerCommand<Icarus> {

    public CenterCommand() {
        super("center", "Teleports you to the center of your location.", "", "ct");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        Location playerLocation = player.getLocation();
        Location location = new Location(player.getWorld(), playerLocation.getBlockX() + 0.5, playerLocation.getY(), playerLocation.getBlockZ() + 0.5, playerLocation.getYaw(), playerLocation.getPitch());
        player.teleport(location);
        player.sendMessage(Messages.COMMAND_CENTER_TELEPORTED.toString());
    }
}
