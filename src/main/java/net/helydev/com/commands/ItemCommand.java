package net.helydev.com.commands;

/**
 * This command was added by DevDipin!
 *
 * This repairs the players item.
 *
 * Date: 5/26/2021
 * Updated: 5/26/2021
 */

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class ItemCommand {
    @Command(name = "repair", permission = "core.command.repair", inGameOnly = true)
    public void Items(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        if (args.length < 1) {
            Material itemType = Material.matchMaterial(xCore.getPlugin().getConfig().getString("settings.block-repair.item"));
            ItemStack itemStack = new ItemStack(itemType);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(Color.translate(xCore.getPlugin().getConfig().getString("settings.block-repair.title")));
            for (String lore : xCore.getPlugin().getMessageconfig().getConfiguration().getStringList("settings.block-repair.lore")) {
                itemMeta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', lore)));
            }
            itemStack.setItemMeta(itemMeta);
            player.getInventory().addItem(itemStack);
            player.sendMessage(Color.translate(xCore.getPlugin().getConfig().getString("settings.block-repair.repaired")));
        }
    }
}