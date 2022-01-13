package net.zargum.plugin.icarus.credits.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.player.profile.UserProfile;
import net.zargum.plugin.icarus.credits.command.arguments.CreditGiveArgument;
import net.zargum.plugin.icarus.credits.command.arguments.CreditInfoArgument;
import net.zargum.plugin.icarus.credits.command.arguments.CreditSetArgument;
import net.zargum.plugin.icarus.credits.command.arguments.CreditTakeArgument;
import net.zargum.zlib.command.HelpCommandArgument;
import net.zargum.zlib.command.ServerCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreditCommand extends ServerCommand<Icarus> {

    public CreditCommand() {
        super("credit", "Shows or manage credits from players.", "[set, give, take] [amount]", "credits");
        addArgument(new CreditGiveArgument(this));
        addArgument(new CreditGiveArgument(this));
        addArgument(new CreditSetArgument(this));
        addArgument(new CreditTakeArgument(this));
        addArgument(new CreditInfoArgument(this));

        HelpCommandArgument<Icarus> helpArgument = new HelpCommandArgument<>(this);
        helpArgument.setRequiresPermission(true);
        helpArgument.setPlayerOnly(false);
        addArgument(helpArgument);
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
        UserProfile profile = plugin.getProfileManager().getProfile(player.getUniqueId());

        int credits = profile.getCredits();
        player.sendMessage((credits > 0) ? Messages.COMMAND_CREDITS_INFO.toString("You", credits+"") : Messages.COMMAND_CREDITS_NO_CREDITS.toString("You"));
        player.sendMessage(Messages.COMMAND_CREDITS_MESSAGE.toString());
    }
}
