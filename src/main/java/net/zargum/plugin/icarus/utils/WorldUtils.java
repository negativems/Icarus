package net.zargum.plugin.icarus.utils;

import net.minecraft.server.v1_8_R3.DedicatedServer;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class WorldUtils {

    public static String getMainWorld() {
        return ((DedicatedServer) MinecraftServer.getServer()).propertyManager.properties.get("level-name").toString();
    }

}
