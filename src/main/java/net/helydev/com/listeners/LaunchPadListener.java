package net.helydev.com.listeners;

import net.helydev.com.utils.Color;
import net.helydev.com.xCore;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class LaunchPadListener implements Listener {
    @Deprecated
    @EventHandler
    public void craftItem(PrepareItemCraftEvent e) {
        Material itemType = e.getRecipe().getResult().getType();
        if(itemType==Material.GOLD_PLATE) {
            e.getInventory().setResult(new ItemStack(Material.AIR));
            for(HumanEntity he:e.getViewers()) {
                if(he instanceof Player) {
                    ((Player)he).sendMessage(Color.translate(xCore.getPlugin().getConfig().getString("launch-pad.cannot-craft")));
                }
            }
        }
    }

    @Deprecated
    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        final Location from = event.getFrom();
        final Location to = event.getTo();
        if (from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ()) {
            return;
        }
        final Player player = event.getPlayer();
        final boolean gold_plate = player.getLocation().add(0.0, 0.0, 0.0).getBlock().getType().equals(Material.GOLD_PLATE);
        if (gold_plate) {
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("launch-pad.used")));
            player.setVelocity(player.getLocation().getDirection().multiply(4));
            player.setVelocity(new Vector(player.getVelocity().getX(), 1.0D, player.getVelocity().getZ()));



            if (xCore.getPlugin().getConfig().getBoolean("launch-pads.sound.enabled")) {
                player.playSound(player.getLocation(), Sound.valueOf(xCore.getPlugin().getConfig().getString("launch-pads.sound.name").toUpperCase()), 1.0F, 1.0F);
            }
            if (xCore.getPlugin().getConfig().getBoolean("launch-pads.particle")) {
                player.playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 0);
            }
        }
    }
}
