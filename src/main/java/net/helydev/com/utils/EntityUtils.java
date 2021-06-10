package net.helydev.com.utils;

import org.bukkit.entity.EntityType;

import java.util.EnumMap;
import java.util.Map;

public class EntityUtils {
    private static Map<EntityType, String> displayNames;
    private static int currentFakeEntityId;

    public static int getFakeEntityId() {
        return EntityUtils.currentFakeEntityId--;
    }

    private EntityUtils() {
    }

    public static String getName(final EntityType type) {
        return EntityUtils.displayNames.get(type);
    }

    public static EntityType parse(final String input) {
        for (final Map.Entry<EntityType, String> entry : EntityUtils.displayNames.entrySet()) {
            if (entry.getValue().replace(" ", "").equalsIgnoreCase(input)) {
                return entry.getKey();
            }
        }
        for (final EntityType type : EntityType.values()) {
            if (input.equalsIgnoreCase(type.toString())) {
                return type;
            }
        }
        return null;
    }

    static {
        EntityUtils.displayNames = new EnumMap<EntityType, String>(EntityType.class);
        EntityUtils.currentFakeEntityId = -1;
        EntityUtils.displayNames.put(EntityType.ARROW, "Arrow");
        EntityUtils.displayNames.put(EntityType.BAT, "Bat");
        EntityUtils.displayNames.put(EntityType.BLAZE, "Blaze");
        EntityUtils.displayNames.put(EntityType.BOAT, "Boat");
        EntityUtils.displayNames.put(EntityType.CAVE_SPIDER, "Cave Spider");
        EntityUtils.displayNames.put(EntityType.CHICKEN, "Chicken");
        EntityUtils.displayNames.put(EntityType.COMPLEX_PART, "Complex Part");
        EntityUtils.displayNames.put(EntityType.COW, "Cow");
        EntityUtils.displayNames.put(EntityType.CREEPER, "Creeper");
        EntityUtils.displayNames.put(EntityType.DROPPED_ITEM, "Item");
        EntityUtils.displayNames.put(EntityType.EGG, "Egg");
        EntityUtils.displayNames.put(EntityType.ENDER_CRYSTAL, "Ender Crystal");
        EntityUtils.displayNames.put(EntityType.ENDER_DRAGON, "Ender Dragon");
        EntityUtils.displayNames.put(EntityType.ENDER_PEARL, "Ender Pearl");
        EntityUtils.displayNames.put(EntityType.ENDER_SIGNAL, "Ender Signal");
        EntityUtils.displayNames.put(EntityType.ENDERMAN, "Enderman");
        EntityUtils.displayNames.put(EntityType.EXPERIENCE_ORB, "Experience Orb");
        EntityUtils.displayNames.put(EntityType.FALLING_BLOCK, "Falling Block");
        EntityUtils.displayNames.put(EntityType.FIREBALL, "Fireball");
        EntityUtils.displayNames.put(EntityType.FIREWORK, "Firework");
        EntityUtils.displayNames.put(EntityType.FISHING_HOOK, "Fishing Rod Hook");
        EntityUtils.displayNames.put(EntityType.GHAST, "Ghast");
        EntityUtils.displayNames.put(EntityType.GIANT, "Giant");
        EntityUtils.displayNames.put(EntityType.HORSE, "Horse");
        EntityUtils.displayNames.put(EntityType.IRON_GOLEM, "Iron Golem");
        EntityUtils.displayNames.put(EntityType.ITEM_FRAME, "Item Frame");
        EntityUtils.displayNames.put(EntityType.LEASH_HITCH, "Lead Hitch");
        EntityUtils.displayNames.put(EntityType.LIGHTNING, "Lightning");
        EntityUtils.displayNames.put(EntityType.MAGMA_CUBE, "Magma Cube");
        EntityUtils.displayNames.put(EntityType.MINECART, "Minecart");
        EntityUtils.displayNames.put(EntityType.MINECART_CHEST, "Chest Minecart");
        EntityUtils.displayNames.put(EntityType.MINECART_FURNACE, "Furnace Minecart");
        EntityUtils.displayNames.put(EntityType.MINECART_HOPPER, "Hopper Minecart");
        EntityUtils.displayNames.put(EntityType.MINECART_MOB_SPAWNER, "Spawner Minecart");
        EntityUtils.displayNames.put(EntityType.MINECART_TNT, "TNT Minecart");
        EntityUtils.displayNames.put(EntityType.OCELOT, "Ocelot");
        EntityUtils.displayNames.put(EntityType.PAINTING, "Painting");
        EntityUtils.displayNames.put(EntityType.PIG, "Pig");
        EntityUtils.displayNames.put(EntityType.PIG_ZOMBIE, "Zombie Pigman");
        EntityUtils.displayNames.put(EntityType.PLAYER, "Player");
        EntityUtils.displayNames.put(EntityType.PRIMED_TNT, "TNT");
        EntityUtils.displayNames.put(EntityType.SHEEP, "Sheep");
        EntityUtils.displayNames.put(EntityType.SILVERFISH, "Silverfish");
        EntityUtils.displayNames.put(EntityType.SKELETON, "Skeleton");
        EntityUtils.displayNames.put(EntityType.SLIME, "Slime");
        EntityUtils.displayNames.put(EntityType.SMALL_FIREBALL, "Fireball");
        EntityUtils.displayNames.put(EntityType.SNOWBALL, "Snowball");
        EntityUtils.displayNames.put(EntityType.SNOWMAN, "Snowman");
        EntityUtils.displayNames.put(EntityType.SPIDER, "Spider");
        EntityUtils.displayNames.put(EntityType.SPLASH_POTION, "Potion");
        EntityUtils.displayNames.put(EntityType.SQUID, "Squid");
        EntityUtils.displayNames.put(EntityType.THROWN_EXP_BOTTLE, "Experience Bottle");
        EntityUtils.displayNames.put(EntityType.UNKNOWN, "Custom");
        EntityUtils.displayNames.put(EntityType.VILLAGER, "Villager");
        EntityUtils.displayNames.put(EntityType.WEATHER, "Weather");
        EntityUtils.displayNames.put(EntityType.WITCH, "Witch");
        EntityUtils.displayNames.put(EntityType.WITHER, "Wither");
        EntityUtils.displayNames.put(EntityType.WITHER_SKULL, "Wither Skull");
        EntityUtils.displayNames.put(EntityType.WOLF, "Wolf");
        EntityUtils.displayNames.put(EntityType.ZOMBIE, "Zombie");
    }
}
