package net.helydev.com.listeners.patches;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherListener implements Listener
{
    public WeatherListener() {
    }

    @EventHandler
    public void onWeatherChange(final WeatherChangeEvent e) {
        if (e.getWorld().getEnvironment() == World.Environment.NORMAL && e.toWeatherState()) {
            e.setCancelled(true);
        }
    }
}