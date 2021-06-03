package net.helydev.com.commands.time;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.entity.Player;

public class DayCommand {

    @Command(name = "day", permission = "core.command.day", inGameOnly = true, aliases = {"daytime"})

    public void setDayTime(CommandArgs command) {
        Player player = command.getPlayer();
        player.setPlayerTime(6000L, false);
        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("time-commands.day")));
    }
}
