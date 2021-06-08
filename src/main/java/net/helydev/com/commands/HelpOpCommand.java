package net.helydev.com.commands;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.Cooldowns;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.entity.Player;

import static net.helydev.com.xCore.getPlugin;

public class HelpOpCommand {
    @Command(name = "helpop", permission = "core.command.helpop", inGameOnly = true)

    public boolean assistance(CommandArgs command) {
        Player sender = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            sender.sendMessage(Color.translate(getPlugin().getMessageconfig().getConfiguration().getString("helpop.usage")));
            return true;
        }
        if (Cooldowns.isOnCooldown("helpop", sender)) {
            sender.sendMessage(Color.translate(getPlugin().getMessageconfig().getConfiguration().getString("helpop.cooldown")));
            return true;
        }
        final StringBuilder message = new StringBuilder();
        for (int i = 0; i < args.length; ++i) {
            message.append(args[i]).append(" ");
        }
        for (final Player online : xCore.getPlugin().getServer().getOnlinePlayers()) {
            if (online.hasPermission("manager.helpop.view")) {
                online.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("helpop.alert").replace("%player%", sender.getName()).replace("%message%", message)));
            }
        }
        Cooldowns.createCooldown("helpop");
        Cooldowns.addCooldown("helpop", sender, 120);
        sender.sendMessage(Color.translate(getPlugin().getMessageconfig().getConfiguration().getString("helpop.recieved")));
        return false;
    }
}
