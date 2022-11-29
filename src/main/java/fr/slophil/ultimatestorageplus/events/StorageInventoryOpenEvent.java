package fr.slophil.ultimatestorageplus.events;


import fr.slophil.ultimatestorageplus.UltimateStoragePlus;
import fr.slophil.ultimatestorageplus.utils.GuiManager;
import fr.slophil.ultimatestorageplus.utils.ItemBuilder;
import fr.slophil.ultimatestorageplus.utils.Storage;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StorageInventoryOpenEvent implements Listener {

    private final UltimateStoragePlus ultimateStoragePlus;

    public StorageInventoryOpenEvent(UltimateStoragePlus ultimateStoragePlus) {
        this.ultimateStoragePlus = ultimateStoragePlus;
    }
    @EventHandler
    public void onStorageInvetoryOpen(InventoryOpenEvent event) {
        Inventory inventory = event.getInventory();

        if (inventory == null) {
            return;
        }

        InventoryHolder inventoryHolder = inventory.getHolder();

        if (inventoryHolder == null) {
            return;
        }

        if (!(inventory.getHolder() instanceof BlockInventoryHolder)) {
            return;
        }

        Location location = ((BlockInventoryHolder) inventoryHolder).getBlock().getLocation();

        if (!ultimateStoragePlus.getStorageList().containsKey(location)) {
            return;
        }

        Storage storage = ultimateStoragePlus.getStorageList().get(location);
        GuiManager customInventory = ultimateStoragePlus.getStorageInventoryList().get(storage);

        int amount = storage.getAmount();
        int stack = 64;

        if (storage.getItemStack() != null && customInventory.getState().equals("pull")) {
            int slot = (int) amount / stack;
            int block = amount % stack;

            for (int i = 0; i < slot; i++) {
                addItem(stack, storage, inventory);
            }
            if (block >= 0) {
                addItem(block, storage, inventory);
            }
        }
    }

    public void addItem(int i, Storage storage, Inventory inventory) {
        ItemMeta itemMeta = storage.getItemStack().getItemMeta();
        ItemStack item = new ItemBuilder(storage.getItemStack().getType()).setItemMeta(itemMeta).setAmount(i).build();
        try {
            storage.removeItem(i);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        inventory.addItem(item);
    }
}

