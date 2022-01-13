package net.zargum.plugin.icarus.credits.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.player.profile.UserProfile;
import net.zargum.plugin.icarus.credits.command.CreditCommand;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommandArgument;
import net.zargum.zlib.utils.JavaUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreditSetArgument extends ServerCommandArgument<Icarus> {

    public CreditSetArgument(CreditCommand command) {
        super(command, "set", "Sets players credits", "<player> <amount>");
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

        String amountArg = args[1];
        if (!JavaUtils.isInt(amountArg)) {
            sender.sendMessage(Messages.NOT_NUMBER.toString());
            sender.sendMessage(getUsage());
            return;
        }

        UserProfile profile = plugin.getProfileManager().getProfile(target.getUniqueId());
        int credits = Integer.parseInt(amountArg);
        int creditsBefore = profile.getCredits();
        profile.setCredits(credits);
        sender.sendMessage(Messages.COMMAND_CREDITS_CHANGED.toString(target.getName(), creditsBefore + "", credits + ""));
        StaffUtil.log(Messages.LOG_CREDIT_SET.toString(sender.getName(), target.getName(), amountArg, creditsBefore+""), (sender instanceof Player) ? (Player) sender : null);
    }
}
