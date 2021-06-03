package net.helydev.com.commands.teleport;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.NumberUtils;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class TeleportPositionCommand {
    @Command(name = "tppos", permission = "core.command.tppos", inGameOnly = true)

    public void TopCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        if (args.length < 3) {
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("tppos.usage")));
        }
        else {
            if (!NumberUtils.isInteger(args[0])) {
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("tppos.only-integer")));
                return;
            }
            if (!NumberUtils.isInteger(args[1])) {
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("tppos.only-integer")));
                return;
            }
            if (!NumberUtils.isInteger(args[2])) {
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("tppos.only-integer")));
                return;
            }
            final int xcoordinate = Integer.parseInt(args[0]);
            final int ycoordinate = Integer.parseInt(args[1]);
            final int zcoordinate = Integer.parseInt(args[2]);
            final Location loc = new Location(player.getWorld(), xcoordinate, ycoordinate, zcoordinate);
            if (xcoordinate > 30000 || ycoordinate > 30000 || zcoordinate > 30000 || xcoordinate < -30000 || ycoordinate < -30000 || zcoordinate < -30000) {
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("tppos.max")));
                return;
            }
            player.teleport(loc);
            player.sendMessage(Color.translate("&7You have been teleported to &ex" + xcoordinate + "&7, &ey" + ycoordinate + "&7, &ez" + zcoordinate + "&e."));
            if (xCore.getPlugin().getMessageconfig().getConfiguration().getBoolean("tppos.sound")) {
                player.playSound(player.getLocation(), Sound.valueOf(xCore.getPlugin().getMessageconfig().getConfiguration().getString("tppos.sound-name").toUpperCase()), 1.0F, 1.0F);
            }
        }
    }
}

