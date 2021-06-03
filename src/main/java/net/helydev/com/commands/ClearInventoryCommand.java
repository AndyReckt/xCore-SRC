package net.helydev.com.commands;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.Utils;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ClearInventoryCommand {
    @Command(name = "clear", permission = "core.command.clear", inGameOnly = true, aliases = {"clearinventory", "inventoryclear"})
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            int total = this.getTotalItems(player);

            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.updateInventory();

            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("clear.cleared")));
            return;
        } else if (player.hasPermission("core.command.clear.others")) {
            Player target = Bukkit.getPlayer(args[0]);

            if (!Utils.isOnline(player, target)) {
                Utils.PLAYER_NOT_FOUND(player, args[0]);
                return;
            }
            int total = this.getTotalItems(target);

            target.getInventory().clear();
            target.getInventory().setArmorContents(null);
            target.updateInventory();

            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("clear.cleared-other").replace("%target%", target.getName())));
            target.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("clear.cleared-other-target").replace("%executor%", player.getName())));
            target.sendMessage(Color.translate("&aYour inventory has been cleared by &f"+ player.getName() + "'s &ainventory. &7("+ total +" total items&7)"));
        }

    }
    private int getTotalItems(Player player) {
        int index = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null) index += item.getAmount();
        }
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null) index += item.getAmount();
        }
        return index;
    }
}
