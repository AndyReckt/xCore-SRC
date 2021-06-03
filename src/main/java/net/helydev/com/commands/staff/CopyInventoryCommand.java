package net.helydev.com.commands.staff;

import net.helydev.com.utils.commands.CommandArgs;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * This command was added by DevDipin!
 *
 * This allows staff to rollback a players old inventory, from their previous death.
 *
 * Date: 5/28/2021
 * Updated: 5/28/2021
 */

public class CopyInventoryCommand {

    @net.helydev.com.utils.commands.Command(name = "copyinv", permission = "core.command.copyinv", inGameOnly = true)
    public boolean copytheinventory(CommandArgs command) {
        Player sender = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /copyinv <player>");
            return false;
        }

        Player target = Bukkit.getServer().getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer with name or UUID with '" + args[0] + "' not found."));
        } else {
            sender.getInventory().setContents(target.getInventory().getContents());
            sender.getInventory().setArmorContents(target.getInventory().getArmorContents());
            sender.sendMessage(ChatColor.WHITE + "You have copied the inventory of " + ChatColor.AQUA + target.getName());
        }
        return false;
    }
}
