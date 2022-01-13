package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.player.profile.UserProfile;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.permissions.PermissionsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class MsgCommand extends ServerCommand<Icarus> {

    private final Icarus plugin;

    public MsgCommand(Icarus plugin) {
        super("msg", "Sends a private message to the specified player.", "<player> <private message>", "tell");
        this.plugin = plugin;
    }

    @Override
    public boolean isRequiresPermission() {
        return false;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {

        if (args.length > 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                if (sender instanceof Player) {

                    Player player = (Player) sender;
                    if (player == target) {
                        player.sendMessage(Messages.COMMAND_MSG_SAME.toString());
                        return;
                    }

                    UserProfile playerProfile = plugin.getProfileManager().getProfile(player.getUniqueId());
                    UserProfile targetProfile = plugin.getProfileManager().getProfile(target.getUniqueId());
                    boolean targetIsStaff = target.hasPermission("staff");
                    boolean targetIsVanished = targetIsStaff && plugin.getProfileManager().getStaffProfile(target.getUniqueId()).isVanished();
                    if (!player.hasPermission("staff") && targetIsStaff && targetIsVanished) {
                        player.sendMessage(Messages.PLAYER_NOT_ONLINE.toString());
                        if (plugin.getProfileManager().getStaffProfile(target.getUniqueId()).isLogs()) {
                            target.sendMessage(Messages.COMMAND_MSG_TRIED_VANISHED.toString(player.getName()));
                        }
                        return;
                    }

                    StringBuilder m = new StringBuilder();
                    for (String arg : Arrays.asList(args).subList(1, args.length)) m.append(arg).append(" ");
                    String message = player.hasPermission(getPermission() + ".color") ? ChatColor.translateAlternateColorCodes('&',m.toString()) : m.toString();

                    String playerDisplayname = PermissionsManager.getDisplayname(player.getUniqueId().toString());
                    String targetDisplayname = PermissionsManager.getDisplayname(target.getUniqueId().toString());

                    player.sendMessage(Messages.COMMAND_MSG_SENT.toString(targetDisplayname, message));
                    target.sendMessage(Messages.COMMAND_MSG_RECIVED.toString(playerDisplayname, message));

                    player.playSound(target.getLocation(), Sound.FIZZ, 10F, 2F);
                    target.playSound(target.getLocation(), Sound.valueOf("mob.guardian.flop"), 5F, 10F);

                    playerProfile.setReplyPlayer(target.getUniqueId());
                    targetProfile.setReplyPlayer(player.getUniqueId());

                    return;
                }

                StringBuilder m = new StringBuilder();
                for (String arg : Arrays.asList(args).subList(1, args.length)) m.append(arg).append(" ");
                String message = ChatColor.translateAlternateColorCodes('&',m.toString());

                String senderDisplayname = sender.getName();
                String targetDisplayname = PermissionsManager.getDisplayname(target.getUniqueId().toString());

                sender.sendMessage(Messages.COMMAND_MSG_SENT.toString(targetDisplayname, message));
                target.sendMessage(Messages.COMMAND_MSG_RECIVED.toString(senderDisplayname, message));

                target.playSound(target.getLocation(), Sound.CHICKEN_EGG_POP, 10.0F, 2F);

                return;
            }
            sender.sendMessage(Messages.PLAYER_NOT_ONLINE.toString());
        }
    }
}
