package fr.slophil.ultimatestorageplus.events;

import fr.slophil.ultimatestorageplus.UltimateStoragePlus;
import fr.slophil.ultimatestorageplus.entities.data.PlacedStorage;
import fr.slophil.ultimatestorageplus.entities.repository.PlacedStorageRepository;
import fr.slophil.ultimatestorageplus.entities.repository.Repositories;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class BarrelBreakEvent  implements Listener {

    private final UltimateStoragePlus ultimateStoragePlus;

    public BarrelBreakEvent(UltimateStoragePlus ultimateStoragePlus) {
        this.ultimateStoragePlus = ultimateStoragePlus;
    }

    @EventHandler
    public void onBarrelBreakEvent(BlockBreakEvent event) throws SQLException {
        Location location = event.getBlock().getLocation();

        PlacedStorageRepository storageRepository = (PlacedStorageRepository) Repositories.PLACED_STORAGE.getRepository();

        Connection connection = ultimateStoragePlus.getConnector().getConnection();
        Optional<PlacedStorage> storage = storageRepository.getByLocation(event.getBlock().getLocation());
        if (storage.isPresent()) {
            storageRepository.remove(storage.get());
        }
    }
}
