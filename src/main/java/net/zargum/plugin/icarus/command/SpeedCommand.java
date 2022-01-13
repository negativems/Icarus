package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.utils.JavaUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedCommand extends ServerCommand<Icarus> {

    public SpeedCommand(){
        super("speed","Change your speed walking and flying.", "<number = 1 to 10> [player]", "flyspeed");
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        if (!JavaUtils.isFloat(args[0])) {
            player.sendMessage(getUsage());
            return;
        }

        float number = Float.parseFloat(args[0]);
        if (number > 10 || number < 0){
            player.sendMessage(getUsage());
            return;
        }

        number = number/10;

        if (args.length == 1) {
            if (player.isFlying()) {
                player.setFlySpeed(number);
                player.sendMessage(Messages.COMMAND_SPEED_FLY.toString(number+""));
                StaffUtil.log(Messages.LOG.toString(player.getName(), "Fly Speed", number + ""), player);
                return;
            }
            if (!player.isFlying()) {
                player.setWalkSpeed(number);
                player.sendMessage(Messages.COMMAND_SPEED_WALK.toString(number+""));
                StaffUtil.log(Messages.LOG.toString(player.getName(), "Walk Speed", number + ""), player);
                return;
            }
        }

        if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage(Messages.PLAYER_NOT_ONLINE.toString());
                return;
            }

            if (target.isFlying()) {
                target.setFlySpeed(number);
                target.sendMessage(Messages.COMMAND_SPEED_FLY.toString(target.getName(), number+""));
                player.sendMessage(Messages.COMMAND_SPEED_FLY_OTHER.toString(target.getName(), number+""));
                StaffUtil.log(Messages.LOG_TARGET.toString(player.getName(), target.getName(), "Fly Speed", number + ""), player);
                return;
            }

            target.setWalkSpeed(number);
            target.sendMessage(Messages.COMMAND_SPEED_WALK.toString(target.getName(), number+""));
            player.sendMessage(Messages.COMMAND_SPEED_WALK_OTHER.toString(target.getName(), number+""));
            StaffUtil.log(Messages.LOG_TARGET.toString(player.getName(), target.getName(), "Walk Speed", number + ""), player);
        }
    }
}
