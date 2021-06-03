package net.helydev.com.listeners;

import net.helydev.com.utils.Color;
import net.helydev.com.xCore;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class CoreListener implements Listener {

    public CoreListener() {
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getEnvironment() == World.Environment.NETHER && event.getBlock().getState() instanceof CreatureSpawner) {
            event.setCancelled(true);
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("listeners.break-spawner-nether")));
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getEnvironment() == World.Environment.NETHER && event.getBlock().getState() instanceof CreatureSpawner) {

            event.setCancelled(true);
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("listeners.place-spawner-nether")));
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerJoinKit(final PlayerJoinEvent event) {
        final Player p = event.getPlayer();
        for (String msg : xCore.getPlugin().getMessageconfig().getConfiguration().getStringList("joinmessage.message")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
    }
}
