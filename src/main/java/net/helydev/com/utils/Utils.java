package net.helydev.com.utils;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class Utils {
    public static boolean isOnline(CommandSender sender, Player player) {
        return player != null && (!(sender instanceof Player) || ((Player)sender).canSee(player));
    }
    public static void PLAYER_NOT_FOUND(CommandSender sender, String player) {
        sender.sendMessage(Color.translate("&6Player '&f" + player + "&6' not found."));
    }

    public static String formatLongMin(long time) {
        long totalSecs = time / 1000L;
        return String.format("%02d:%02d", totalSecs / 60L, totalSecs % 60L);
    }

    public static String formatIntMin(int time) {
        return String.format("%02d:%02d", time / 60, time % 60);
    }

    public static DecimalFormat getDecimalFormat() {
        return new DecimalFormat("0.0");
    }

    public static String stringifyLocation(final Location location) {
        return "[" + location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch() + "]";
    }
}
