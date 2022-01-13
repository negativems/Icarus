package net.zargum.plugin.icarus.airdrop;

import lombok.Getter;
import net.minecraft.server.v1_8_R3.*;
import net.zargum.plugin.icarus.Icarus;
import net.zargum.plugin.icarus.messages.Messages;
import net.zargum.zlib.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class Airdrop extends BukkitRunnable {

    private final Icarus plugin;

    private final Location destination;
    private final EntityLiving entityDragon;
    private int movement = 0;
    private final int totalBlocksToTravel; // 100 blocks
    private final int movements; // 100 movements
    private final int height; // 140
    private final Map<UUID, EnderDragonPosition> locations = new HashMap<>(); // Saves dragon starting point location of all players when task start

    public Airdrop(Icarus plugin, Location destination, int movements, int travelTotalBlocks, int height) {
        this.plugin = plugin;
        this.destination = destination;
        this.destination.setY(height);
        this.movements = movements;
        this.totalBlocksToTravel = travelTotalBlocks;
        this.height = height;

        entityDragon = new EntityEnderDragon(((CraftWorld) destination.getWorld()).getHandle());
        entityDragon.locY = height;

        init();
    }

    private void init() {
        // Announce
        plugin.getServer().broadcastMessage(Messages.AIRDROP_ANNOUNCE.toString());

        for (Player player : Bukkit.getOnlinePlayers()) {
            Vector vector = destination.clone().getDirection().multiply(((double) totalBlocksToTravel / 2) * -1);

            Location startLocation = player.getLocation().subtract(vector.getX(), 0, vector.getZ());
            startLocation.setY(height);
            Location endLocation = destination.clone().add(0,0,30);

            EnderDragonPosition dragonPosition = new EnderDragonPosition(startLocation, endLocation);
            dragonPosition.setCurrentLocation(startLocation);

            System.out.println("Dragon Start Location = " + LocationUtils.toStringNoYawPitch(startLocation));
            System.out.println("Dragon End Location = " + LocationUtils.toStringNoYawPitch(endLocation));
            System.out.println("Blocks Distance = " + startLocation.distance(endLocation));

            locations.put(player.getUniqueId(), dragonPosition);
            entityDragon.setLocation(startLocation.getX(), height, startLocation.getZ(), startLocation.getYaw(), startLocation.getPitch());
            player.sendPacket(new PacketPlayOutSpawnEntityLiving(entityDragon));
        }

        runTaskTimerAsynchronously(plugin, 0, 1);
    }

    @Override
    public void run() {
        if (Bukkit.getOnlinePlayers().size() == 0) cancel();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.isOnline()) cancel();

            // Location current = new Location(entity.world.getWorld(), entity.locX, entity.locY, entity.locZ);
            EnderDragonPosition dragonPosition = locations.get(player.getUniqueId());
            Location currentLocation = dragonPosition.getCurrentLocation();
            Location endLocation = dragonPosition.getEndLocation();
            System.out.println("Distance: " + endLocation.distance(currentLocation) + " going from (" + currentLocation.getX() + ", " + currentLocation.getZ() + ") to (" + endLocation.getX() + ", " + endLocation.getZ() + ")");

            Vector direction = endLocation.toVector().subtract(currentLocation.toVector());
            if (dragonPosition.getPosition() != null) System.out.println("Last direction: " + dragonPosition.getPosition().distance(direction));
            dragonPosition.setPosition(direction);

            if (currentLocation.getY() - endLocation.getY() > 0 && entityDragon.onGround) {
                direction.setY(Math.min(0.42, currentLocation.getY() - endLocation.getY()));
            }

            player.sendPacket(new PacketPlayOutEntityVelocity(entityDragon.getId(), direction.getX(), direction.getY(), direction.getZ())); // si la end location
            player.sendPacket(new PacketPlayOutEntity.PacketPlayOutRelEntityMove(entityDragon.getId(), (byte) direction.getBlockX(), (byte) direction.getBlockY(), (byte) direction.getBlockZ(), false));

            if (movement >= movements - 1) player.sendPacket(new PacketPlayOutEntityDestroy(entityDragon.getId()));
        }
        movement++;
        if (movement >= movements) cancel();
    }
}
