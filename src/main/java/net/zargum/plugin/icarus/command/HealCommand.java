package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.command.ServerCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand extends ServerCommand<Icarus> {
    public HealCommand() {
        super("heal", "Regenerate all hearts or by amount", "[player]");
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            player.setHealth(player.getMaxHealth());
            player.setFoodLevel(20);
            player.sendMessage(Messages.COMMAND_HEALTH.toString());
            return;
        }

        if (args.length == 1) {
            if (Bukkit.getPlayer(args[0]) != null){
                Player target = Bukkit.getPlayer(args[0]);
                target.setHealth(target.getMaxHealth());
                target.setFoodLevel(20);
                target.sendMessage(Messages.COMMAND_HEALTH_RECIVED.toString());
                player.sendMessage(Messages.COMMAND_HEALTH_OTHER.toString(target.getName()));
                return;
            }

            player.sendMessage(Messages.PLAYER_NOT_ONLINE.toString());
            return;
        }

        player.sendMessage(getUsage());
    }
}
