package net.helydev.com.commands;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.Utils;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FeedCommand {

    @Command(name = "feed", permission = "core.command.feed", inGameOnly = true)
    public void feed(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        if (args.length < 1) {
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("feed.fed")));
            player.setFoodLevel(20);
         } else if (player.hasPermission("core.command.feed.others")) {
            Player target = Bukkit.getPlayer(args[0]);
            if (!Utils.isOnline(player, target)) {
                Utils.PLAYER_NOT_FOUND(player, args[0]);
                return;
            }
            if (target.getFoodLevel() == 20) { //Do they have a full hunger bar? wtf
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("feed.already-has").replace("%target%", target.getName())));
                return;
            }
            if(target != null && target.getFoodLevel() == 20) {
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("feed.cannot-give-self")));
                return;
            }
            target.setFoodLevel(20);
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("feed.target-fed").replace("%executor%", player.getName())));
        }
    }
}
