package net.zargum.plugin.icarus.command;

import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.command.ServerCommand;
import net.zargum.zlib.sounds.SoundUtil;
import net.zargum.zlib.sounds.Sounds;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KillMobsCommand extends ServerCommand<Icarus> {

    public KillMobsCommand() {
        super("killmobs", "Remove entities or specific entities", "[entity type]", "removeentities, re, killmob");
    }

    @Override
    public boolean isPlayerOnly() {
        return true;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        World world = player.getWorld();
        List<EntityType> entityTypes = new ArrayList<>();
        int count = 0;

        if (args.length == 0) {
            entityTypes.add(EntityType.ARROW);
            entityTypes.add(EntityType.BAT);
            entityTypes.add(EntityType.BLAZE);
            entityTypes.add(EntityType.COW);
            entityTypes.add(EntityType.PIG);
            entityTypes.add(EntityType.HORSE);
            entityTypes.add(EntityType.SHEEP);
            entityTypes.add(EntityType.SQUID);
            entityTypes.add(EntityType.WITCH);
            entityTypes.add(EntityType.OCELOT);
            entityTypes.add(EntityType.SPIDER);
            entityTypes.add(EntityType.ZOMBIE);
            entityTypes.add(EntityType.CHICKEN);
            entityTypes.add(EntityType.CREEPER);
            entityTypes.add(EntityType.ENDERMAN);
            entityTypes.add(EntityType.GUARDIAN);
            entityTypes.add(EntityType.SKELETON);
            entityTypes.add(EntityType.PIG_ZOMBIE);
            entityTypes.add(EntityType.PRIMED_TNT);
            entityTypes.add(EntityType.DROPPED_ITEM);
        } else {
            for (String entity : args) {
                if (entity.contains(",")) entity = entity.substring(0, entity.length() - 1);
                entity = entity.toUpperCase();
                if (EnumUtils.isValidEnum(EntityType.class, entity)) entityTypes.add(EntityType.valueOf(entity));
            }
        }

        for (Chunk chunk : world.getLoadedChunks()) {
            for (Entity entity : chunk.getEntities()) {
                for (EntityType entityType : entityTypes) {
                    if (entity.getType().equals(entityType)) {
                        entity.remove();
                        count++;
                    }
                }
            }
        }

        StringBuilder entitiesString = new StringBuilder();
        int i = 1;
        for (EntityType entityType : entityTypes) {
            String entityName = entityType.name();
            entityName = entityName.replaceAll("_", " ");
            StringUtils.capitalize(entityName.toLowerCase());
            entitiesString.append(entityName);
            if (i < entityTypes.size()) entitiesString.append("&f,&7 ");
            i++;
        }
        SoundUtil.play(player, Sounds.SUCCESS);
        player.sendMessage(Messages.KILL_ENITIES.toString(entitiesString.toString(), count + ""));
    }
}
