package net.helydev.com.listeners.signs;

import net.helydev.com.utils.CC;
import net.helydev.com.utils.Color;
import net.helydev.com.xCore;
import net.minecraft.util.gnu.trove.map.TObjectLongMap;
import net.minecraft.util.gnu.trove.map.hash.TObjectLongHashMap;
import net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PotRefillSignListener implements Listener
{
    private final String[] lines;
    private final String[] error;
    private static long BEACON_COOLDOWN_DELAY;
    public static TObjectLongMap<UUID> BEACON_COOLDOWN;

    public PotRefillSignListener() {
        this.lines = new String[] { Color.translate(xCore.getPlugin().getConfig().getString("signs.refill.lines.1")), Color.translate(xCore.getPlugin().getConfig().getString("signs.refill.lines.2")), Color.translate(xCore.getPlugin().getConfig().getString("signs.refill.lines.3")), Color.translate(xCore.getPlugin().getConfig().getString("signs.refill.lines.4")) };
        this.error = new String[] { Color.translate(xCore.getPlugin().getConfig().getString("signs.refill.lines.1")), Color.translate(xCore.getPlugin().getConfig().getString("signs.refill.lines.2")), Color.translate(xCore.getPlugin().getConfig().getString("signs.refill.lines.3")), Color.translate("&cError") };
    }

    public void openMainInventory(final Player player) {
        final Inventory inv = Bukkit.createInventory(null, xCore.getPlugin().getConfig().getInt("signs.refill.size"), CC.translate(xCore.getPlugin().getConfig().getString("signs.menu.refill.title")));
        int var4 = 0;
        for (final String var6 : xCore.getPlugin().getSignsConfig().getConfiguration().getConfigurationSection("signs.refill.contents").getKeys(false)) {
            ++var4;
            final ItemStack var7 = xCore.getPlugin().getSignsConfig().getConfiguration().getItemStack("signs.refill.contents." + var6 + ".item");
            if (var7 != null && !var7.getType().equals(Material.AIR)) {
                inv.setItem(var4 - 1, var7);
            }
        }
        player.openInventory(inv);
    }

    @EventHandler
    public void onSignPlace(final SignChangeEvent event) {
        if (event.getLine(0).equals("[Refill]")) {
            final Player player = event.getPlayer();
            if (player.hasPermission("core.comamnd.*")) {
                for (int i = 0; i < this.lines.length; ++i) {
                    event.setLine(i, this.lines[i]);
                }
            }
            else {
                for (int i = 0; i < this.error.length; ++i) {
                    event.setLine(i, this.error[i]);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public boolean onPlayerInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getClickedBlock();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && block.getState() instanceof Sign) {
            final Sign sign = (Sign)block.getState();
            for (int i = 0; i < this.lines.length; ++i) {
                if (!sign.getLine(i).equals(this.lines[i])) {
                    return false;
                }
            }
            final UUID uuid = player.getUniqueId();
            final long timestamp = PotRefillSignListener.BEACON_COOLDOWN.get(uuid);
            final long millis = System.currentTimeMillis();
            final long remaining = (timestamp == PotRefillSignListener.BEACON_COOLDOWN.getNoEntryValue()) ? -1L : (timestamp - millis);
            if (remaining > 0L) {
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("refill-sign.cooldown").replace("%time%", DurationFormatUtils.formatDurationWords(remaining, true, true))));
                return false;
            }
            if (xCore.getPlugin().getConfig().getBoolean("signs.refill.enabled")) {
                PotRefillSignListener.BEACON_COOLDOWN.put(player.getUniqueId(), System.currentTimeMillis() + PotRefillSignListener.BEACON_COOLDOWN_DELAY);
                this.openMainInventory(player);
            }

            //Plays a sound when a player opens a voucher.
            if (xCore.getPlugin().getVoucherConfig().getConfiguration().getBoolean("signs.refill.sounds.enabled")) {
                player.playSound(player.getLocation(), Sound.valueOf(xCore.getPlugin().getVoucherConfig().getConfiguration().getString("signs.refill.sounds.sound").toUpperCase()), 1.0F, 1.0F);
            }
            //Plays a particle effect around the player.
            if (xCore.getPlugin().getVoucherConfig().getConfiguration().getBoolean("signs.refill.particles")) {
                player.playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 0);
            }
        }
        return false;
    }

    static {
        PotRefillSignListener.BEACON_COOLDOWN_DELAY = TimeUnit.MINUTES.toMillis(xCore.getPlugin().getConfig().getInt("signs.refill.cooldown.time"));
        PotRefillSignListener.BEACON_COOLDOWN = new TObjectLongHashMap<>();
    }
}