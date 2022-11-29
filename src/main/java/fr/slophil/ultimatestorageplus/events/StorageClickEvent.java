package fr.slophil.ultimatestorageplus.events;

import fr.slophil.ultimatestorageplus.UltimateStoragePlus;
import fr.slophil.ultimatestorageplus.utils.GuiManager;
import fr.slophil.ultimatestorageplus.utils.Storage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;


public class StorageClickEvent implements Listener {

    private final UltimateStoragePlus ultimateStoragePlus;

    public StorageClickEvent(UltimateStoragePlus ultimateStoragePlus) {
        this.ultimateStoragePlus = ultimateStoragePlus;
    }

    @EventHandler
    public void onInventoryOpenEvent(PlayerInteractEvent event) {
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

        if (!ultimateStoragePlus.getStorageList().containsKey(location)) {
            return;
        }

        Player player = (Player) event.getPlayer();
        Storage storage = ultimateStoragePlus.getStorageList().get(location);
        GuiManager guiManager = ultimateStoragePlus.getStorageInventoryList().get(storage);

        event.setCancelled(true);
        player.openInventory(guiManager.getInventory());
    }
}
