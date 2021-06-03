package net.helydev.com.commands;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class CoreCommand implements Listener {

    @Command(name = "core", aliases = {"dev", "andrew"})
    public void sendMessage(CommandArgs command) {
        CommandSender player = command.getSender();
        String[] args = command.getArgs();
        if(args.length!=1) {
            player.sendMessage(Color.translate("&7&m---*--------------------*---"));
            player.sendMessage(Color.translate("&7▶ &ePlugin: &fxCore v2"));
            player.sendMessage("");
            player.sendMessage(Color.translate("&7▶ &eVersion: &f" + xCore.getPlugin().getDescription().getVersion()));
            player.sendMessage(Color.translate("&7▶ &eDescription: &f" + xCore.getPlugin().getDescription().getDescription()));
            player.sendMessage(Color.translate("&7▶ &eAuthor: &fLeandroSSJ & AndrewSSJ"));
            player.sendMessage("");
            player.sendMessage(Color.translate("&7▶ &cDiscord&7: &fhttps://discord.gg/mb7uw7QnAV"));
            player.sendMessage(Color.translate("&7&m---*--------------------*---"));
        } else if(player.hasPermission("core.reload")) {
            if (player.hasPermission("core.reload")) {
                if (args[0].equalsIgnoreCase("reload")) {
                    xCore.getPlugin().reload();
                    player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("core-command.reloaded")));
                }
            }
            if (player.hasPermission("core.version")) {
                if (args[0].equalsIgnoreCase("version")) {
                    player.sendMessage(Color.translate("&a[xCore] &7This server is currently running &6&l" + xCore.getPlugin().getDescription().getVersion() + "&7."));
                }
            }
        }
    }
}
