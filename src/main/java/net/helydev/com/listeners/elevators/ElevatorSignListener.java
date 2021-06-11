package net.helydev.com.listeners.elevators;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.RayTrace;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ElevatorSignListener implements Listener {

    private static final String signTitle = ChatColor.translateAlternateColorCodes('&', xCore.getPlugin().getConfig().getString("settings.elevators.signs.title"));

    @EventHandler
    public void elevator(VehicleExitEvent event) {
        if (!(event.getExited() instanceof Player)) {
            return;
        }
        if (!xCore.getPlugin().getConfig().getBoolean("settings.elevators.minecart.enabled")) {
            return;
        }
        Player player = (Player) event.getExited();
        if (event.getVehicle() instanceof Minecart && player.isSneaking()) {
            Location location = event.getVehicle().getLocation();
            location.add(0.0, 1.0, 0.0);
            if (!(location.getBlock().getState() instanceof Sign)) {
                if (location.getBlock().getType() != Material.FENCE_GATE) {
                    return;
                }
            }
            while (location.getY() < 255.0 && location.getBlock().getType().isSolid()) {
                location.add(0.0, 1.0, 0.0);
            }
            location.setYaw(player.getLocation().getYaw());
            location.setPitch(player.getLocation().getPitch());
            new BukkitRunnable() {
                public void run() {
                    player.teleport(location);
                }
            }.runTaskLater(xCore.getPlugin(), 1L);
        }
    }


    public boolean isSign(final Block block) {
        if (block.getState() instanceof Sign) {
            Sign sign = (Sign) block.getState();
            String[] lines = sign.getLines();
            return lines[0].equals(signTitle) && (lines[1].equalsIgnoreCase("Up") || lines[1].equalsIgnoreCase("Down"));
        }
        return false;
    }


    public boolean isSafe(final Block block) {
        return block != null && !block.getType().isSolid() && block.getType() != Material.GLASS && block.getType() != Material.STAINED_GLASS;
    }

    @EventHandler
    public boolean onSign(SignChangeEvent event) {
        if (xCore.getPlugin().getConfig().getBoolean("settings.elevators.signs.enabled")) {
            {
                Player player = event.getPlayer();
                String[] lines = event.getLines();
                if (lines[0].equalsIgnoreCase("[Elevator]") && (lines[1].equalsIgnoreCase("Up") || lines[1].equalsIgnoreCase("Down"))) {
                    event.setLine(0, signTitle);
                    player.sendMessage(Color.translate(xCore.getPlugin().getConfig().getString("settings.elevators.signs.messages.created")));
                }
            }
        }
        return true;
    }

    @EventHandler
    public void onInteractSign(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK
                && (event.getClickedBlock().getType() == Material.SIGN_POST || event.getClickedBlock().getType() == Material.WALL_SIGN)) {

            Player player = event.getPlayer();
            Block block = event.getClickedBlock();
            if (block == null) {
                return;
            }

            if (isSign(block)) {

                Vector playerVector = player.getEyeLocation().toVector(), direction = player.getEyeLocation().getDirection();
                RayTrace rayTrace = new RayTrace(playerVector, direction);

                boolean glitching = false;

                for (Vector vec : rayTrace.traverse(playerVector.distance(block.getLocation().toVector()) - 0.5, 0.1)) {
                    Block bypass = Bukkit.getWorld(player.getWorld().getUID()).getBlockAt(vec.toLocation(player.getWorld()));
                    if (bypass != null && bypass.getType().name().contains("GLASS")) {
                        glitching = true;
                    }
                }

                if (block.getState() instanceof Sign && !glitching) {
                    Sign sign = (Sign)block.getState();
                    String[] lines = sign.getLines();
                    this.getSafeLocation(player, sign.getLocation(), lines[1].equalsIgnoreCase("Up") ? true : false);                }
            }
        }
    }

    private void getSafeLocation(Player player, Location signLocation, boolean up) {

        Block block = signLocation.getBlock();
        do {
            block = block.getRelative(up ? BlockFace.UP : BlockFace.DOWN);
            if (block.getY() > block.getWorld().getMaxHeight() || block.getY() <= 1) {
                player.sendMessage(Color.translate(xCore.getPlugin().getConfig().getString("settings.elevators.signs.messages.cannot-locate")));
                return;
            }
        } while (!this.isSign(block));
        final boolean underSafe = this.isSafe(block.getRelative(BlockFace.DOWN));
        final boolean overSafe = this.isSafe(block.getRelative(BlockFace.UP));
        if (!underSafe && !overSafe) {
            player.sendMessage(Color.translate(xCore.getPlugin().getConfig().getString("settings.elevators.signs.cannot-locate")));
            return;
        }
        Location location = player.getLocation().clone();
        location.setX(block.getX() + 0.5);
        location.setY(block.getY() + (underSafe ? -1 : 0));
        location.setZ(block.getZ() + 0.5);
        location.setPitch(0.0f);
        player.teleport(location);
    }

}