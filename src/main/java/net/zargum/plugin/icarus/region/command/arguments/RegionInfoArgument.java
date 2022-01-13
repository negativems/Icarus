package net.zargum.plugin.icarus.region.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.region.Region;
import net.zargum.plugin.icarus.region.RegionManager;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.joptsimple.internal.Strings;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class RegionInfoArgument extends ServerCommandArgument<Icarus> {

    private final RegionManager manager;

    public RegionInfoArgument(ServerCommand<Icarus> command) {
        super(command, "info", "Get all info from a region", "[region]");
        manager = plugin.getRegionManager();
        addToTablistCompletion(0, manager.getAllRegionsName().toArray(new String[0]));
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            Player player = (Player) sender;
            if (manager.getRegionsByPlayer(player).size() == 0) {
                player.sendMessage(Messages.REGION_NOT_IN_REGION.toString());
                return;
            }
            List<Region> regions = manager.getRegionsByPlayer(player);
            List<String> regionsList = new ArrayList<>();
            for (Region region : regions) regionsList.add(region.getName());
            String regionsText = Strings.join(regionsList, "&f, &e");
            player.sendMessage(Messages.REGION_REGIONS.toString(regions.size()+"", regionsText));
            return;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.ARG_ONLY_PLAYER.toString());
            return;
        }

        String regionName = args[0];
        if (!manager.exist(regionName)) {
            sender.sendMessage(Messages.REGION_NOT_EXISTS.toString(regionName));
            return;
        }
        Region region = manager.getRegion(regionName);
        Location l1 = region.getFirstLocation();
        Location l2 = region.getSecondLocation();

        String worldName = region.getFirstLocation().getWorld().getName();
        String location1 = l1.getBlockX() + ", " + l1.getBlockY() + ", " + l1.getBlockZ();
        String location2 = l2.getBlockX() + ", " + l2.getBlockY() + ", " + l2.getBlockZ();
        String regionFlags = plugin.getFlagManager().getFormattedFlags(region.getFlags());

        sender.sendMessage(Messages.REGION_INFO.toString(regionName, worldName, location1, location2, regionFlags, region.getPriority()+""));
    }
}
