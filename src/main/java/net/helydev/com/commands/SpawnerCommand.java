package net.helydev.com.commands;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.ItemBuilder;
import net.helydev.com.utils.commands.CommandArgs;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

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
        player.getInventory().addItem(new ItemBuilder(Material.MOB_SPAWNER).displayName(Color.translate("&r&a" + spawner + " Spawner")).build());
        player.sendMessage(Color.translate("&eYou just got a &a" + spawner + " Spawner&e."));
        return false;
    }

}
