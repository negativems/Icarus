package net.zargum.plugin.icarus.npc.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.npc.NPCManager;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import net.zargum.zlib.npc.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCDistanceArgument extends ServerCommandArgument<Icarus> {

    private final NPCManager manager;

    public NPCDistanceArgument(ServerCommand<Icarus> command) {
        super(command, "distance", "Adds hologram line over of an npc", "<id>");
        manager = plugin.getNpcManager();
        addToTablistCompletion(0, manager.getNPCsId().toArray(new String[0]));
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender commandSender, String label, String[] args) {
        Player player = (Player) commandSender;
        String npcId = args[0];
        if (manager.get(npcId) == null) {
            player.sendMessage(Messages.NPC_NOT_EXISTS.toString());
            return;
        }

        NPC npc = manager.get(npcId);
        if (player.getLocation().getWorld() != npc.getLocation().getWorld()) {
            player.sendMessage(Messages.NPC_WORLD_NOT_MATCH.toString());
            return;
        }

        int meters = (int) player.getLocation().distance(npc.getLocation());
        player.sendMessage(Messages.NPC_DISTANCE.toString(npcId, meters + ""));
    }
}
