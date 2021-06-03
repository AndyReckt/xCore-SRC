package net.helydev.com.utils.api;

import net.helydev.com.xCore;
import org.bukkit.configuration.file.FileConfiguration;

public final class Configuration {

    public static void init(final FileConfiguration config) {
        final String DONATE_URL = xCore.getPlugin().getConfig().getString("server.donate-url");
    }
}

