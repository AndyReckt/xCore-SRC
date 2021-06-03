package net.helydev.com.listeners;

import net.helydev.com.utils.BukkitUtils;
import net.helydev.com.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class VoidGlitchFixListener implements Listener {

    public VoidGlitchFixListener() {
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            Entity entity = event.getEntity();
            if (entity instanceof Player) {
                Location destination = BukkitUtils.getHighestLocation(entity.getLocation());
                if (destination != null && entity.teleport(destination, PlayerTeleportEvent.TeleportCause.PLUGIN)) {
                    event.setCancelled(true);
                    entity.teleport(Bukkit.getWorld("world").getSpawnLocation());
                    ((Player) entity).sendMessage(Color.translate("&eYou were teleported to spawn."));
                }
            }
        }
    }
}
