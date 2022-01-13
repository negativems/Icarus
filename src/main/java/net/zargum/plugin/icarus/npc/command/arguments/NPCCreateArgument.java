package net.zargum.plugin.icarus.npc.command.arguments;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.plugin.icarus.npc.NPCManager;
import net.zargum.plugin.icarus.utils.StaffUtil;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.command.ServerCommandArgument;
import net.zargum.zlib.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NPCCreateArgument extends ServerCommandArgument<Icarus> {

    private final NPCManager manager;

    public NPCCreateArgument(ServerCommand<Icarus> command) {
        super(command, "create", "Creates an npc", "<id>");
        manager = plugin.getNpcManager();
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender commandSender, String label, String[] args) {
        String npcId = args[0];
        Player player = (Player) commandSender;

        if (manager.get(npcId) != null) {
            player.sendMessage(Messages.NPC_ALREADY_CREATED.toString());
            return;
        }

        Location location = player.getLocation();
        NPC npc = new NPC(plugin, npcId, location);
        npc.removeFromTablist();
        npc.create();

        for (Player target : Bukkit.getOnlinePlayers()) {
            npc.show(target);
        }

        manager.create(npc);
//        addToTablistCompletion(0, npcId);
        player.sendMessage(Messages.NPC_CREATED.toString(npcId));
        StaffUtil.log(Messages.LOG_NPC_CREATED_DELETED.toString(player.getName(), "created", npcId, location.getBlockX()+"", location.getBlockY()+"", location.getBlockZ()+"", location.getWorld().getName()), player);
    }
}
