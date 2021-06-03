package net.helydev.com.commands.donator;

import com.google.common.collect.Lists;
import net.helydev.com.utils.Color;
import net.helydev.com.utils.base.ExperienceManager;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BottleCommand {

    private static final String BOTTLED_EXP_DISPLAY_NAME = ChatColor.GOLD + "XP Bottle";

    private ItemStack createExpBottle(int experience) {
        ItemStack stack = new ItemStack(Material.EXP_BOTTLE, 1);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(BOTTLED_EXP_DISPLAY_NAME);
        meta.setLore(Lists.newArrayList(ChatColor.WHITE.toString() + experience + ChatColor.YELLOW + " XP"));
        stack.setItemMeta(meta);
        return stack;
    }

    @Command(name = "bottle", permission = "core.command.bottle", inGameOnly = true)
    public boolean bottlexp(CommandArgs command) {
        Player player = command.getPlayer();

        if (player.getExp() == 0.0f) {
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("bottle.no-xp")));
            return true;
        }


        player.getInventory().addItem(createExpBottle(new ExperienceManager(player).getCurrentExp()));
        player.setLevel(0);
        player.setExp(0);
        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("bottle.botteled")));
        return true;
    }


}

