package net.zargum.plugin.icarus.region.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.region.RegionManager;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegionDeleteArgument extends ServerCommandArgument<Icarus> {

    private final RegionManager manager;

    public RegionDeleteArgument(ServerCommand<Icarus> command) {
        super(command, "delete", "Deletes a region", "<region>");
        manager = plugin.getRegionManager();
        addToTablistCompletion(0, manager.getAllRegionsName().toArray(new String[0]));
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        String regionName = args[0];
        if (!manager.exist(regionName)) {
            player.sendMessage(Messages.REGION_NOT_EXISTS.toString(regionName));
            return;
        }
        manager.delete(regionName);
        player.sendMessage(Messages.REGION_DELETED.toString(regionName));
    }
}
