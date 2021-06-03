package net.helydev.com.commands.links;

import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class StoreCommand {

    @Command(name = "store", permission = "core.command.store", inGameOnly = true)

    public void sendMessage(CommandArgs command) {
        CommandSender commandSender = command.getSender();
        String[] args = command.getArgs();
        if (args.length == 0) {
            for (String msg : xCore.getPlugin().getMessageconfig().getConfiguration().getStringList("store.message")) {
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            }
        }
    }
}
