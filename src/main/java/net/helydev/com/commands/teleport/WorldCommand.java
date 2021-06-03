package net.helydev.com.commands.teleport;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WorldCommand {

    @Command(name = "world", permission = "core.command.world", inGameOnly = true)

    public void teleportWorld(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        if (args.length == 0) {
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("world.usage")));
            return;
        }
        World world = Bukkit.getWorld(args[0]);
        if (world == null) {
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("world.invalid")));
            return;
        }
        if (world.getName().equalsIgnoreCase(player.getWorld().getName())) {
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("world.already-in")));
            return;
        }
        player.teleport(new Location(world, 0, world.getHighestBlockYAt(0, 0) + 30, 0));
        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("world.teleported").replace("%world%", world.getName())));

    }
}
