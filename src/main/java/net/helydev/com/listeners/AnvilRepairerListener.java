package net.helydev.com.listeners;

import net.helydev.com.utils.Color;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class AnvilRepairerListener implements Listener {
    private xCore plugin;
    private static int COST_TO_REPAIR;

    public AnvilRepairerListener() {
    }

    @EventHandler
    public void onUseAnvil(final InventoryOpenEvent event) {
        if (event.getInventory().getType() == InventoryType.ANVIL) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onAnvilRepair(final PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
            final Block block = event.getClickedBlock();
            if (block == null) {
                return;
            }
            final ItemStack itemStack = event.getItem();
            if (block.getType().equals(Material.ANVIL)) {
                if (itemStack == null) {
                    player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("anvil-repairer.hold-item")));
                    return;
                }
                event.setCancelled(true);
                event.setUseInteractedBlock(Event.Result.DENY);
                if (itemStack.getDurability() == 0) {
                    player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("anvil-repairer.not-damaged")));
                    return;
                }
                player.getInventory().remove(itemStack);
                final Item drop = Bukkit.getWorld(player.getWorld().getUID()).dropItem(block.getRelative(BlockFace.UP).getLocation().add(0.5, 0.5, 0.5), itemStack);
                drop.setPickupDelay(160);
                drop.setVelocity(new Vector(0, 0, 0));
                Bukkit.getServer().getScheduler().runTaskLater(this.plugin, new Runnable() {
                    @Override
                    public void run() {
                        drop.getItemStack().setDurability((short) 0);
                        player.getInventory().addItem(new ItemStack[]{drop.getItemStack()});
                        player.playSound(player.getLocation(), Sound.ANVIL_USE, 1.0f, 1.0f);
                        drop.remove();
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("anvil-repairer.repaired")));
                    }
                }, 100L);
            }
        }
    }
    static {
        AnvilRepairerListener.COST_TO_REPAIR = 1500;
    }
}
