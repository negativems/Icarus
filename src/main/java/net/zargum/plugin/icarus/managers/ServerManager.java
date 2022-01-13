package net.zargum.plugin.icarus.managers;

import lombok.Getter;
import net.zargum.plugin.icarus.Icarus;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServerManager {

    private final Icarus plugin;
    @Getter private final long defaultHelpopCooldown;
    private final Map<UUID, Long> cooldowns;

    public ServerManager(Icarus plugin) {
        this.plugin = plugin;
        this.defaultHelpopCooldown = 10;
        this.cooldowns = new HashMap<>();
    }

    public boolean hasHelpopCooldown(UUID uniqueId) {
        if (cooldowns.containsKey(uniqueId) && getHelpopCooldown(uniqueId) < 0) cooldowns.remove(uniqueId);
        return cooldowns.containsKey(uniqueId);
    }

    public Long getHelpopCooldown(UUID uniqueId) {
        if (!cooldowns.containsKey(uniqueId)) return null;
        long cooldownMillis = cooldowns.get(uniqueId);
        return defaultHelpopCooldown - (System.currentTimeMillis() - cooldownMillis);
    }
    public void setHelpopCooldown(UUID uniqueId) {
        cooldowns.put(uniqueId,System.currentTimeMillis());
    }
}
