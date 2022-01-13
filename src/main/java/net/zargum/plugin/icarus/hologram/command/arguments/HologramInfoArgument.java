package net.zargum.plugin.icarus.hologram.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.hologram.HologramManager;
import net.zargum.plugin.icarus.hologram.command.HologramCommand;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.command.ServerCommandArgument;
import net.zargum.zlib.hologram.Hologram;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HologramInfoArgument extends ServerCommandArgument<Icarus> {

    private final HologramManager manager;

    public HologramInfoArgument(HologramCommand command) {
        super(command, "info", "Shows info of an hologram", "<id>", "i");
        manager = plugin.getHologramManager();
        addToTablistCompletion(0, manager.getHologramsId().toArray(new String[0]));
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        String hologramId = args[0];
        if (manager.getHologram(hologramId) == null) {
            sender.sendMessage(Messages.HOLOGRAM_NOT_EXISTS.toString(hologramId));
            return;
        }
        Hologram hologram = manager.getHologram(hologramId);
        String world = hologram.getLocation().getWorld().getName();
        String x = hologram.getLocation().getX() + "";
        String y = hologram.getLocation().getY() + "";
        String z = hologram.getLocation().getZ() + "";
        String autoUpdate = hologram.isAutoUpdate() + "";
        StringBuilder linesBuilder = new StringBuilder();
        int i = 0;
        for (String line : hologram.getLines()) {
            i++;
            linesBuilder.append(ChatColor.AQUA).append(i).append(". ").append(ChatColor.RESET).append(line).append("\n");
        }
        sender.sendMessage(Messages.HOLOGRAM_INFO.toString(hologramId, x, y, z, world, autoUpdate, linesBuilder.toString()));
    }
}
