package fr.slophil.ultimatestorageplus;

import fr.slophil.ultimatestorageplus.commands.UltimateStoragePlusCommand;
import fr.slophil.ultimatestorageplus.events.BarrelBreakEvent;
import fr.slophil.ultimatestorageplus.events.BarrelPlaceEvent;
import fr.slophil.ultimatestorageplus.events.BarrelClickEvent;
import fr.slophil.ultimatestorageplus.utils.BlockType;
import fr.slophil.ultimatestorageplus.utils.SQLiteConnector;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class UltimateStoragePlus extends JavaPlugin {


    public static final NamespacedKey STORAGE_KEY = new NamespacedKey("ultimatestorageplus", "persistent_storage");

    //    private StorageInventory guiManager;
    private SQLiteConnector connector;

    private static UltimateStoragePlus instance;

    private final String pluginPrefix = ChatColor.GOLD.toString() + ChatColor.BOLD.toString() +
            getDescription().getName() + ChatColor.DARK_RED.toString() + " : ";

    @Override
    public void onEnable() {
        this.instance = this;
        saveDefaultConfig();
        this.setup();
        this.registerEvents();
        this.setupCommands();

        this.connector = new SQLiteConnector(this);
        connector.connect();

        getLogger().log(Level.INFO, "Plugin enabled !");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Plugin disabled !");
    }

    private void setup() {
        BlockType.registerRecipes();
    }

    private void registerEvents() {
        PluginManager pluginManager = this.getServer().getPluginManager();

        pluginManager.registerEvents(new BarrelBreakEvent(this), this);
        pluginManager.registerEvents(new BarrelPlaceEvent(this), this);
        pluginManager.registerEvents(new BarrelClickEvent(this), this);
    }

    private void setupCommands() {
        this.getCommand("UltimateStoragePlus").setExecutor(new UltimateStoragePlusCommand(this));
    }

    public SQLiteConnector getConnector() {
        return connector;
    }

    public static UltimateStoragePlus getInstance() {
        return instance;
    }

    //config messages

    public String getPluginPrefix() {
        return this.pluginPrefix;
    }

    public String getPlayerNoPermission() {
        return ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("PlayerNoPermission"));
    }

    public String getMustBePlayer() {
        return ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("MustBePlayer"));
    }

    public String getCommandUsage() {
        return ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("CommandUsage"));
    }
}
