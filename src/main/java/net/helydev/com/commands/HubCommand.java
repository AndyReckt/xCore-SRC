package net.helydev.com.commands;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import net.minecraft.util.org.apache.commons.io.output.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class HubCommand {

    @Command(name = "hub", permission = "", inGameOnly = true)

    public void conectedHub(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        if (args.length < 1) {
        teleport(player, (xCore.getPlugin().getConfig().getString("hub-command.hub-server")));

        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("hub.sent")));
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(xCore.getPlugin(), new Runnable(){
            public void run(){
                if(player.isOnline()){
                    player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("hub.error")));
                }
            }
        }, 20 * 5);
        return;
        }
    }

    public static void teleport(Player pl, String input)
    {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(input);
        }
        catch (IOException localIOException) {}
        pl.sendPluginMessage(xCore.getPlugin(), "BungeeCord", b.toByteArray());
    }
}
