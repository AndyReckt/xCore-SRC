package net.helydev.com.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Builder {

    public static List<String> colorList(List<String> list) {
        ArrayList<String> newList = new ArrayList<>();
        for (String string : list) {
            newList.add(Color.translate(string));
        }
        return newList;
    }

    public static ItemStack nameItem(ItemStack item, String name, short durability, int amount, List<String> lores) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Color.translate(name));
        meta.setLore(colorList(lores));
        item.setItemMeta(meta);
        item.setAmount(amount);
        item.setDurability(durability);
        return item;
    }

    public static ItemStack nameItem(Material item, String name, short durability, int amount, List<String> lores) {
        return nameItem(new ItemStack(item), name, durability, amount, lores);

    }

}

