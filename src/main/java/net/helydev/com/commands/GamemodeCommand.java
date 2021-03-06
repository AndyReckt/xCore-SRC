package net.helydev.com.commands;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Locale;

public class GamemodeCommand {

    @Command(name = "gamemode", permission = "core.command.gamemode", inGameOnly = true, aliases = {"gm"})
    public void setGamemode(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length < 1 && !command.getLabel().equalsIgnoreCase("gmc") && !command.getLabel().equalsIgnoreCase("gma") && !command.getLabel().equalsIgnoreCase("gms")) {
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("gamemode.usage")));

            return;
        }

        GameMode mode;

        if (command.getLabel().equalsIgnoreCase("gmc")) {
            mode = GameMode.CREATIVE;
        }
        else if (command.getLabel().equalsIgnoreCase("gma")) {
            mode = GameMode.ADVENTURE;
        }
        else if (command.getLabel().equalsIgnoreCase("gms")) {
            mode = GameMode.SURVIVAL;
        }
        else {
            mode = this.getGameModeByName(args[0]);
        }

        Player target;

        if (args.length > 1) {
            if (player.hasPermission("core.command.gamemode.others")) {
                target = Bukkit.getPlayer(args[1]);
            }
            else {
                target = null;
            }
        }
        else {
            target = player;
        }

        if (target == null) {
            assert player != null;
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("gamemode.not-online")));
            return;
        }

        if (target.getGameMode() == mode) {
            assert mode != null;
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("gamemode.is-already").replace("%target%", target.getName()).replace("%mode%", mode.name())));
            return;
        }

        target.setGameMode(mode);
        assert mode != null;
        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("gamemode.other-changed").replace("%mode", mode.name())));
    }

    private GameMode getGameModeByName(String id) {
        id = id.toLowerCase(Locale.ENGLISH);

        if (id.equalsIgnoreCase("gmc") || id.contains("creat") || id.equalsIgnoreCase("1") || id.equalsIgnoreCase("c")) {
            return GameMode.CREATIVE;
        }

        if (id.equalsIgnoreCase("gms") || id.contains("survi") || id.equalsIgnoreCase("0") || id.equalsIgnoreCase("s")) {
            return GameMode.SURVIVAL;
        }

        if (id.equalsIgnoreCase("gma") || id.contains("advent") || id.equalsIgnoreCase("2") || id.equalsIgnoreCase("a")) {
            return GameMode.ADVENTURE;
        }

        return null;
    }

}
