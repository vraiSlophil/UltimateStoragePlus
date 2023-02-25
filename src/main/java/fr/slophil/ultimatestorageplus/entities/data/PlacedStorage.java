package fr.slophil.ultimatestorageplus.entities.data;

import fr.slophil.ultimatestorageplus.utils.BlockType;
import org.bukkit.Location;

public class PlacedStorage {

    private Location location;
    private BlockType blockType;

    public PlacedStorage(Location location, BlockType type) {
        this.location = location;
        this.blockType = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }
}
