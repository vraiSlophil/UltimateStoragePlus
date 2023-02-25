package fr.slophil.ultimatestorageplus;

import fr.slophil.ultimatestorageplus.commands.UltimateStoragePlusCommand;
import fr.slophil.ultimatestorageplus.events.BarrelBreakEvent;
import fr.slophil.ultimatestorageplus.events.BarrelPlaceEvent;
import fr.slophil.ultimatestorageplus.events.StorageClickEvent;
import fr.slophil.ultimatestorageplus.utils.BlockType;
import fr.slophil.ultimatestorageplus.utils.SQLiteConnector;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class UltimateStoragePlus extends JavaPlugin {


    public static final NamespacedKey STORAGE_KEY = new NamespacedKey("ULTIMATESTORAGEPLUS", "persistent_storage");

//    private StorageInventory guiManager;
    private SQLiteConnector connector;

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




//    public void put(Barrel barrel, ItemStack item) {
//        Inventory inv = barrel.getInventory();
//        PersistentDataContainer container = barrel.getPersistentDataContainer();
//        NamespacedKey key = new NamespacedKey(this, "amount");
//
//        // Nouvel item
//        if(inv.getItem(0).getType() == Material.AIR) {
//            container.set(key, PersistentDataType.INTEGER, item.getAmount());
//            item.setAmount(1);
//            inv.setItem(0, item);
//            return;
//        }
//
//        if(!inv.getItem(0).isSimilar(item)) {
//            return;
//        }
//
//        Integer amount = container.get(key, PersistentDataType.INTEGER);
//        amount += item.getAmount();
//
//        container.set(key, PersistentDataType.INTEGER, amount);
//    }
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
}
