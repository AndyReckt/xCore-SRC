package net.helydev.com.commands.broadcast;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BroadcastRawCommand {

    @Command(name = "broadcastraw", permission = "core.command.broadcastraw", inGameOnly = true, aliases = {"bcraw"})
    public void senBroadcastRaw(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        StringBuilder r = new StringBuilder();
        if (args.length < 1) {
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("bcraw.usage")));
        } else {
            for (String arg : args) {
                r.append(arg).append(" ");
            }
            r = new StringBuilder(r.toString().replace("&", "§"));
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', r.toString()));
        }
    }
}
