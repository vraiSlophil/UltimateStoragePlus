package fr.slophil.ultimatestorageplus.entities.data;

import fr.slophil.ultimatestorageplus.utils.BlockType;
import org.bukkit.Location;

public class PlacedStorage {

    private Location location;
    private BlockType blockType;

    /**
     * Constructor
     *
     * @param location Location of the block
     * @param type     Type of the block
     */
    public PlacedStorage(Location location, BlockType type) {
        this.location = location;
        this.blockType = type;
    }

    /**
     * Get the location of the block
     *
     * @return Location of the block
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Set the location of the block
     *
     * @param location Location of the block
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Get the type of the block
     *
     * @return Type of the block
     */
    public BlockType getBlockType() {
        return blockType;
    }

    /**
     * Set the type of the block
     *
     * @param blockType Type of the block
     */
    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }
}
