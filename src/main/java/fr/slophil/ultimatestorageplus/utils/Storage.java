package fr.slophil.ultimatestorageplus.utils;

import org.bukkit.inventory.ItemStack;

public class Storage {
    private int maxQuantity;
    private int storedQuantity;
    private ItemStack storedItem;

    public Storage(int maxQuantity) {
        this.maxQuantity = maxQuantity;
        this.storedQuantity = 0;
        this.storedItem = null;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public ItemStack getStoredItem() {
        return storedItem;
    }

    public int getStoredQuantity() {
        return storedQuantity;
    }

    public int addItems(int quantity, ItemStack itemStack) {
        if (storedItem == null) {
            storedItem = itemStack;
        } else if (!storedItem.isSimilar(itemStack)) {
            return quantity;
        }

        storedQuantity += quantity;

        if (storedQuantity > maxQuantity) {
            return storedQuantity - maxQuantity;
        }
        return 0;
    }

    public int removeItems(int quantity) {
        if (storedQuantity == 0) {
            return quantity;
        }

        storedQuantity -= quantity;

        if (storedQuantity < 0) {
            return (-1)*storedQuantity;
        }

        if (storedQuantity == 0) {
            storedItem = null;
        }

        return 0;
    }
}









//public abstract class Storage implements Listener, Block {
//
//    private final Location location;
//    private int amount = 200;
//    private final int capacity;
//    private final UUID storageUniqueId = UUID.randomUUID();
//    private ItemStack itemStack = new ItemBuilder(Material.BEDROCK).setDisplayName(ChatColor.RED + "snickers").setGlow(true).build();
//    private final StorageInventory inventory;
//    private final InventoryHolder holder;
////
////    public Storage(UltimateStoragePlus ultimateStoragePlus, Location location, int capacity, int amount,  ItemStack itemStack){
////        this.ultimateStoragePlus = ultimateStoragePlus;
////        this.location = location;
////        this.amount = amount;
////        this.capacity = capacity;
////        this.itemStack = itemStack;
////        this.inventory = new StorageInventory(this);
////        this.ultimateStoragePlus.getStorageList().putIfAbsent(location, this);
////        this.ultimateStoragePlus.getStorageInventoryList().putIfAbsent(this, getInventory());
////    }
////
////    public Storage(UltimateStoragePlus ultimateStoragePlus, Location location, int capacity, ItemStack itemStack){
////        this.ultimateStoragePlus = ultimateStoragePlus;
////        this.location = location;
////        this.capacity = capacity;
////        this.itemStack = itemStack;
////        this.inventory = new StorageInventory(this);
////        this.ultimateStoragePlus.getStorageList().putIfAbsent(location, this);
////        this.ultimateStoragePlus.getStorageInventoryList().putIfAbsent(this, getInventory());
////    }
//
//    public Storage(UltimateStoragePlus ultimateStoragePlus, Location location, int capacity, InventoryHolder holder){
//        this.location = location;
//        this.capacity = capacity;
//        this.holder = holder;
//        this.inventory = new StorageInventory(this);
//        ultimateStoragePlus.getStorageList().putIfAbsent(location, this);
//        ultimateStoragePlus.getStorageInventoryList().putIfAbsent(this, getInventory());
//    }
//
//    public void removeItem(int amount) throws Throwable {
//
//        if (itemStack == null) {
//            throw (new Throwable("The storage's item type is null at coordinates : " + this.location + "."));
//        }
//        this.amount = Math.max((this.amount - amount), 0);
//    }
//
//    public void addItem(int amount) throws Throwable {
//        if (((this.amount + amount) <= this.capacity) && itemStack != null) {
//            this.amount = this.amount + amount;
//        } else {
//            throw (new Throwable("The storage's item type is null or maximum capacity has been reached at coordinates : " + this.location + "."));
//        }
//    }
//
//    public void setAmount(int amount) {
//        this.amount = amount;
//    }
//
//    public void setItemStack(ItemStack itemStack) {
//        this.itemStack = itemStack;
//    }
//
//    public Location getLocation() {
//        return this.location;
//    }
//
//    public int getCapacity() {
//        return this.capacity;
//    }
//
//    public int getAmount() {
//        return this.amount;
//    }
//
//    public UUID getStorageUniqueId() {
//        return this.storageUniqueId;
//    }
//
//    public StorageInventory getInventory(){
//        return this.inventory;
//    }
//
//    public ItemStack getItemStack() {
//        return this.itemStack;
//    }
//
//    public InventoryHolder getInventoryHolder() {
//        return this.holder;
//    }
//
//}
