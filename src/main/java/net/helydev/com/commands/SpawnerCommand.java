package net.helydev.com.commands;

/**
 * This command was added by DevDipin!
 *
 * Gives you the specified spawner name.
 *
 * Date: 5/25/2021
 * Updates: 5/25/2021
 */

import net.helydev.com.utils.Color;

import net.helydev.com.utils.ItemBuilder;
import net.helydev.com.utils.commands.CommandArgs;
import net.minecraft.util.org.apache.commons.lang3.text.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class SpawnerCommand {

    @net.helydev.com.utils.commands.Command(name = "spawner", permission = "core.command.spawner", inGameOnly = true)
    public boolean spawner(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "/spawner <entity>");
            return false;
        }
        String spawner = args[0];
        Player p = (Player)player;
        Inventory inv = (Inventory)p.getInventory();
        inv.addItem(new ItemStack[] { new ItemBuilder(Material.MOB_SPAWNER).displayName(ChatColor.GREEN + spawner + " Spawner").loreLine(ChatColor.WHITE + WordUtils.capitalizeFully(spawner)).build() });
        p.sendMessage(Color.translate("&eYou just got a &a" + spawner + " Spawner&e."));
        return false;
    }

    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        return Collections.emptyList();
    }
}
