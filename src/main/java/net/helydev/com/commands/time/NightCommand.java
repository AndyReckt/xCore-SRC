package net.helydev.com.commands.time;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.entity.Player;

public class NightCommand {

    @Command(name = "night", permission = "core.command.night", inGameOnly = true, aliases = {"nighttime"})

    public void setNightTime(CommandArgs command) {
        Player player = command.getPlayer();
        player.setPlayerTime(18000L, false);
        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("time-commands.night")));
    }
}

