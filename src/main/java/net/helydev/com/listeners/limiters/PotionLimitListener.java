package net.helydev.com.listeners.limiters;

import net.helydev.com.utils.Color;
import net.helydev.com.xCore;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class PotionLimitListener implements Listener {
    private ArrayList<PotionLimit> potionLimits;

    public PotionLimitListener(xCore xCore) {
    }

    public void enable() {
        this.loadPotionLimits();
        xCore.getPlugin().getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this.getInstance());
    }

    private Object getInstance() {
        return this;
    }

    public void loadPotionLimits() {
        final ConfigurationSection section = xCore.getPlugin().getLimitersFile().getConfiguration().getConfigurationSection("potion-limiter");
        for (final String type : section.getKeys(false)) {
            if (section.getInt(String.valueOf(String.valueOf(type)) + ".level") == -1) {
                continue;
            }
            final PotionLimit potionLimit = new PotionLimit();
            potionLimit.setType(PotionEffectType.getByName(type));
            potionLimit.setLevel(section.getInt(String.valueOf(String.valueOf(type)) + ".level"));
            potionLimit.setExtended(section.getBoolean(String.valueOf(String.valueOf(type)) + ".extended"));
            this.potionLimits.add(potionLimit);
        }
    }

    public ArrayList<PotionLimit> getPotionLimits() {
        return this.potionLimits;
    }

    @EventHandler
    public void onPotionBrew(final BrewEvent event) {
        final BrewerInventory brewer = event.getContents();
        final ItemStack ingredient = brewer.getIngredient().clone();
        final ItemStack[] potions = new ItemStack[3];
        for (int i = 0; i < 3; ++i) {
            if (event.getContents().getItem(i) != null) {
                potions[i] = brewer.getItem(i).clone();
            }
        }
        new BukkitRunnable() {
            public void run() {
                for (int i = 0; i < 3; ++i) {
                    if (brewer.getItem(i) != null && brewer.getItem(i).getType() == Material.POTION) {
                        for (final PotionEffect potionEffect : Potion.fromItemStack(brewer.getItem(i)).getEffects()) {
                            for (final PotionLimit potionLimit : PotionLimitListener.this.potionLimits) {
                                if (potionLimit.getType().equals((Object)potionEffect.getType())) {
                                    final int maxLevel = potionLimit.getLevel();
                                    final int level = potionEffect.getAmplifier() + 1;
                                    final Potion potion = Potion.fromItemStack(brewer.getItem(i));
                                    if (maxLevel == 0 || level > maxLevel) {
                                        brewer.setIngredient(ingredient);
                                        for (int item = 0; item < 3; ++item) {
                                            brewer.setItem(item, potions[item]);
                                        }
                                        return;
                                    }
                                    if (potion.hasExtendedDuration() && !potionLimit.isExtended()) {
                                        brewer.setIngredient(ingredient);
                                        for (int item = 0; item < 3; ++item) {
                                            brewer.setItem(item, potions[item]);
                                        }
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskLater((Plugin)xCore.getPlugin(), 1L);
    }

    @EventHandler
    public void onPlayerItemConsume(final PlayerItemConsumeEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = event.getItem();
        if (!item.getType().equals((Object)Material.POTION)) {
            return;
        }
        if (item.getType().equals((Object)Material.POTION) && item.getDurability() == 0) {
            return;
        }
        for (final PotionEffect potionEffect : Potion.fromItemStack(item).getEffects()) {
            for (final PotionLimit potionLimit : this.potionLimits) {
                if (potionLimit.getType().equals((Object)potionEffect.getType())) {
                    final int maxLevel = potionLimit.getLevel();
                    final int level = potionEffect.getAmplifier() + 1;
                    final Potion potion = Potion.fromItemStack(item);
                    if (maxLevel == 0 || level > maxLevel) {
                        event.setCancelled(true);
                        player.setItemInHand(new ItemStack(Material.AIR));
                        player.sendMessage(Color.translate("&cThis Potion Effect is disabled."));
                        return;
                    }
                    if (potion.hasExtendedDuration() && !potionLimit.isExtended()) {
                        event.setCancelled(true);
                        player.setItemInHand(new ItemStack(Material.AIR));
                        player.sendMessage(Color.translate("&cThis Potion Effect is disabled."));
                        return;
                    }
                    continue;
                }
            }
        }
    }

    @EventHandler
    public void onPotionSplash(final PotionSplashEvent event) {
        final ThrownPotion thrownPotion = event.getPotion();
        for (final PotionEffect potionEffect : thrownPotion.getEffects()) {
            for (final PotionLimit potionLimit : this.potionLimits) {
                if (potionLimit.getType().equals((Object)potionEffect.getType())) {
                    if (thrownPotion.getShooter() instanceof Player) {
                        final Player shooter = (Player)thrownPotion.getShooter();
                        final int maxLevel = potionLimit.getLevel();
                        final int level = potionEffect.getAmplifier() + 1;
                        final Potion potion = Potion.fromItemStack(thrownPotion.getItem());
                        if (maxLevel == 0 || level > maxLevel) {
                            event.setCancelled(true);
                            shooter.sendMessage(Color.translate("&cThis Potion Effect is disabled."));
                            return;
                        }
                        if (potion.hasExtendedDuration() && !potionLimit.isExtended()) {
                            event.setCancelled(true);
                            shooter.sendMessage(Color.translate("&cThis Potion Effect is disabled."));
                            return;
                        }
                        continue;
                    }
                    else {
                        final int maxLevel2 = potionLimit.getLevel();
                        final int level2 = potionEffect.getAmplifier();
                        final Potion potion2 = Potion.fromItemStack(thrownPotion.getItem());
                        if (maxLevel2 == 0 || level2 > maxLevel2) {
                            event.setCancelled(true);
                            return;
                        }
                        if (potion2.hasExtendedDuration() && !potionLimit.isExtended()) {
                            event.setCancelled(true);
                            return;
                        }
                        continue;
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBrew(final BrewEvent event) {
        if (event.getContents().getIngredient().getType() == Material.GHAST_TEAR || event.getContents().getIngredient().getType() == Material.BLAZE_POWDER) {
            event.setCancelled(true);
        }
    }

    public static class PotionLimit
    {
        private PotionEffectType type;
        private int level;
        private boolean extended;

        public PotionEffectType getType() {
            return this.type;
        }

        public void setType(final PotionEffectType type) {
            this.type = type;
        }

        public int getLevel() {
            return this.level;
        }

        public void setLevel(final int level) {
            this.level = level;
        }

        public boolean isExtended() {
            return this.extended;
        }

        public void setExtended(final boolean extended) {
            this.extended = extended;
        }
    }
}

