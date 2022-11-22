package fr.slophil.ultimatestorageplus.events;

import fr.slophil.ultimatestorageplus.UltimateStoragePlus;
import fr.slophil.ultimatestorageplus.utils.Storage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BarrelPlaceEvent  implements Listener {

    private final UltimateStoragePlus ultimateStoragePlus;

    public BarrelPlaceEvent(UltimateStoragePlus ultimateStoragePlus) {
        this.ultimateStoragePlus = ultimateStoragePlus;
    }

    @EventHandler
    public void onBarrelPlace(BlockPlaceEvent event){
        Block block = event.getBlockPlaced();
        Location blockLocation = block.getLocation();
        ItemStack item = event.getItemInHand();

        if(block.getType() != Material.BARREL) {
            return;
        }

        if (item.getType() != Material.BARREL) {
            return;
        }

        if(!(item.hasItemMeta())) {
            return;
        }

        if (!(block.getState() instanceof TileState || block.getState() instanceof InventoryHolder)) {
            return;
        }

        TileState blockState = (TileState) block.getState();
        PersistentDataContainer blockContainer = blockState.getPersistentDataContainer();
        PersistentDataContainer itemContainer = item.getItemMeta().getPersistentDataContainer();
        NamespacedKey key = ultimateStoragePlus.getRecipeManager().getKey();

        if (itemContainer.has(key, PersistentDataType.INTEGER)){
            InventoryHolder holder = (Barrel) block.getState();

            new Storage(ultimateStoragePlus, blockLocation, itemContainer.get(key, PersistentDataType.INTEGER), holder);
            blockContainer.set(key, PersistentDataType.INTEGER, itemContainer.get(key, PersistentDataType.INTEGER));
            blockState.update();
        }
    }
}
