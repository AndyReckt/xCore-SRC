package net.helydev.com.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Cooldowns {
    private static HashMap<String, HashMap<UUID, Long>> cooldown;

    public static void createCooldown(final String s) {
        if (Cooldowns.cooldown.containsKey(s)) {
            throw new IllegalArgumentException("Cooldown already exists.");
        }
        Cooldowns.cooldown.put(s, new HashMap<UUID, Long>());
    }

    public static HashMap<UUID, Long> getCooldownMap(final String s) {
        if (Cooldowns.cooldown.containsKey(s)) {
            return Cooldowns.cooldown.get(s);
        }
        return null;
    }

    public static void addCooldown(final String s, final Player player, final int n) {
        if (!Cooldowns.cooldown.containsKey(s)) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append(s).append(" does not exist")));
        }
        Cooldowns.cooldown.get(s).put(player.getUniqueId(), System.currentTimeMillis() + n * 1000L);
    }

    public static boolean isOnCooldown(final String s, final Player player) {
        return Cooldowns.cooldown.containsKey(s) && Cooldowns.cooldown.get(s).containsKey(player.getUniqueId()) && System.currentTimeMillis() <= Cooldowns.cooldown.get(s).get(player.getUniqueId());
    }

    public static int getCooldownForPlayerInt(final String s, final Player player) {
        return (int)(Cooldowns.cooldown.get(s).get(player.getUniqueId()) - System.currentTimeMillis()) / 1000;
    }

    public static long getCooldownForPlayerLong(final String s, final Player player) {
        return (int)(Cooldowns.cooldown.get(s).get(player.getUniqueId()) - System.currentTimeMillis());
    }

    public static void removeCooldown(final String s, final Player player) {
        if (!Cooldowns.cooldown.containsKey(s)) {
            throw new IllegalArgumentException(String.valueOf(new StringBuilder().append(s).append(" does not exist")));
        }
        Cooldowns.cooldown.get(s).remove(player.getUniqueId());
    }

    static {
        Cooldowns.cooldown = new HashMap<String, HashMap<UUID, Long>>();
    }
}

