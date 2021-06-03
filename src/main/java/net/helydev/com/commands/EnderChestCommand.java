package net.helydev.com.commands;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.Utils;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EnderChestCommand {

    @Command(name = "enderchest", permission = "core.command.enderchest", inGameOnly = true, aliases = {"ec", "pv", "vault", "privatevault", "chest"})

    public void openEnderchest(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        if (args.length < 1) {
            player.openInventory(player.getEnderChest());
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("enderchest.opened")));
        } else if (player.hasPermission("core.command.enderchest.others")) {
            Player target = Bukkit.getPlayer(args[0]);
            if (!Utils.isOnline(player, target)) {
                Utils.PLAYER_NOT_FOUND(player, args[0]);
                return;
            }
            if (target.equals(player)) {
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("enderchest.open-self")));
                return;
            }
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("enderchest.opened-player").replace("%target", target.getName())));
            player.openInventory(target.getEnderChest());

        }
    }
}
