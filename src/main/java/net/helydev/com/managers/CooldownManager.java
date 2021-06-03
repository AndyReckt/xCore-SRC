package net.helydev.com.managers;

import org.apache.logging.log4j.core.appender.FileManager;

import java.util.HashMap;
import java.util.UUID;

public class CooldownManager
{
    private FileManager config;
    private int requestTime;
    private HashMap<UUID, Long> requestCooldown;
    private int reportTime;
    private HashMap<UUID, Long> reportCooldown;

    public long getRequestC(final UUID uuid) {
        final long n = this.requestCooldown.get(uuid) / 1000L + this.reportTime - System.currentTimeMillis() / 1000L;
        if (n > 0L) {
            return n;
        }
        return n;
    }

    public boolean isRequest(final UUID uuid) {
        return this.requestCooldown.containsKey(uuid);
    }

    public boolean isReport(final UUID uuid) {
        return this.reportCooldown.containsKey(uuid);
    }

    public long getReportC(final UUID uuid) {
        final long n = this.reportCooldown.get(uuid) / 1000L + this.reportTime - System.currentTimeMillis() / 1000L;
        if (n > 0L) {
            return n;
        }
        return n;
    }

    public CooldownManager(final FileManager config) {
        this.reportCooldown = new HashMap<UUID, Long>();
        this.requestCooldown = new HashMap<UUID, Long>();
        this.config = config;
    }

    public void removeReport(final UUID uuid) {
        this.reportCooldown.remove(uuid);
    }

    public void removeRequest(final UUID uuid) {
        this.requestCooldown.remove(uuid);
    }
}
