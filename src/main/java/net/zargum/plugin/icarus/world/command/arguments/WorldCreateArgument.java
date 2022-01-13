package net.zargum.plugin.icarus.world.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.plugin.icarus.world.WorldManager;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldCreateArgument extends ServerCommandArgument<Icarus> {

    private final WorldManager manager;
    public WorldCreateArgument(ServerCommand<Icarus> command) {
        super(command, "create", "Creates a world", "<world name> [environment=normal,nether,end] [void=-v]");
        manager = plugin.getWorldManager();
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        String worldName = args[0];
        if (manager.existsWorld(worldName) && manager.isLoaded(worldName)) {
            sender.sendMessage(Messages.WORLD_ALREADY_EXIST.toString(worldName));
            return;
        }

        if (manager.existsWorld(worldName) && !manager.isLoaded(worldName)) {
            sender.sendMessage(Messages.WORLD_LOADING.toString(worldName));
            WorldCreator wc = new WorldCreator(worldName);
            wc.createWorld();
            sender.sendMessage(Messages.WORLD_LOADED.toString(worldName));
            return;
        }

        String environmentName = "normal";

        if (args.length > 1) environmentName = args[1];

        if (!environmentName.equalsIgnoreCase("end") && !EnumUtils.isValidEnum(World.Environment.class, environmentName.toUpperCase())) {
            sender.sendMessage(Messages.WORLD_ENVIRONMENT_ERROR.toString());
            return;
        }

        World.Environment environment = World.Environment.valueOf(environmentName.toUpperCase());
        if (args.length == 3) {
            String option = args[2];
            if (option.equalsIgnoreCase("-v")) {
                WorldCreator wc = new WorldCreator(worldName);
                wc.environment(environment);
                wc.type(WorldType.FLAT);
                wc.generatorSettings("2;0;1;");
                wc.createWorld();
                plugin.getWorldManager().getHandler().getWorlds().add(worldName);
                sender.sendMessage(Messages.COMMAND_WORLD_CREATED.toString());
                StaffUtil.log(Messages.LOG_WORLD_CREATED_VOID.toString(sender.getName(), worldName, environmentName.toLowerCase()), (sender instanceof Player) ? (Player) sender : null);
                return;
            }
            sender.sendMessage(Messages.OPTION_NOT_EXISTS.toString());
            return;
        }
        WorldCreator wc = new WorldCreator(worldName);
        wc.environment(environment);
        wc.createWorld();
        plugin.getWorldManager().getHandler().getWorlds().add(worldName);
        sender.sendMessage(Messages.COMMAND_WORLD_CREATED.toString());
        StaffUtil.log(Messages.LOG_WORLD_CREATED.toString(sender.getName(), worldName, environmentName.toLowerCase()), (sender instanceof Player) ? (Player) sender : null);
    }
}
