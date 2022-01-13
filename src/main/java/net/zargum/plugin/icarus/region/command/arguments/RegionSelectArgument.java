package net.zargum.plugin.icarus.region.command.arguments;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;
import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.region.Region;
import net.zargum.plugin.icarus.region.RegionManager;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegionSelectArgument extends ServerCommandArgument<Icarus> {

    private final RegionManager manager;

    public RegionSelectArgument(ServerCommand<Icarus> command) {
        super(command, "select", "Select region with worldedit", "<region>");
        manager = plugin.getRegionManager();
        addToTablistCompletion(0, manager.getAllRegionsName().toArray(new String[0]));
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        String regionName = args[0];
        if (!manager.exist(regionName)) {
            player.sendMessage(Messages.REGION_NOT_EXISTS.toString(regionName));
            return;
        }

        Region region = manager.getRegion(regionName);
        Selection selection = new CuboidSelection(region.getFirstLocation().getWorld(), region.getFirstLocation(), region.getSecondLocation());
        plugin.getWorldEdit().setSelection(player, selection);
        player.sendMessage(Messages.REGION_SELECTED.toString(regionName));
    }
}
