package net.helydev.com.commands;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.Utils;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InvseeCommand {

    @Command(name = "invsee", permission = "core.command.invsee", inGameOnly = true)

    public void openInvsee(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        if (args.length < 1) {
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("invsee.usage")));
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            if (!Utils.isOnline(player, target)) {
                Utils.PLAYER_NOT_FOUND(player, args[0]);
                return;
            }
            if (target.equals(player)) {
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("invsee.open-self")));
                return;
            }
            player.openInventory(target.getInventory());
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("invsee.opened").replace("%target%", target.getName())));
        }
    }
}
