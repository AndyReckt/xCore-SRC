package net.helydev.com.commands;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class PlaytimeCommand {
    @Command(name = "playtime", permission = "core.command.clear", inGameOnly = true)
    public void onCommand(CommandArgs command) {
        Player sender = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            final long l = sender.getStatistic(Statistic.PLAY_ONE_TICK);
            sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("playtime.self").replace("%time%", DurationFormatUtils.formatDurationWords(l * 50L, true, true))));
            return;
        }
        final Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            assert false;
            sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("playtime.offline")));

            return;
        }
        final long i = target.getStatistic(Statistic.PLAY_ONE_TICK);
        sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("playtime.other").replace("%target%", target.getName()).replace("%time%", DurationFormatUtils.formatDurationWords(i * 50L, true, true))));
    }
}

