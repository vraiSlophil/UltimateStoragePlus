package fr.slophil.ultimatestorageplus.events;

import fr.slophil.ultimatestorageplus.UltimateStoragePlus;
import fr.slophil.ultimatestorageplus.utils.GuiManager;
import fr.slophil.ultimatestorageplus.utils.Storage;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BarrelBreakEvent  implements Listener {

    private final UltimateStoragePlus ultimateStoragePlus;

    public BarrelBreakEvent(UltimateStoragePlus ultimateStoragePlus) {
        this.ultimateStoragePlus = ultimateStoragePlus;
    }

    @EventHandler
    public void onBarrelBreakEvent(BlockBreakEvent event) {
        Location location = event.getBlock().getLocation();

        if (!ultimateStoragePlus.getStorageList().containsKey(location)) {
            return;
        }

        ultimateStoragePlus.getStorageList().remove(location);
    }
}
