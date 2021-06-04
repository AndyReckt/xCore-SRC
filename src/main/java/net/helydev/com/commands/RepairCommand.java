package net.helydev.com.commands;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class RepairCommand {
    @Command(name = "repair", permission = "core.command.repair", inGameOnly = true)
    public void Items(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("repair.usage")));
        }
        else if (args[0].equalsIgnoreCase("hand")) {
            final ItemStack itemInHand = player.getItemInHand();
            @Deprecated
            final Material material = Material.getMaterial(itemInHand.getTypeId());
            if (material == null || material == Material.AIR || material == Material.POTION || material == Material.GOLDEN_APPLE || material.isBlock() || material.getMaxDurability() < 1) {
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("repair.no-item")));
                return;
            }
            if (itemInHand.getDurability() == 0) {
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("repair.not-damaged")));
                return;
            }
            itemInHand.setDurability((short)0);
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("repair.repaired-hand")));
        }
        else if (args[0].equalsIgnoreCase("all")) {
            if (!player.hasPermission("command.repair.all")) {
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("repair.no-permission")));
                return;
            }
            final ArrayList<ItemStack> list = new ArrayList<>();
            for (final ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack != null && itemStack.getType() != Material.POTION && itemStack.getType() != Material.GOLDEN_APPLE && !itemStack.getType().isBlock() && itemStack.getType().getMaxDurability() > 1 && itemStack.getDurability() != 0) {
                    list.add(itemStack);
                }
            }
            for (final ItemStack itemStack2 : player.getInventory().getArmorContents()) {
                if (itemStack2 != null && itemStack2.getType() != Material.AIR) {
                    list.add(itemStack2);
                }
            }
            if (list.isEmpty()) {
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("repair.inventory-repair-fail")));
                return;
            }
            for (ItemStack itemStack : list) {
                itemStack.setDurability((short) 0);
            }
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("repair.repaired-inventory")));
        }
        else {
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("repair.usage")));
        }
    }
}