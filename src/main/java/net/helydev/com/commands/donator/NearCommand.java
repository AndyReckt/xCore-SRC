package net.helydev.com.commands.donator;

import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NearCommand {

    @Command(name = "near", permission = "core.command.near", inGameOnly = true)
    public boolean onCommand(CommandArgs command) {
        Player p = command.getPlayer();

        List<String> nearby = getNearbyEnemies(p);
        for (String msg : xCore.getPlugin().getMessageconfig().getConfiguration().getStringList("near.list")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg).replace("%list%", (nearby.isEmpty() ? "None" : nearby.toString().replace("[", "").replace("]", ""))));
        }
        return true;
    }


    private List<String> getNearbyEnemies(final Player player){
        final List<String> players = new ArrayList<>();
        final Collection<Entity> nearby = player.getNearbyEntities(50.0, 50.0, 50.0);
        for(final Entity entity : nearby){
            if(entity instanceof Player){
                final Player target = (Player) entity;
                if(!target.canSee(player)){
                    continue;
                }
                if(!player.canSee(target)){
                    continue;
                }
                if(target.hasPotionEffect(PotionEffectType.INVISIBILITY)){
                    continue;
                }
                players.add(target.getName());
            }
        }
        return players;
    }
}
