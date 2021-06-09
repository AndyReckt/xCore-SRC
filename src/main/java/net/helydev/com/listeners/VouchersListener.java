package net.helydev.com.listeners;

import net.helydev.com.utils.CC;
import net.helydev.com.utils.Color;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public class VouchersListener implements Listener {
    @EventHandler
    public void onInteractVouch(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        for (final String vouchItem : xCore.getPlugin().getVoucherConfig().getConfiguration().getConfigurationSection("vouchers").getKeys(false)) {
            final ItemStack vouchItemStackCustom = new ItemStack(Material.valueOf(xCore.getPlugin().getVoucherConfig().getConfiguration().getString("vouchers." + vouchItem + ".item")), 1, (short) xCore.getPlugin().getVoucherConfig().getConfiguration().getInt("vouchers." + vouchItem + ".item-data"));
            final ItemMeta vouchItemMetaCustom = vouchItemStackCustom.getItemMeta();
            vouchItemMetaCustom.setDisplayName(xCore.getPlugin().getVoucherConfig().getConfiguration().getString("vouchers." + vouchItem + ".name"));
            final List<String> lore = new ArrayList<>();
            for (String string : xCore.getPlugin().getVoucherConfig().getConfiguration().getStringList("vouchers." + vouchItem + ".lores")) {
                string = string.replace("&", "§");
                string = string.replace("%d_arrow%", "»");
                lore.add(string);
            }
            vouchItemMetaCustom.setLore(lore);
            vouchItemStackCustom.setItemMeta(vouchItemMetaCustom);
            if (player.getItemInHand().getItemMeta() == null || player.getItemInHand() == null || player.getItemInHand().getItemMeta().getDisplayName() == null || player.getItemInHand().getItemMeta().getLore() == null) {
                return;
            }
            if (!player.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(CC.translate(xCore.getPlugin().getVoucherConfig().getConfiguration().getString("vouchers." + vouchItem + ".name")))) {
                continue;
            }
            final List<String> nopermcommands = xCore.getPlugin().getVoucherConfig().getConfiguration().getStringList("vouchers." + vouchItem + ".permissions.commands");
            final List<String> commands = xCore.getPlugin().getVoucherConfig().getConfiguration().getStringList("vouchers." + vouchItem + ".commands");
            for (final String str : commands) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), str.replace("%player%", player.getName()));
            }
            if (player.getItemInHand().getAmount() > 1) {
                player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
            } else {
                player.setItemInHand(null);
            }
            final List<String> message = xCore.getPlugin().getVoucherConfig().getConfiguration().getStringList("vouchers." + vouchItem + ".opened");
            for (final String str2 : message) {
                player.sendMessage(CC.translate(str2));
            }

            ///////////////////////////////////////// LISTENERS /////////////////////////////////////////////////////
            //Plays a sound when a player opens a voucher.
            if (xCore.getPlugin().getVoucherConfig().getConfiguration().getBoolean("vouchers." + vouchItem + ".sound.enabled")) {
                player.playSound(player.getLocation(), Sound.valueOf(xCore.getPlugin().getVoucherConfig().getConfiguration().getString("vouchers." + vouchItem + ".sound.sound-name").toUpperCase()), 1.0F, 1.0F);
            }
            //Plays a particle effect around the player.
            if (xCore.getPlugin().getVoucherConfig().getConfiguration().getBoolean("vouchers." + vouchItem + ".particles.enabled")) {
                player.playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 0);
            }
            //Checks if it limits for people with specified permission.
            if (xCore.getPlugin().getVoucherConfig().getConfiguration().getBoolean("vouchers." + vouchItem + ".permissions.enabled")) {
                if (!player.hasPermission(xCore.getPlugin().getVoucherConfig().getConfiguration().getString("vouchers." + vouchItem + ".permissions.permission")))
                    if (xCore.getPlugin().getVoucherConfig().getConfiguration().getBoolean("vouchers." + vouchItem + ".permissions.send-message")) {
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("vouchers.no-permissions")));
                        return;
                    }
            }
            //Executes no permissions commands if you do not have permissions to use a voucher.
            if (xCore.getPlugin().getVoucherConfig().getConfiguration().getBoolean("vouchers." + vouchItem + ".permissions.commands-enabled")) {
                if (!player.hasPermission(xCore.getPlugin().getVoucherConfig().getConfiguration().getString("vouchers." + vouchItem + ".permissions.permission")))
                    for (final String str : nopermcommands) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), str.replace("%player%", player.getName()));
                    }
            }
            ///////////////////////////////////////// LISTENERS /////////////////////////////////////////////////////
            player.updateInventory();
        }
    }
}
