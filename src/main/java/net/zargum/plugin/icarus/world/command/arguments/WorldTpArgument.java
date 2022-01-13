package net.zargum.plugin.icarus.world.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.world.WorldManager;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldTpArgument extends ServerCommandArgument<Icarus> {

    private final WorldManager manager;

    public WorldTpArgument(ServerCommand<Icarus> command) {
        super(command, "teleport", "Teleports to a world", "<world name>", "tp");
        manager = plugin.getWorldManager();
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        String worldName = args[0];
        if (!manager.existsWorld(worldName) || !manager.isLoaded(worldName)) {
            sender.sendMessage(Messages.WORLD_NOT_EXIST.toString(worldName));
            return;
        }

        player.teleport(plugin.getServer().getWorld(worldName).getSpawnLocation().add(0.5, 0, 0.5));
        player.sendMessage(Messages.TELEPORTED.toString(worldName));
    }
}
