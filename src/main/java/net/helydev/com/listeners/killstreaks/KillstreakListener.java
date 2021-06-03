package net.helydev.com.listeners.killstreaks;

import net.helydev.com.xCore;
import net.minecraft.util.gnu.trove.map.TObjectIntMap;
import net.minecraft.util.gnu.trove.map.hash.TObjectIntHashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.UUID;

public class KillstreakListener implements Listener {

    public static KillstreakListener INSTANCE;

    private final TObjectIntMap<UUID> killStreakMap;

    public KillstreakListener() {
        KillstreakListener.INSTANCE = this;
        killStreakMap = new TObjectIntHashMap<>();
    }

    public int getKillStreak(OfflinePlayer player) {
        return killStreakMap.get(player.getUniqueId());
    }

    @EventHandler
    public void onDeath(final PlayerDeathEvent event) {
            Player entity = event.getEntity();
            if (entity.getKiller() instanceof Player) {
                Player player = event.getEntity().getKiller();
                killStreakMap.adjustOrPutValue(player.getUniqueId(), 1, 1);
                killstreak:
                for (KillStreaks killStreaks : xCore.killStreaks) {
                    if (killStreakMap.get(player.getUniqueId()) == killStreaks.getNumber()) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), killStreaks.getCommand().replace("%player%", player.getName()));
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', xCore.getPlugin().getConfig().getString("killstreaks.broadcast-message").replace("%item%", killStreaks.getName()).replace("%killstreak%", Integer.toString(killStreaks.getNumber())).replace("%player%", player.getName())));
                        return;
                    }
                }
            }
            killStreakMap.put(entity.getUniqueId(), 0);
    }
}
