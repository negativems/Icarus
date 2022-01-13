package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.utils.JavaUtils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetspawnCommand extends ServerCommand<Icarus> {

    private final Icarus plugin;

    public SetspawnCommand(Icarus plugin) {
        super("setspawn", "Set the create location.", "[yaw] [pitch]");
        this.plugin = plugin;
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        Location location = player.getLocation();

        if ((args.length > 0 && !JavaUtils.isFloat(args[0])) || (args.length > 1 && !JavaUtils.isFloat(args[1]))) {
            player.sendMessage(getUsage());
            return;
        }

        float yaw = args.length > 0 && JavaUtils.isFloat(args[0]) ? Float.parseFloat(args[0]) : location.getYaw();
        float pitch = args.length > 1 && JavaUtils.isFloat(args[1])? Float.parseFloat(args[1]) : location.getPitch();

        if (yaw > 180 || yaw < -180) {
            player.sendMessage(Messages.COMMAND_SETSPAWN_INVALID_YAW.toString());
            return;
        }
        if (pitch > 90 || pitch < -90) {
            player.sendMessage(Messages.COMMAND_SETSPAWN_INVALID_PITCH.toString());
            return;
        }

        location.setX(location.getBlockX() + 0.5);
        location.setZ(location.getBlockZ() + 0.5);

        location.setYaw(yaw);
        location.setPitch(pitch);

        location.getWorld().setSpawnLocation(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        plugin.getLocationManager().setSpawn(location);

        player.sendMessage(Messages.COMMAND_SETSPAWN_SET.toString());
    }
}
