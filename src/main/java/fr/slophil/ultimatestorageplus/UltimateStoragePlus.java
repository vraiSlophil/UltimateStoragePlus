package fr.slophil.ultimatestorageplus;

import fr.slophil.ultimatestorageplus.commands.UltimateStoragePlusCommand;
import fr.slophil.ultimatestorageplus.events.*;
import fr.slophil.ultimatestorageplus.utils.BlockType;
import fr.slophil.ultimatestorageplus.utils.GuiManager;
import fr.slophil.ultimatestorageplus.utils.SQLiteConnector;
import fr.slophil.ultimatestorageplus.utils.Storage;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.logging.Level;

public final class UltimateStoragePlus extends JavaPlugin {
    private RecipeManager recipeManager;

//    private GuiManager guiManager;

    private static UltimateStoragePlus instance;

    private final String pluginPrefix = ChatColor.GOLD.toString() + ChatColor.BOLD.toString() +
            getDescription().getName() + ChatColor.DARK_RED.toString() + " : ";

    @Override
    public void onEnable() {
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
        pluginManager.registerEvents(new StorageClickEvent(this), this);
        pluginManager.registerEvents(new StorageInventoryClickEvent(this), this);
        pluginManager.registerEvents(new StorageInventoryOpenEvent(this), this);
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

    public RecipeManager getRecipeManager() {
        return this.recipeManager;
    }

    public String getPlayerNoPermission() {
        return ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("PlayerNoPermission"));
    }

    public String getCommandUsage() {
        return ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("CommandUsage"));
    }

    public String getMustBePlayer() {
        return ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("MustBePlayer"));
    }
}
