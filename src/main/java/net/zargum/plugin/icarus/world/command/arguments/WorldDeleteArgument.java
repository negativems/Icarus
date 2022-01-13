package net.zargum.plugin.icarus.world.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.utils.WorldUtils;
import net.zargum.plugin.icarus.world.WorldManager;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldDeleteArgument extends ServerCommandArgument<Icarus> {

    private final WorldManager manager;

    public WorldDeleteArgument(ServerCommand<Icarus> command) {
        super(command, "delete", "Deletes a world", "<world name>");
        manager = plugin.getWorldManager();
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        String worldName = args[0];
        if (!manager.existsWorld(worldName) || !manager.isLoaded(worldName)) {
            sender.sendMessage(Messages.WORLD_NOT_EXIST.toString(worldName));
            return;
        }

        if (plugin.getServer().getWorld(worldName) == null) {
            sender.sendMessage(Messages.WORLD_NOT_EXIST.toString(worldName + " (2)"));
            return;
        }

        Location spawn = plugin.getLocationManager().getSpawn();
        if (spawn.getWorld().equals(Bukkit.getWorld(worldName))) {
            sender.sendMessage(Messages.WORLD_DELETE_SPAWN.toString(worldName));
            return;
        }

        if (WorldUtils.getMainWorld().equals(worldName)) {
            sender.sendMessage(Messages.WORLD_DELETED_DEFAULT_WORLD.toString());
            return;
        }

        for (Player player : Bukkit.getWorld(worldName).getPlayers()) {
            if (player.isDead()) player.spigot().respawn();
            player.teleport(spawn);
        }
        plugin.getServer().unloadWorld(worldName, true);
        manager.getHandler().getDeletedWorlds().add(worldName);
    }
}
