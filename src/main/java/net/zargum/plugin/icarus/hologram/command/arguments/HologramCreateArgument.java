package net.zargum.plugin.icarus.hologram.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.hologram.HologramManager;
import net.zargum.plugin.icarus.hologram.command.HologramCommand;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommandArgument;
import net.zargum.zlib.hologram.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HologramCreateArgument extends ServerCommandArgument<Icarus> {

    private final HologramManager manager;

    public HologramCreateArgument(HologramCommand command) {
        super(command, "create", "Creates an hologram", "<id>");
        manager = plugin.getHologramManager();
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        String hologramId = args[0];
        Player player = (Player) sender;

        if (manager.getHologram(hologramId) != null) {
            player.sendMessage(Messages.HOLOGRAM_ALREADY_CREATED.toString(hologramId));
            return;
        }

        Location location = player.getLocation().add(0, -1.975 + 0.9875, 0);
        Hologram hologram = new Hologram(plugin, hologramId, location);
        hologram.addLine(hologramId);
        hologram.create();

        manager.addHologram(hologram);
//        addToTablistCompletion(0, hologramId);
        player.sendMessage(Messages.HOLOGRAM_CREATED.toString(hologramId));
        StaffUtil.log(Messages.LOG_HOLOGRAM_CREATED.toString(sender.getName(), hologramId), player);
    }
}
