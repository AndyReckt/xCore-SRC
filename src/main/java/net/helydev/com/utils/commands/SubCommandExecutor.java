package net.helydev.com.utils.commands;

import com.google.common.collect.ImmutableList;
import net.helydev.com.utils.BukkitUtils;
import net.helydev.com.utils.Color;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class SubCommandExecutor implements CommandExecutor, TabCompleter
{
    protected List<SubCommand> arguments;
    protected String label;

    public SubCommandExecutor(final String label) {
        this.arguments = new ArrayList<SubCommand>();
        this.label = label;
    }

    public boolean containsArgument(final SubCommand argument) {
        return this.arguments.contains(argument);
    }

    public void addArgument(final SubCommand argument) {
        this.arguments.add(argument);
    }

    public void removeArgument(final SubCommand argument) {
        this.arguments.remove(argument);
    }

    public SubCommand getArgument(final String id) {
        for (final SubCommand argument : this.arguments) {
            final String name = argument.getName();
            if (name.equalsIgnoreCase(id) || Arrays.asList(argument.getAliases()).contains(id.toLowerCase())) {
                return argument;
            }
        }
        return null;
    }

    public List<SubCommand> getArguments() {
        return (List<SubCommand>)ImmutableList.copyOf((Collection)this.arguments);
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (args.length < 1) {
            sender.sendMessage(Color.translate("&c" + WordUtils.capitalizeFully(this.label) + " - Help Commands"));
            for (final SubCommand argument : this.arguments) {
                final String permission = argument.getPermission();
                if (permission != null) {
                    sender.hasPermission(permission);
                }
            }
            return true;
        }
        final SubCommand argument2 = this.getArgument(args[0]);
        final String permission2 = (argument2 == null) ? null : argument2.getPermission();
        if (argument2 == null || (permission2 != null && !sender.hasPermission(permission2))) {
            sender.sendMessage(ChatColor.RED + WordUtils.capitalizeFully(this.label) + " sub-command " + args[0] + " not found.");
            return true;
        }
        argument2.onCommand(sender, command, label, args);
        return true;
    }

    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        List<String> results = new ArrayList<String>();
        if (args.length < 2) {
            for (final SubCommand argument : this.arguments) {
                final String permission = argument.getPermission();
                if (permission == null || sender.hasPermission(permission)) {
                    results.add(argument.getName());
                }
            }
        }
        else {
            final SubCommand argument2 = this.getArgument(args[0]);
            if (argument2 == null) {
                return results;
            }
            final String permission2 = argument2.getPermission();
            if (permission2 == null || sender.hasPermission(permission2)) {
                results = argument2.onTabComplete(sender, command, label, args);
                if (results == null) {
                    return null;
                }
            }
        }
        return BukkitUtils.getCompletions(args, results, 80);
    }
}

