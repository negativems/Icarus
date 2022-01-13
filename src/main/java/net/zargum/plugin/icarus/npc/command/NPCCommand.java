package net.zargum.plugin.icarus.npc.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.npc.command.arguments.*;
import net.zargum.zlib.command.HelpCommandArgument;
import net.zargum.zlib.command.ServerCommand;
import org.bukkit.command.CommandSender;

public class NPCCommand extends ServerCommand<Icarus> {

    public NPCCommand() {
        super("npc", "Manage npcs.");

        // Arguments
        addArgument(new NPCAddLineArgument(this));
        addArgument(new NPCRemoveLineArgument(this));
        addArgument(new NPCCreateArgument(this));
        addArgument(new NPCDeleteArgument(this));
        addArgument(new NPCHideNameArgument(this));
        addArgument(new NPCInfoArgument(this));
        addArgument(new NPCListArgument(this));
        addArgument(new NPCSetArmorArgument(this));
        addArgument(new NPCSetDisplayNameArgument(this));
        addArgument(new NPCSetHandArgument(this));
        addArgument(new NPCSetLocationArgument(this));
        addArgument(new NPCSetSkinArgument(this));
        addArgument(new NPCUpdateArgument(this));
        addArgument(new NPCShowNameArgument(this));
        addArgument(new NPCDistanceArgument(this));
        addArgument(new HelpCommandArgument<>(this));
    }

    @Override
    public int getMinArgRequired() {
        return 1;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {



    }
}
