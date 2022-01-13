package net.zargum.plugin.icarus.hologram.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.hologram.HologramManager;
import net.zargum.plugin.icarus.hologram.command.HologramCommand;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.command.ServerCommandArgument;
import net.zargum.zlib.utils.JavaUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HologramNearArgument extends ServerCommandArgument<Icarus> {

    private final HologramManager manager;

    public HologramNearArgument(HologramCommand command) {
        super(command, "near", "Shows you the nearest holograms by distance", "[distance]");
        manager = plugin.getHologramManager();
        addToTablistCompletion(0, manager.getHologramsId().toArray(new String[0]));
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender commandSender, String label, String[] args) {
        Player player = (Player) commandSender;
        String distanceArg = args.length > 0 ? args[0] : "20";
        if (!JavaUtils.isDouble(distanceArg)) {
            player.sendMessage(Messages.NOT_NUMBER.toString());
            return;
        }
        double distance = Double.parseDouble(distanceArg);

        String[] holograms = manager.getHologramsIdsByDistance(player.getLocation(), distance);
        player.sendMessage(Messages.HOLOGRAM_NEAR.toString(holograms.length + "", distance + "", formatHologramList(holograms)));
    }

    private String formatHologramList(String[] holograms) {
        StringBuilder result = new StringBuilder();

        for (String id : holograms) {
            result.append(ChatColor.YELLOW).append(id).append(ChatColor.WHITE).append(", ");
        }

        StringUtils.join(holograms, ChatColor.GRAY + ", " + ChatColor.YELLOW);

        return result.toString();
    }
}
