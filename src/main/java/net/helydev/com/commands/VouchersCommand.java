package net.helydev.com.commands;

import net.helydev.com.utils.CC;
import net.helydev.com.utils.Color;
import net.helydev.com.utils.JavaUtils;
import net.helydev.com.utils.Utils;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class VouchersCommand {
    @Command(name = "voucher", permission = "core.command.voucher", inGameOnly = false)
    public void giveVoucher(final CommandArgs command) {
        final Player player = command.getPlayer();
        final String[] args = command.getArgs();
        if (args.length < 1) {
            for (String helpmessage : xCore.getPlugin().getMessageconfig().getConfiguration().getStringList("vouchers.help")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', helpmessage));
            }
        }
        else if (args[0].equalsIgnoreCase("give")) {
            if (args.length < 3) {
                for (String helpmessage : xCore.getPlugin().getMessageconfig().getConfiguration().getStringList("vouchers.help")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', helpmessage));
                }
            }
            else {
                final Player target = Bukkit.getPlayer(args[1]);
                if (!Utils.isOnline(player, target)) {
                    player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("vouchers.not-online").replace("%player%", args[1])));
                }
                else {
                    final Integer amount = JavaUtils.tryParseInt(args[2]);
                    if (amount == null) {
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("vouchers.invalid-number").replace("%number%", args[2])));
                    }
                    else if (amount <= 0) {
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("vouchers.not-positive")));
                    }
                    else if (amount > 64) {
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("vouchers.max")));
                    }
                    else {
                        int j = 0;
                        int h = 0;
                        for (final String Voucher : xCore.getPlugin().getVoucherConfig().getConfiguration().getConfigurationSection("vouchers").getKeys(false)) {
                            ++j;
                            if (!Voucher.equals(args[3])) {
                                ++h;
                            }
                        }
                        if (j != h) {
                            final String Voucher2 = args[3];
                            this.giveVoucherItem(target, amount, Voucher2);
                            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("vouchers.given").replace("%player%", target.getName())));
                            target.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("vouchers.given-target")));
                        }
                        else {
                            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("vouchers.not-exist")));
                        }
                    }
                }
            }
        }
        else if (args[0].equalsIgnoreCase("giveall")) {
            if (args.length < 2) {
                for (String helpmessage : xCore.getPlugin().getMessageconfig().getConfiguration().getStringList("vouchers.help")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', helpmessage));
                }
            }
            else {
                final Integer amount2 = JavaUtils.tryParseInt(args[1]);
                if (amount2 == null) {
                    player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("vouchers.invalid-number").replace("%number%", args[1])));
                    return;
                }
                if (amount2 <= 0) {
                    player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("vouchers.not-positive")));
                    return;
                }
                if (amount2 > 64) {
                    player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("vouchers.max")));
                    return;
                }
                int i = 0;
                int h2 = 0;
                for (final String Voucher2 : xCore.getPlugin().getVoucherConfig().getConfiguration().getConfigurationSection("vouchers").getKeys(false)) {
                    ++i;
                    if (!Voucher2.equals(args[2])) {
                        ++h2;
                    }
                }
                if (i != h2) {
                    final String Voucher3 = args[2];
                    for (final Player online : Bukkit.getServer().getOnlinePlayers()) {
                        this.giveVoucherItem(online, amount2, Voucher3);
                    }
                }
                else {
                    player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("vouchers.not-exist")));
                }
            }
        }
    }

    private void giveVoucherItem(final Player player, final int amount, final String vouchName) {
        final ItemStack stack = new ItemStack(Material.valueOf(xCore.getPlugin().getVoucherConfig().getConfiguration().getString("vouchers." + vouchName + ".item")), amount, (short)xCore.getPlugin().getConfig().getInt("vouchers." + vouchName + ".item-data"));
        final ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(CC.translate(xCore.getPlugin().getVoucherConfig().getConfiguration().getString("vouchers." + vouchName + ".name")));
        final List<String> lore = new ArrayList<>();
        for (final String string : xCore.getPlugin().getVoucherConfig().getConfiguration().getStringList("vouchers." + vouchName + ".lores")) {
            lore.add(CC.translate(string));
        }
        meta.setLore(lore);
        stack.setItemMeta(meta);
        player.getInventory().addItem(stack);
    }
}

