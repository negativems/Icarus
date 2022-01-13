package net.zargum.plugin.icarus.hologram.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.hologram.HologramManager;
import net.zargum.plugin.icarus.hologram.command.HologramCommand;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommandArgument;
import net.zargum.zlib.hologram.Hologram;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HologramMoveHereArgument extends ServerCommandArgument<Icarus> {

    private final HologramManager manager;

    public HologramMoveHereArgument(HologramCommand command) {
        super(command, "movehere", "Changes the location from an hologram", "<id>", "setlocation");
        manager = plugin.getHologramManager();
        addToTablistCompletion(0, manager.getHologramsId().toArray(new String[0]));
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        String hologramId = args[0];
        if (manager.getHologram(hologramId) == null) {
            sender.sendMessage(Messages.HOLOGRAM_NOT_EXISTS.toString(hologramId));
            return;
        }

        Player player = (Player) sender;
        Location location = player.getLocation().clone().add(0, -1.975, 0);

        Hologram hologram = manager.getHologram(hologramId);
        hologram.setLocation(location);
        hologram.update();

        player.sendMessage(Messages.HOLOGRAM_SET_LOCATION.toString(hologramId));
        StaffUtil.log(Messages.LOG_HOLOGRAM_UPDATE_LOCATION.toString(player.getName(), hologramId), player);
    }
}
