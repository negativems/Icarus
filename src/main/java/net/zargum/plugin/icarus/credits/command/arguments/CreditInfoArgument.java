package net.zargum.plugin.icarus.credits.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.player.profile.UserProfile;
import net.zargum.plugin.icarus.credits.command.CreditCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreditInfoArgument extends ServerCommandArgument<Icarus> {

    public CreditInfoArgument(CreditCommand command) {
        super(command, "info", "Shows credits from a player", "<player>");
    }

    @Override
    public boolean isRequiresPermission() {
        return true;
    }

    @Override
    public boolean isPlayerOnly() {
        return false;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Messages.PLAYER_NOT_ONLINE.toString());
            sender.sendMessage(getUsage());
            return;
        }

        UserProfile profile = plugin.getProfileManager().getProfile(target.getUniqueId());
        int targetCredits = profile.getCredits();
        sender.sendMessage((targetCredits > 0) ? Messages.COMMAND_CREDITS_INFO.toString(target.getDisplayName(), targetCredits+"") : Messages.COMMAND_CREDITS_NO_CREDITS.toString(target.getDisplayName()));
    }
}
