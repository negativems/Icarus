package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.command.ServerCommand;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class EnderchestCommand extends ServerCommand<Icarus> {

    public EnderchestCommand() {
        super("enderchest", "Send a broadcast message.", "[player]", "ec");
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        Inventory enderchest = player.getEnderChest();
        if (args.length > 0) {
            OfflinePlayer target = plugin.getServer().getOfflinePlayer(args[0]);
            if (!target.hasPlayedBefore()) {
                player.sendMessage(Messages.PLAYER_NOT_EXISTS.toString());
                return;
            }
            enderchest = target.getPlayer().getEnderChest();
        }

        player.openInventory(enderchest);
    }
}
