package net.helydev.com.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class KillTrackerListener implements Listener {


    public KillTrackerListener() {
    }

    @SuppressWarnings("unused")
    private void addDeathLore(final ItemStack stack, final Player player, final Player killer) {
        final ItemMeta meta = stack.getItemMeta();
        final List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>(2);
        if(lore.isEmpty() || !lore.get(0).startsWith(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Kills ")) {

            lore.add(0, ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Kills "+ ChatColor.RED +1);
        }else{
            final String killsString = lore.get(0).replace(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Kills ", "").replace(ChatColor.YELLOW + "]", "");
            int kills = 1;
            try{
                Integer.parseInt(killsString);
                kills = Integer.parseInt(killsString);
            }catch(NumberFormatException e){
                e.printStackTrace();
            }
            int killafteradd = kills +1;
            lore.set(0, ChatColor.GOLD + ChatColor.BOLD.toString() + "Kills "+ ChatColor.RED +killafteradd);
        }
        meta.setLore( lore.subList(0, Math.min(6, lore.size())));
        stack.setItemMeta(meta);
    }
}
