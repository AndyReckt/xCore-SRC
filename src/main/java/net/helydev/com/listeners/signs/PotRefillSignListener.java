package net.helydev.com.listeners.signs;

import net.helydev.com.utils.Color;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PotRefillSignListener implements Listener {
    private String[] lines;
    private String[] error;

    public PotRefillSignListener() {
    }

    public PotRefillSignListener(final xCore plugin) {
        final String[] lines = new String[]{Color.translate(xCore.getPlugin().getConfig().getString("potion-refill-sign.line-1")), Color.translate(xCore.getPlugin().getConfig().getString("potion-refill-sign.line-2")), Color.translate(xCore.getPlugin().getConfig().getString("potion-refill-sign.line-3")), Color.translate(xCore.getPlugin().getConfig().getString("potion-refill-sign.line-4"))};
        final String[] error = new String[]{Color.translate(xCore.getPlugin().getConfig().getString("potion-refill-sign.line-1")), Color.translate(xCore.getPlugin().getConfig().getString("potion-refill-sign.line-2")), Color.translate(xCore.getPlugin().getConfig().getString("potion-refill-sign.line-3")), Color.translate(xCore.getPlugin().getConfig().getString("potion-refill-sign.line-4"))};
    }

    public Inventory openMainInventory(final Player player) {
        final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, xCore.getPlugin().getConfig().getString("potion-refill-sign.title"));
        inv.setItem(36, new ItemStack(Material.POTION, 1, (short)8259));
        inv.setItem(37, new ItemStack(Material.POTION, 1, (short)8259));
        inv.setItem(38, new ItemStack(Material.POTION, 1, (short)8259));
        inv.setItem(39, new ItemStack(Material.POTION, 1, (short)8259));
        inv.setItem(40, new ItemStack(Material.ENDER_PEARL, 16));
        inv.setItem(41, new ItemStack(Material.POTION, 1, (short)8226));
        inv.setItem(42, new ItemStack(Material.POTION, 1, (short)8226));
        inv.setItem(43, new ItemStack(Material.POTION, 1, (short)8226));
        inv.setItem(44, new ItemStack(Material.POTION, 1, (short)8226));
        inv.setItem(18, new ItemStack(Material.POTION, 1, (short)16421));
        inv.setItem(29, new ItemStack(Material.POTION, 1, (short)16421));
        final ItemStack goldsword = new ItemStack(Material.GOLD_SWORD, 1);
        final ItemStack healpot = new ItemStack(Material.POTION, 1, (short)16421);
        for (int in1 = 0; in1 < 9; ++in1) {
            inv.setItem(in1, healpot);
        }
        for (int in1 = 9; in1 < 18; ++in1) {
            inv.setItem(in1, healpot);
        }
        for (int in1 = 19; in1 < 29; ++in1) {
            inv.setItem(in1, healpot);
        }
        for (int in1 = 30; in1 < 36; ++in1) {
            inv.setItem(in1, healpot);
        }
        for (int in1 = 45; in1 < 54; ++in1) {
            inv.setItem(in1, goldsword);
        }
        player.openInventory(inv);
        return inv;
    }

        public void onSignPlace(final SignChangeEvent event) {
        if (event.getLine(0).equals("[Refill]")) {
            final Player player = event.getPlayer();
            if (player.hasPermission("core.sign.create")) {
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

    public void onPlayerInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getClickedBlock();
        if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) && block.getState() instanceof Sign) {
            final Sign sign = (Sign)block.getState();
            for (int i = 0; i < Objects.requireNonNull(this.lines).length; ++i) {
                if (!sign.getLine(i).equals(this.lines[i])) {
                    return;
                }
            }
            this.openMainInventory(player);
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("refill-sign.used")));
            if (xCore.getPlugin().getConfig().getBoolean("potion-refill-sign.sound")) {
                player.playSound(player.getLocation(), Sound.valueOf(xCore.getPlugin().getConfig().getString("potion-refill-sign.sound-name").toUpperCase()), 1.0F, 1.0F);
            }
        }
    }
}
