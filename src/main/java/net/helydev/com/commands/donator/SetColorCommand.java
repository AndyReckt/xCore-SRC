package net.helydev.com.commands.donator;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class SetColorCommand {
    public static List<Material> list = Arrays.asList(Material.GLASS, Material.STAINED_GLASS, Material.STAINED_GLASS_PANE, Material.THIN_GLASS, Material.HARD_CLAY, Material.STAINED_CLAY, Material.WOOL, Material.CARPET);

    @Command(name = "setcolor", permission = "core.command.setcolor", inGameOnly = true)

    public boolean sendMessage(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        if (player.getItemInHand() == null || player.getItemInHand().getType().equals(Material.AIR)) {
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("setcolor.cannot-color")));
            return false;
        }

        if (args.length < 1) {
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("setcolor.usage")));
            return true;
        }

        if (args[0].equalsIgnoreCase("reset")) {
            if (player.getItemInHand().getType() == Material.STAINED_CLAY) {
                player.getItemInHand().setType(Material.HARD_CLAY);
            } else if (player.getItemInHand().getType() == Material.STAINED_GLASS) {
                player.getItemInHand().setType(Material.GLASS);
            } else if (player.getItemInHand().getType() == Material.STAINED_GLASS_PANE) {
                player.getItemInHand().setType(Material.THIN_GLASS);
            }

            player.getItemInHand().setDurability((short) 0);
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("setcolor.reset")));
            return false;
        }

        if (getColor(args[0]) == null) {
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("setcolor.invalid-color")));
            return false;
        }

        if (list.contains(player.getItemInHand().getType())) {
            if (player.getItemInHand().getType() == Material.HARD_CLAY) {
                player.getItemInHand().setType(Material.STAINED_CLAY);
            } else if (player.getItemInHand().getType() == Material.GLASS) {
                player.getItemInHand().setType(Material.STAINED_GLASS);
            } else if (player.getItemInHand().getType() == Material.THIN_GLASS) {
                player.getItemInHand().setType(Material.STAINED_GLASS_PANE);
            }

            player.getItemInHand().setDurability(getColor(args[0]).getData());
            return false;
        }

        player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("setcolor.cannot-color")));
        return false;
    }

    public static DyeColor getColor(final String s) {
        DyeColor c;
        try {
            c = DyeColor.valueOf(s.toUpperCase());
        } catch (Exception e) {
            c = null;
        }
        return c;
    }
}