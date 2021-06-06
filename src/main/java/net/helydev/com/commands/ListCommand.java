package net.helydev.com.commands;

import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ListCommand {
    @Command(name = "list", permission = "core.command.list", inGameOnly = true)
    public boolean onCommand(CommandArgs command) {
        Player p = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            for (String s : xCore.getPlugin().getMessageconfig().getConfiguration().getStringList("list.online")) {
                String formatted = s.replace("%online%", Integer.toString(Bukkit.getOnlinePlayers().length));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', formatted));
            }
            return true;
        }
        return false;
    }
}