package net.zargum.plugin.icarus.staffmode;

import lombok.Getter;
import net.zargum.zlib.utils.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

// TODO: All the Staff System
public class StaffMode {

    @Getter private final ArrayList<Player> staffModeList = new ArrayList<>();

    public void vanishFromAll(Player player) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players != null) {
                players.showPlayer(player);
                players.hidePlayer(player);
            }
        }
    }

    public void setStaffMode(Player player) {
        if (!staffModeList.contains(player)) {
            staffModeList.add(player);

            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6You are in staff mode."));

            player.spigot().setCollidesWithEntities(false);
            player.setHealth(20D);
            player.setFoodLevel(20);
            player.setSaturation(20F);
            player.setAllowFlight(true);
            player.setGameMode(GameMode.CREATIVE);
            player.getInventory().clear();
            player.setCanPickupItems(true);

            player.getInventory().setItem(0, new ItemBuilder(Material.WATCH).setDisplayName(ChatColor.BLUE + "Alive Players").build());
            player.getInventory().setItem(1, new ItemBuilder(Material.DIAMOND_PICKAXE).setDisplayName(ChatColor.BLUE + "Alive Players " + ChatColor.GRAY + "(Y: -35)").build());
            player.getInventory().setItem(2, new ItemBuilder(Material.getMaterial(405)).setDisplayName(ChatColor.BLUE + "Alive Players " + ChatColor.GRAY + "(Nether)").build());
            player.getInventory().setItem(3, new ItemBuilder(Material.INK_SACK).setDurability(8).setDisplayName(ChatColor.BLUE + "Show Spectators").build());
            player.getInventory().setItem(7, new ItemBuilder(Material.BOOK).setDisplayName(ChatColor.BLUE + "Inventory Viewer").build());
            player.getInventory().setItem(6, new ItemBuilder(Material.COMPASS).setDisplayName(ChatColor.BLUE + "Go to center").build());
            player.getInventory().setItem(8, new ItemBuilder(Material.NETHER_STAR).setDisplayName(ChatColor.BLUE + "Miner Alerts").build());
            player.getInventory().setItem(5, new ItemBuilder(Material.INK_SACK).setDurability(8).setDisplayName(ChatColor.BLUE + "Unvanish").build());
            player.getInventory().setItem(4, new ItemBuilder(Material.CARPET).setDurability(14).setDisplayName(ChatColor.BLUE + "Better Vision").build());
        }
    }

}
