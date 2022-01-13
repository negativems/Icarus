package net.zargum.plugin.icarus.region.command.arguments;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.region.RegionManager;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegionCreateArgument extends ServerCommandArgument<Icarus> {

    private final RegionManager manager;

    public RegionCreateArgument(ServerCommand<Icarus> command) {
        super(command, "create", "Creates a region with selected zone", "<region>");
        manager = plugin.getRegionManager();
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        String regionName = args[0];
        Selection selection = plugin.getWorldEdit().getSelection(player);
        if (manager.exist(regionName)) {
            player.sendMessage(Messages.REGION_ALREADY_CREATED.toString(regionName));
            return;
        }
        if (selection == null) {
            player.sendMessage(Messages.REGION_NOT_SELECTED.toString());
            return;
        }
        if (!(selection instanceof CuboidSelection)) {
            player.sendMessage(Messages.REGION_NOT_CUBOID.toString());
            return;
        }
        Location firstLocation = selection.getMaximumPoint();
        Location secondLocation = selection.getMinimumPoint();
        manager.create(regionName, firstLocation, secondLocation);
        player.sendMessage(Messages.REGION_CREATED.toString(regionName));
    }
}
