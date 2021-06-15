package net.helydev.com.utils;

public class NumberUtils
{
    public static boolean isInteger(final String value) {
        try {
            Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            return true;
        }
        return false;
    }
}

