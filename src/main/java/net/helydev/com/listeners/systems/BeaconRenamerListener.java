package net.helydev.com.listeners.systems;

import net.helydev.com.utils.CC;
import net.helydev.com.utils.Color;
import net.helydev.com.xCore;
import net.minecraft.util.gnu.trove.map.TObjectLongMap;
import net.minecraft.util.gnu.trove.map.hash.TObjectLongHashMap;
import net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BeaconRenamerListener implements Listener {
    private static long BEACON_COOLDOWN_DELAY;
    public static TObjectLongMap<UUID> BEACON_COOLDOWN;

    public BeaconRenamerListener() {
    }

    private static String get(final List<String> event) {
        return event.get(new Random().nextInt(event.size()));
    }

    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getClickedBlock();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && block.getState() instanceof Beacon) {
            if (event.getAction() != Action.LEFT_CLICK_BLOCK)
                return;
            if (block.getType() != Material.BEACON)
                return;
            if (player.getItemInHand().getType() != Material.DIAMOND_SWORD) {
                player.sendMessage(Color.translate(xCore.getPlugin().getConfig().getString("beacon-rename.messages.no-sword")));
                return;
            }
            event.setCancelled(true);
            final UUID uuid = player.getUniqueId();
            final long timestamp = BeaconRenamerListener.BEACON_COOLDOWN.get(uuid);
            final long millis = System.currentTimeMillis();
            final long remaining = (timestamp == BeaconRenamerListener.BEACON_COOLDOWN.getNoEntryValue()) ? -1L : (timestamp - millis);
            if (remaining > 0L) {
                player.sendMessage(ChatColor.RED + "You cannot use this beacon for another " + ChatColor.BOLD + DurationFormatUtils.formatDurationWords(remaining, true, true) + ".");
                return;
            }
            BeaconRenamerListener.BEACON_COOLDOWN.put(player.getUniqueId(), System.currentTimeMillis() + BeaconRenamerListener.BEACON_COOLDOWN_DELAY);
            this.randomName(player);
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 2.0f, 1.0f);
        }
    }

    public void randomName(final Player event) {
        final List<String> rename = xCore.getPlugin().getConfig().getStringList("beacon-rename.name-list");
        new BukkitRunnable() {
            private int count;

            public void run() {
                if (this.count == 1) {
                    final String string = get(Color.translate(rename));
                    event.sendMessage(Color.translate(xCore.getPlugin().getConfig().getString("beacon-rename.messages.one").replace("%rename%", string)));
                }
                if (this.count == 2) {
                    final String string = get(Color.translate(rename));
                    event.sendMessage(Color.translate(xCore.getPlugin().getConfig().getString("beacon-rename.messages.two").replace("%rename%", string)));
                }
                if (this.count == 3) {
                    final String string = get(Color.translate(rename));
                    event.sendMessage(Color.translate(xCore.getPlugin().getConfig().getString("beacon-rename.messages.three").replace("%rename%", string)));
                }
                if (this.count == 4) {
                    final String string = get(Color.translate(rename));
                    event.sendMessage(Color.translate(xCore.getPlugin().getConfig().getString("beacon-rename.messages.fourth").replace("%rename%", string)));
                }
                if (this.count == 5) {
                    final String string = get(Color.translate(rename));
                    event.sendMessage(Color.translate(xCore.getPlugin().getConfig().getString("beacon-rename.messages.fifth").replace("%rename%", string)));
                    final ItemStack stack = event.getItemInHand();
                    final ItemMeta meta = stack.getItemMeta();
                    meta.setDisplayName(CC.translate(string));
                    stack.setItemMeta(meta);
                    this.cancel();
                }
                else if (this.count > 5) {
                    this.cancel();
                }
                ++this.count;
            }
        }.runTaskTimer(xCore.getPlugin(), 0L, 12L);
    }

    static {
        BeaconRenamerListener.BEACON_COOLDOWN_DELAY = TimeUnit.SECONDS.toMillis(10L);
        BeaconRenamerListener.BEACON_COOLDOWN = new TObjectLongHashMap<>();
    }
}