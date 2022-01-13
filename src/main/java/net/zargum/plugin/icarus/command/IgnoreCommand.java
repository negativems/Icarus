package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.player.profile.UserProfile;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.sounds.SoundUtil;
import net.zargum.zlib.sounds.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IgnoreCommand extends ServerCommand<Icarus> {

    public IgnoreCommand() {
        super("ignore", "Ignore player (chat messages and msg).", "<player>");
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
        UserProfile profile = plugin.getProfileManager().getProfile(player.getUniqueId());
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            player.sendMessage(Messages.PLAYER_NOT_ONLINE.toString());
            return;
        }

        if (profile.getIgnoredPlayers().contains(target.getUniqueId())) {
            SoundUtil.play(player, Sounds.SUCCESS);
            profile.getIgnoredPlayers().remove(target.getUniqueId());
            player.sendMessage(Messages.IGNORE_REMOVED.toString(target.getName()));
            return;
        }

        SoundUtil.play(player, Sounds.SUCCESS);
        profile.getIgnoredPlayers().add(target.getUniqueId());
        player.sendMessage(Messages.IGNORE_ADDED.toString(target.getName()));
    }
}
