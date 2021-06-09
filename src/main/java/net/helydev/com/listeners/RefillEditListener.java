package net.helydev.com.listeners;

import net.helydev.com.configs.SignsConfig;
import net.helydev.com.utils.CC;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.Iterator;

public class RefillEditListener implements Listener {
    @EventHandler
    public void onInvClosed(final InventoryCloseEvent e) {
        if (e.getInventory().getTitle().equalsIgnoreCase(CC.translate("&d&nRefill Sign Editor"))) {
            final Inventory inventory = e.getInventory();
            int n = 0;
            for (int items = 0; items < xCore.getPlugin().getConfig().getInt("signs.refill.size"); ++items) {
                final ItemStack itemStack = inventory.getItem(items);
                ++n;
                final FileConfiguration kit = SignsConfig.getLicenceFileConfig();
                kit.set("signs.refill.contents." + n + ".item", (Object)itemStack);
                try {
                    kit.save(SignsConfig.getLicenceFile());
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public static void onOpenInventory(final Player player) {
        final Inventory inv = Bukkit.createInventory((InventoryHolder)null, xCore.getPlugin().getConfig().getInt("signs.refill.size"), CC.translate("&d&nRefill Sign Editor"));
        final Iterator contentsGkit = SignsConfig.getLicenceFileConfig().getConfigurationSection("signs.refill.contents").getKeys(false).iterator();
        int n = 0;
        while (contentsGkit.hasNext()) {
            final Object items = contentsGkit.next();
            ++n;
            final ItemStack itemStack = SignsConfig.getLicenceFileConfig().getItemStack("signs.refill.contents." + items + ".item");
            if (itemStack != null && !itemStack.getType().equals((Object)Material.AIR)) {
                inv.setItem(n - 1, itemStack);
            }
        }
        player.openInventory(inv);
    }
}
