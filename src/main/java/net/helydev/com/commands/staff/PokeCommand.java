package net.helydev.com.commands.staff;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PokeCommand {
    @net.helydev.com.utils.commands.Command(name = "poke", permission = "core.command.poke", inGameOnly = true)

    public boolean sendMessage(CommandArgs command) {
        Player sender = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1) {
            sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("poke.usage")));
            return true;
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("poke.not-found")));
                return true;
            }
            target.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("poke.poked")));
            target.playSound(target.getLocation(),
                    Sound.valueOf(xCore.getPlugin().getMessageconfig().getConfiguration().getString("poke.sound")), 1F, 1F);
            return true;
        }
    }
}

