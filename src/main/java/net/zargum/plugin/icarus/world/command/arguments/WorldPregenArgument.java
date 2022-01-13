package net.zargum.plugin.icarus.world.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.world.WorldManager;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import net.zargum.zlib.utils.JavaUtils;
import org.bukkit.*;
import org.bukkit.command.CommandSender;

public class WorldPregenArgument extends ServerCommandArgument<Icarus> {

    private final WorldManager manager;

    public WorldPregenArgument(ServerCommand<Icarus> command) {
        super(command, "pregen", "Pregenerate a world", "<world name> [number of blocks (default 1000)]", "tp");
        manager = plugin.getWorldManager();
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        String worldName = args[0];
        if (!manager.existsWorld(worldName) || !manager.isLoaded(worldName)) {
            sender.sendMessage(Messages.WORLD_NOT_EXIST.toString(worldName));
            return;
        }

        int size = 1000;
        World world = Bukkit.getWorld(worldName);
        if (args.length > 1 && !JavaUtils.isInt(args[1])) {
            sender.sendMessage(Messages.COMMAND_WORLD_INVALID_PREGEN_SIZE.toString());
            return;
        }
        if (args.length > 1 && JavaUtils.isInt(args[1])) {
            size = Integer.parseInt(args[1]);
        }

        final double chunks = size * size / 256.0;
        plugin.log(ChatColor.GREEN + "Counted " + chunks + " Chunks !");
        plugin.log("Loading chunks please wait...");
        int finalSize = size;
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            double millis = System.currentTimeMillis();
            for (int x = (int)(-(finalSize / 2.0)); x < finalSize / 2.0; x += 16) {
                for (int z = (int)(-(finalSize / 2.0)); z < finalSize / 2.0; z += 16) {
                    final Chunk c = world.getChunkAt(new Location(world, x, 0.0, z));
                    if (!c.load()) {
                        plugin.log(ChatColor.RED + "An error occured while loading " + c.toString() + " located at " + x + ":" + z + " , maybe it was already loaded ?");
                    }
                }
            }
            millis = System.currentTimeMillis() - millis;
            plugin.log(ChatColor.GREEN + "Chunks generated successfull for world " + worldName + " (" + millis + "ms)");
            sender.sendMessage(Messages.COMMAND_WORLD_PREGEN_SUCCESS.toString(chunks+"", finalSize +""));
        });
    }
}
