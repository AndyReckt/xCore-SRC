package net.helydev.com.listeners.patches;

import net.helydev.com.utils.Color;
import net.helydev.com.xCore;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class AntiDropDownListener implements Listener {

    private xCore instance = null;

    public AntiDropDownListener() {
        this.instance = instance;
    }

    @EventHandler
    public void onDoorPlace(final BlockPlaceEvent e) {
        final Player player = e.getPlayer();
        ItemStack hand = player.getItemInHand();
        final Location loc = e.getBlockPlaced().getLocation();
        final World world = loc.getWorld();
        final int x = loc.getBlockX();
        final int z = loc.getBlockZ();
        final int y = loc.getBlockY();
        final Block b1 = new Location(world, (double) (x + 1), (double) y, (double) z).getBlock();
        final Block b2 = new Location(world, (double) (x - 1), (double) y, (double) z).getBlock();
        final Block b3 = new Location(world, (double) x, (double) y, (double) (z + 1)).getBlock();
        final Block b4 = new Location(world, (double) x, (double) y, (double) (z - 1)).getBlock();
        if (e.getBlock().getType() == Material.WOODEN_DOOR) {
            e.setCancelled(true);
            player.sendMessage(Color.translate("&cYou cannot place a door over here!"));
        }
    }
}
