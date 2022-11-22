package fr.slophil.ultimatestorageplus.utils;

import fr.slophil.ultimatestorageplus.UltimateStoragePlus;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Storage {

    private final Location location;
    private int amount = 200;
    private final int capacity;
    private final UUID storageUniqueId = UUID.randomUUID();
    private ItemStack itemStack = new ItemBuilder(Material.BEDROCK).setDisplayName(ChatColor.RED + "snickers").setGlow(true).build();
    private final GuiManager inventory;
    private final InventoryHolder holder;
//
//    public Storage(UltimateStoragePlus ultimateStoragePlus, Location location, int capacity, int amount,  ItemStack itemStack){
//        this.ultimateStoragePlus = ultimateStoragePlus;
//        this.location = location;
//        this.amount = amount;
//        this.capacity = capacity;
//        this.itemStack = itemStack;
//        this.inventory = new GuiManager(this);
//        this.ultimateStoragePlus.getStorageList().putIfAbsent(location, this);
//        this.ultimateStoragePlus.getStorageInventoryList().putIfAbsent(this, getInventory());
//    }
//
//    public Storage(UltimateStoragePlus ultimateStoragePlus, Location location, int capacity, ItemStack itemStack){
//        this.ultimateStoragePlus = ultimateStoragePlus;
//        this.location = location;
//        this.capacity = capacity;
//        this.itemStack = itemStack;
//        this.inventory = new GuiManager(this);
//        this.ultimateStoragePlus.getStorageList().putIfAbsent(location, this);
//        this.ultimateStoragePlus.getStorageInventoryList().putIfAbsent(this, getInventory());
//    }

    public Storage(UltimateStoragePlus ultimateStoragePlus, Location location, int capacity, InventoryHolder holder){
        this.location = location;
        this.capacity = capacity;
        this.holder = holder;
        this.inventory = new GuiManager(this);
        ultimateStoragePlus.getStorageList().putIfAbsent(location, this);
        ultimateStoragePlus.getStorageInventoryList().putIfAbsent(this, getInventory());
    }

    public void removeItem(int amount) throws Throwable {

        if (itemStack == null) {
            throw (new Throwable("The storage's item type is null at coordinates : " + this.location + "."));
        }
        this.amount = Math.max((this.amount - amount), 0);
    }

    public void addItem(int amount) throws Throwable {
        if (((this.amount + amount) <= this.capacity) && itemStack != null) {
            this.amount = this.amount + amount;
        } else {
            throw (new Throwable("The storage's item type is null or maximum capacity has been reached at coordinates : " + this.location + "."));
        }
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public Location getLocation() {
        return this.location;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public int getAmount() {
        return this.amount;
    }

    public UUID getStorageUniqueId() {
        return this.storageUniqueId;
    }

    public GuiManager getInventory(){
        return this.inventory;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public InventoryHolder getInventoryHolder() {
        return this.holder;
    }

}
