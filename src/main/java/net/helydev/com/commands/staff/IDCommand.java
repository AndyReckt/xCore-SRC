package net.helydev.com.commands.staff;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class IDCommand {

    @Command(name = "itemid", permission = "core.command.itemid", inGameOnly = true)
    public boolean onCommand(CommandArgs command) {
        Player p = command.getPlayer();

        if(p != null){
            if(p.getInventory().getItemInHand() != null && p.getItemInHand().getType() != Material.AIR){
                p.sendMessage(ChatColor.YELLOW + "The Item ID of your " + p.getItemInHand().getType().toString().replace("_", "").toLowerCase() + " is: " + p.getItemInHand().getTypeId());
                return true;
            }
            p.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("itemid.no-item")));
            return true;
        }
        return false;
    }
}
