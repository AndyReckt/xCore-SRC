package net.helydev.com.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Manager
{
    private ArrayList<UUID> admintoggle;
    private ArrayList<UUID> ownertoggle;
    private ArrayList<UUID> socialspy;
    private ArrayList<UUID> stafftoggle;
    private HashMap<UUID, UUID> replyMSG;
    private boolean maintenance;

    public boolean isSocial(final UUID uuid) {
        return this.socialspy.contains(uuid);
    }

    public void removeAdmin(final UUID uuid) {
        this.admintoggle.remove(uuid);
    }

    public void addSocial(final UUID uuid) {
        this.socialspy.add(uuid);
    }

    public void addOwner(final UUID uuid) {
        this.ownertoggle.add(uuid);
    }

    public boolean isAdmin(final UUID uuid) {
        return this.admintoggle.contains(uuid);
    }

    public boolean isReply(final UUID uuid) {
        return this.replyMSG.containsKey(uuid);
    }

    public void setMaintenance(final boolean maintenance) {
        this.maintenance = maintenance;
    }

    public void removeStaff(final UUID uuid) {
        this.stafftoggle.remove(uuid);
    }

    public void addReply(final UUID uuid, final UUID uuid2) {
        this.replyMSG.put(uuid, uuid2);
    }

    public UUID getTarget(final UUID uuid) {
        return this.replyMSG.get(uuid);
    }

    public void removeReply(final UUID uuid) {
        this.replyMSG.remove(uuid);
    }

    public Manager() {
        this.replyMSG = new HashMap<UUID, UUID>();
        this.stafftoggle = new ArrayList<UUID>();
        this.admintoggle = new ArrayList<UUID>();
        this.ownertoggle = new ArrayList<UUID>();
        this.socialspy = new ArrayList<UUID>();
    }

    public void addAdmin(final UUID uuid) {
        this.admintoggle.add(uuid);
    }

    public boolean isMaintenance() {
        return this.maintenance;
    }

    public boolean isStaff(final UUID uuid) {
        return this.stafftoggle.contains(uuid);
    }

    public void addStaff(final UUID uuid) {
        this.stafftoggle.add(uuid);
    }

    public void removeOwner(final UUID uuid) {
        this.ownertoggle.remove(uuid);
    }

    public boolean isOwner(final UUID uuid) {
        return this.ownertoggle.contains(uuid);
    }

    public void removeSocial(final UUID uuid) {
        this.socialspy.remove(uuid);
    }
}
