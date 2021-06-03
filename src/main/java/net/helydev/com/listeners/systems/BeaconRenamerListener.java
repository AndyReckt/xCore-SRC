package net.helydev.com.listeners.systems;

import net.helydev.com.utils.Color;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class BeaconRenamerListener implements Listener {
    HashMap<String, Long> cooldown = new HashMap<>();

    Random rand = new Random();

    public BeaconRenamerListener(final xCore plugin) {
        this.cooldown = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        int cooldownTime = 60;

        if (event.getAction() != Action.LEFT_CLICK_BLOCK)
            return;
        if (block.getType() != Material.BEACON)
            return;
        if (player.getItemInHand().getType() != Material.DIAMOND_SWORD) {
            player.sendMessage(Color.translate(xCore.getPlugin().getConfig().getString("beacon-rename.need-sword")));
            return;
        }
        if (this.cooldown.containsKey(player.getName())) {
            long secondsLeft = (Long) this.cooldown.get(player.getName()) / 1000L + cooldownTime
                    - System.currentTimeMillis() / 1000L;
            if (secondsLeft > 0L) {
                player.sendMessage(ChatColor.RED + "Sorry, you are currently on cooldown for " + secondsLeft + "s.");
                return;
            }
        }
        this.cooldown.put(player.getName(), System.currentTimeMillis());
        randomName(player);
        player.playSound(player.getLocation(), Sound.BLAZE_HIT, 2.0F, 1.0F);
    }

    public void randomName(Player player) {
        ItemStack item = player.getItemInHand();
        ItemMeta meta = item.getItemMeta();
        if (player.getItemInHand().getType() != Material.DIAMOND_SWORD) {
            player.sendMessage(ChatColor.RED + "You must have the sword in your hand while I choose the name.");
            return;
        }
        List<String> names = xCore.getPlugin().getConfig().getStringList("beacon-rename.name-list");
        String randomly = get(names);
        meta.setDisplayName(Color.translate(randomly));
        item.setItemMeta(meta);
        player.sendMessage(Color.translate("&eYour sword has been random renamed to &r" + randomly)
                + ChatColor.YELLOW.toString() + '.');
    }

    private String get(List<String> words) {
        return words.get((new Random()).nextInt(words.size()));
    }
}