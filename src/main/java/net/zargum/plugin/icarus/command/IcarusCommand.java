package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.zlib.command.ServerCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class IcarusCommand extends ServerCommand<Icarus> {

    private final Icarus plugin;

    public IcarusCommand(Icarus plugin){
        super("icarus","Icarus core main command.", "<reload>", "ic");
        this.plugin = plugin;
    }

    @Override
    public int getMinArgRequired() {
        return 1;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        plugin.reloadConfig();
        plugin.getPluginLoader().disablePlugin(plugin);
        plugin.getPluginLoader().enablePlugin(plugin);
        sender.sendMessage(ChatColor.GREEN + "Icarus config reloaded.");
    }
}
