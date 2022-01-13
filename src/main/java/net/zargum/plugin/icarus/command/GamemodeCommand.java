package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.utils.GamemodeUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand extends ServerCommand<Icarus> {

    public GamemodeCommand() {
        super("gamemode", "Change game mode.", "<0|1|2|3|s|c|a|sp> [player]", "gm");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 1) {
                GameMode gamemode = GamemodeUtils.getGamemode(args[0]);
                if (gamemode != null) {
                    String beforeGamemodeName = player.getGameMode().name().toLowerCase();
                    String afterGamemodeName = GamemodeUtils.getName(args[0]);
                    player.setGameMode(gamemode);
                    player.sendMessage(Messages.COMMAND_GAMEMODE_SUCCESS.toString(gamemode.name().toLowerCase()));
                    StaffUtil.log(Messages.LOG_GAMEMODE_CHANGED.toString(player.getName(), beforeGamemodeName, afterGamemodeName), player);
                    return;
                }
                player.sendMessage(Messages.NOT_GAMEMODE.toString());
                return;
            }
        }
        if (args.length == 2) {
            String gamemode = args[0];
            Player target = Bukkit.getPlayer(args[1]);

            if (target != null) {
                if (GamemodeUtils.isGamemode(gamemode)) {
                    String beforeGamemodeName = target.getGameMode().name().toLowerCase();
                    String afterGamemodeName = GamemodeUtils.getName(gamemode);
                    target.setGameMode(GamemodeUtils.getGamemode(gamemode));
                    sender.sendMessage(Messages.COMMAND_GAMEMODE_OTHER_SUCCESS.toString(target.getName(), afterGamemodeName));
                    target.sendMessage(Messages.COMMAND_GAMEMODE_TARGET.toString(afterGamemodeName));
                    StaffUtil.log(Messages.LOG_GAMEMODE_CHANGED_TARGET.toString(sender.getName(), target.getName(), beforeGamemodeName, afterGamemodeName), (sender instanceof Player) ? (Player) sender : null);
                    return;
                }
                sender.sendMessage(Messages.NOT_GAMEMODE.toString());
                return;
            }
            sender.sendMessage(Messages.PLAYER_NOT_ONLINE.toString(args[1]));
        }
    }
}
