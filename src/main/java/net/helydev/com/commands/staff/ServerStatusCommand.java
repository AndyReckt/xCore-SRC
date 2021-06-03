package net.helydev.com.commands.staff;

import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

public class ServerStatusCommand {
    @net.helydev.com.utils.commands.Command(name = "serverstatus", permission = "core.command.serverstatus", inGameOnly = true)

    public boolean sendMessage(CommandArgs command) {
        Player sender = command.getPlayer();
        String[] args = command.getArgs();
        if (!sender.hasPermission("core.command.serverstatus")) {
            return true;
        } else {
            long startTime = ManagementFactory.getRuntimeMXBean().getStartTime();
            String upTime = DurationFormatUtils.formatDurationWords(System.currentTimeMillis() - startTime, true, true);
            final DecimalFormat TPS_FORMAT = new DecimalFormat("00.0");
            for (String msg : xCore.getPlugin().getMessageconfig().getConfiguration().getStringList("server-status.status")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg).replace("%tps%", TPS_FORMAT.format(Bukkit.spigot().getTPS()[0])).replace("%uptime%", upTime).replace("%free_memory%", String.valueOf(Runtime.getRuntime().freeMemory())));
            }
            return true;
        }
    }
}
