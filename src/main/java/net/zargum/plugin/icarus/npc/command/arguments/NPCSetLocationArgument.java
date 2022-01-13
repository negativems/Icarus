package net.zargum.plugin.icarus.npc.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.npc.NPCManager;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import net.zargum.zlib.utils.JavaUtils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCSetLocationArgument extends ServerCommandArgument<Icarus> {

    private final NPCManager manager;

    public NPCSetLocationArgument(ServerCommand<Icarus> command) {
        super(command, "setlocation", "Change the location from a NPC", "<id>", "movehere");
        manager = plugin.getNpcManager();
        addToTablistCompletion(0, manager.getNPCsId().toArray(new String[0]));
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        String npcId = args[0];
        if (manager.get(npcId) == null) {
            sender.sendMessage(Messages.NPC_NOT_EXISTS.toString(npcId));
            return;
        }

        Player player = (Player) sender;
        Location location = player.getLocation().clone();

        float yaw = location.getYaw();
        float pitch = location.getPitch();
        if (args.length >= 3 && JavaUtils.isFloat(args[2])) yaw = Float.parseFloat(args[2]);
        if (args.length > 3 && JavaUtils.isFloat(args[3])) pitch = Float.parseFloat(args[3]);

        location.setYaw(yaw);
        location.setPitch(pitch);

        manager.setLocation(npcId, location);
        player.sendMessage(Messages.NPC_SET_LOCATION.toString(npcId));
        StaffUtil.log(Messages.LOG_NPC_UPDATE.toString(player.getName(), "location", npcId), player);
    }
}
