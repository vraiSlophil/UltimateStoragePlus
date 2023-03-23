package fr.slophil.ultimatestorageplus.events;

import fr.slophil.ultimatestorageplus.UltimateStoragePlus;
import fr.slophil.ultimatestorageplus.entities.data.PlacedStorage;
import fr.slophil.ultimatestorageplus.entities.repository.PlacedStorageRepository;
import fr.slophil.ultimatestorageplus.entities.repository.Repositories;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;

import java.sql.SQLException;
import java.util.Optional;


public class BarrelClickEvent implements Listener {

    private final UltimateStoragePlus ultimateStoragePlus;

    public BarrelClickEvent(UltimateStoragePlus ultimateStoragePlus) {
        this.ultimateStoragePlus = ultimateStoragePlus;
    }

    // that check if the player click on a storage by checking if the clicked block is in the database
    @EventHandler
    public void onStorageClickEvent(PlayerInteractEvent event) throws SQLException {
        Action action = event.getAction();
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.PHYSICAL)) {
            return;
        }
        if (!block.getType().equals(Material.BARREL)) {
            return;
        }
        if (!(block.getState() instanceof Barrel || block.getState() instanceof InventoryHolder)) {
            return;
        }
//        InventoryHolder inventoryHolder = ((InventoryHolder) block.getState()).getInventory().getHolder();
//        if (inventoryHolder == null) {
//            return;
//        }
//        if (!(inventoryHolder instanceof Barrel)) {
//            return;
//        }
        PlacedStorageRepository repo = (PlacedStorageRepository) Repositories.PLACED_STORAGE.getRepository();
        Optional<PlacedStorage> query = repo.getByLocation(block.getLocation());
        if (query.isEmpty()) {
            return;
        }
        event.setCancelled(true);
//        StorageInventory storageInventory = new StorageInventory(inventoryHolder, query.get().getBlockType());
//        storageInventory.openInventory(player);


        player.isSneaking();

    }
}
