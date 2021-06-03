package net.helydev.com.commands.staff;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public class LoreCommand {

    @net.helydev.com.utils.commands.Command(name = "lore", permission = "core.command.lore", inGameOnly = true)

    public void sendMessage(CommandArgs command) {
        Player sender = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("lore.usage")));
            return;
        }

        ItemStack stack = sender.getItemInHand();

        if (stack == null) {
            sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("lore.not-holding")));
            return;
        }

        ItemMeta meta = stack.getItemMeta();

        String text = ChatColor.translateAlternateColorCodes('&', StringUtils.join(args, ' ', 0, args.length));
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>(2);

        lore.add(text);
        meta.setLore(lore);

        stack.setItemMeta(meta);

        //Added
        sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("lore.added")));


    }

}
