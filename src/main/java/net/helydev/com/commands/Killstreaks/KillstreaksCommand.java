package net.helydev.com.commands.Killstreaks;

import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.ChatColor;
import net.helydev.com.utils.commands.Command;
import org.bukkit.command.CommandSender;

public class KillstreaksCommand {

    @Command(name = "killstreaks", permission = "core.command.killstreaks", inGameOnly = true)

    public void sendMessage(CommandArgs command) {
        CommandSender commandSender = command.getSender();
        String[] args = command.getArgs();
        if (args.length == 0) {
            for (String msg : xCore.getPlugin().getMessageconfig().getConfiguration().getStringList("killstreaks.list")) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            }
        }
    }
}

