package net.helydev.com.commands.donator;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class RenameCommand {

    public static List<String> DISALLOWED;

    @Command(name = "rename", permission = "core.command.rename", inGameOnly = true)

    public boolean sendMessage(CommandArgs command) {
        Player sender = command.getPlayer();
        String[] args = command.getArgs();
        ItemStack item = sender.getItemInHand();

        if (args.length < 1) {
            sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("rename.usage")));
            return true;
        }
        ItemStack stack = sender.getItemInHand();
        if (stack == null || stack.getType() == Material.AIR) {
            sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("rename.not-holding")));
            return true;
        }
        ItemMeta meta = stack.getItemMeta();
        String oldName = meta.getDisplayName();
        if (oldName != null) {
            oldName = oldName.trim();
        }
        String newName = args[0].equalsIgnoreCase("none") || args[0].equalsIgnoreCase("null") ? null : ChatColor.translateAlternateColorCodes('&', StringUtils.join(args, ' ', 0, args.length));
        if (oldName != null && oldName.equals(newName)) {
            sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("rename.already-named")));
            return true;
        }
        if (xCore.getPlugin().getConfig().getBoolean("rename-command.swords-only")) {
            if (!sender.hasPermission("core.rename.bypass")) {
                if (!item.getType().name().endsWith("_SWORD")) {
                    sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("rename.only-swords")));
                    return false;
                }
            }
        }

        if (newName != null) {
            final String lower = newName.toLowerCase();
            for (final String word : DISALLOWED) {
                if (lower.contains(word)) {
                    if (xCore.getPlugin().getConfig().getBoolean("rename-command.play-sound")) {
                        sender.playSound(sender.getLocation(), Sound.valueOf(xCore.getPlugin().getConfig().getString("rename-command.sound-name").toUpperCase()), 1.0F, 1.0F);
                        sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("rename.not-allowed")));
                        if (xCore.getPlugin().getConfig().getBoolean("rename-command.warn")) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), xCore.getPlugin().getConfig().getString("rename-command.warn-command"));
                            return true;
                        }
                    }
                }
            }
        }
        meta.setDisplayName(newName);
        stack.setItemMeta(meta);
        if (newName == null) {
            assert oldName != null;
            sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("rename.removed-name").replace("%name%", oldName)));
            return true;
        }
        sender.updateInventory();
        sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("rename.renamed")));
        return true;
    }

    static {
        DISALLOWED = (xCore.getPlugin().getConfig().getStringList("rename-command.blocked-names"));
    }
}

