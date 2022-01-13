package net.zargum.plugin.icarus.utils;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityAmbient;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;

public class NametagEntity extends EntityAmbient {
    public NametagEntity(Player player) {
        super(((CraftWorld)player.getWorld()).getHandle());
        Location location = player.getLocation();
        this.setInvisible(true);
        this.setPosition(location.getX(), location.getY(), location.getZ());

        try {
            Field invulnerable = Entity.class.getDeclaredField("invulnerable");
            invulnerable.setAccessible(true);
            invulnerable.setBoolean(this, true);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        this.world.addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
        this.persistent = true;
        this.hideTag(player);
    }

    public void hideTag(Player player) {
        this.mount(((CraftPlayer)player).getHandle());
    }

    public void showTag() {
        this.mount(null);
    }

    public void h() {
        this.motX = this.motY = this.motZ = 0.0D;
        this.a(0.0F, 0.0F);
        this.a(0.0F, 0.0F, 0.0F);
    }

    public void o(Entity entity) {
    }
}
