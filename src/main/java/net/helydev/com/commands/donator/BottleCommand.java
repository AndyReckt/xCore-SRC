package net.helydev.com.commands.donator;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.base.ExperienceManager;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.Collections;

public class BottleCommand {

    @Command(name = "bottle", permission = "core.command.bottle", inGameOnly = true)
    public void bottlexp(CommandArgs command) {
        Player sender = command.getPlayer();
        ItemStack item = sender.getItemInHand();

        if (item == null || item.getType() != Material.GLASS_BOTTLE || item.getAmount() != 1) {
            sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("bottle.no-bottle")));
            return;
        }

        ExperienceManager manager = new ExperienceManager(sender);
        int experience = manager.getCurrentExp();
        manager.setExp(0.0D);

        if (experience == 0) {
            sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("bottle.no-xp")));
        }

        ItemStack result = new ItemStack(Material.EXP_BOTTLE);
        ItemMeta meta = result.getItemMeta();
        meta.setLore(Collections.singletonList(
                ChatColor.BLUE + "XP: " + ChatColor.WHITE + NumberFormat.getInstance().format(experience)
        ));
        result.setItemMeta(meta);

        sender.setItemInHand(result);
        sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("bottle.bottled").replace("%amount%", NumberFormat.getInstance().format(experience))));
        if (xCore.getPlugin().getMessageconfig().getConfiguration().getBoolean("bottle.sound")) {
            sender.playSound(sender.getLocation(), Sound.valueOf(xCore.getPlugin().getMessageconfig().getConfiguration().getString("bottle.sound-name").toUpperCase()), 1.0F, 1.0F);
        }
    }

}