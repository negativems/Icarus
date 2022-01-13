package net.zargum.plugin.icarus.region.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.region.RegionManager;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.joptsimple.internal.Strings;

import java.util.List;

public class RegionListArgument extends ServerCommandArgument<Icarus> {

    private final RegionManager manager;

    public RegionListArgument(ServerCommand<Icarus> command) {
        super(command, "list", "List of all regions");
        manager = plugin.getRegionManager();
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (manager.getAllRegions().size() == 0) {
            sender.sendMessage(Messages.REGION_LIST_EMPTY.toString());
            return;
        }
        List<String> regions = manager.getAllRegionsName();
        String regionsTextList = Strings.join(regions, "&f, &e");
        String regionsCount = regions.size()+"";
        sender.sendMessage(Messages.REGION_LIST.toString(regionsCount, regionsTextList));
    }
}
