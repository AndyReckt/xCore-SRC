package net.helydev.com.commands;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.ItemBuilder;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import net.minecraft.util.org.apache.commons.lang3.text.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SpawnerCommand {

    @net.helydev.com.utils.commands.Command(name = "spawner", permission = "core.command.spawner", inGameOnly = true)
    public boolean spawner(CommandArgs command) {
        Player sender = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("spawner.usage")));
            return false;
        }
        final String spawner = args[0];
        final Inventory inv = sender.getInventory();
        inv.addItem(new ItemBuilder(Material.MOB_SPAWNER).displayName(ChatColor.GREEN + "Spawner").loreLine(ChatColor.WHITE + WordUtils.capitalizeFully(spawner)).build());
        sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("spawner.given").replace("%spawner%", spawner)));
        return false;
    }
}
