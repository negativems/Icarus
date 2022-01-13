package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.airdrop.Airdrop;
import net.zargum.zlib.command.ServerCommand;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DragonCommand extends ServerCommand<Icarus> {
    public DragonCommand() {
        super("dragon", "Spawns packet dragon");
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        Location destination = new Location(player.getWorld(), 0.0D, 115.0D, 0.0D);

        new Airdrop(plugin, destination, 160, 100, 140);
    }
}
