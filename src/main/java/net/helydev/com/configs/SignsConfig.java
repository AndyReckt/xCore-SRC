package net.helydev.com.configs;

import net.helydev.com.xCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SignsConfig {
    public static File file;
    public static YamlConfiguration configuration;

    public static File getSignsConfig() {
        return new File(xCore.getPlugin().getDataFolder(), "signs.yml");
    }

    public static File getLicenceFile() {
        return new File(xCore.getPlugin().getDataFolder(), "signs.yml");
    }

    public static FileConfiguration getLicenceFileConfig() {
        return YamlConfiguration.loadConfiguration(getSignsConfig());
    }

    public void save() {
        try {
            configuration.save(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void setStandard() {
        SignsConfig.file = new File(xCore.getPlugin().getDataFolder(), "signs.yml");
        if (!SignsConfig.file.exists()) {
            xCore.getPlugin().saveResource("signs.yml", false);
        }
        SignsConfig.configuration = YamlConfiguration.loadConfiguration(SignsConfig.file);
    }
}

