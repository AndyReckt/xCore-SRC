package net.helydev.com.commands.teleport;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TpAllCommand {

    @Command(name = "tpall", permission = "core.command.teleportall", inGameOnly = true)

    public boolean teleportAll(CommandArgs command) {
        Player sender = command.getPlayer();

        @Deprecated
        Player player = (Player) sender;
        Player[] arrayOfPlayer;
        int j = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length;
        for (int i = 0; i < j; i++) {
            Player target = arrayOfPlayer[i];
            if ((!target.equals(player)) && (player.canSee(target))) {
                target.teleport(player, PlayerTeleportEvent.TeleportCause.COMMAND);
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("tp-all.target-message").replace("%player%", player.getName())));
            }
        }
        return true;
    }
}
