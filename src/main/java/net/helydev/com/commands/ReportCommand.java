package net.helydev.com.commands;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.Cooldowns;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static net.helydev.com.xCore.getPlugin;

public class ReportCommand {
    @Command(name = "report", permission = "core.command.report", inGameOnly = true)

    public boolean reportplayers(CommandArgs command) {
        Player sender = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            sender.sendMessage(Color.translate(getPlugin().getMessageconfig().getConfiguration().getString("report.usage")));
            return true;
        }
        if (Cooldowns.isOnCooldown("report", sender)) {
            sender.sendMessage(Color.translate(getPlugin().getMessageconfig().getConfiguration().getString("report.cooldown")));
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage(Color.translate(getPlugin().getMessageconfig().getConfiguration().getString("report.specify-player")));
            return true;
        }
        final Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Color.translate(getPlugin().getMessageconfig().getConfiguration().getString("report.not-online")));
            return true;
        }
        final StringBuilder message = new StringBuilder();
        for (int i = 1; i < args.length; ++i) {
            message.append(args[i]).append(" ");
        }
        if (target instanceof Player) {
            for (final Player online : getPlugin().getServer().getOnlinePlayers()) {
                if (online.hasPermission("core.command.report.view")) {
                    online.sendMessage(Color.translate(getPlugin().getMessageconfig().getConfiguration().getString("report.alert").replace("%player%", sender.getName()).replace("%message%", message).replace("%target%", target.getName())));
                }
            }
        }
        Cooldowns.createCooldown("report");
        Cooldowns.addCooldown("report", sender, 45);
        sender.sendMessage(Color.translate(getPlugin().getMessageconfig().getConfiguration().getString("report.recieved")));
        return false;
    }
}
