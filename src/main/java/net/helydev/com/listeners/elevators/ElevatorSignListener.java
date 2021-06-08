package net.helydev.com.listeners.elevators;

import net.helydev.com.utils.CC;
import net.helydev.com.xCore;
import org.apache.commons.lang.StringUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ElevatorSignListener implements Listener {
    private String signTitle;

    public boolean isHalfBlock(final Block block) {
        switch (block.getType()) {
            case BED_BLOCK:
            case STEP:
            case WOOD_STEP:
            case GOLD_PLATE:
            case WOOD_PLATE:
            case STONE_PLATE:
            case IRON_PLATE:
            case STONE_BUTTON:
            case WOOD_BUTTON:
            case TRAP_DOOR:
            case DOUBLE_STEP:
            case WOOD_DOUBLE_STEP:
            case ENCHANTMENT_TABLE:
            case SOUL_SAND:
            case BREWING_STAND:
            case ENDER_PORTAL_FRAME:
            case DRAGON_EGG:
            case TRIPWIRE_HOOK:
            case COCOA:
            case CHEST:
            case ENDER_CHEST:
            case TRAPPED_CHEST:
            case FLOWER_POT:
            case ANVIL:
            case REDSTONE_COMPARATOR:
            case DAYLIGHT_DETECTOR:
            case HOPPER: {
                return false;
            }
            default: {
                return true;
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onSignUpdate(final SignChangeEvent e) {
        this.signTitle = CC.translate(xCore.getPlugin().getConfig().getString("settings.elevators.title"));

        if (StringUtils.containsIgnoreCase(e.getLine(0), "Elevator")) {
            boolean up;
            if (StringUtils.containsIgnoreCase(e.getLine(1), "Up")) {
                up = true;
            }
            else {
                if (!StringUtils.containsIgnoreCase(e.getLine(1), "Down")) {
                    e.getPlayer().sendMessage(CC.translate(xCore.getPlugin().getConfig().getString("settings.elevators.messages.invalid-usage")));
                    return;
                }
                up = false;
            }
            e.setLine(0, this.signTitle);
            e.setLine(1, up ? "Up" : "Down");
            e.setLine(2, "");
            e.setLine(3, "");
        }
    }

    @Deprecated
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerInteract(final PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null) {
            if (xCore.getPlugin().getConfig().getBoolean("settings.elevators.not-found")) {
                final Block block = e.getClickedBlock();
                if (block.getType() == Material.SIGN || block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {
                    final Sign sign = (Sign)block.getState();
                    final String[] lines = sign.getLines();
                    if (lines[0].equals(this.signTitle)) {
                        final Block b = e.getPlayer().getTargetBlock(null, 8);
                        if (b.getType() != Material.WALL_SIGN && b.getType() != Material.SIGN_POST && this.isHalfBlock(block)) {
                            return;
                        }
                        boolean up;
                        if (lines[1].equalsIgnoreCase("Up")) {
                            up = true;
                        }
                        else {
                            if (!lines[1].equalsIgnoreCase("Down")) {
                                return;
                            }
                            up = false;
                        }
                        this.signClick(e.getPlayer(), sign.getLocation(), up);
                    }
                }
            }
            else {
                final Player player = e.getPlayer();
                final Block block2 = e.getClickedBlock();
                if (block2.getType() == Material.SIGN || block2.getType() == Material.SIGN_POST || block2.getType() == Material.WALL_SIGN) {
                    final Sign sign2 = (Sign)block2.getState();
                    if (sign2.getLine(0).equalsIgnoreCase(this.signTitle)) {
                        final Location loc = block2.getLocation();
                        final Block b2 = player.getTargetBlock(null, 8);
                        if (b2.getType() != Material.WALL_SIGN && b2.getType() != Material.SIGN_POST && this.isHalfBlock(block2)) {
                            return;
                        }
                        if (sign2.getLine(1).equalsIgnoreCase("Up")) {
                            while (true) {
                                final Location location1 = loc.add(0.0, 1.0, 0.0);
                                final Location location2 = loc.add(0.0, 1.0, 0.0);
                                if (location1.getY() >= 250.0) {
                                    break;
                                }
                                if (location1.getBlock().getType() == Material.AIR && location2.getBlock().getType() == Material.AIR) {
                                    player.teleport(new Location(location1.getWorld(), location1.getBlockX() + 0.5, location1.getBlockY() - 1.0, location1.getBlockZ() + 0.5));
                                    break;
                                }
                            }
                        }
                        if (sign2.getLine(1).equalsIgnoreCase("Down")) {
                            while (true) {
                                final Location location1 = loc.subtract(0.0, 1.0, 0.0);
                                final Location location2 = loc.subtract(0.0, 1.0, 0.0);
                                if (location1.getY() <= 0.0) {
                                    break;
                                }
                                if (location1.getBlock().getType() == Material.AIR && location2.getBlock().getType() == Material.AIR) {
                                    player.teleport(new Location(location2.getWorld(), location2.getBlockX() + 0.5, location2.getBlockY() - 1.0, location2.getBlockZ() + 0.5));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void signClick(final Player player, final Location signLocation, final boolean up) {
        Block block = signLocation.getBlock();
        do {
            block = block.getRelative(up ? BlockFace.UP : BlockFace.DOWN);
            if (block.getY() > block.getWorld().getMaxHeight() || block.getY() <= 1) {
                player.sendMessage(CC.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("settings.elevators.messages.cannot-find")));
                return;
            }
        } while (!this.isSign(block));
        final boolean underSafe = this.isSafe(block.getRelative(BlockFace.DOWN));
        final boolean overSafe = this.isSafe(block.getRelative(BlockFace.UP));
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            final Location location = player.getLocation().clone();
            location.setX(block.getX() + 0.5);
            location.setY(block.getY() + (underSafe ? -1 : 0));
            location.setZ(block.getZ() + 0.5);
            location.setPitch(0.0f);
            player.teleport(location);
            return;
        }
        if (!underSafe && !overSafe) {
            player.sendMessage(CC.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("settings.elevators.messsages.blocked")));
            return;
        }
        final Location location2 = player.getLocation().clone();
        location2.setX(block.getX() + 0.5);
        location2.setY(block.getY() + (underSafe ? -1 : 0));
        location2.setZ(block.getZ() + 0.5);
        location2.setPitch(0.0f);
        player.teleport(location2);
    }

    private boolean isSign(final Block block) {
        if (block.getState() instanceof Sign) {
            final Sign sign = (Sign)block.getState();
            final String[] lines = sign.getLines();
            return lines[0].equals(this.signTitle) && (lines[1].equalsIgnoreCase("Up") || lines[1].equalsIgnoreCase("Down"));
        }
        return false;
    }

    private boolean isSafe(final Block block) {
        return block != null && !block.getType().isSolid() && block.getType() != Material.GLASS && block.getType() != Material.STAINED_GLASS;
    }
}
