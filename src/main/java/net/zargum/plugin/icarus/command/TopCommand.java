package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.sounds.SoundUtil;
import net.zargum.zlib.sounds.Sounds;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TopCommand extends ServerCommand<Icarus> {

    public TopCommand(){
        super("top","Teleports you to the top block of your position", "");
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        Location playerLocation = player.getLocation().clone();
        double x = playerLocation.getX();
        double y = playerLocation.getWorld().getHighestBlockAt(playerLocation).getY();
        double z = playerLocation.getZ();
        float yaw = playerLocation.getYaw();
        float pitch = playerLocation.getPitch();

        player.teleport(new Location(player.getWorld(), x, y+1, z, yaw, pitch));
        player.sendMessage(Messages.COMMAND_TOP_TELEPORTED.toString());
        SoundUtil.play(player, Sounds.SUCCESS);
    }
}
