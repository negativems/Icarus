package net.zargum.plugin.icarus.world.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.world.WorldManager;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldInfoArgument extends ServerCommandArgument<Icarus> {

    private final WorldManager manager;

    public WorldInfoArgument(ServerCommand<Icarus> command) {
        super(command, "info", "Shows information from a world", "[world name]");
        manager = plugin.getWorldManager();
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        World world;
        int x, y, z;
        int playerCount;
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Messages.ONLY_PLAYER.toString());
                return;
            }
            Player player = (Player) sender;
            world = player.getLocation().getWorld();
            playerCount = world.getPlayers().size();
            x = player.getLocation().getBlockX();
            y = player.getLocation().getBlockY();
            z = player.getLocation().getBlockZ();
        } else {
            String worldName = args[0];
            if (!manager.existsWorld(worldName) || !manager.isLoaded(worldName)) {
                sender.sendMessage(Messages.WORLD_NOT_EXIST.toString(worldName));
                return;
            }
            world = plugin.getServer().getWorld(worldName);
            playerCount = world.getPlayers().size();
            x = world.getSpawnLocation().getBlockX();
            y = world.getSpawnLocation().getBlockY();
            z = world.getSpawnLocation().getBlockZ();
        }

        String flags = plugin.getFlagManager().getFormattedFlags(manager.getFlags(world));
        sender.sendMessage(Messages.WORLD_INFO.toString(world.getName(), playerCount + "", flags, x + "", y + "", z + ""));

    }
}
