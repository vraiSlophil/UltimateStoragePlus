package fr.slophil.ultimatestorageplus.events;

import fr.slophil.ultimatestorageplus.UltimateStoragePlus;
import fr.slophil.ultimatestorageplus.utils.GuiManager;
import fr.slophil.ultimatestorageplus.utils.ItemBuilder;
import fr.slophil.ultimatestorageplus.utils.Storage;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StorageInventoryClickEvent implements Listener {

    private final UltimateStoragePlus ultimateStoragePlus;

    public StorageInventoryClickEvent(UltimateStoragePlus ultimateStoragePlus) {
        this.ultimateStoragePlus = ultimateStoragePlus;
    }

    @EventHandler
    public void onStorageInventoryClickEvent(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();

        if (inventory == null) {
            return;
        }

        InventoryHolder inventoryHolder = inventory.getHolder();
        ItemStack itemStack = event.getCurrentItem();

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

        if (!customInventory.getInventory().equals(inventory)) {
            return;
        }

        if (itemStack == null) {
            return;
        }

        List<Integer> menuSlots = new ArrayList<>(Arrays.asList(27, 28, 29, 30, 31, 32, 33, 34, 35, 36));

//        List<Integer> getSlots = new ArrayList<>(Arrays.asList(
//                0, 1, 2, 3,
//                9, 10, 11, 12,
//                18, 19, 20, 21,
//                27, 28, 29, 30,
//                36, 37, 38, 39,
//                45, 46, 47, 48
//        ));
//
//        List<Integer> setSlots = new ArrayList<>(Arrays.asList(
//                5, 6, 7, 8,
//                14, 15, 16, 17,
//                23, 24, 25, 26,
//                32, 33, 34, 35,
//                41, 42, 43, 44,
//                50, 51, 52, 53
//        ));

        int slot = event.getSlot();
        InventoryAction inventoryAction = event.getAction();
        Player player = (Player) event.getWhoClicked();
        ItemStack cursor = event.getCursor();

        if (menuSlots.contains(slot)) {
            event.setCancelled(true);
        }

        // First step

//        int amount = storage.getAmount();
//
//        AtomicInteger index = new AtomicInteger();
//
//        storage.setItemStack(new ItemBuilder(Material.BEDROCK).setDisplayName(ChatColor.RED + "snickers").setGlow(true).build());
//        storage.setAmount(256);
//
//        if (storage.getItemStack() != null) {
//            for (Integer slotGetter : getSlots) {
//                index.getAndIncrement();
//
//                if (!((amount - ((index.get()+1) * 64)) < (amount - ((index.get()) * 64)))) {
//                    ItemMeta itemMeta = storage.getItemStack().getItemMeta();
//                    ItemStack item = new ItemBuilder(storage.getItemStack().getType()).setItemMeta(itemMeta).setAmount(amount - (amount - ((index.get()) * 64))).build();
//
//                    inventory.setItem(slotGetter, item);
//                } else {
//                    ItemMeta itemMeta = storage.getItemStack().getItemMeta();
//                    ItemStack item = new ItemBuilder(storage.getItemStack().getType()).setItemMeta(itemMeta).setAmount(64).build();
//
//                    inventory.setItem(slotGetter, item);
//                }
//            }
//        } else {
//            return;
//            setSlots.forEach(slotSetter -> {
//                return;
//            });
//        }
//
//        if (cursor != null) { //if putting container in
//            if (!setSlots.contains(slot)) {
//                event.setCancelled(true);
//            }
//
//            try {
//                storage.removeItem(itemStack.getAmount());
//            } catch (Throwable throwable) {
//                event.setCancelled(true);
//                player.closeInventory();
//            }
//
//            if (cursor.hasItemMeta()) { //if putting a Storage Container in the dsu
//                if (cursor.getItemMeta().getPersistentDataContainer().has(ultimateStoragePlus.getRecipeManager().getKey(), PersistentDataType.INTEGER)) {
//                    event.setCancelled(true);
//                    player.closeInventory();
//                }
//            }
//            if (event.isShiftClick()) {
//                event.setCancelled(true);
//            }
//        } else { //if taking container out
//            event.setCancelled(true);
//            if (itemStack != null && itemStack.getType() != Material.WHITE_STAINED_GLASS_PANE) {
//                player.setItemOnCursor(itemStack.clone());
//                inv.setItem(event.getSlot(), DSUManager.getEmptyBlock());
//                main.dsuupdatemanager.updateItems(inv, null);
//            }
//        }
//        if (setSlots.contains(slot)) {
//        switch (inventoryAction){
//            case PICKUP_ALL -> {
//                if (getSlots.contains(slot)){
//                    try {
//                        storage.removeItem(itemStack.getAmount());
//                    } catch (Throwable throwable) {
//                        event.setCancelled(true);
//                        player.closeInventory();
//                    }
//                }
//            }
//        }
//        }
    }

}
