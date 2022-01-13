package net.zargum.plugin.icarus.world.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.zlib.command.ServerCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class WorldsCommand extends ServerCommand<Icarus> {

    public WorldsCommand(Icarus plugin){
        super("worlds","List of all worlds.");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {

        sender.sendMessage(" ");
        sender.sendMessage(ChatColor.GOLD + "Worlds (" + Bukkit.getWorlds().size() + ")");
        for (World world : Bukkit.getWorlds()) sender.sendMessage(ChatColor.GRAY + "- " + ChatColor.YELLOW + world.getName());
    }
}
