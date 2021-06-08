package net.helydev.com.commands;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand {
    @Command(name = "spawn", permission = "core.command.spawn", inGameOnly = true)
    public void spawn(CommandArgs command) {
        CommandSender sender = command.getSender();
        Player player = (Player) sender;
        String[] args = command.getArgs();
        if (player.hasPermission("core.command.spawn.bypass")) {
            sendLocation(player);
        }

        if (args.length < 1) {
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("spawn.self")));
            sendLocation(player);
        } else if (player.hasPermission("core.command.spawn.bypass")) {
            Player target = Bukkit.getPlayer(args[0]);
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("spawn.other").replace("%target%", target.getName())));

            sendLocation(target);
        }
    }

    public static void sendLocation(Player player) {
        World w = Bukkit.getServer().getWorld(xCore.getPlugin().getConfig().getString("spawn.world"));
        double x = xCore.getPlugin().getConfig().getDouble("spawn.x");
        double y = xCore.getPlugin().getConfig().getDouble("spawn.y");
        double z = xCore.getPlugin().getConfig().getDouble("spawn.z");
        float yaw = (float) xCore.getPlugin().getConfig().getDouble("spawn.yaw");
        float pitch = (float) xCore.getPlugin().getConfig().getDouble("spawn.pitch");
        player.teleport(new Location(w, x, y, z, yaw, pitch));
    }
}
