package net.helydev.com.commands.donator;

import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;


public class EndPlayersCommand {
    @Command(name = "endplayers", permission = "core.command.endplayers", inGameOnly = true)
    public void EndPlayers(CommandArgs command) {
        CommandSender commandSender = command.getSender();
        World end = Bukkit.getServer().getWorld("world_the_end");
        int Endp = end.getPlayers().size();
        if(commandSender.hasPermission("core.command.endplayers")) {
            for (final String messages : xCore.getPlugin().getMessageconfig().getConfiguration().getStringList("endplayers.message")) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages).replace("%endplayers%", String.valueOf(Endp)));
            }
        }else {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', xCore.getPlugin().getMessageconfig().getConfiguration().getString("basic.no-permission")));
        }
    }
}
