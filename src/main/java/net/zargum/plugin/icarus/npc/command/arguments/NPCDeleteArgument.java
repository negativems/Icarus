package net.zargum.plugin.icarus.npc.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.npc.NPCManager;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCDeleteArgument extends ServerCommandArgument<Icarus> {

    private final NPCManager manager;

    public NPCDeleteArgument(ServerCommand<Icarus> command) {
        super(command, "delete", "Deletes an npc", "<id>");
        manager = command.getPlugin().getNpcManager();
        addToTablistCompletion(0, manager.getNPCsId().toArray(new String[0]));
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        String npcId = args[0];

        if (manager.get(npcId) == null) {
            sender.sendMessage(Messages.NPC_NOT_EXISTS.toString(npcId));
            return;
        }

        Location location = manager.get(npcId).getLocation();
        manager.delete(npcId);
//        removeFromTablistCompletion(0, npcId);
        sender.sendMessage(Messages.NPC_DELETED.toString(npcId));
        StaffUtil.log(Messages.LOG_NPC_CREATED_DELETED.toString(sender.getName(), "deleted", npcId, location.getBlockX()+"", location.getBlockY()+"", location.getBlockZ()+"", location.getWorld().getName()), (sender instanceof Player) ? (Player) sender : null);
    }
}
