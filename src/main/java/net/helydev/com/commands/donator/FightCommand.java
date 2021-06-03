package net.helydev.com.commands.donator;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FightCommand {

    public FightCommand() {
    }

    @Command(name = "fight", permission = "core.command.fight", inGameOnly = true)
    public boolean sendbroadcast(CommandArgs command) {
        Player sender = command.getPlayer();
        if (sender.hasPermission("core.command.fight")) {
            Bukkit.broadcastMessage(Color.translate("&7&l&m-----------------------------------------------------"));
            Bukkit.broadcastMessage(Color.translate("&b&lPlayer Fights"));
            Bukkit.broadcastMessage(Color.translate("&a" + sender.getName() + "&7 is looking for a player to fight!"));
            Bukkit.broadcastMessage(Color.translate(" "));
            Bukkit.broadcastMessage(Color.translate("&bX: &7" + sender.getLocation().getBlockX()));
            Bukkit.broadcastMessage(Color.translate("&bZ: &7" + sender.getLocation().getBlockZ()));
            Bukkit.broadcastMessage(Color.translate("&7&l&m-----------------------------------------------------"));
        }
        return false;
    }
}
