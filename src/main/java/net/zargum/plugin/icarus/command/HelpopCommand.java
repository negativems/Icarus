package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.sounds.SoundUtil;
import net.zargum.zlib.sounds.Sounds;
import net.zargum.zlib.utils.JavaUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpopCommand extends ServerCommand<Icarus> {

    private final Icarus plugin;

    public HelpopCommand(Icarus plugin){
        super("helpop","Send a message to staff members.", "<message>", "request, rq");
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
        if (plugin.getServerManager().hasHelpopCooldown(player.getUniqueId())){
            long cooldown = plugin.getServerManager().getHelpopCooldown(player.getUniqueId());
            player.sendMessage(Messages.COMMAND_HELPOP_COOLDOWNED.toString(JavaUtils.format(cooldown)));
            return;
        }

        if (StaffUtil.getStaffCount() == 0) player.sendMessage(Messages.NO_STAFF_ONLINE.toString());
        plugin.getServerManager().setHelpopCooldown(player.getUniqueId());
        StringBuilder message = new StringBuilder();
        for (String arg : args) message.append(arg).append(" ");

        player.sendMessage(Messages.COMMAND_HELPOP_SENT.toString());
        SoundUtil.play(player, Sounds.SUCCESS);
        StaffUtil.message(Messages.REQUIRE_ASSISTANCE.toString(player.getName(), message.toString()));
    }
}
