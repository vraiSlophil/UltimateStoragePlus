package fr.slophil.ultimatestorageplus.events;

import fr.slophil.ultimatestorageplus.UltimateStoragePlus;
import fr.slophil.ultimatestorageplus.entities.data.PlacedStorage;
import fr.slophil.ultimatestorageplus.entities.repository.PlacedStorageRepository;
import fr.slophil.ultimatestorageplus.entities.repository.Repositories;
import fr.slophil.ultimatestorageplus.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.sql.SQLException;
import java.util.Optional;

import static fr.slophil.ultimatestorageplus.utils.StorageInventory.INVENTORY_KEY;


public class BarrelClickEvent implements Listener {

    private final UltimateStoragePlus ultimateStoragePlus;

    public BarrelClickEvent(UltimateStoragePlus ultimateStoragePlus) {
        this.ultimateStoragePlus = ultimateStoragePlus;
    }

    // that check if the player click on a storage by checking if the clicked block is in the database
//    @EventHandler
//    public void onStorageClickEvent(PlayerInteractEvent event) throws SQLException {
//        Action action = event.getAction();
//        Player player = event.getPlayer();
//        Block block = event.getClickedBlock();
//        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.PHYSICAL)) {
//            return;
//        }
//        if (!block.getType().equals(Material.BARREL)) {
//            return;
//        }
//        if (!(block.getState() instanceof Barrel || block.getState() instanceof InventoryHolder)) {
//            return;
//        }
////        InventoryHolder inventoryHolder = ((InventoryHolder) block.getState()).getInventory().getHolder();
////        if (inventoryHolder == null) {
////            return;
////        }
////        if (!(inventoryHolder instanceof Barrel)) {
////            return;
////        }
//        PlacedStorageRepository repo = (PlacedStorageRepository) Repositories.PLACED_STORAGE.getRepository();
//        Optional<PlacedStorage> query = repo.getByLocation(block.getLocation());
//        if (query.isEmpty()) {
//            return;
//        }
//        event.setCancelled(true);
////        StorageInventory storageInventory = new StorageInventory(inventoryHolder, query.get().getBlockType());
////        storageInventory.openInventory(player);



//    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().isSneaking()) {
            Block block = event.getClickedBlock();
            if (block.getType() == Material.BARREL) {
                Barrel barrel = (Barrel) block.getState();
                Inventory inv = barrel.getInventory();
                Player player = event.getPlayer();
                ItemStack handItem = player.getInventory().getItemInMainHand();
                if ((handItem != null || !handItem.isSimilar(new ItemStack(Material.AIR))) && handItem.getAmount() == 64) {
                    ItemStack firstSlotItem = inv.getItem(0);
                    if (firstSlotItem == null || firstSlotItem.getType() == Material.AIR) {
                        ItemBuilder builder = new ItemBuilder(handItem.getType());
                        builder.setItemMeta(handItem.getItemMeta());
                        builder.setPersistentDataContainer(INVENTORY_KEY, PersistentDataType.INTEGER, handItem.getAmount());
                        ItemStack newItem = builder.build();
                        inv.setItem(0, newItem);
                        handItem.setAmount(0);
                    } else if (areSimilarIgnoringPersistentData(firstSlotItem, handItem, INVENTORY_KEY)) {
                        ItemMeta meta = firstSlotItem.getItemMeta();
                        PersistentDataContainer container = meta.getPersistentDataContainer();
                        int currentQuantity = container.has(INVENTORY_KEY, PersistentDataType.INTEGER) ? container.get(INVENTORY_KEY, PersistentDataType.INTEGER) : 0;
                        container.set(INVENTORY_KEY, PersistentDataType.INTEGER, currentQuantity + handItem.getAmount());
                        firstSlotItem.setItemMeta(meta);
                        handItem.setAmount(0);
                    }
                }
            }
        }
    }

    private boolean areSimilarIgnoringPersistentData(ItemStack item1, ItemStack item2, NamespacedKey key) {
        if (item1 == null || item2 == null) {
            return false;
        }
        if (item1.getType() != item2.getType()) {
            return false;
        }
        ItemMeta meta1 = item1.getItemMeta();
        ItemMeta meta2 = item2.getItemMeta();
        if (meta1 == null || meta2 == null) {
            return false;
        }
        // Créer une copie des ItemMeta et supprimer les PersistentDataContainer
        ItemMeta copyMeta1 = meta1.clone();
        ItemMeta copyMeta2 = meta2.clone();
        copyMeta1.getPersistentDataContainer().remove(key);
        copyMeta2.getPersistentDataContainer().remove(key);

        // Créer des copies des ItemStack avec les nouveaux ItemMeta
        ItemStack copyItem1 = item1.clone();
        ItemStack copyItem2 = item2.clone();
        copyItem1.setItemMeta(copyMeta1);
        copyItem2.setItemMeta(copyMeta2);

        // Comparer les copies
        return copyItem1.isSimilar(copyItem2);
    }
}
