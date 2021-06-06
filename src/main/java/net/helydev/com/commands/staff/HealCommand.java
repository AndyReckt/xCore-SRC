package net.helydev.com.commands.staff;

import com.google.common.collect.ImmutableSet;
import net.helydev.com.utils.BukkitUtils;
import net.helydev.com.utils.Color;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class HealCommand {
    private static final ImmutableSet<PotionEffectType> HEALING_REMOVEABLE_POTION_EFFECTS;

    static {
        HEALING_REMOVEABLE_POTION_EFFECTS = ImmutableSet.of(PotionEffectType.SLOW, PotionEffectType.SLOW_DIGGING, PotionEffectType.POISON, PotionEffectType.WEAKNESS);
    }

    @Command(name = "heal", permission = "core.command.heal", inGameOnly = true)
    public boolean sendMessage(CommandArgs command) {
        Player sender = command.getPlayer();
        String[] args = command.getArgs();

        Player onlyTarget = null;
        Collection<Player> targets;
        if (args.length > 0 && sender.hasPermission("core.command.heal.others")) {
            if (args[0].equalsIgnoreCase("all") && sender.hasPermission("core.command.heal.others")) {
                targets = ImmutableSet.copyOf(Bukkit.getOnlinePlayers());
            } else {
                if ((onlyTarget = BukkitUtils.playerWithNameOrUUID(args[0])) == null || !xCore.canSee(sender, onlyTarget)) {
                    sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("heal-command.not-found")));
                    return true;
                }
                targets = ImmutableSet.of(onlyTarget);
            }
        } else {
            if (sender == null) {
                assert false;
                sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("heal-command.usage")));
                return true;
            }
            targets = ImmutableSet.of((onlyTarget = sender));
        }
        if (onlyTarget != null && onlyTarget.getHealth() == onlyTarget.getMaxHealth()) {
            sender.sendMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("heal-command.full-hearts").replace("%target%", onlyTarget.getName())));
            return true;
        }
        for (final Player target : targets) {
            target.setHealth(target.getMaxHealth());
            for (final PotionEffectType type : HealCommand.HEALING_REMOVEABLE_POTION_EFFECTS) {
                target.removePotionEffect(type);
            }
            target.setFireTicks(0);
        }
        Bukkit.broadcastMessage(Color.translate(xCore.getPlugin().getMessageconfig().getConfiguration().getString("heal-command.healed-all").replace("%executor%", sender.getName())));
        return true;
    }
}