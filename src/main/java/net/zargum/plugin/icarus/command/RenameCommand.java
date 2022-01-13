package net.zargum.plugin.icarus.command;

import net.md_5.bungee.api.ChatColor;
import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.command.ServerCommand;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RenameCommand extends ServerCommand<Icarus> {

    public RenameCommand(){
        super("rename","Rename item at your hand", "<text>", "rn");
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        ItemStack item = player.getItemInHand();
        String newName = null;
        if (item == null || item.getType() == Material.AIR) {
            player.sendMessage(Messages.NOT_HOLDING.toString());
            return;
        }

        ItemMeta meta = item.getItemMeta();
        String oldName = meta.getDisplayName();
        if (oldName != null) oldName = oldName.trim();

        if (!args[0].equalsIgnoreCase("none") && !args[0].equalsIgnoreCase("null"))
            newName = ChatColor.translateAlternateColorCodes('&', StringUtils.join(args, ' ', 0, args.length));

        if (oldName == null && newName == null) {
            player.sendMessage(Messages.COMMAND_RENAME_ALREADY_UNNAMED.toString());
            return;
        }

        if (oldName != null && oldName.equals(newName)) {
            player.sendMessage(Messages.COMMAND_RENAME_ALREADY_NAMED.toString());
            return;
        }

        meta.setDisplayName(newName);
        item.setItemMeta(meta);
        if (newName == null) {
            player.sendMessage(Messages.RENAMING_EMPTY_SUCCESS.toString());
            return;
        }
        player.sendMessage(Messages.RENAMING_SUCCESS.toString(newName));
    }
}
