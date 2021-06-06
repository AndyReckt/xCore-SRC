package net.helydev.com.commands.staff;

import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class SetMOTDCommand {

    @Command(name = "setmotd", permission = "core.command.setmotd", inGameOnly = true)

    public boolean sendMessage(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if(args.length < 1){
            sender.sendMessage(ChatColor.RED + "You must specify a message.");
            return false;
        }

        String message = StringUtils.join(args, ' ', 0, args.length);
        MinecraftServer.getServer().setMotd(ChatColor.translateAlternateColorCodes('&', message));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou have set this servers MOTD to: &a" + message));
        return true;
    }
}
