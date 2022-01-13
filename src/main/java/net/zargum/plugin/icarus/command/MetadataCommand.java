package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.command.ServerCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MetadataCommand extends ServerCommand<Icarus> {

    public MetadataCommand() {
        super("metadata", "Gets the metadata from item hand.", "", "md");
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
            player.sendMessage(Messages.NOT_HOLDING.toString());
            return;
        }

        ItemStack item = player.getItemInHand();
        player.sendMessage(Messages.COMMAND_METADATA.toString(item.getItemMeta().toString(), item.getItemMeta().getItemFlags().toString()));
    }
}
