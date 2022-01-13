package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.player.profile.StaffProfile;
import net.zargum.zlib.command.ServerCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LogCommand extends ServerCommand<Icarus> {

    private final Icarus plugin;

    public LogCommand(Icarus plugin){
        super("log","Allows to see server logs", "", "logs");
        this.plugin = plugin;
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        StaffProfile staffProfile = plugin.getProfileManager().getStaffProfile(player.getUniqueId());
        String status = !staffProfile.isLogs() ? "activated" : "desactivated";

        staffProfile.setLogs(!staffProfile.isLogs());
        player.sendMessage(Messages.COMMAND_LOG.toString(status));
    }
}
