package net.zargum.plugin.icarus.region.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.region.Region;
import net.zargum.plugin.icarus.region.RegionManager;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegionRenameArgument extends ServerCommandArgument<Icarus> {

    private final RegionManager manager;

    public RegionRenameArgument(ServerCommand<Icarus> command) {
        super(command, "rename", "Renames region", "<region> <new name>");
        manager = plugin.getRegionManager();
        addToTablistCompletion(0, manager.getAllRegionsName().toArray(new String[0]));
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        String regionName = args[0];
        String renamedName = args[1];
        Region region = manager.getRegion(regionName);
        region.setName(renamedName);
        sender.sendMessage(Messages.REGION_RENAMED.toString(regionName, renamedName));
        StaffUtil.log(Messages.LOG_REGION_RENAMED.toString(sender.getName(), region.getName(), renamedName), (sender instanceof Player) ? (Player) sender : null);
    }
}
