package net.helydev.com.commands.donator;

import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PerksCommand implements Listener {
    private final Set<UUID> speed;
    private final Set<UUID> fire_resistance;
    private final Set<UUID> invisibility;

    public PerksCommand() {
        this.speed = new HashSet<>();
        this.fire_resistance = new HashSet<>();
        this.invisibility = new HashSet<>();
    }

    @Command(name = "perks", permission = "core.command.perks", inGameOnly = true)
    public boolean inventory(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        if (args.length < 1) {
            for (String msg : xCore.getPlugin().getMessageconfig().getConfiguration().getStringList("perks.help")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("speed")) {
            if (!this.speed.contains(player.getUniqueId())) {
                this.speed.add(player.getUniqueId());
                player.removePotionEffect(PotionEffectType.SPEED);
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("perks.removed-speed")));
            }
            else {
                this.speed.remove(player.getUniqueId());
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 1));
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("perks.used-speed")));
            }
        }

        if (args[0].equalsIgnoreCase("fireresistance")) {
            if (!this.fire_resistance.contains(player.getUniqueId())) {
                this.fire_resistance.add(player.getUniqueId());
                player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("perks.removed-fireresistance")));
            }
            else {
                this.fire_resistance.remove(player.getUniqueId());
                player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000, 0));
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("perks.used-fireresistance")));
            }
        }

        if (args[0].equalsIgnoreCase("invisibility")) {
            if (!this.invisibility.contains(player.getUniqueId())) {
                this.invisibility.add(player.getUniqueId());
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("perks.removed-invisibility")));
            }
            else {
                this.invisibility.remove(player.getUniqueId());
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 0));
                player.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("perks.used-invisibility")));
            }
        }
        return false;
    }
}