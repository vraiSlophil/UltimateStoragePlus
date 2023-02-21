package fr.slophil.ultimatestorageplus.utils;

import fr.slophil.ultimatestorageplus.UltimateStoragePlus;
import fr.slophil.ultimatestorageplus.entities.repository.Repositories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Cette classe permet de se connecter à une base de données SQLite.
 * La base de données sera créée dans le dossier du plugin si celui-ci existe,
 * sinon elle sera créée à la racine du projet.
 */
public class SQLiteConnector {
    private Connection connection;
    private UltimateStoragePlus plugin;

    /**
     * Constructeur de la classe.
     *
     * @param ultimateStoragePlus L'instance du plugin
     */
    public SQLiteConnector(UltimateStoragePlus ultimateStoragePlus) {
        this.plugin = ultimateStoragePlus;
    }

    /**
     * Cette méthode permet de se connecter à la base de données SQLite.
     * Elle vérifie au préalable si le dossier du plugin existe, si oui la base de données sera créée dedans,
     * sinon elle sera créée à la racine du projet.
     *
     * @return true si la connexion a réussi, false sinon
     */
    public boolean connect() {
        try {
            // Vérification de l'existence du dossier du plugin
            Path dbFile = Path.of(plugin.getDataFolder().getAbsolutePath(), "UltimateStoragePlusDatabase.db");
            if (!Files.exists(dbFile.getParent())) {
                Files.createDirectories(dbFile.getParent());
            }
            if (!Files.exists(dbFile)) {
                Files.createFile(dbFile);
            }

            // Connexion à la base de données
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
     * Cette méthode permet de fermer la connexion à la base de données.
     *
     * @return true si la fermeture a réussi, false sinon
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
     * Cette méthode permet de récupérer la connexion à la base de données.
     *
     * @return la connexion à la base de données
     */
    public Connection getConnection() {
        return connection;
    }
}

