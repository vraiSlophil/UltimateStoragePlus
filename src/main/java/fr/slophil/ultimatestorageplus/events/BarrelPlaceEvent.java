package fr.slophil.ultimatestorageplus.events;

import fr.slophil.ultimatestorageplus.UltimateStoragePlus;
import fr.slophil.ultimatestorageplus.entities.data.PlacedStorage;
import fr.slophil.ultimatestorageplus.entities.repository.PlacedStorageRepository;
import fr.slophil.ultimatestorageplus.entities.repository.Repositories;
import fr.slophil.ultimatestorageplus.utils.BlockType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.sql.SQLException;

public class BarrelPlaceEvent implements Listener {

    private final UltimateStoragePlus ultimateStoragePlus;

    public BarrelPlaceEvent(UltimateStoragePlus ultimateStoragePlus) {
        this.ultimateStoragePlus = ultimateStoragePlus;
    }

    /**
     * Event called when a barrel is placed
     */
    @EventHandler
    public void onBarrelPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        Location blockLocation = block.getLocation();
        ItemStack item = event.getItemInHand();

        if ((block.getType() != Material.BARREL)
                || (item.getType() != Material.BARREL)) {
            return;
        }

        if (!(item.hasItemMeta())) {
            return;
        }

        if (!(block.getState() instanceof TileState
                || block.getState() instanceof InventoryHolder)) {
            return;
        }

        TileState blockState = (TileState) block.getState();
        PersistentDataContainer blockContainer = blockState.getPersistentDataContainer();
        PersistentDataContainer itemContainer = item.getItemMeta().getPersistentDataContainer();

        if (itemContainer.has(UltimateStoragePlus.STORAGE_KEY, PersistentDataType.INTEGER)) {
            InventoryHolder holder = (Barrel) block.getState();

            PlacedStorageRepository repository = (PlacedStorageRepository) Repositories.PLACED_STORAGE.getRepository();

            PlacedStorage coords = new PlacedStorage(blockLocation, BlockType.getBlockType(itemContainer.get(UltimateStoragePlus.STORAGE_KEY, PersistentDataType.INTEGER)));
            try {
                repository.save(coords);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
