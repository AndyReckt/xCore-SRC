package net.helydev.com.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Color {

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> translateFromArray(List<String> text) {
        List<String> messages = new ArrayList<>();
        for (String string : text) {
            messages.add(translate(string)); }
        return messages;
    }

    public static ItemStack nameItem(final ItemStack item, final short durability, final int amount) {
        final ItemMeta meta = item.getItemMeta();
        item.setItemMeta(meta);
        item.setAmount(amount);
        item.setDurability(durability);
        return item;
    }

    public static ItemStack nameItem(final Material item, final short durability, final int amount) {
        return nameItem(new ItemStack(item), durability, amount);
    }

    public static List<String> translate(List<String> s) {
        return s.stream().map(Color::translate).collect(Collectors.toList());
    }
}

