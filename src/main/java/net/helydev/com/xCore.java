package net.helydev.com;


import net.helydev.com.commands.*;
import net.helydev.com.commands.Killstreaks.KillstreaksCommand;
import net.helydev.com.commands.broadcast.BroadcastCommand;
import net.helydev.com.commands.broadcast.BroadcastRawCommand;
import net.helydev.com.commands.donator.*;
import net.helydev.com.commands.links.*;
import net.helydev.com.commands.media.FamousCommand;
import net.helydev.com.commands.media.MediaOwnerCommand;
import net.helydev.com.commands.media.PartnerCommand;
import net.helydev.com.commands.media.YoutuberCommand;
import net.helydev.com.commands.staff.*;
import net.helydev.com.commands.teleport.*;
import net.helydev.com.commands.time.DayCommand;
import net.helydev.com.commands.time.NightCommand;
import net.helydev.com.configs.Config;
import net.helydev.com.listeners.*;
import net.helydev.com.listeners.elevators.ElevatorSignListener;
import net.helydev.com.listeners.killstreaks.KillStreaks;
import net.helydev.com.listeners.killstreaks.KillstreakListener;
import net.helydev.com.listeners.limiters.PotionLimitListener;
import net.helydev.com.listeners.patches.*;
import net.helydev.com.listeners.signs.PotRefillSignListener;
import net.helydev.com.listeners.systems.BeaconRenamerListener;
import net.helydev.com.utils.chat.ChatUtil;
import net.helydev.com.utils.commands.CommandFramework;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class xCore extends JavaPlugin {

    private static xCore plugin;
    private Config limitersFile;
    public static List<KillStreaks> killStreaks = new ArrayList<>();

    private Config message;
    private PotRefillSignListener PerkMenuListener;

    public static xCore getPlugin() {
        return plugin;
    }

    public static boolean canSee(CommandSender sender, Player target){
        return target != null && (!(sender instanceof Player) || ((Player) sender).canSee(target));
    }


    /**
     * When the core first loads...
     */

    @Override
    public void onEnable() {
        plugin = this;
        //START OF LOADING MESSAGE
        ChatUtil.sendMessage(Bukkit.getConsoleSender(),"&7&l&m---------------------------------");
        ChatUtil.sendMessage(Bukkit.getConsoleSender(),"&b&lxCore 2.0");
        ChatUtil.sendMessage(Bukkit.getConsoleSender(),"&7");
        ChatUtil.sendMessage(Bukkit.getConsoleSender(),"&7You are using the most recent version of &bxCore&7!");
        ChatUtil.sendMessage(Bukkit.getConsoleSender(),"&7This was remastered by &aDevDipin&7.");
        ChatUtil.sendMessage(Bukkit.getConsoleSender(),"&7Original Creator: &aLeandroSSJ");
        ChatUtil.sendMessage(Bukkit.getConsoleSender(),"&7");
        ChatUtil.sendMessage(Bukkit.getConsoleSender(),"&7Need support? Join our discord.");
        ChatUtil.sendMessage(Bukkit.getConsoleSender(),"&a&nhttps://discord.gg/mb7uw7QnAV");
        ChatUtil.sendMessage(Bukkit.getConsoleSender(),"&7&l&m---------------------------------");
        //END OF LOADING MESSAGE
        xCore.getPlugin().saveDefaultConfig();
        xCore.getPlugin().reloadConfig();
        xCore.getPlugin().registerconfig();
        xCore.getPlugin().registermanagers();
        xCore.getPlugin().registerLimiters();
        xCore.getPlugin().registerCommand();
        xCore.getPlugin().registerListeners();
        for (String item : plugin.getConfig().getConfigurationSection("killstreaks.items").getKeys(false)) {
            String name = plugin.getConfig().getString("killstreaks.items." + item + ".name");
            int kills = plugin.getConfig().getInt("killstreaks.items." + item + ".kills");
            List<String> command = plugin.getConfig().getStringList("killstreaks.items." + item + ".command");
            KillStreaks killsAdd = new KillStreaks(name, kills, command);
            killStreaks.add(killsAdd);
        }
    }

    /**
     * Registering config.yml...
     */

    public void registerconfig() {
        this.message=new Config(xCore.getPlugin(), "messages", xCore.getPlugin().getDataFolder().getAbsolutePath());
        ChatUtil.sendMessage(Bukkit.getConsoleSender(),"&b[xCore] &aRegistering &a&lconfig.yml&a..");
        ChatUtil.sendMessage(Bukkit.getConsoleSender(),"&b[xCore] &aRegistering &a&lmessages.yml&a..");
    }

    public void registerLimiters() {
        this.limitersFile= new Config(xCore.getPlugin(), "limiters", xCore.getPlugin().getDataFolder().getAbsolutePath());
        ChatUtil.sendMessage(Bukkit.getConsoleSender(),"&b[xCore] &aRegistering &a&llimiters.yml&a..");
    }

    /**
     * Registering managers...
     */

    public void registermanagers() {
        this.message=new Config(xCore.getPlugin(), "messages", xCore.getPlugin().getDataFolder().getAbsolutePath());
    }

    /**
     * Getting the messages.yml...
     */
    public Config getMessageconfig(){
        return message;
    }

    /**
     * On plugin disable...
     */
    @Override
    public void onDisable() {
        ChatUtil.sendMessage(Bukkit.getConsoleSender(),"&b[xCore] &aDisabling plugin..");
    }

    /**
     * Registering commands...
     */

    public void registerCommand() {
        ChatUtil.sendMessage(Bukkit.getConsoleSender(),"&b[xCore] &aRegistering commands..");
        CommandFramework commandFramework = new CommandFramework(this);
        commandFramework.registerCommands(new CoreCommand());
        if (xCore.getPlugin().getConfig().getBoolean("commands.SpawnCommand")) {
            commandFramework.registerCommands(new SpawnCommand());
            commandFramework.registerCommands(new SetSpawnCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.CopyInventoryCommand")) {
            commandFramework.registerCommands(new CopyInventoryCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.FightCommand")) {
            commandFramework.registerCommands(new FightCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.TeleportPositionCommand")) {
            commandFramework.registerCommands(new TeleportPositionCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.NearCommand")) {
            commandFramework.registerCommands(new NearCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("killstreaks.enabled")) {
            commandFramework.registerCommands(new KillstreaksCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.HealCommand")) {
            commandFramework.registerCommands(new HealCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.PlaytimeCommand")) {
            commandFramework.registerCommands(new PlaytimeCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.SkullCommand")) {
            commandFramework.registerCommands(new SkullCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.BottleCommand")) {
            commandFramework.registerCommands(new BottleCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.EnchantCommand")) {
            commandFramework.registerCommands(new EnchantCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.SpawnerCommand")) {
            commandFramework.registerCommands(new SpawnerCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("settings.custom-enchants.enabled")) {
            commandFramework.registerCommands(new CustomEnchantmentCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.LoreCommand")) {
            commandFramework.registerCommands(new LoreCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.ServerStatusCommand")) {
            commandFramework.registerCommands(new ServerStatusCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.HiderCommand")) {
            commandFramework.registerCommands(new HiderCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.SetMOTDCommand")) {
            commandFramework.registerCommands(new SetMOTDCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.ItemIDCommand")) {
            commandFramework.registerCommands(new IDCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.PokeCommand")) {
            commandFramework.registerCommands(new PokeCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.GameModeCommand")) {
            commandFramework.registerCommands(new GamemodeCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.EnderChestCommand")) {
            commandFramework.registerCommands(new EnderChestCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.CraftCommand")) {
            commandFramework.registerCommands(new CraftCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.FeedCommand")) {
            commandFramework.registerCommands(new FeedCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.HubCommand")) {
            commandFramework.registerCommands(new HubCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.InvseeCommand")) {
            commandFramework.registerCommands(new InvseeCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.ClearInventoryCommand")) {
            commandFramework.registerCommands(new ClearInventoryCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.EndPlayersCommand")) {
            commandFramework.registerCommands(new EndPlayersCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.StoreCommand")) {
            commandFramework.registerCommands(new StoreCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.DiscordCommand")) {
            commandFramework.registerCommands(new DiscordCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.TelegramCommand")) {
            commandFramework.registerCommands(new TelegramCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.TeamspeakCommand")) {
            commandFramework.registerCommands(new TeamspeakCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.WebsiteCommand")) {
            commandFramework.registerCommands(new WebsiteCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.DayCommand")) {
            commandFramework.registerCommands(new DayCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.NightCommand")) {
            commandFramework.registerCommands(new NightCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.FamousCommand")) {
            commandFramework.registerCommands(new FamousCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.YoutuberCommand")) {
            commandFramework.registerCommands(new YoutuberCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.PartnerCommand")) {
            commandFramework.registerCommands(new PartnerCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.MediaOwnerCommand")) {
            commandFramework.registerCommands(new MediaOwnerCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.BroadcastCommand")) {
            commandFramework.registerCommands(new BroadcastCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.BroadcastRawCommand")) {
            commandFramework.registerCommands(new BroadcastRawCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.TeleportCommand")) {
            commandFramework.registerCommands(new TeleportCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("settings.server.perks-system")) {
            commandFramework.registerCommands(new PerksCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.TeleportHereCommand")) {
            commandFramework.registerCommands(new TeleportHereCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.RenameCommand")) {
            commandFramework.registerCommands(new RenameCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.HelpCommand")) {
            commandFramework.registerCommands(new HelpCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.TopCommand")) {
            commandFramework.registerCommands(new TopCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.TpAllCommand")) {
            commandFramework.registerCommands(new TpAllCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.ListCommand")) {
            commandFramework.registerCommands(new ListCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.RepairCommand")) {
            commandFramework.registerCommands(new RepairCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.HatCommand")) {
            commandFramework.registerCommands(new HatCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.WorldCommand")) {
            commandFramework.registerCommands(new WorldCommand());
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.SetColorCommand")) {
            commandFramework.registerCommands(new SetColorCommand());
        }
        commandFramework.registerHelp();
        ChatUtil.sendMessage(Bukkit.getConsoleSender(),"&b[xCore] &aRegistered all commands!");

    }

    /**
     * Registering listeners...
     */

    private void registerListeners() {
        PluginManager manager = Bukkit.getServer().getPluginManager();
        ChatUtil.sendMessage(Bukkit.getConsoleSender(),"&b[xCore] &aRegistering listeners..");
        manager.registerEvents(new CoreListener(), this);
        manager.registerEvents(new BlockGlitchListener(), this);
        if (xCore.getPlugin().getConfig().getBoolean("settings.server.kill-tracker")) {
            manager.registerEvents(new KillTrackerListener(), this);
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.SkullCommand")) {
            manager.registerEvents(new SkullListener(), this);
        }
        if (xCore.getPlugin().getConfig().getBoolean("killstreaks.enabled")) {
            manager.registerEvents(new KillstreakListener(), this);
        }
        if (xCore.getPlugin().getConfig().getBoolean("settings.elevators.enabled")) {
            manager.registerEvents(new ElevatorSignListener(), this);
        }
        manager.registerEvents(new WeatherListener(), this);
        if (xCore.getPlugin().getConfig().getBoolean("settings.server.stat-trak")) {
            manager.registerEvents(new StatTrakListener(), this);
        }
        if (xCore.getPlugin().getConfig().getBoolean("settings.void-teleport-fix")) {
            manager.registerEvents(new VoidGlitchFixListener(), this);
        }
        if (xCore.getPlugin().getConfig().getBoolean("join-sound.enabled")) {
            manager.registerEvents(new JoinEventListener(), this);
        }
        if (xCore.getPlugin().getConfig().getBoolean("commands.BottleCommand"))
            manager.registerEvents(new BottledExpListener(), this);
        if (xCore.getPlugin().getConfig().getBoolean("settings.server.pearl-glitch")) {
            manager.registerEvents(new PearlGlitchListener(), this);
        }
        if (xCore.getPlugin().getConfig().getBoolean("enderchest.enabled")) {
            manager.registerEvents(new EnderchestListener(), this);
        }
        if (xCore.getPlugin().getConfig().getBoolean("potion-refill-sign.enabled")) {
            manager.registerEvents(new PotRefillSignListener(), this);
        }
        if (xCore.getPlugin().getConfig().getBoolean("settings.limiters.potion-limiter")) {
            manager.registerEvents(new PotionLimitListener(this), this);
        }
        if (xCore.getPlugin().getConfig().getBoolean("beacon-rename.enabled")) {
            manager.registerEvents(new BeaconRenamerListener(this), this);
        }
        manager.registerEvents(new SplashPotionFixListener(), this);
        manager.registerEvents(new WhitelistListener(), this);
        ChatUtil.sendMessage(Bukkit.getConsoleSender(),"&b[xCore] &aRegistered listeners!");
    }

    /**
     * Reloading configs...
     */

    public void reload(){
        this.reloadConfig();
        this.message.reload();
        ChatUtil.sendMessage(Bukkit.getConsoleSender(),"&b[xCore] &aNOTICE: A player has just reloaded the config from ingame!");
    }

    public Config getLimitersFile() {
        return this.limitersFile;
    }

    public PotRefillSignListener getPerkMenuListener() {
        return this.PerkMenuListener;
    }
}
