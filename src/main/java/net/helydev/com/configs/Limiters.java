package net.helydev.com.configs;

import net.helydev.com.xCore;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Limiters {
    private File file;
    private YamlConfiguration configuration;

    public void LimitersFile() {
        this.file = new File(xCore.getPlugin().getDataFolder(), "limiters.yml");
        if (!this.file.exists()) {
            xCore.getPlugin().saveResource("limiters.yml", false);
        }
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public void load() {
        this.file = new File(xCore.getPlugin().getDataFolder(), "limiters.yml");
        if (!this.file.exists()) {
            xCore.getPlugin().saveResource("limiters.yml", false);
        }
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public YamlConfiguration getConfiguration() {
        return this.configuration;
    }

    public File getFile() {
        return this.file;
    }

    public double getDouble(final String path) {
        if (this.configuration.contains(path)) {
            return this.configuration.getDouble(path);
        }
        return 0.0;
    }

    public int getInt(final String path) {
        if (this.configuration.contains(path)) {
            return this.configuration.getInt(path);
        }
        return 0;
    }

    public boolean getBoolean(final String path) {
        return this.configuration.contains(path) && this.configuration.getBoolean(path);
    }

    public String getString(final String path) {
        if (this.configuration.contains(path)) {
            return ChatColor.translateAlternateColorCodes('&', this.configuration.getString(path));
        }
        return "String at path: " + path + " not found!";
    }

    public List<String> getStringList(final String path) {
        if (this.configuration.contains(path)) {
            final ArrayList<String> strings = new ArrayList<String>();
            for (final String string : this.configuration.getStringList(path)) {
                strings.add(ChatColor.translateAlternateColorCodes('&', string));
            }
            return strings;
        }
        return Collections.singletonList("String List at path: " + path + " not found!");
    }
}

