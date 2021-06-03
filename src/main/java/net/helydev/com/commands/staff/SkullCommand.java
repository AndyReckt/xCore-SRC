package net.helydev.com.commands.staff;
import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullCommand {

    private ItemStack playerSkullForName(String name) {
        ItemStack is = new ItemStack(Material.SKULL_ITEM, 1);
        is.setDurability((short)3);
        SkullMeta meta = (SkullMeta)is.getItemMeta();
        meta.setOwner(name);
        is.setItemMeta(meta);
        return is;
    }

    @Command(name = "skull", permission = "core.command.skull", inGameOnly = true)
    public boolean skulls(CommandArgs command) {
        Player sender = command.getPlayer();
        String[] args = command.getArgs();


        if (sender == null) {
            assert false;
            sender.sendMessage(ChatColor.RED + "You are not a player");
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("skull.usage")));
            return true;
        }
        sender.getInventory().addItem(playerSkullForName(args[0]));
        sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("skull.given").replace("%player%", args[0])));


        return true;
    }
}