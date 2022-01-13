package net.zargum.plugin.icarus.utils;

import org.bukkit.configuration.Configuration;

public class ConfigUtils {

    public static boolean isEmptyList(Configuration config, String path) {
        if (!config.contains(path)) throw new IllegalArgumentException("Path '" + path + "' not exists");
        return config.isList(path) && config.getList(path).isEmpty();
//        ConfigurationSection configurationSection = config.getConfigurationSection(path);
//        if (configurationSection != null) return configurationSection.getKeys(false).isEmpty();
    }

}
