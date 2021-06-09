package net.helydev.com.commands;

import net.helydev.com.listeners.RefillEditListener;
import net.helydev.com.utils.commands.Command;
import net.helydev.com.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class RefillEditCommand {
    @Command(name = "refilledit", permission = "hcf.command.refilledit", inGameOnly = true, aliases = { "refill", "editrefill" })
    public void sendMessage(final CommandArgs command) {
        final Player player = command.getPlayer();
        RefillEditListener.onOpenInventory(player);
    }
}
