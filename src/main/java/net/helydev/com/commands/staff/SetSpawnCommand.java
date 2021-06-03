package net.helydev.com.commands.staff;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand {
    @Command(name = "setspawn", permission = "core.command.setspawn", inGameOnly = true)
    public boolean SetSpawns(CommandArgs command) {
        CommandSender sender = command.getSender();
        Player player = (Player) sender;

        if (sender.hasPermission("core.command.setspawn")) {
            xCore.getPlugin().getConfig().set("spawn.world", player.getLocation().getWorld().getName());
            xCore.getPlugin().getConfig().set("spawn.x", player.getLocation().getX());
            xCore.getPlugin().getConfig().set("spawn.y", player.getLocation().getY());
            xCore.getPlugin().getConfig().set("spawn.z", player.getLocation().getZ());
            xCore.getPlugin().getConfig().set("spawn.yaw", player.getLocation().getYaw());
            xCore.getPlugin().getConfig().set("spawn.pitch", player.getLocation().getPitch());
            xCore.getPlugin().saveConfig();
            player.sendMessage(ChatColor.GREEN + "Spawn set!");
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("spawn.setspawn.set")));
            return true;
        }
    return false;
}
}



