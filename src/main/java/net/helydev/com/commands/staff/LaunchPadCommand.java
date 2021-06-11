package net.helydev.com.commands.staff;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.JavaUtils;
import net.helydev.com.utils.Utils;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LaunchPadCommand {
    @Command(name = "launchpad", permission = "core.command.launchpad", inGameOnly = true, aliases = {"launcher", "launchplate"})

    public boolean sendMessage(CommandArgs command) {
        Player sender = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("launch-pad.usage")));
        }
        else if (args[0].equalsIgnoreCase("give")) {
            if (args.length < 3) {
                sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("launch-pad.usage")));
            }
            else {
                final Player target = Bukkit.getPlayer(args[1]);
                if (!Utils.isOnline(sender, target)) {
                    sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("launch-pad.not-online")));
                }
                else {
                    final Integer amount = JavaUtils.tryParseInt(args[2]);
                    if (amount == null) {
                        sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("launchpad.invalid")));
                    }
                    else if (amount <= 0) {
                        sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("launch-pad.invalid")));
                    }
                    else if (amount > 64) {
                        sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("launch-pad.max")));
                    }
                    else {
                        this.giveVoucherItem(target, amount);
                        sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("launch-pad.given").replace("%player%", target.getName())));
                    }
                }
            }
        }
        return true;
    }

    private void giveVoucherItem(final Player player, final int amount) {
        final List<String> lore = new ArrayList<>();

        final ItemStack stack = new ItemStack(Material.GOLD_PLATE, amount);
        final ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(Color.translate("&d&lLaunch Pad &7(Right-Click)"));
        lore.add(Color.translate("&7Click to place your launch pad."));
        meta.setLore(lore);
        stack.setItemMeta(meta);
        player.getInventory().addItem(stack);
    }
}
