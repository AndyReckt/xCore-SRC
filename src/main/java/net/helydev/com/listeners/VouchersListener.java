package net.helydev.com.listeners;

import net.helydev.com.utils.CC;
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
        for (final String vouchItem : xCore.getPlugin().getConfig().getConfigurationSection("vouchers").getKeys(false)) {
            final ItemStack vouchItemStackCustom = new ItemStack(Material.valueOf(xCore.getPlugin().getConfig().getString("vouchers." + vouchItem + ".item")), 1, (short) xCore.getPlugin().getConfig().getInt("vouchers." + vouchItem + ".item-data"));
            final ItemMeta vouchItemMetaCustom = vouchItemStackCustom.getItemMeta();
            vouchItemMetaCustom.setDisplayName(xCore.getPlugin().getConfig().getString("vouchers." + vouchItem + ".name"));
            final List<String> lore = new ArrayList<>();
            for (String string : xCore.getPlugin().getConfig().getStringList("vouchers." + vouchItem + ".lores")) {
                string = string.replace("&", "§");
                string = string.replace("%d_arrow%", "»");
                lore.add(string);
            }
            vouchItemMetaCustom.setLore(lore);
            vouchItemStackCustom.setItemMeta(vouchItemMetaCustom);
            if (player.getItemInHand().getItemMeta() == null || player.getItemInHand() == null || player.getItemInHand().getItemMeta().getDisplayName() == null || player.getItemInHand().getItemMeta().getLore() == null) {
                return;
            }
            if (!player.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(CC.translate(xCore.getPlugin().getConfig().getString("vouchers." + vouchItem + ".name")))) {
                continue;
            }
            final List<String> commands = xCore.getPlugin().getConfig().getStringList("vouchers." + vouchItem + ".commands");
            for (final String str : commands) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), str.replace("%player%", player.getName()));
            }
            if (player.getItemInHand().getAmount() > 1) {
                player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
            } else {
                player.setItemInHand(null);
            }
            final List<String> message = xCore.getPlugin().getConfig().getStringList("vouchers." + vouchItem + ".opened");
            for (final String str2 : message) {
                player.sendMessage(CC.translate(str2));
            }
            if (xCore.getPlugin().getConfig().getBoolean("vouchers." + vouchItem + ".sound.enabled")) {
                player.playSound(player.getLocation(), Sound.valueOf(xCore.getPlugin().getConfig().getString("vouchers." + vouchItem + ".sound.sound-name").toUpperCase()), 1.0F, 1.0F);
                player.playEffect(player.getLocation(), Effect.MOBSPAWNER_FLAMES, 0);
            }
            player.updateInventory();
        }
    }
}
