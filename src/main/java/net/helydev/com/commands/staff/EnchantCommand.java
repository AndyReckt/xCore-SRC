package net.helydev.com.commands.staff;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import net.helydev.com.xCore;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.util.org.apache.commons.lang3.math.NumberUtils;

public class EnchantCommand {

    public EnchantCommand() {
        final xCore plugin;
    }

    @Command(name = "enchant", permission = "core.command.enchant", inGameOnly = true)
    public boolean enchanter(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (player != null) {
            if (player.hasPermission("core.command.enchant")) {
                if (args.length < 2 || args.length > 3) {
                    player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("enchant-command.usage")));
                    return true;
                }

                if (args.length == 3) {
                    if (player == null) {
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("enchant-command.not-online")));
                        return true;
                    }

                    Enchantment enchantment = Enchantment.getByName(args[1].toUpperCase());

                    int level = -1;
                    if (NumberUtils.isNumber(args[2]))
                        level = Integer.parseInt(args[2]);

                    if (level == -1) {
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("enchant-command.invalid")));
                        return true;
                    }

                    if (enchantment == null) {
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("gamemode.invalid-message").replace("%args%", args[0].toUpperCase())));
                        return true;
                    }

                    player.getItemInHand().addUnsafeEnchantment(enchantment, level);
                    player.sendMessage((ChatColor.WHITE + "Enchanted %player% with %enchantment% %level%.").replace("%player%", player.getName())
                            .replace("%enchantment%", enchantment.getName()).replace("%level%", "" + level));
                } else {
                    Enchantment enchantment = Enchantment.getByName(args[0].toUpperCase());

                    int level = -1;
                    if (NumberUtils.isNumber(args[1]))
                        level = Integer.parseInt(args[1]);

                    if (level == -1) {
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("enchant-command.invalid")));
                        return true;
                    }

                    if (enchantment == null) {
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("gamemode.invalid-message").replace("%args%", args[0].toUpperCase())));
                        return true;
                    }

                    ((Player) player).getItemInHand().addUnsafeEnchantment(enchantment, level);
                    player.sendMessage((ChatColor.WHITE + "Enchanted %player% with %enchantment% %level%.").replace("%player%", ((Player) player).getDisplayName())
                            .replace("%enchantment%", enchantment.getName()).replace("%level%", "" + level));
                }
            }
        }
        return true;
    }
}


