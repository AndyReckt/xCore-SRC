package net.helydev.com.listeners;

import net.helydev.com.utils.Color;
import net.helydev.com.xCore;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class RepairListener implements Listener {
    private void onClickRepair(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (event.getClickedBlock().getType() == Material.matchMaterial(xCore.getPlugin().getConfig().getString("settings.block-repair.item"))) {
            event.setCancelled(true);
        }

        if (event.getClickedBlock().getType() != Material.matchMaterial(xCore.getPlugin().getConfig().getString("settings.block-repair.item"))) {
            return;
        }

        ItemStack item = player.getItemInHand();

        if (item == null || item.getType() == Material.AIR) {
            player.sendMessage(Color.translate(xCore.getPlugin().getConfig().getString("settings.block-repair.no-item")));
            return;
        }

        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                item.setDurability((short) 0);
                player.sendMessage(Color.translate(xCore.getPlugin().getConfig().getString("settings.block-repair.repaired")));
                player.setItemInHand(item);
                player.updateInventory();

            }
        };
        player.setItemInHand(new ItemStack(Material.AIR));
        player.playSound(player.getLocation(),
                Sound.valueOf(xCore.getPlugin().getConfig().getString("settings.block-repair.sound")),
                1F, 1F);
        player.updateInventory();
    }
}