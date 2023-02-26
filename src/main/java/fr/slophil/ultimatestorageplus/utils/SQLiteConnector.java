package fr.slophil.ultimatestorageplus.utils;

import fr.slophil.ultimatestorageplus.UltimateStoragePlus;
import fr.slophil.ultimatestorageplus.entities.repository.Repositories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class is used to connect to the SQLite database
 */
public class SQLiteConnector {
    private Connection connection;
    private UltimateStoragePlus plugin;

    public SQLiteConnector(UltimateStoragePlus ultimateStoragePlus) {
        this.plugin = ultimateStoragePlus;
    }

    /**
     * Connect to the SQLite database
     *
     * @return true if the connection is successful, false otherwise
     */
    public boolean connect() {
        try {
            Path dbFile = Path.of(plugin.getDataFolder().getAbsolutePath(), "UltimateStoragePlusDatabase.db");
            if (!Files.exists(dbFile.getParent())) {
                Files.createDirectories(dbFile.getParent());
            }
            if (!Files.exists(dbFile)) {
                Files.createFile(dbFile);
            }
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile.toAbsolutePath());
            for (Repositories repository : Repositories.values()) {
                repository.getRepository().hydrate(connection);
                repository.getRepository().create();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Disconnect from the SQLite database
     *
     * @return true if the disconnection is successful, false otherwise
     */
    public boolean disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get the connection to the SQLite database
     *
     * @return the connection to the SQLite database
     */
    public Connection getConnection() {
        return connection;
    }
}

