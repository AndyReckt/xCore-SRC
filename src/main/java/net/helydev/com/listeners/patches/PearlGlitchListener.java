package net.helydev.com.listeners.patches;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.material.Openable;

public class PearlGlitchListener implements Listener {

    private final ImmutableSet<Material> blockedPearlTypes = Sets.immutableEnumSet(
            Material.THIN_GLASS,
            Material.IRON_FENCE,
            Material.FENCE,
            Material.NETHER_FENCE,
            Material.FENCE_GATE,
            Material.ACACIA_STAIRS,
            Material.BIRCH_WOOD_STAIRS,
            Material.BRICK_STAIRS,
            Material.COBBLESTONE_STAIRS,
            Material.DARK_OAK_STAIRS,
            Material.JUNGLE_WOOD_STAIRS,
            Material.NETHER_BRICK_STAIRS,
            Material.QUARTZ_STAIRS,
            Material.SANDSTONE_STAIRS,
            Material.SMOOTH_STAIRS,
            Material.SPRUCE_WOOD_STAIRS,
            Material.WOOD_STAIRS,
            Material.WOOD_STEP,
            Material.WOOD_DOUBLE_STEP,
            Material.STEP,
            Material.DOUBLE_STEP
    );

    public PearlGlitchListener() {
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPearlClip(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            Location to = event.getTo();
            Block block = to.getBlock();
            Material type = block.getType();
            Block above = block.getRelative(BlockFace.UP);
            Material aboveType = above.getType();
            if (type == Material.GLASS) {
                event.setCancelled(true);
                return;
            }
            if (block.getType().isSolid()) {
                if (type == Material.FENCE_GATE) {
                    if(((Openable) block.getState().getData()).isOpen()){
                        return;
                    }
                }
                if ((aboveType == Material.STEP) || (type == Material.STEP)
                        || (aboveType == Material.WOOD_STEP) || (type == Material.WOOD_STEP)) {
                    return;
                }
                if ((type == Material.COBBLESTONE_STAIRS) || (type == Material.SMOOTH_STAIRS)
                        || (type == Material.BRICK_STAIRS) || (type == Material.SANDSTONE_STAIRS) || (type == Material.QUARTZ_STAIRS)
                        || (type == Material.WOOD_STAIRS) || (type == Material.NETHER_BRICK_STAIRS) || (type == Material.SPRUCE_WOOD_STAIRS)
                        || (type == Material.SIGN) || (type == Material.SIGN_POST) || (type == Material.WALL_SIGN)
                        || (type == Material.LEVER)
                ) {
                    return;
                }
                event.setCancelled(true);
                return;
            }
            if (type == Material.FENCE_GATE && aboveType == Material.GLASS) {
                event.setCancelled(true);
                return;
            }

            if (aboveType == Material.FENCE_GATE) {
                if(((Openable) block.getState().getData()).isOpen()){
                    return;
                }
                if (type == Material.AIR) {
                    return;
                }
                event.setCancelled(true);
                return;
            }
            if (blockedPearlTypes.contains(to.getBlock().getType())) {
                if (type == Material.FENCE_GATE) {
                    if(((Openable) block.getState().getData()).isOpen()){
                        return;
                    }
                }
                event.setCancelled(true);
                return;
            }
            to.setX(to.getBlockX() + 0.5);
            to.setZ(to.getBlockZ() + 0.5);
            event.setTo(to);
        }
    }
}