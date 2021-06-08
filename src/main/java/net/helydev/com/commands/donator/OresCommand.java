package net.helydev.com.commands.donator;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class OresCommand {
    @Command(name = "ores", permission = "core.command.ores", inGameOnly = true)
    public void oresss(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        if (args.length == 0) {
            for (String msg : xCore.getPlugin().getMessageconfig().getConfiguration().getStringList("ores.stats-self")) {
                msg = msg.replace("%emeralds%", String.valueOf(player.getStatistic(Statistic.MINE_BLOCK, Material.EMERALD_ORE)));
                msg = msg.replace("%target%", player.getName());
                msg = msg.replace("%diamonds%", String.valueOf(player.getStatistic(Statistic.MINE_BLOCK, Material.DIAMOND_ORE)));
                msg = msg.replace("%gold%", String.valueOf(player.getStatistic(Statistic.MINE_BLOCK, Material.GOLD_ORE)));
                msg = msg.replace("%redstone%", String.valueOf(player.getStatistic(Statistic.MINE_BLOCK, Material.REDSTONE_ORE)));
                msg = msg.replace("%iron%", String.valueOf(player.getStatistic(Statistic.MINE_BLOCK, Material.IRON_ORE)));
                msg = msg.replace("%coal%", String.valueOf(player.getStatistic(Statistic.MINE_BLOCK, Material.COAL_ORE)));
                msg = msg.replace("%lapis%", String.valueOf(player.getStatistic(Statistic.MINE_BLOCK, Material.LAPIS_ORE)));
                player.sendMessage(Color.translate(msg));
            }
        } else {
            final Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("ores.offline")));
            }

            for (String msg2 : xCore.getPlugin().getMessageconfig().getConfiguration().getStringList("ores.stats-target")) {
                msg2 = msg2.replace("%name%", String.valueOf(target.getName()));
                msg2 = msg2.replace("%emeralds%", String.valueOf(target.getStatistic(Statistic.MINE_BLOCK, Material.EMERALD_ORE)));
                msg2 = msg2.replace("%diamonds%", String.valueOf(target.getStatistic(Statistic.MINE_BLOCK, Material.DIAMOND_ORE)));
                msg2 = msg2.replace("%gold%", String.valueOf(target.getStatistic(Statistic.MINE_BLOCK, Material.GOLD_ORE)));
                msg2 = msg2.replace("%redstone%", String.valueOf(target.getStatistic(Statistic.MINE_BLOCK, Material.REDSTONE_ORE)));
                msg2 = msg2.replace("%iron%", String.valueOf(target.getStatistic(Statistic.MINE_BLOCK, Material.IRON_ORE)));
                msg2 = msg2.replace("%coal%", String.valueOf(target.getStatistic(Statistic.MINE_BLOCK, Material.COAL_ORE)));
                msg2 = msg2.replace("%lapis%", String.valueOf(target.getStatistic(Statistic.MINE_BLOCK, Material.LAPIS_ORE)));
                player.sendMessage(Color.translate(msg2));
            }
        }
    }
}
