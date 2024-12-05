package fr.slophil.ultimatestorageplus.entities.repository;

import fr.slophil.ultimatestorageplus.entities.data.PlacedStorage;
import fr.slophil.ultimatestorageplus.utils.BlockType;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PlacedStorageRepository implements Repository<PlacedStorage> {

    private Connection connection;

    /**
     * This method is used to hydrate the repository
     *
     * @param connection
     */
    @Override
    public void hydrate(Connection connection) {
        this.connection = connection;
    }

    /**
     * This method is used to create the table
     *
     * @throws SQLException
     */
    @Override
    public void create() throws SQLException {
        PreparedStatement create = connection.prepareStatement("""
                CREATE TABLE IF NOT EXISTS BlockCoordinates(
                    blockX INT(255) NOT NULL,
                    blockY INT(255) NOT NULL,
                    blockZ INT(255) NOT NULL,
                    blockWorld VARCHAR(64) NOT NULL,
                    blockType VARCHAR(64) NOT NULL,
                    PRIMARY KEY (blockX, blockY, blockZ, blockWorld)
                );
                """);
        create.execute();
        create.close();

    }

    /**
     * This method is used to delete the table
     *
     * @throws SQLException
     */
    @Override
    public void delete() throws SQLException {
        PreparedStatement delete = connection.prepareStatement("""
                DROP TABLE IF EXISTS BlockCoordinates;
                """);
        delete.execute();
        delete.close();
    }

    /**
     * This method is used to save an entity
     *
     * @param entity
     * @throws SQLException
     */
    @Override
    public void save(PlacedStorage entity) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("""
                INSERT INTO BlockCoordinates(blockX, blockY, blockZ, blockWorld, blockType) VALUES (
                    ?, ?, ?, ?, ?
                );
                """);

        stmt.setLong(1, entity.getLocation().getBlockX());
        stmt.setLong(2, entity.getLocation().getBlockY());
        stmt.setLong(3, entity.getLocation().getBlockZ());
        stmt.setString(4, entity.getLocation().getWorld().getName());
        stmt.setString(5, entity.getBlockType().name());

        stmt.execute();
        stmt.close();
    }

    /**
     * This method is used to edit an entity
     *
     * @param entity
     * @throws SQLException
     */
    @Override
    public void edit(PlacedStorage entity) throws SQLException {

    }

    /**
     * This method is used to remove an entity
     *
     * @param entity
     * @throws SQLException
     */
    @Override
    public void remove(PlacedStorage entity) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("""
                DELETE FROM BlockCoordinates WHERE
                    blockX = ? AND
                    blockY = ? AND
                    blockZ = ? AND
                    blockWorld = ?
                ;
                """);

        stmt.setLong(1, entity.getLocation().getBlockX());
        stmt.setLong(2, entity.getLocation().getBlockY());
        stmt.setLong(3, entity.getLocation().getBlockZ());
        stmt.setString(4, entity.getLocation().getWorld().getName());

        stmt.execute();
        stmt.close();
    }

    /**
     * This method is used to get an entity by its location
     *
     * @param location
     * @return
     * @throws SQLException
     */
    public Optional<PlacedStorage> getByLocation(Location location) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("""
                SELECT * FROM BlockCoordinates WHERE
                    blockX = ? AND
                    blockY = ? AND
                    blockZ = ? AND
                    blockWorld = ?
                ;
                """);

        stmt.setLong(1, location.getBlockX());
        stmt.setLong(2, location.getBlockY());
        stmt.setLong(3, location.getBlockZ());
        stmt.setString(4, location.getWorld().getName());

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            return Optional.empty();
        }

        long blockX = rs.getLong(1), blockY = rs.getLong(2), blockZ = rs.getLong(3);
        String worldName = rs.getString(4), blockTypeName = rs.getString(5);

        rs.close();
        stmt.close();

        Location loc = new Location(Bukkit.getWorld(worldName), blockX, blockY, blockZ);
        BlockType type = BlockType.valueOf(blockTypeName);

        return Optional.of(new PlacedStorage(loc, type));
    }

}
