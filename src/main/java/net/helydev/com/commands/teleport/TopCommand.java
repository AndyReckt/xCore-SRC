package net.helydev.com.commands.teleport;

import net.helydev.com.utils.BukkitUtils;
import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TopCommand {

    @Command(name = "top", permission = "core.command.top", inGameOnly = true)

    public boolean TopCommands(CommandArgs command) {
        Player player = command.getPlayer();
        final Location destination = BukkitUtils.getHighestLocation(player.getLocation());
        if (destination != null) {
            player.teleport(destination.add(0.5, 1.0, 0.5), PlayerTeleportEvent.TeleportCause.PLUGIN);
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("top.used")));
            if (xCore.getPlugin().getMessageconfig().getConfiguration().getBoolean("top.sound")) {
                player.playSound(player.getLocation(), Sound.valueOf(xCore.getPlugin().getMessageconfig().getConfiguration().getString("top.sound-name").toUpperCase()), 1.0F, 1.0F);
            }
            return false;
        }
        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("top.highest-location")));
        return false;

    }
}
