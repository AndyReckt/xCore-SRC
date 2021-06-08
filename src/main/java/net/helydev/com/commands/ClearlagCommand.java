package net.helydev.com.commands;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;

import java.util.List;

public class ClearlagCommand {
    @Command(name = "clearlag", permission = "core.command.clearlag", inGameOnly = true, aliases = { "lagg", "clearlag", "lagclear" })
    public void sendMessage(final CommandArgs command) {
        final Player player = command.getPlayer();
        final String[] args = command.getArgs();
        final List<Entity> entity = Bukkit.getWorld("world").getEntities();
        final int total = entity.size();
        for (final Entity e : entity) {
            if (!(e instanceof Player) && !(e instanceof ItemFrame) && !(e instanceof Villager) && !(e instanceof EnderDragon) && !(e instanceof Minecart) && !(e instanceof Horse)) {
                e.remove();
            }
        }
        Bukkit.broadcastMessage(Color.translate(xCore.getPlugin().getConfig().getString("clear-lag.message").replace("%total%", String.valueOf(total))));
        final List<Entity> entityEnd = Bukkit.getWorld("world_the_end").getEntities();
        final int totalEnd = entityEnd.size();
        for (final Entity e2 : entityEnd) {
            if (!(e2 instanceof Player) && !(e2 instanceof ItemFrame) && !(e2 instanceof Villager) && !(e2 instanceof EnderDragon) && !(e2 instanceof Minecart)) {
                e2.remove();
            }
        }
    }
}
