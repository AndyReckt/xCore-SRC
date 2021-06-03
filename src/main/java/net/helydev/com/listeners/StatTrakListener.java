package net.helydev.com.listeners;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.util.org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class StatTrakListener implements Listener {

    private static final String minerLine = ChatColor.BLUE + "------" + ChatColor.YELLOW + " Pickaxe Stats " + ChatColor.BLUE + "------";
    private static final String axeLine = ChatColor.BLUE + "------" + ChatColor.YELLOW + " Axe Stats " + ChatColor.BLUE + "------";
    private static final String spadeLine = ChatColor.BLUE + "------" + ChatColor.YELLOW + " Shovel Stats " + ChatColor.BLUE + "------";

    public StatTrakListener() {
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void blockBreak(BlockBreakEvent event) {
        Material type = event.getBlock().getType();
        ItemStack held = event.getPlayer().getItemInHand();
        if (type.equals(Material.GLOWING_REDSTONE_ORE)) {
            type = Material.REDSTONE_ORE;
        }
        if (held != null) {
            List<String> lore = new ArrayList<>();
            if (held.hasItemMeta() && held.getItemMeta().hasLore()) {
                lore = held.getItemMeta().getLore();
                if (lore == null) lore = new ArrayList<>();
            }
            if (held.getType().name().toLowerCase().contains("pickaxe") && type.name().toLowerCase().contains("ore")) {
                if (containsLine(lore)) {
                    List<String> l = new ArrayList<>();
                    l.add(minerLine);
                    l.add(ChatColor.AQUA + formatOre(Material.DIAMOND_ORE) + ": " + ChatColor.YELLOW + "0");
                    l.add(ChatColor.GREEN + formatOre(Material.EMERALD_ORE) + ": " + ChatColor.YELLOW + "0");
                    l.add(ChatColor.RED + formatOre(Material.REDSTONE_ORE) + ": " + ChatColor.YELLOW + "0");
                    l.add(ChatColor.BLUE + formatOre(Material.LAPIS_ORE) + ": " + ChatColor.YELLOW + "0");
                    l.add(ChatColor.GOLD + formatOre(Material.GOLD_ORE) + ": " + ChatColor.YELLOW + "0");
                    l.add(ChatColor.WHITE + formatOre(Material.QUARTZ_ORE) + ": " + ChatColor.YELLOW + "0");
                    l.add(ChatColor.GRAY + formatOre(Material.IRON_ORE) + ": " + ChatColor.YELLOW + "0");
                    l.add(ChatColor.DARK_GRAY + formatOre(Material.COAL_ORE) + ": " + ChatColor.YELLOW + "0");
                    l.addAll(lore);
                    lore = l;
                }

                for (int i = 0; i < lore.size(); i++) {
                    String line = lore.get(i);
                    if (line.length() <= 2) {
                        continue;
                    }

                    if (line.substring(2).startsWith(formatOre(type))) {
                        int count = Integer.parseInt(line.substring(formatOre(type).length() + 6));
                        int lastIdx = line.lastIndexOf(String.valueOf(count));
                        String replaced = line.substring(0, lastIdx) + (count + 1);

                        lore.set(i, replaced);
                    }
                }
            } else if (held.getType().name().toLowerCase().contains("axe") && !held.getType().name().toLowerCase().contains("pickaxe") && type.name().toLowerCase().contains("log")) {
                if (containsLine(lore)) {
                    List<String> l = new ArrayList<>();
                    l.add(axeLine);
                    l.add(ChatColor.WHITE + "Oak Logs: " + ChatColor.YELLOW + "0");
                    l.add(ChatColor.WHITE + "Acacia Logs: " + ChatColor.YELLOW + "0");
                    l.add(ChatColor.WHITE + "Birch Logs: " + ChatColor.YELLOW + "0");
                    l.add(ChatColor.WHITE + "Dark Oak Logs: " + ChatColor.YELLOW + "0");
                    l.add(ChatColor.WHITE + "Jungle Logs: " + ChatColor.YELLOW + "0");
                    l.add(ChatColor.WHITE + "Spruce Logs: " + ChatColor.YELLOW + "0");
                    l.addAll(lore);
                    lore = l;
                }

                @Deprecated
                int data = event.getBlock().getData();
                String name = null;
                if (data == 1) {
                    if (type == Material.LOG) {
                        name = "Spruce Logs";
                    } else if (type.equals(Material.LOG_2)) {
                        name = "Dark Oak Logs";
                    }
                } else if (data == 2) {
                    name = "Birch Logs";
                } else if (data == 3) {
                    name = "Jungle Logs";
                } else {
                    if (type.equals(Material.LOG)) {
                        name = "Oak Logs";
                    } else if (type.equals(Material.LOG_2)) {
                        name = "Acacia Logs";
                    }
                }

                if (name != null) {
                    for (int i = 0; i < lore.size(); i++) {
                        String line = lore.get(i);
                        if (line.length() <= 2) {
                            continue;
                        }

                        if (line.substring(2).startsWith(formatOre(type))) {
                            int count = Integer.parseInt(line.substring(formatOre(type).length() + 6));
                            int lastIdx = line.lastIndexOf(String.valueOf(count));
                            String replaced = line.substring(0, lastIdx) + (count + 1);

                            lore.set(i, replaced);
                        }
                    }
                }

            } else if (held.getType().name().toLowerCase().contains("spade")) {
                if (containsLine(lore)) {
                    List<String> l = new ArrayList<>();
                    l.add(spadeLine);
                    l.add(ChatColor.WHITE + "Dirt: " + ChatColor.YELLOW + "0");
                    l.add(ChatColor.WHITE + "Grass: " + ChatColor.YELLOW + "0");
                    l.add(ChatColor.WHITE + "Gravel: " + ChatColor.YELLOW + "0");
                    l.add(ChatColor.WHITE + "Mycelium: " + ChatColor.YELLOW + "0");
                    l.add(ChatColor.WHITE + "Sand: " + ChatColor.YELLOW + "0");
                    l.add(ChatColor.WHITE + "Snow: " + ChatColor.YELLOW + "0");
                    l.add(ChatColor.WHITE + "Soul Sand: " + ChatColor.YELLOW + "0");
                    l.addAll(lore);
                    lore = l;
                }


                String name = formatOre(type);
                if (type.equals(Material.MYCEL)) {
                    name = "Mycelium";
                } else if (type.equals(Material.SNOW) || type.equals(Material.SNOW_BLOCK)) {
                    name = "Snow";
                }

                if (name != null) {
                    for (int i = 0; i < lore.size(); i++) {
                        String line = lore.get(i);
                        if (line.length() <= 2) {
                            continue;
                        }

                        if (line.substring(2).startsWith(formatOre(type))) {
                            int count = Integer.parseInt(line.substring(formatOre(type).length() + 6));
                            int lastIdx = line.lastIndexOf(String.valueOf(count));
                            String replaced = line.substring(0, lastIdx) + (count + 1);

                            lore.set(i, replaced);
                        }
                    }
                }
            }
            ItemMeta meta = held.getItemMeta();

            if (meta != null) {
                meta.setLore(lore);
                held.setItemMeta(meta);
            }
        }
    }

    public String formatOre(Material material) {
        return WordUtils.capitalize(material.name().toLowerCase().replace("_", " "));
    }

    public boolean containsLine(List<String> lore) {
        for (String line : lore) {
            if (line.equalsIgnoreCase(minerLine) || line.equalsIgnoreCase(axeLine) || line.equalsIgnoreCase(spadeLine)) {
                return false;
            }
        }
        return true;
    }
}
