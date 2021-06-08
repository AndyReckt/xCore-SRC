package net.helydev.com.listeners;

import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class MobstackListener implements Listener {
    private final List<EntityType> mobList;

    public MobstackListener() {
        this.mobList = new ArrayList<>();
    }

    public void enable() {
        this.loadEntityList();
        this.startStackTask();
        xCore.getPlugin().getServer().getPluginManager().registerEvents(this, xCore.getPlugin());
    }

    public void disable() {
        this.mobList.clear();
        for (final World world : Bukkit.getWorlds()) {
            for (final LivingEntity entity : world.getLivingEntities()) {
                if (entity instanceof Monster && entity.isCustomNameVisible()) {
                    entity.remove();
                }
            }
        }
    }

    public void loadEntityList() {
        if (!this.mobList.isEmpty()) {
            this.mobList.clear();
        }
        for (final String entityName : xCore.getPlugin().getConfig().getStringList("settings.server.mob-stack.entity")) {
            final EntityType entityType = EntityType.valueOf(entityName.toUpperCase());
            this.mobList.add(entityType);
        }
    }

    public void startStackTask() {
        new BukkitRunnable() {
            public void run() {
                final int radius = 5;
                final List<EntityType> entityTypes = MobstackListener.this.mobList;
                for (final World world : Bukkit.getServer().getWorlds()) {
                    for (final LivingEntity entity : world.getLivingEntities()) {
                        if (entityTypes.contains(entity.getType()) && entity.isValid()) {
                            for (final Entity nearby : entity.getNearbyEntities(radius, radius, radius)) {
                                if (nearby instanceof LivingEntity && nearby.isValid() && entityTypes.contains(nearby.getType())) {
                                    MobstackListener.this.stackOne(entity, (LivingEntity)nearby, ChatColor.BLUE);
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(xCore.getPlugin(), 40L, 40L);
    }

    public void unstackOne(final LivingEntity livingEntity, final ChatColor color) {
        final String displayName = livingEntity.getCustomName();
        int stackSize = this.getAmount(displayName, color);
        if (stackSize <= 1) {
            return;
        }
        --stackSize;
        final String newDisplayName = color + "x" + stackSize;
        final LivingEntity newEntity = (LivingEntity)livingEntity.getWorld().spawnEntity(livingEntity.getLocation(), livingEntity.getType());
        newEntity.setCustomName(newDisplayName);
        newEntity.setCustomNameVisible(false);
        livingEntity.setHealth(0.0);
        if (newEntity instanceof Ageable) {
            ((Ageable)newEntity).setAdult();
        }
        if (newEntity instanceof Zombie) {
            ((Zombie)newEntity).setBaby(false);
        }
    }

    public void stackOne(final LivingEntity target, final LivingEntity stackee, final ChatColor color) {
        if (target.getType() != stackee.getType()) {
            return;
        }
        final String displayName = target.getCustomName();
        final int oldAmount = this.getAmount(displayName, color);
        int newAmount = 1;
        if (this.isStacked(stackee, color)) {
            newAmount = this.getAmount(stackee.getCustomName(), color);
        }
        if (newAmount >= 300) {
            return;
        }
        stackee.remove();
        if (oldAmount == 0) {
            final int amount = newAmount + 1;
            final String newDisplayName = color + "x" + amount;
            target.setCustomName(newDisplayName);
            target.setCustomNameVisible(true);
        }
        else {
            final int amount = oldAmount + newAmount;
            final String newDisplayName = color + "x" + amount;
            target.setCustomName(newDisplayName);
        }
    }

    public int getAmount(final String displayName, final ChatColor color) {
        if (displayName == null) {
            return 0;
        }
        final String nameColor = ChatColor.getLastColors(displayName);
        if (nameColor.equals('§' + color.getChar())) {
            return 0;
        }
        final String name1 = displayName.replace("x", "");
        String name2 = ChatColor.stripColor(name1.replace("§f", ""));
        name2 = ChatColor.stripColor(name2);
        if (!name2.matches("[0-9]+")) {
            return 0;
        }
        if (name2.length() > 4) {
            return 0;
        }
        return Integer.parseInt(name2);
    }

    public boolean isStacked(final LivingEntity entity, final ChatColor color) {
        return this.getAmount(entity.getCustomName(), color) != 0;
    }

    @EventHandler
    public void onEntityDeath(final EntityDeathEvent event) {
        if (event.getEntity() != null) {
            final LivingEntity entity = event.getEntity();
            if (entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.VILLAGER) {
                this.unstackOne(entity, ChatColor.BLUE);
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(final PlayerInteractEntityEvent event) {
        final Player player = event.getPlayer();
        final Entity entity = event.getRightClicked();
        final ItemStack item = player.getItemInHand();
        if (item == null || item.getType() == Material.AIR) {
            return;
        }
        if (entity instanceof Cow) {
            final Cow cow = (Cow)entity;
            if (item.getType() == Material.WHEAT && cow.canBreed() && cow.isCustomNameVisible()) {
                cow.setBreed(false);
                final Cow baby = (Cow)cow.getWorld().spawnEntity(cow.getLocation(), EntityType.COW);
                baby.setBaby();
            }
        }
    }
}
