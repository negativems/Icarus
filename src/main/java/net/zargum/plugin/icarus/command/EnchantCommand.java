package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.utils.Enchantments;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.utils.JavaUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class EnchantCommand extends ServerCommand<Icarus> {

    public EnchantCommand() {
        super("enchant", "Enchant item in hand.", "<enchant> [level]");
        List<String> tabCompletion = new ArrayList<>();
        Enchantments.ENCHANTMENTS.forEach((s, enchantment) -> tabCompletion.add(s));
        Enchantments.ALIASENCHANTMENTS.forEach((s, enchantment) -> tabCompletion.add(s));
        addToTablistCompletion(0, tabCompletion.toArray(new String[0]));
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        final ItemStack item = player.getItemInHand();

        if (item == null || item.getType() == Material.AIR) {
            player.sendMessage(Messages.NOTHING_IN_HAND.toString());
            return;
        }

        if (args.length == 0) {
            final Set<String> allowed = new TreeSet<>();
            final Set<String> other = new TreeSet<>();
            for (Map.Entry<String, Enchantment> entry : Enchantments.entrySet()) {
                final String enchantmentName = entry.getValue().getName();
                if (entry.getValue().canEnchantItem(item)) allowed.add(enchantmentName);
                else other.add(enchantmentName);
            }
            player.sendMessage(Messages.COMMAND_ENCHANT_LIST.toString(String.join(", ", allowed), String.join(", ", other)));
            return;
        }

        Enchantment enchantment = Enchantments.getByName(args[0]);
        if (enchantment == null) {
            player.sendMessage(Messages.COMMAND_ENCHANT_UNKNOWN.toString());
            return;
        }

        int level = 0;
        if (args.length > 1) {
            if (JavaUtils.isInt(args[1])) level = Integer.parseInt(args[1]);
        }

        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 10F, 2F);
        ItemMeta meta = item.getItemMeta();

        if (level == 0) {
            level = meta.getEnchantLevel(enchantment) + 1;
        }

        if (level == -1) {
            if (meta.hasEnchant(enchantment)) {
                player.sendMessage(Messages.COMMAND_ENCHANT_REMOVED.toString(toString(enchantment.getName())));
                meta.removeEnchant(enchantment);
                item.setItemMeta(meta);
                return;
            }
            player.sendMessage(Messages.COMMAND_ENCHANT_CANT_REMOVE.toString(enchantment.getName()));
            return;
        }

        if (meta.hasEnchant(enchantment)) {
            if (meta.getEnchantLevel(enchantment) == level) {
                player.sendMessage(Messages.COMMAND_ENCHANT_CANT_UPDATE.toString());
                return;
            }
            player.sendMessage(Messages.COMMAND_ENCHANT_UPDATED.toString(toString(enchantment.getName())));
        } else {
            player.sendMessage(Messages.COMMAND_ENCHANT_ADDED.toString(toString(enchantment.getName())));
        }

        meta.addEnchant(enchantment, level, true);
        item.setItemMeta(meta);
        player.updateInventory();
    }

    private String toString(String enchantment) {
        return enchantment.replaceAll("_", " ").toLowerCase();
    }
}
