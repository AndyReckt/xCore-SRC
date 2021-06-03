package net.helydev.com.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import net.helydev.com.xCore;
import net.md_5.bungee.api.ChatColor;

public class WhitelistListener implements Listener {

    public WhitelistListener() {
    }

    @EventHandler
    public void handleKicks(PlayerLoginEvent e) {
        if (e.getResult() == PlayerLoginEvent.Result.KICK_WHITELIST)
            e.setKickMessage(ChatColor.translateAlternateColorCodes('&', xCore.getPlugin().getConfig().getString("settings.server.whitelist-message")));
    }
}
