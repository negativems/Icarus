package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.utils.TimeUtils;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlaytimeCommand extends ServerCommand<Icarus> {

    public PlaytimeCommand() {
        super("playtime", "Shows how time you played", "[player]", "pt");
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public boolean isRequiresPermission() {
        return false;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            String formattedTime = TimeUtils.formatDuration((long) player.getStatistic(Statistic.PLAY_ONE_TICK) *20*1000);
            String joinedTimes = (player.getStatistic(Statistic.LEAVE_GAME)+1)+"";
            player.sendMessage(Messages.COMMAND_PLAYTIME.toString(formattedTime, joinedTimes));
            return;
        }

        Player target = plugin.getServer().getPlayer(args[0]);
        if (target != null) {
            String formattedTime = TimeUtils.formatDuration(target.getStatistic(Statistic.PLAY_ONE_TICK)/20);
            String joinedTimes = (target.getStatistic(Statistic.LEAVE_GAME)+1)+"";
            player.sendMessage(Messages.COMMAND_PLAYTIME_OTHER.toString(formattedTime, joinedTimes));
            return;
        }

        player.sendMessage(Messages.PLAYER_NOT_ONLINE.toString());
    }
}
