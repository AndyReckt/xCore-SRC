package net.helydev.com.utils.commands;

import org.apache.commons.lang.*;
import org.bukkit.command.*;
import java.util.*;

public abstract class SubCommand
{
    private String name;
    protected boolean isPlayerOnly;
    protected String description;
    protected String permission;
    protected String[] aliases;

    public SubCommand(final String name, final String description) {
        this(name, description, (String)null);
    }

    public SubCommand(final String name, final String description, final String permission) {
        this(name, description, permission, ArrayUtils.EMPTY_STRING_ARRAY);
    }

    public SubCommand(final String name, final String description, final String[] aliases) {
        this(name, description, null, aliases);
    }

    public SubCommand(final String name, final String description, final String permission, final String[] aliases) {
        this.isPlayerOnly = false;
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.aliases = Arrays.copyOf(aliases, aliases.length);
    }

    public String getName() {
        return this.name;
    }

    public boolean isPlayerOnly() {
        return this.isPlayerOnly;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPermission() {
        return this.permission;
    }

    public String[] getAliases() {
        if (this.aliases == null) {
            this.aliases = ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return Arrays.copyOf(this.aliases, this.aliases.length);
    }

    public abstract boolean onCommand(final CommandSender p0, final Command p1, final String p2, final String[] p3);

    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        return Collections.emptyList();
    }
}

