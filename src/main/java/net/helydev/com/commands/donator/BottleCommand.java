package net.helydev.com.commands.donator;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BottleCommand implements Listener {
    private Map<ThrownExpBottle, Integer> thrownExpBottleIntegerMap;
    private String name;

    public void BottleExpCommand() {
        this.thrownExpBottleIntegerMap = new HashMap<ThrownExpBottle, Integer>();
        this.name = ChatColor.GOLD + "XP Bottle";
    }

    @Command(name = "bottle", permission = "core.command.bottle", aliases = { "bottlexp", "xpbottle" }, inGameOnly = true)
    public void sendMessage(final CommandArgs command) {
        final Player player = command.getPlayer();
        final String[] args = command.getArgs();
        final int exp = player.getTotalExperience();
        if (exp == 0) {
            player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("bottle.no-xp")));
        }
        else {
            final ItemStack bottle = this.createBottle(player);
            player.setTotalExperience(0);
            player.setLevel(0);
            player.setExp(0.0f);
            if (player.getInventory().addItem(new ItemStack[] { bottle }).isEmpty()) {
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("bottle.bottled")));
            }
            else {
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("bottle.no-space")));
            }
        }
    }

    @EventHandler
    public void onPlayerClick(final PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        final ItemStack item = e.getItem();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || (e.getAction() == Action.RIGHT_CLICK_BLOCK && item != null && item.getType() == Material.EXP_BOTTLE)) {
            final ItemMeta meta = item.getItemMeta();
            final String name = meta.getDisplayName();
            if (meta.hasDisplayName() && meta.hasLore() && name.equals(this.name) && meta.getLore().size() == 3) {
                final String loreline = meta.getLore().get(2);
                e.setCancelled(true);
                Integer xp;
                try {
                    xp = Integer.parseInt(loreline.substring((ChatColor.YELLOW + "Experience: " + ChatColor.GRAY).length(), loreline.length()));
                }
                catch (Exception ex) {
                    player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("bottle.invalid")));
                    return;
                }
                final ThrownExpBottle thrownExpBottle = (ThrownExpBottle)player.launchProjectile((Class)ThrownExpBottle.class);
                this.thrownExpBottleIntegerMap.put(thrownExpBottle, xp);
                final ItemStack hand = item.clone();
                hand.setAmount(hand.getAmount() - 1);
                player.getInventory().setItemInHand(hand);
                player.updateInventory();
            }
        }
    }

    public int getExpToLevel(final int expLevel) {
        return (expLevel >= 30) ? (62 + (expLevel - 30) * 7) : ((expLevel >= 15) ? (17 + (expLevel - 15) * 3) : 17);
    }

    public int fromXP(final int levels, final float exp) {
        int xp = 0;
        for (int i = levels; i >= 0; --i) {
            xp += this.getExpToLevel(i);
        }
        xp -= levels;
        return xp;
    }

    public ItemStack createBottle(final Player player) {
        final int exp = this.fromXP(player.getLevel(), player.getExp());
        final int levels = player.getLevel();
        final float remainder = player.getExp();
        final ItemStack itemStack = new ItemStack(Material.EXP_BOTTLE, 1);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(this.name);
        itemMeta.setLore((List)Arrays.asList(ChatColor.YELLOW + "Owner: " + ChatColor.GRAY + player.getName(), ChatColor.YELLOW + "Worth: " + ChatColor.GRAY + new DecimalFormat("#.#").format(levels + remainder) + " Levels", ChatColor.YELLOW + "Experience: " + ChatColor.GRAY + exp));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}