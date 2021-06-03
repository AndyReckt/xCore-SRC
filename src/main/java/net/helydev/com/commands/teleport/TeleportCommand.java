package net.helydev.com.commands.teleport;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.Utils;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class TeleportCommand {

    @Command(name = "teleport", permission = "core.command.teleport", inGameOnly = true, aliases = { "tp"})

    public void tpPlayer(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        if (args.length < 1) {
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("teleport.usage")));
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            if (!Utils.isOnline(player, target)) {
                Utils.PLAYER_NOT_FOUND(player, args[0]);
                return;
            }
            player.teleport(target);
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("teleport.teleported").replace("%target%", target.getName())));
            if (xCore.getPlugin().getMessageconfig().getConfiguration().getBoolean("teleport.sound")) {
                player.playSound(player.getLocation(), Sound.valueOf(xCore.getPlugin().getMessageconfig().getConfiguration().getString("teleport.sound-name").toUpperCase()), 1.0F, 1.0F);
            }
        }
    }
}
