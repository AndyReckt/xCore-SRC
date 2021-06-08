package net.helydev.com.listeners.patches;

import net.helydev.com.utils.Color;
import net.helydev.com.xCore;
import net.minecraft.util.com.google.common.collect.ImmutableSet;
import net.minecraft.util.com.google.common.collect.Sets;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.material.Openable;

public class PearlGlitchListener implements Listener
{
    private final ImmutableSet<Material> blockedPearlTypes;
    private final ImmutableSet<Material> returnTypes;

    public PearlGlitchListener() {
        this.blockedPearlTypes = (ImmutableSet<Material>)Sets.immutableEnumSet((Enum)Material.THIN_GLASS, (Enum[])new Material[] { Material.GLASS, Material.STAINED_GLASS, Material.STAINED_GLASS_PANE, Material.IRON_FENCE, Material.CHEST, Material.TRAPPED_CHEST, Material.FENCE });
        this.returnTypes = (ImmutableSet<Material>)Sets.immutableEnumSet((Enum)Material.BRICK_STAIRS, (Enum[])new Material[] { Material.SMOOTH_STAIRS, Material.WOOD_STAIRS, Material.SPRUCE_WOOD_STAIRS, Material.TORCH, Material.NETHER_BRICK_STAIRS, Material.QUARTZ_STAIRS, Material.BEDROCK, Material.CAKE, Material.STEP, Material.LEVER });
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPearlClip(final PlayerTeleportEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            return;
        }
        final Location to = event.getTo();
        final Block block = to.getBlock();
        final Material type = block.getType();
        final Block above = block.getRelative(BlockFace.UP);
        final Material aboveType = above.getType();
        final Player player = event.getPlayer();
        final Material westType = block.getRelative(BlockFace.WEST).getType();
        final Material eastType = block.getRelative(BlockFace.EAST).getType();
        final Material belowType = block.getRelative(BlockFace.DOWN).getType();
        final Material southType = block.getRelative(BlockFace.SOUTH).getType();
        final Material northType = block.getRelative(BlockFace.NORTH).getType();
        if (type == Material.GLASS) {
            event.setCancelled(true);
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("pearl-glitch.detected")));
            return;
        }
        if (block.getType().isSolid() || aboveType == Material.GLASS) {
            if (aboveType == Material.IRON_DOOR_BLOCK) {
                event.setCancelled(true);
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("pearl-glitch.detected")));
                return;
            }
            if (type == Material.FENCE_GATE) {
                if (((Openable)block.getState().getData()).isOpen()) {
                    if (getDirection(player.getLocation().getYaw()).equalsIgnoreCase("EAST") && eastType.isSolid()) {
                        event.setCancelled(true);
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("pearl-glitch.detected")));
                        return;
                    }
                    if (getDirection(player.getLocation().getYaw()).equalsIgnoreCase("NORTH") && northType.isSolid()) {
                        event.setCancelled(true);
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("pearl-glitch.detected")));
                        return;
                    }
                    if (getDirection(player.getLocation().getYaw()).equalsIgnoreCase("SOUTH") && southType.isSolid()) {
                        event.setCancelled(true);
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("pearl-glitch.detected")));
                        return;
                    }
                    if (getDirection(player.getLocation().getYaw()).equalsIgnoreCase("WEST") && westType.isSolid()) {
                        event.setCancelled(true);
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("pearl-glitch.detected")));
                    }
                }
                else {
                    if (belowType == Material.CHEST) {
                        return;
                    }
                    event.setCancelled(true);
                    player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("pearl-glitch.detected")));
                }
            }
            else if (aboveType == Material.STEP || type == Material.STEP || aboveType == Material.WOOD_STEP || type == Material.WOOD_STEP) {
                if (getDirection(player.getLocation().getYaw()).equalsIgnoreCase("EAST")) {
                    if (!eastType.isSolid()) {
                        to.setX(to.getBlockX() + 1);
                        to.setY(to.getBlockY() - 1);
                        event.setTo(to);
                        return;
                    }
                    if (!this.returnTypes.contains(eastType) && !this.returnTypes.contains(type) && eastType != Material.TRAP_DOOR) {
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("pearl-glitch.detected")));
                        event.setCancelled(true);
                        return;
                    }
                    if (((Openable)block.getRelative(BlockFace.EAST).getState().getData()).isOpen()) {
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("pearl-glitch.detected")));
                        event.setCancelled(true);
                        return;
                    }
                    to.setZ(to.getBlockX() + 1);
                    to.setY(to.getBlockY() - 1);
                    event.setTo(to);
                }
                else if (getDirection(player.getLocation().getYaw()).equalsIgnoreCase("WEST")) {
                    if (!westType.isSolid()) {
                        to.setX(to.getBlockX() - 1);
                        to.setY(to.getBlockY() - 1);
                        event.setTo(to);
                        return;
                    }
                    if (!this.returnTypes.contains(westType) && !this.returnTypes.contains(type) && westType != Material.TRAP_DOOR) {
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("pearl-glitch.detected")));
                        event.setCancelled(true);
                        return;
                    }
                    if (((Openable)block.getRelative(BlockFace.WEST).getState().getData()).isOpen()) {
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("pearl-glitch.detected")));
                        event.setCancelled(true);
                        return;
                    }
                    to.setZ(to.getBlockX() - 1);
                    to.setY(to.getBlockY() - 1);
                    event.setTo(to);
                }
                else if (getDirection(player.getLocation().getYaw()).equalsIgnoreCase("NORTH")) {
                    if (!northType.isSolid()) {
                        to.setZ(to.getBlockZ() - 1);
                        to.setY(to.getBlockY() - 1);
                        event.setTo(to);
                        return;
                    }
                    if (!this.returnTypes.contains(northType) && !this.returnTypes.contains(type) && northType != Material.TRAP_DOOR) {
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("pearl-glitch.detected")));
                        event.setCancelled(true);
                        return;
                    }
                    if (((Openable)block.getRelative(BlockFace.NORTH).getState().getData()).isOpen()) {
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("pearl-glitch.detected")));
                        event.setCancelled(true);
                        return;
                    }
                    to.setZ(to.getBlockZ() - 1);
                    to.setY(to.getBlockY() - 1);
                    event.setTo(to);
                }
                else {
                    if (!getDirection(player.getLocation().getYaw()).equalsIgnoreCase("SOUTH")) {
                        to.setX(to.getBlockX() + 0.5);
                        to.setZ(to.getBlockZ() + 0.5);
                        event.setTo(to);
                        return;
                    }
                    if (!southType.isSolid()) {
                        to.setZ(to.getBlockZ() + 1);
                        to.setY(to.getBlockY() - 1);
                        event.setTo(to);
                        return;
                    }
                    if (!this.returnTypes.contains(southType) && !this.returnTypes.contains(type) && southType != Material.TRAP_DOOR) {
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("pearl-glitch.detected")));
                        event.setCancelled(true);
                        return;
                    }
                    if (((Openable)block.getRelative(BlockFace.SOUTH).getState().getData()).isOpen()) {
                        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("pearl-glitch.detected")));
                        event.setCancelled(true);
                        return;
                    }
                    to.setZ(to.getBlockZ() + 1);
                    to.setY(to.getBlockY() - 1);
                    event.setTo(to);
                }
            }
            else {
                if (this.returnTypes.contains(to.getBlock().getType())) {
                    return;
                }
                if (aboveType == Material.GLASS) {
                    if (type == Material.CHEST) {
                        return;
                    }
                    if (type == Material.AIR) {
                        to.setX(to.getBlockX() + 0.5);
                        to.setZ(to.getBlockZ() + 0.5);
                        return;
                    }
                    event.setCancelled(true);
                    player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("pearl-glitch.detected")));
                }
                else {
                    if (type == Material.THIN_GLASS) {
                        return;
                    }
                    player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("pearl-glitch.detected")));
                    event.setCancelled(true);
                }
            }
        }
        else if (aboveType == Material.FENCE_GATE) {
            if (((Openable)block.getState().getData()).isOpen()) {
                return;
            }
            if (type == Material.AIR) {
                return;
            }
            event.setCancelled(true);
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("pearl-glitch.detected")));
        }
        else {
            if (this.blockedPearlTypes.contains(to.getBlock().getType())) {
                event.setCancelled(true);
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("pearl-glitch.detected")));
                return;
            }
            to.setX(to.getBlockX() + 0.5);
            to.setZ(to.getBlockZ() + 0.5);
            event.setTo(to);
        }
    }

    private static String getDirection(float yaw) {
        yaw -= 180.0f;
        yaw %= 360.0f;
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        if (0.0f <= yaw && yaw < 22.5) {
            return "NORTH";
        }
        if (22.5 <= yaw && yaw < 67.5) {
            return "NORTH-EAST";
        }
        if (67.5 <= yaw && yaw < 112.5) {
            return "EAST";
        }
        if (112.5 <= yaw && yaw < 157.5) {
            return "SOUTH-EAST";
        }
        if (157.5 <= yaw && yaw < 202.5) {
            return "SOUTH";
        }
        if (202.5 <= yaw && yaw < 247.5) {
            return "SOUTH-WEST";
        }
        if (247.5 <= yaw && yaw < 292.5) {
            return "WEST";
        }
        if (292.5 <= yaw && yaw < 337.5) {
            return "NORTH-WEST";
        }
        if (337.5 <= yaw && yaw < 360.0) {
            return "NORTH";
        }
        return "?";
    }
}