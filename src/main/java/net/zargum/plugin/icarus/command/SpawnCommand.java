package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.zLib;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand extends ServerCommand<Icarus> {

    private final Icarus plugin;

    public SpawnCommand(Icarus plugin) {
        super("spawn", "Teleports you to create location.");
        this.plugin = plugin;
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public boolean isRequiresPermission() {
        return false;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        Location location = plugin.getLocationManager().getSpawn();

        if (player.hasPermission(getPermission()+".bypass")) {
            player.teleport(location);
            player.sendMessage(Messages.COMMAND_SPAWN_SUCCESS.toString());
            return;
        }

        player.sendMessage(Messages.COMMAND_SPAWN_TELEPORTING.toString("3"));
        zLib.getInstance().getTeleportManager().addPlayer(player.getUniqueId(),3, plugin.getLocationManager().getSpawn());
    }
}
