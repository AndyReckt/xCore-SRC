package net.helydev.com.commands;

import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.ChatColor;
import net.helydev.com.utils.commands.Command;
import org.bukkit.command.CommandSender;

public class HelpCommand {

    @Command(name = "help", permission = "core.command.help", inGameOnly = true)

    public void sendMessage(CommandArgs command) {
        CommandSender commandSender = command.getSender();
        String[] args = command.getArgs();
        if (args.length == 0) {
            for (String msg : xCore.getPlugin().getMessageconfig().getConfiguration().getStringList("help.help-command")) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            }
        }
    }
}
