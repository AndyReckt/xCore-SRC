package net.helydev.com.commands.staff;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CustomEnchantmentCommand {
    @Command(name = "customenchant", permission = "core.command.customenchant", inGameOnly = true, aliases = {"ce"})

    public void sendMessage(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        final Player player = (Player)sender;
        final ItemStack item = player.getInventory().getItemInHand();
        if (item.getType() == Material.AIR) {
            player.sendMessage(Color.translate("&cYou need a object in your hand."));
            return;
        }
        if (args.length < 1) {
            this.getUsage(player);
            return;
        }
        if (args[0].equalsIgnoreCase("speed")) {
            final ItemMeta itemMeta = item.getItemMeta();
            final List<String> lore = new ArrayList<>();
            if (itemMeta.hasLore()) {
                lore.addAll(itemMeta.getLore());
            }
            lore.add(Color.translate(xCore.getPlugin().getConfig().getString("settings.custom-enchants.Speed-Lore")));
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            player.sendMessage(Color.translate("&b" + Color.translate(xCore.getPlugin().getConfig().getString("settings.custom-enchants.Speed-Lore") + " added inventory!")));
            return;
        }
        if (args[0].equalsIgnoreCase("fire")) {
            final ItemMeta itemMeta = item.getItemMeta();
            final List<String> lore = new ArrayList<>();
            if (itemMeta.hasLore()) {
                lore.addAll(itemMeta.getLore());
            }
            lore.add(Color.translate(xCore.getPlugin().getConfig().getString("settings.custom-enchants.Fire-Resistance-Lore")));
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            player.sendMessage(Color.translate("&b" + Color.translate(xCore.getPlugin().getConfig().getString("settings.custom-enchants.Fire-Resistance-Lore")) + " added inventory!"));
            return;
        }
        player.sendMessage(Color.translate("&cThis effect doesn´t exist!"));
    }

    public void getUsage(final Player player) {
        player.sendMessage("");
        player.sendMessage(Color.translate("&9&lCustom Enchants"));
        player.sendMessage(Color.translate(" &f» &bSpeed"));
        player.sendMessage(Color.translate(" &f» &bFire Resistance"));
        player.sendMessage("");
    }
}

