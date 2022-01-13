package net.zargum.plugin.icarus.utils;

import net.minecraft.server.v1_8_R3.*;
import net.zargum.plugin.icarus.Icarus;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class EntityUtil {

    public static void walkToLocation(EntityLiving entity, Location location, double speed, Player player) {
        new BukkitRunnable() {
            public void run() {
                if (!player.isOnline()) cancel();

                Location current = new Location(entity.world.getWorld(), entity.locX, entity.locY, entity.locZ);
                System.out.println("Distance: " + location.distance(current) + " going from (" + current.getX() + ", " + current.getZ() + ") to (" + location.getX() + ", " + location.getZ() + ")");
                if(location.distance(current) > 0.3) {
                    float[] rotation = getRotations(current, location);
                    float yaw = rotation[0];
                    float pitch = rotation[1];
                    Vector direction = new Vector(-Math.sin(yaw * 3.1415927F / 180.0F) * (float) 1 * 0.5F, 0, Math.cos(yaw * 3.1415927F / 180.0F) * (float) 1 * 0.5F).multiply(speed);

                    if(current.getY() - location.getY() > 0 && entity.getBukkitEntity().isOnGround()) {
                        direction.setY(Math.min(0.42, current.getY() - location.getY()));
                    }

                    player.sendPacket(new PacketPlayOutEntityVelocity(entity.getId(), direction.getX(), direction.getY(), direction.getZ()));
                    player.sendPacket(new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(entity.getId(), (byte) direction.getBlockX(), (byte) direction.getBlockY(), (byte) direction.getBlockZ(), (byte) yaw, (byte) pitch, false));

                    System.out.println("Movement triggered");
                } else {
                    System.out.println("Movement finished");
                    this.cancel();
                }
            }
        }.runTaskTimer(Icarus.getInstance(), 0L, 1L);
    }

    public static float[] getRotations(Location one, Location two) {
        double diffX = two.getX() - one.getX();
        double diffZ = two.getZ() - one.getZ();
        double diffY = two.getY() + 2.0 - 0.4 - (one.getY() + 2.0);
        double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float) (-Math.atan2(diffY, dist) * 180.0 / 3.141592653589793);
        return new float[]{yaw, pitch};
    }

}
