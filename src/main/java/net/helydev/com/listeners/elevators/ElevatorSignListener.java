package net.helydev.com.listeners.elevators;

import net.helydev.com.utils.Color;
import net.helydev.com.xCore;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by InspectMC
 * Date: 7/29/2020
 * Time: 6:26 PM
 */

public class ElevatorSignListener implements Listener {


    String elevatorsign = ChatColor.translateAlternateColorCodes('&', "&9[Elevator]");

    public ElevatorSignListener() {
    }

    @EventHandler
    public void signPlace(SignChangeEvent event) {

        if (event.getLine(0).equalsIgnoreCase("[Elevator]") && event.getLine(1).equalsIgnoreCase("Up")) {
            event.setLine(0, ChatColor.translateAlternateColorCodes('&', "" + elevatorsign));
            event.setLine(1, ChatColor.translateAlternateColorCodes('&', "Up"));
        }
        if (event.getLine(0).equalsIgnoreCase("[Elevator]") && event.getLine(1).equalsIgnoreCase("Down")) {
            event.setLine(0, ChatColor.translateAlternateColorCodes('&', "" + elevatorsign));
            event.setLine(1, ChatColor.translateAlternateColorCodes('&', "Down"));
        }
        if(event.getLine(0).equalsIgnoreCase("[Elevator]")) {
            event.setLine(0, ChatColor.RED + "");
            event.setLine(1, ChatColor.RED + "Unknown");
            event.setLine(2, ChatColor.RED + "Usage");
            event.getPlayer().sendMessage(Color.translate(xCore.getPlugin().getConfig().getString("settings.elevators.invalid")));
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null) {
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();
            if (block.getType() == Material.SIGN || block.getType() == Material.SIGN_POST || block.getType() == Material.WALL_SIGN) {
                Sign sign = (Sign) block.getState();
                if (sign.getLine(0).equalsIgnoreCase(elevatorsign)) {
                    Location loc = block.getLocation();
                    if (sign.getLine(1).equalsIgnoreCase("Up")) {
                        while (true) {
                            Location location1 = loc.add(0.0, 1.0, 0.0);
                            Location location2 = loc.add(0.0, 1.0, 0.0);
                            if (location1.getY() >= 250.0) {
                                break;
                            }
                            if (location1.getBlock().getType() == Material.AIR && location2.getBlock().getType() == Material.AIR) {
                                player.teleport(new Location(location1.getWorld(), (double) location1.getBlockX() + 0.5, (double) location1.getBlockY() - 1.0, location1.getBlockZ()));
                                //Plays the sound when they use the elevator sign
                                if (xCore.getPlugin().getConfig().getBoolean("settings.elevators.used-sound")) {
                                    player.playSound(player.getLocation(), Sound.valueOf(xCore.getPlugin().getConfig().getString("settings.elevators.sound_name").toUpperCase()), 1.0F, 1.0F);
                                }
                                break;
                            }
                        }
                    }
                    if (sign.getLine(1).equalsIgnoreCase("Down")) {
                        while (true) {
                            Location location1 = loc.subtract(0.0, 1.0, 0.0);
                            Location location2 = loc.subtract(0.0, 1.0, 0.0);
                            if (location1.getY() <= 0.0) {
                                break;
                            }
                            if (location1.getBlock().getType() == Material.AIR && location2.getBlock().getType() == Material.AIR) {
                                player.teleport(new Location(location2.getWorld(), (double) location2.getBlockX() + 0.5, (double) location2.getBlockY() - 1, (double) location2.getBlockZ() + 0.5));
                                //Plays the sound when they use the elevator sign
                                if (xCore.getPlugin().getConfig().getBoolean("settings.elevators.used-sound")) {
                                    player.playSound(player.getLocation(), Sound.valueOf(xCore.getPlugin().getConfig().getString("settings.elevators.sound_name").toUpperCase()), 1.0F, 1.0F);
                                }
                                break;
                            }
                        }
                    }
                }
            }

        }
    }
}
