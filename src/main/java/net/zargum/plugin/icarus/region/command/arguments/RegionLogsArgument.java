package net.zargum.plugin.icarus.region.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.region.RegionManager;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegionLogsArgument extends ServerCommandArgument<Icarus> {

    private final RegionManager manager;

    public RegionLogsArgument(ServerCommand<Icarus> command) {
        super(command, "logs", "Shows logs on interact with a region event");
        manager = plugin.getRegionManager();
    }

    @Override
    public void execute(CommandSender sender, String s, String[] strings) {
        Player player = (Player) sender;
        if (!manager.getLogPlayers().contains(player.getUniqueId())) {
            manager.getLogPlayers().add(player.getUniqueId());
            player.sendMessage(Messages.REGION_LOGS_ENABLED.toString());
        } else {
            manager.getLogPlayers().remove(player.getUniqueId());
            player.sendMessage(Messages.REGION_LOGS_DISABLED.toString());
        }
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }
}
