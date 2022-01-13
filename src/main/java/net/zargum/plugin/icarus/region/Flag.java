package net.zargum.plugin.icarus.region;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum Flag {
        
    // Combat
    COMBAT,
    HIT_ANIMALS,
    HIT_MONSTERS,
    GET_DAMAGE,
    FALL_DAMAGE,
    ENDERPEARL,

    // Blocks
    BREAK,
    PLACE,
    INTERACT,
    OPEN_CHEST,
    FRAME_DESTROY,
    FRAME_INTERACT,

    // Natural
    FIRE_SPREAD,
    ENDERMAN_GRIEF,
    LAVA_SPREAD,
    ICE_FORM,
    MYCELIUM_SPREAD,
    VINE_GROWTH,
    CROP_GROWTH,
    SOIL_DRY,

    // Movement
    JOIN,
    SPAWN,

    // MOBS
    SPAWN_ANIMALS,
    SPAWN_MONSTERS,

    // Other
    HUNGER,
    DROP,
    PICKUP,
    EXPLOSION;

    public static List<String> getFlags() {
        List<String> result = new ArrayList<>();
        for (Flag flag : Flag.values()) result.add(flag.name());
        return result;
    }

}
