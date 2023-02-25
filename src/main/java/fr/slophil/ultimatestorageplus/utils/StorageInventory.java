package fr.slophil.ultimatestorageplus.utils;

import fr.slophil.ultimatestorageplus.UltimateStoragePlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Map;

public class StorageInventory implements Listener, InventoryHolder {

    private final static NamespacedKey INVENTORY_KEY = new NamespacedKey(UltimateStoragePlus.getInstance(), "storage_inventory");
    private final InventoryHolder inventoryHolder;
    private final Inventory inventory;
    private boolean dropMode;
    private boolean pullMode;
    private final int inventorySize = 4 * 9;

    private ItemStack storedItem;

    private int storedQuantity;

    private int maxQuantity;

    /**
     * Constructor of the custom inventory
     *
     * @param inventoryHolder the inventory holder
     */
    public StorageInventory(InventoryHolder inventoryHolder, BlockType blockType) {
        this.inventoryHolder = inventoryHolder;
        this.dropMode = true;
        this.pullMode = false;

        this.storedItem = inventoryHolder.getInventory().getItem(0);
        this.storedQuantity = inventoryHolder.getInventory().getItem(0).getItemMeta().getPersistentDataContainer().get(INVENTORY_KEY, PersistentDataType.INTEGER);
        this.maxQuantity = blockType.getItem().getItemMeta().getPersistentDataContainer().get(UltimateStoragePlus.STORAGE_KEY, PersistentDataType.INTEGER);

        this.inventory = Bukkit.createInventory(null, this.inventorySize, ChatColor.YELLOW + "Ultimate Storage - " + this.maxQuantity + "capacity");
    }

    /**
     * This function is used to open the inventory to the player
     *
     * @param player the player who will open the inventory
     * @return nothing
     */
    public void openInventory(Player player) {
        updateInventory();
        player.openInventory(this.inventory);
    }

    public void updateInventory() {

        if (this.dropMode) {
            int slot = 0;
            while (this.storedQuantity > 0 && slot < 27) {
                ItemStack itemStack = new ItemStack(this.storedItem);

                PersistentDataContainer container = itemStack.getItemMeta().getPersistentDataContainer();
                container.remove(INVENTORY_KEY);

                int quantityToAdd = Math.min(this.storedQuantity, this.storedItem.getMaxStackSize());
                itemStack.setAmount(quantityToAdd);
                this.inventory.setItem(slot, itemStack);
                this.storedQuantity -= quantityToAdd;
                slot++;
            }
        }

        for (int i = 27; i < 36; i++) {
            this.inventory.setItem(i, new ItemBuilder(Material.BARRIER).setDisplayName(ChatColor.BLACK + "").setGlow(true).build());
        }
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Stocked item : " + (this.storedItem == null ? "anything" : this.storedItem.getType().toString()));
        lore.add(ChatColor.GRAY + "Quantity : " + this.storedQuantity + " / " + this.maxQuantity);
        ItemStack barrel = new ItemBuilder(Material.BARREL).setDisplayName(ChatColor.YELLOW + "Ultimate Storage - " + this.maxQuantity + "capacity").setLore(lore).build();
        this.inventory.setItem(30, barrel);
        if (this.pullMode) {
            ItemStack pullItem = new ItemBuilder(Material.CHEST).setDisplayName(ChatColor.YELLOW + "Mode pull").build();
            this.inventory.setItem(32, pullItem);
        } else {
            ItemStack dropItem = new ItemBuilder(Material.HOPPER).setDisplayName(ChatColor.YELLOW + "Mode drop").build();
            this.inventory.setItem(32, dropItem);
        }
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory().equals(this.inventory) || event.getInventory().equals(event.getWhoClicked().getInventory()))) {
            return;
        }
        event.setCancelled(true);
        ItemStack currentItem = event.getCurrentItem();
        if (currentItem == null || currentItem.getType() == Material.AIR) {
            return;
        }
        if (event.getRawSlot() >= 27) {
            if (event.getRawSlot() == 32) {
                this.dropMode = !this.dropMode;
                this.pullMode = !this.pullMode;
                updateInventory();
            }
            return;
        }
        if (this.dropMode && currentItem.isSimilar(this.storedItem)) {
            int quantityToAdd = currentItem.getAmount();
            int quantityAdded = this.addItems(quantityToAdd, currentItem);
            currentItem.setAmount(quantityToAdd - quantityAdded);
        } else if (this.pullMode) {
            int quantityToRemove = currentItem.getAmount();
            int quantityRemoved = this.removeItems(quantityToRemove);
            if (quantityRemoved > 0) {
                ItemStack itemStack = this.storedItem;
                itemStack.setAmount(quantityRemoved);
                Player player = (Player) event.getWhoClicked();
                player.getInventory().addItem(itemStack);
            }
        }
        updateInventory();
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!event.getInventory().equals(this.inventory)) {
            return;
        }
        event.setCancelled(true);
        Map<Integer, ItemStack> draggedItems = event.getNewItems();
        if (draggedItems.isEmpty()) {
            return;
        }
        int quantityMoved = 0;
        for (ItemStack item : draggedItems.values()) {
            if (item.isSimilar(this.storedItem)) {
                quantityMoved += item.getAmount();
            }
        }
        if (this.dropMode) {
            int quantityAdded = this.addItems(quantityMoved, draggedItems.get(0));
            if (quantityAdded < quantityMoved) {
                quantityMoved = quantityAdded;
            }
        } else if (this.pullMode) {
            int quantityRemoved = this.removeItems(quantityMoved);
            if (quantityRemoved < quantityMoved) {
                quantityMoved = quantityRemoved;
            }
            ItemStack itemStack = new ItemStack(this.storedItem);
            itemStack.setAmount(quantityMoved);
            Player player = (Player) event.getWhoClicked();
            player.getInventory().addItem(itemStack);
        }
        updateInventory();
    }

    public int put(int quantity, ItemStack itemStack) {
        Inventory inv = this.inventoryHolder.getInventory();
        PersistentDataContainer container = inv.getItem(0).getItemMeta().getPersistentDataContainer();

        ItemStack itemStackToAdd = itemStack.clone();
        itemStackToAdd.getItemMeta().getPersistentDataContainer().remove(INVENTORY_KEY);

        if (storedItem == null || storedItem.getType() == Material.AIR) {
            itemStack.setAmount(1);
            inv.setItem(0, itemStack);
            storedItem.getItemMeta().getPersistentDataContainer().set(INVENTORY_KEY, PersistentDataType.INTEGER, itemStack.getAmount());
            return 0;
        } else if (!storedItem.isSimilar(itemStackToAdd)) {
            return quantity;
        }

        storedQuantity += quantity;
        storedItem.getItemMeta().getPersistentDataContainer().set(INVENTORY_KEY, PersistentDataType.INTEGER, storedQuantity);

        if (storedQuantity > maxQuantity) {
            storedItem.getItemMeta().getPersistentDataContainer().set(INVENTORY_KEY, PersistentDataType.INTEGER, (storedQuantity - maxQuantity));
            return storedQuantity - maxQuantity;
        }
        return 0;
    }

    public int removeItems(int quantity) {
        if (storedQuantity == 0) {
            return quantity;
        }

        storedQuantity -= quantity;
        storedItem.getItemMeta().getPersistentDataContainer().set(INVENTORY_KEY, PersistentDataType.INTEGER, storedQuantity);

        if (storedQuantity < 0) {
            storedItem.getItemMeta().getPersistentDataContainer().set(INVENTORY_KEY, PersistentDataType.INTEGER, ((-1) * storedQuantity));
            return (-1) * storedQuantity;
        }

        if (storedQuantity == 0) {
            storedItem = null;
        }

        return 0;
    }


}


