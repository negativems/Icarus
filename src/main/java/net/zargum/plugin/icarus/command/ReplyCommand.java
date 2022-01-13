package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.player.profile.UserProfile;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.permissions.PermissionsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ReplyCommand extends ServerCommand<Icarus> {

    private final Icarus plugin;

    public ReplyCommand(Icarus plugin) {
        super("reply", "Reply a private message to the last player.", "<private message>", "r");
        this.plugin = plugin;
    }

    @Override
    public boolean isRequiresPermission() {
        return false;
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {

        Player player = (Player) sender;
        UserProfile playerProfile = plugin.getProfileManager().getProfile(player.getUniqueId());

        if (playerProfile.getReplyPlayer() != null){
            Player target = Bukkit.getPlayer(playerProfile.getReplyPlayer());
            if (target != null){
                String targetDisplayname = PermissionsManager.getDisplayname(target.getUniqueId().toString());

                StringBuilder m = new StringBuilder();
                for (String arg : Arrays.asList(args).subList(1, args.length)) m.append(arg).append(" ");
                String message = player.hasPermission(getPermission() + ".color") ? ChatColor.translateAlternateColorCodes('&', m.toString()) : m.toString();

                target.sendMessage(Messages.COMMAND_MSG_SENT.toString(targetDisplayname,message));
                return;
            }
            player.sendMessage(Messages.PLAYER_NOT_ONLINE.toString());
            return;
        }
        player.sendMessage(Messages.COMMAND_REPLY_EMPTY.toString());
    }
}
