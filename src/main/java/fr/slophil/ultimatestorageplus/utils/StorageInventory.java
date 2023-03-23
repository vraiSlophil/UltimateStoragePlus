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
    private final int inventorySize = 4 * 9;
    private final InventoryHolder inventoryHolder;
    private final Inventory inventory;
    private boolean dropMode;
    private boolean pullMode;
    private ItemStack storedItem;

    private int storedQuantity;

    private final int maxQuantity;

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
        this.storedQuantity = 0;
        if (this.storedItem != null) {
            this.storedQuantity = this.storedItem.getItemMeta().getPersistentDataContainer().get(INVENTORY_KEY, PersistentDataType.INTEGER);
        }
        this.maxQuantity = blockType.getItem().getItemMeta().getPersistentDataContainer()
                .get(UltimateStoragePlus.STORAGE_KEY, PersistentDataType.INTEGER);
        this.inventory = Bukkit.createInventory(null, this.inventorySize,
                ChatColor.YELLOW + "Ultimate Storage - " + this.maxQuantity + " capacity");

        UltimateStoragePlus.getInstance().getServer().getPluginManager().registerEvents(this, UltimateStoragePlus.getInstance());
    }

    /**
     * This method is used to open the inventory to the player
     *
     * @param player the player who will open the inventory
     * @return nothing
     */
    public void openInventory(Player player) {
        updateInventory();
        player.openInventory(this.inventory);
    }

    /**
     * This method is used to update the inventory
     *
     * @return nothing
     */
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
        lore.add(ChatColor.GRAY + "Stocked item : " + (this.storedItem == null ? "nothing" : this.storedItem.getType().toString()));
        lore.add(ChatColor.GRAY + "Quantity : " + this.storedQuantity + " / " + this.maxQuantity);
        ItemStack barrel = new ItemBuilder(Material.BARREL).setDisplayName(ChatColor.YELLOW + "Ultimate Storage - " + this.maxQuantity + " capacity").setLore(lore).build();
        this.inventory.setItem(30, barrel);
        if (this.pullMode) {
            ItemStack pullItem = new ItemBuilder(Material.CHEST).setDisplayName(ChatColor.YELLOW + "Mode pull").build();
            this.inventory.setItem(32, pullItem);
        } else {
            ItemStack dropItem = new ItemBuilder(Material.HOPPER).setDisplayName(ChatColor.YELLOW + "Mode drop").build();
            this.inventory.setItem(32, dropItem);
        }
    }

    /**
     * This method is used to get the inventory of this class
     *
     * @return the inventory of this class
     */
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * This methodis used to add ItemStack to the StorageInventory
     *
     * @param quantity  the quantity of ItemStack to add
     * @param itemStack the ItemStack to add
     * @return How many item that can't be added
     */
    public int addItems(int quantity, ItemStack itemStack) {
        Inventory inv = this.inventoryHolder.getInventory();
        ItemStack itemStackClone = itemStack.clone();
        if (storedItem == null) {
            itemStackClone.setAmount(1);
            itemStackClone.getItemMeta().getPersistentDataContainer().set(INVENTORY_KEY, PersistentDataType.INTEGER, itemStack.getAmount());
            inv.setItem(0, itemStackClone);
            return 0;
        }
        if (!storedItem.isSimilar(itemStack)) {
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
            storedItem.getItemMeta().getPersistentDataContainer().set(INVENTORY_KEY, PersistentDataType.INTEGER, 0);
            return Math.abs(storedQuantity);
        }
        if (storedQuantity == 0) {
            storedItem = null;
        }
        return 0;
    }

    /**
     * This method is used to fill the player inventory and to not duplicate lines of code
     *
     * @param itemStackToAdd  ItemStack to add
     * @param quantityMoved   quantity of the ItemStack to add
     * @param playerInventory player inventory in which we will add the ItemStack
     */
    private void fillPlayerInventory(ItemStack itemStackToAdd, int quantityMoved, Inventory playerInventory) {
        int stackSize = itemStackToAdd.getMaxStackSize();
        int stackToAddBack = quantityMoved / stackSize;
        // if quantityMoved = 128, stackToAddBack = 2. Because there is 2 stacks
        itemStackToAdd.setAmount(stackSize);
        for (int i = 0; i < stackToAddBack; i++) {
            playerInventory.addItem(itemStackToAdd);
        }
        itemStackToAdd.setAmount(1);
        int itemToAddBack = quantityMoved % stackSize;
        // if quantityMoved = 89, itemToAddBack = 1. Because there is 1 stack and 1 item of 25 units
        for (int i = 0; i < itemToAddBack; i++) {
            playerInventory.addItem(itemStackToAdd);
        }
    }

    private void storageInventoryInteraction(ItemStack itemStackToAdd, Inventory playerInventory, int quantityMoved) {
        if (this.dropMode) {
            int quantityAdded = this.addItems(quantityMoved, itemStackToAdd);
            if (quantityAdded > 0) {
                quantityMoved -= quantityAdded;
                this.fillPlayerInventory(itemStackToAdd, quantityMoved, playerInventory);
            }
        } else if (this.pullMode) {
            int quantityRemoved = this.removeItems(quantityMoved);
            this.fillPlayerInventory(itemStackToAdd, quantityMoved, playerInventory);
        }
        updateInventory();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        System.out.println("Inventory click");
        if (!event.getClickedInventory().equals(this.inventory)) {
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

        ItemStack itemStackToAdd = (this.storedItem == null) ? currentItem.clone() : this.storedItem.clone();
        if (itemStackToAdd.getItemMeta().getPersistentDataContainer().has(INVENTORY_KEY, PersistentDataType.INTEGER)) {
            itemStackToAdd.getItemMeta().getPersistentDataContainer().remove(INVENTORY_KEY);
        }

        Inventory playerInventory = event.getWhoClicked().getInventory();
        int quantityMoved = currentItem.getAmount();

        storageInventoryInteraction(itemStackToAdd, playerInventory, quantityMoved);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        System.out.println("Inventory drag");
        Inventory playerInventory = event.getWhoClicked().getInventory();
        if (!event.getInventory().equals(this.inventory)) {
            return;
        }
        event.setCancelled(true);
        Map<Integer, ItemStack> draggedItems = event.getNewItems();
        if (draggedItems.isEmpty()) {
            return;
        }
        ItemStack itemStackToAdd = draggedItems.values().iterator().next().clone();
        if (storedItem != null && storedItem.isSimilar(itemStackToAdd)) {
            itemStackToAdd = this.storedItem.clone();
            itemStackToAdd.getItemMeta().getPersistentDataContainer().remove(INVENTORY_KEY);

        }
        int quantityMoved = 0;
        for (ItemStack item : draggedItems.values()) {
            if (item.isSimilar(itemStackToAdd)) {
                quantityMoved += item.getAmount();
            }
        }
        storageInventoryInteraction(itemStackToAdd, playerInventory, quantityMoved);
    }
}