package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.permissions.PermissionsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ListCommand extends ServerCommand<Icarus> {

    public ListCommand(){
        super("list","Get a list of all players online.", "", "players, online");
    }

    @Override
    public boolean isRequiresPermission() {
        return false;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {

        int onlineCount = Bukkit.getOnlinePlayers().size();
        int maxPlayers = Bukkit.getMaxPlayers();
        List<String> groups = PermissionsManager.getOrderedGroups();
        StringBuilder ranks = new StringBuilder();
        StringBuilder players = new StringBuilder();
        int i = 0;
        for (String group : groups) {
            ranks.append(PermissionsManager.getPrimaryColor(group)).append(group);
            if (i < groups.size()-1) ranks.append(ChatColor.WHITE + ", ");
            i++;
        }

        i = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            String playerGroup = PermissionsManager.getPrimaryGroup(player);
            ChatColor color = PermissionsManager.getPrimaryColor(playerGroup);
            players.append(color).append(player.getName());
            if (i < Bukkit.getOnlinePlayers().size()-1) players.append(ChatColor.WHITE + ", ");
            i++;
        }
        sender.sendMessage(Messages.COMMAND_LIST_RANKS.toString(ranks.toString()));
        sender.sendMessage(Messages.COMMAND_LIST_PLAYERS.toString(onlineCount+"", maxPlayers+"", players.toString()));
    }
}
