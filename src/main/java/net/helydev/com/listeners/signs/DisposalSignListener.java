package net.helydev.com.listeners.signs;

import net.helydev.com.utils.CC;
import net.helydev.com.utils.Color;
import net.helydev.com.xCore;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class DisposalSignListener implements Listener {
    private final String[] lines;
    private final String[] error;

    public DisposalSignListener() {
        this.lines = new String[] { Color.translate(xCore.getPlugin().getConfig().getString("signs.disposal.lines.1")), Color.translate(xCore.getPlugin().getConfig().getString("signs.disposal.lines.2")), Color.translate(xCore.getPlugin().getConfig().getString("signs.disposal.lines.3")), Color.translate(xCore.getPlugin().getConfig().getString("signs.disposal.lines.4")) };
        this.error = new String[] { Color.translate(xCore.getPlugin().getConfig().getString("signs.disposal.lines.1")), Color.translate(xCore.getPlugin().getConfig().getString("signs.disposal.lines.2")), Color.translate(xCore.getPlugin().getConfig().getString("signs.disposal.lines.3")), Color.translate("&cError") };
    }

    public void openMainInventory(final Player player) {
        final Inventory inv = Bukkit.createInventory(null, xCore.getPlugin().getConfig().getInt("signs.disposal.size"), CC.translate(xCore.getPlugin().getConfig().getString("signs.menu.disposal.title")));
        player.openInventory(inv);
    }

    @EventHandler
    public void onSignPlace(final SignChangeEvent event) {
        if (event.getLine(0).equals("[Disposal]")) {
            final Player player = event.getPlayer();
            if (player.hasPermission("core.comamnd.*")) {
                for (int i = 0; i < this.lines.length; ++i) {
                    event.setLine(i, this.lines[i]);
                }
            }
            else {
                for (int i = 0; i < this.error.length; ++i) {
                    event.setLine(i, this.error[i]);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final Block block = event.getClickedBlock();
        if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) && block.getState() instanceof Sign) {
            final Sign sign = (Sign)block.getState();
            for (int i = 0; i < this.lines.length; ++i) {
                if (!sign.getLine(i).equals(this.lines[i])) {
                    return;
                }
            }
            this.openMainInventory(player);
        }
    }
}
