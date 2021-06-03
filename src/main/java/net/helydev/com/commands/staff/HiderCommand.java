package net.helydev.com.commands.staff;

import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.helydev.com.xCore;
import net.helydev.com.utils.Color;

public class HiderCommand implements Listener {
    private static boolean status = false;

    @Command(name = "hider", permission = "core.command.hider", inGameOnly = true)
    public boolean onCommand(CommandArgs command) {
        Player sender = command.getPlayer();
        String[] args = command.getArgs();
        if (args.length < 1) {
            for (String msg : xCore.getPlugin().getMessageconfig().getConfiguration().getStringList("hider.help")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("on")) {
            for (int i = 0; i < (Bukkit.getOnlinePlayers()).length; i++) {
                Player test = Bukkit.getOnlinePlayers()[i];
                byte b;
                int j;
                Player[] arrayOfPlayer;
                for (j = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length, b = 0; b < j; ) {
                    Player players = arrayOfPlayer[b];
                    test.hidePlayer(players);
                    b++;
                }
            }
            Bukkit.broadcastMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("hider.enabled-broadcast").replace("%player%", sender.getName())));

            status = true;
            return true;
        }
        if (args[0].equalsIgnoreCase("off")) {
            for (int i = 0; i < (Bukkit.getOnlinePlayers()).length; i++) {
                Player test = Bukkit.getOnlinePlayers()[i];
                byte b;
                int j;
                Player[] arrayOfPlayer;
                for (j = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length, b = 0; b < j; ) {
                    Player players = arrayOfPlayer[b];
                    test.showPlayer(players);
                    b++;
                }
            }
            Bukkit.broadcastMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("hider.disabled-broadcast").replace("%player%", sender.getName())));
            status = false;
            return true;
        }
        return true;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (status) {
            byte b;
            int i;
            Player[] arrayOfPlayer;
            for (i = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length, b = 0; b < i; ) {
                Player players = arrayOfPlayer[b];
                event.getPlayer().hidePlayer(players);
                players.hidePlayer(event.getPlayer());
                b++;
            }
        }
    }
}


