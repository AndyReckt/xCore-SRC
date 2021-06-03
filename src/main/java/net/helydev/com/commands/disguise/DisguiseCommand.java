package net.helydev.com.commands.disguise;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.entity.Player;

public class    DisguiseCommand {

    @Command(name = "disguise", permission = "core.command.disguise", inGameOnly = true)
    public void setSkinDisguise(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        if (args.length < 1) {
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("disguise.usage")));
        }
        if (args.length < 2) {
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("disguise.usage")));
        } if(args[0].equalsIgnoreCase("player")){
            String skin = args[1];
            DisguiseAPI.disguiseToAll(player, new PlayerDisguise(skin).setViewSelfDisguise(false));
            player.sendMessage(Color.translate("&eYou have been disguised as &f"+ skin +"&a."));
        }
    }
}