//public class StorageInventory {
//
//    private Storage owner;
//    private Inventory inventory;
//    private String state;
//
//    private final ItemStack pullItemStack = new ItemBuilder(Material.ENDER_PEARL).setDisplayName(ChatColor.YELLOW + "Pull").setGlow(true).build();
//    private final ItemStack dropItemStack = new ItemBuilder(Material.ENDER_EYE).setDisplayName(ChatColor.YELLOW + "Drop").setGlow(true).build();
//
//    public StorageInventory(UltimateStoragePlus ultimateStoragePlus) {
//    }
//    public StorageInventory(Storage owner) {
//        this.owner = owner;
//        int rows = 4 * 9;
//        Formatter cap = new Formatter();
//        cap.format("%,d", owner.getCapacity());
//        String title = "%sUltimate Storage - %s capacity".formatted(ChatColor.YELLOW, cap);
//
//        this.inventory = Bukkit.createInventory(owner.getInventoryHolder(), rows, title);
//        setup(this.inventory, owner);
//    }
//
//    private void setup(Inventory inventory, Storage owner) {
//        for (int slot = 27; slot < 4*9; slot++) {
//            inventory.setItem(slot, new ItemBuilder(Material.BARRIER).setDisplayName(ChatColor.BLACK + "").setGlow(true).build());
//        }
//
//        if (owner.getItemStack() != null) {
//            Formatter amount = new Formatter();
//            amount.format("%,d", owner.getAmount());
//            String name = "%sContains %s of %s".formatted(ChatColor.YELLOW, amount, owner.getItemStack().getType());
//            inventory.setItem(28, new ItemBuilder(Material.MAP).setDisplayName(name).setGlow(true).build());
//        }
//        this.setState("pull");
//
//    }
//
//    public void setState(String state) {
//        this.state = state;
//        if (state.equals("drop")) {
//            this.inventory.setItem(27, dropItemStack);
//            return;
//        }
//        this.inventory.setItem(27, pullItemStack);
//    }
//
//    public Inventory getInventory() {
//        return this.inventory;
//    }
//
//    public Storage getStorage(){
//        return this.owner;
//    }
//
//    public String getState() {
//        return this.state;
//    }
//}
