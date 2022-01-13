package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.events.PlayerRepairEvent;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.command.ServerCommand;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RepairCommand extends ServerCommand<Icarus> {

    public RepairCommand(){
        super("repair","Repairs item on your hand", "", "fix");
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        ItemStack item = player.getItemInHand();
        if (item == null || item.getType() == Material.AIR) {
            player.sendMessage(Messages.NOT_HOLDING.toString());
            return;
        }

        plugin.getServer().getPluginManager().callEvent(new PlayerRepairEvent(player, item));
    }
}
