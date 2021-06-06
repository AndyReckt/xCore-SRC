package net.helydev.com.commands.donator;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class HatCommand {

    @Command(name = "hat", permission = "core.command.hat", inGameOnly = true)
    public void onCommand(CommandArgs command) {
        Player sender = command.getPlayer();
        ItemStack stack = sender.getItemInHand();
        if (stack == null || stack.getType() == Material.AIR) {
            sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("hat-command.not-holding")));
            return;
        }
        if (stack.getType().getMaxDurability() != 0) {
            sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("hat-command.not-suitable")));
            return;
        }

        PlayerInventory inventory = sender.getInventory();
        ItemStack helmet = inventory.getHelmet();

        if (helmet != null && helmet.getType() != Material.AIR) {
            sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("hat-command.already-have")));
            return;
        }

        int amount = stack.getAmount();
        if (amount > 1) {
            --amount;
            stack.setAmount(amount);
        } else {
            sender.setItemInHand(new ItemStack(Material.AIR, 1));
        }
        helmet = stack.clone();
        helmet.setAmount(1);
        inventory.setHelmet(helmet);
        sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("hat-command.done")));
    }
}