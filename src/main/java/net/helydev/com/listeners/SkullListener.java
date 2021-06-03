package net.helydev.com.listeners;

import net.helydev.com.utils.Color;
import net.helydev.com.xCore;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SkullListener implements Listener {
    public SkullListener() {
    }

    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            final Player player = event.getPlayer();
            final BlockState state = event.getClickedBlock().getState();
            if (state instanceof Skull) {
                final Skull skull = (Skull)state;

                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("skull.click").replace("%player%", skull.getOwner())));
            }
        }
    }
}