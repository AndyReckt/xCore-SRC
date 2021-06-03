package net.helydev.com.utils;

import net.helydev.com.xCore;

public class ConfigEnd {
    public static xCore plugin;

    public static String getString(final String string) {
        return ConfigEnd.plugin.getConfig().getString("setend." + string);
    }
}
