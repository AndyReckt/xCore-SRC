package net.helydev.com.utils.chat;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_7_R4.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ChatUtil
{
    private static char ALT_COLOUR_CODE = '&';

    public static String getName(final ItemStack stack) {
        if (stack.tag != null && stack.tag.hasKeyOfType("display", 10)) {
            final NBTTagCompound nbttagcompound = stack.tag.getCompound("display");
            if (nbttagcompound.hasKeyOfType("Name", 8)) {
                return nbttagcompound.getString("Name");
            }
        }
        return stack.getItem().a(stack) + ".name";
    }

    public static void reset(final IChatBaseComponent text) {
        final ChatModifier modifier = text.getChatModifier();
        modifier.a((ChatHoverable)null);
        modifier.setChatClickable((ChatClickable)null);
        modifier.setBold(false);
        modifier.setColor(EnumChatFormat.RESET);
        modifier.setItalic(false);
        modifier.setRandom(false);
        modifier.setStrikethrough(false);
        modifier.setUnderline(false);
    }


    public static String translate(String string) {
        return ChatColor.translateAlternateColorCodes(ALT_COLOUR_CODE, string);
    }

    public static List<String> translate(Iterable<? extends String> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false).filter(Objects::nonNull).map(ChatUtil::translate).collect(Collectors.toList());
    }

    public static List<String> translate(List<String> list) {
        List<String> buffered = new ArrayList<String>();

        list.forEach(string -> buffered.add(translate(string)));
        return buffered;
    }

    public static String[] translate(String... strings) {
        return translate(Arrays.asList(strings)).stream().toArray(String[]::new);
    }

    public static void sendMessage(Player receiver, String... strings) {
        receiver.sendMessage(translate(strings));
    }

    public static void sendMessage(CommandSender commandSender, String... strings) {
        commandSender.sendMessage(translate(strings));
    }

    public static void sendMessage(CommandSender commandSender, BaseComponent[] value) {
        if(commandSender instanceof Player) {
            ((Player) commandSender).spigot().sendMessage(value);
        } else {
            commandSender.sendMessage(TextComponent.toLegacyText(value));
        }
    }


    public static void broadcastConsole(String... strings) {
        ConsoleCommandSender consoleCommandSender = Bukkit.getServer().getConsoleSender();
        consoleCommandSender.sendMessage(translate(strings));
    }

    public static void error(String... strings) {
        Bukkit.getConsoleSender().sendMessage(translate("&4[ERROR]: &c" + strings));
    }

    public static void warn(String... strings) {
        Bukkit.getConsoleSender().sendMessage(translate("&e[WARN]: &6" + strings));
    }

    public static void info(String... strings) {
        Bukkit.getConsoleSender().sendMessage(translate("&2[INFO]: &a" + strings));
    }

    public static boolean endsWith(String word, List<String> suffix) {
        for(String message : suffix) {
            if(word.toLowerCase().endsWith(message)) {
                return true;
            }
        }
        return false;
    }
    public static void send(final CommandSender sender, final IChatBaseComponent text) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            final PacketPlayOutChat packet = new PacketPlayOutChat(text);
            final EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
            entityPlayer.playerConnection.sendPacket(packet);
        }
        else {
            sender.sendMessage(text.c());
        }
    }
}