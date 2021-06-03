package net.helydev.com.commands.media;

import net.helydev.com.utils.SymbolUtil;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class YoutuberCommand {

    @Command(name = "youtuber", permission = "core.command.youtuber", inGameOnly = true)

    public void sendMessage(CommandArgs command) {
        CommandSender commandSender = command.getSender();
        String[] args = command.getArgs();
        if (args.length == 0) {
            for (String msg : xCore.getPlugin().getMessageconfig().getConfiguration().getStringList("youtuber.message")) {
                msg = msg.replace("%d_arrow%", SymbolUtil.UNICODE_ARROWS_RIGHT);
                commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            }
        }
    }
}
