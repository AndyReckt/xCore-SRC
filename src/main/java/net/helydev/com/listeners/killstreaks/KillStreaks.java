package net.helydev.com.listeners.killstreaks;

import java.util.List;

public class KillStreaks {

    private String name;
    private int number;
    private List<String> command;

    public KillStreaks(String name, int number, List<String> command) {
        this.name = name;
        this.number = number;
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public String getCommand() {
        return String.valueOf(command);
    }
}