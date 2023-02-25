package fr.slophil.ultimatestorageplus.events;

import fr.slophil.ultimatestorageplus.UltimateStoragePlus;
import fr.slophil.ultimatestorageplus.entities.repository.PlacedStorageRepository;
import fr.slophil.ultimatestorageplus.entities.repository.Repositories;
import fr.slophil.ultimatestorageplus.utils.GuiManager;
import fr.slophil.ultimatestorageplus.utils.Storage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.sql.Connection;
import java.sql.SQLException;


public class StorageClickEvent implements Listener {

    private final UltimateStoragePlus ultimateStoragePlus;

    public StorageClickEvent(UltimateStoragePlus ultimateStoragePlus) {
        this.ultimateStoragePlus = ultimateStoragePlus;
    }

    @EventHandler
    public void onInventoryOpenEvent(PlayerInteractEvent event) throws SQLException {
        Action action = event.getAction();

        if (action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();

        if(block.getType() != Material.BARREL) {
            return;
        }

        if (!(block.getState() instanceof Barrel || block.getState() instanceof InventoryHolder)) {
            return;
        }

        Inventory inventory = ((Barrel) block.getState()).getInventory();
        InventoryHolder inventoryHolder = inventory.getHolder();

        if (inventoryHolder == null) {
            return;
        }

        if (!(inventoryHolder instanceof Barrel)) {
            return;
        }

        Location location = ((Barrel) inventoryHolder).getLocation();

        PlacedStorageRepository repo = (PlacedStorageRepository) Repositories.PLACED_STORAGE.getRepository();
        Connection conn = ultimateStoragePlus.getConnector().getConnection();

        if (repo.getByLocation(location).isEmpty()) {
            return;
        }

        event.setCancelled(true);
        Player player = event.getPlayer();
//        Storage storage = ultimateStoragePlus.getStorageList().get(location);
//        GuiManager guiManager = ultimateStoragePlus.getStorageInventoryList().get(storage);

        event.getPlayer().openInventory(Bukkit.createInventory(null, InventoryType.ANVIL));
    }
}
