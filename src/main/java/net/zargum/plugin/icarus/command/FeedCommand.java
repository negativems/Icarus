package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.command.ServerCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand extends ServerCommand<Icarus> {

    public FeedCommand() {
        super("feed","Fill food bar of player.", "[player]");
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0){
            player.setFoodLevel(20);
            player.setSaturation(20);
            player.sendMessage(Messages.COMMAND_FEED.toString());
            return;
        }
        if (args.length == 1){
            if (Bukkit.getPlayer(args[0]) != null){
                Player target = Bukkit.getPlayer(args[0]);
                target.setFoodLevel(20);
                target.setSaturation(20);
                target.sendMessage(Messages.COMMAND_FEED_RECIVED.toString());
                player.sendMessage(Messages.COMMAND_FEED_OTHER.toString(target.getName()));
                return;
            }
            player.sendMessage(Messages.PLAYER_NOT_ONLINE.toString());
        }
    }
}
