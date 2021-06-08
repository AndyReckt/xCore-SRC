package net.helydev.com.commands.broadcast;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BroadcastCommand {

    @Command(name = "broadcast", permission = "core.command.broadcast", inGameOnly = false, aliases = {"bc"})
    public void sendBroadcast(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        StringBuilder r = new StringBuilder();
        if (args.length < 1) {
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("broadcast.usage")));
        } else {
            for (String arg : args) {
                r.append(arg).append(" ");
            }
            r = new StringBuilder(r.toString().replace("&", "§"));
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&4[Alert] &f" + r));
        }
    }

}
