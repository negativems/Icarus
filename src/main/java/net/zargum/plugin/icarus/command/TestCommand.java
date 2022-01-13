package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.permissions.PermissionsManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class TestCommand extends ServerCommand<Icarus> {

    private final Icarus plugin;

    public TestCommand(Icarus plugin){
        super("test","Testing command.");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
//        sender.sendMessage(PermissionsManager.getPrimaryGroup((Player) sender));
//        sender.sendMessage(PermissionsManager.getGroupDisplayName(PermissionsManager.getPrimaryGroup((Player) sender)));
        Player player = (Player) sender;

        Location playerLocation = player.getLocation();
        Location dropLocation = new Location(player.getWorld(), 0, 100, 0);

        Vector playerLocationVector = playerLocation.toVector();
        Vector dropLocationVector = dropLocation.toVector();

        Location startLocation = playerLocation.subtract(dropLocation.getDirection().multiply(-3));

        Vector fromVector = startLocation.toVector();
        Vector toVector = dropLocation.toVector();

        Vector vector = toVector.subtract(fromVector);

        playerLocation.getWorld().spawnArrow(startLocation, vector, (float) 3, (float) 0);
    }
}
