package net.helydev.com.commands;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import net.minecraft.util.org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import java.io.DataOutputStream;
import java.io.IOException;

public class HubCommand {

    public static void teleport(final Player pl, final String input) {
        final ByteArrayOutputStream b = new ByteArrayOutputStream();
        final DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(input);
        }
        catch (IOException ex) {}
        pl.sendPluginMessage((Plugin)xCore.getPlugin(), "BungeeCord", b.toByteArray());
    }

    @Command(name = "hub", inGameOnly = true)
    public void conectedHub(CommandArgs command) {
        final Player player = command.getPlayer();
        teleport(player, xCore.getPlugin().getConfig().getString("hub-command.hub-server"));
        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("hub.sent")));
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)xCore.getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                if (player.isOnline()) {
                    player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("hub.error")));
                }
            }
        }, 100L);
    }
}