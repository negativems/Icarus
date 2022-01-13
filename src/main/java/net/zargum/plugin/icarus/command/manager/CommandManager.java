package net.zargum.plugin.icarus.command.manager;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.command.*;
import net.zargum.plugin.icarus.hologram.command.HologramCommand;
import net.zargum.plugin.icarus.npc.command.NPCCommand;
import net.zargum.plugin.icarus.region.command.RegionCommand;
import net.zargum.plugin.icarus.credits.command.CreditCommand;
import net.zargum.plugin.icarus.world.command.WorldCommand;
import net.zargum.plugin.icarus.world.command.WorldsCommand;
import net.zargum.zlib.command.CommandRegister;

public class CommandManager extends CommandRegister<Icarus> {

    public CommandManager(Icarus plugin) {
        super(plugin);
        registerCommand(new DragonCommand());
        registerCommand(new RepairCommand());
        registerCommand(new CenterCommand());
        registerCommand(new EnderchestCommand());
        registerCommand(new HologramCommand());
        registerCommand(new MetadataCommand());
        registerCommand(new UniqueidCommand());
        registerCommand(new IgnoreCommand());
        registerCommand(new PlaytimeCommand());
        registerCommand(new CreditCommand());
        registerCommand(new BroadcastCommand());
        registerCommand(new EnchantCommand());
        registerCommand(new FeedCommand());
        registerCommand(new FlyCommand());
        registerCommand(new GamemodeCommand());
        registerCommand(new HealCommand());
        registerCommand(new HelpopCommand(plugin));
        registerCommand(new IcarusCommand(plugin));
        registerCommand(new ListCommand());
        registerCommand(new LogCommand(plugin));
        registerCommand(new MsgCommand(plugin));
        registerCommand(new NPCCommand());
        registerCommand(new RegionCommand());
        registerCommand(new KillMobsCommand());
        registerCommand(new RenameCommand());
        registerCommand(new ReplyCommand(plugin));
        registerCommand(new SetspawnCommand(plugin));
        registerCommand(new SkullCommand());
        registerCommand(new SkullinfoCommand(plugin));
        registerCommand(new SpawnCommand(plugin));
        registerCommand(new SpeedCommand());
        registerCommand(new TeleportCommand());
        registerCommand(new TestCommand(plugin));
        registerCommand(new TopCommand());
        registerCommand(new WorldCommand(plugin));
        registerCommand(new WorldsCommand(plugin));
    }
}