package fr.slophil.ultimatestorageplus.entities.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface Repository<T> {

    /**
     * This method is used to hydrate the repository
     *
     * @param connection
     */
    void hydrate(Connection connection);

    /**
     * This method is used to create the table
     *
     * @throws SQLException
     */
    void create() throws SQLException;

    /**
     * This method is used to delete the table
     *
     * @throws SQLException
     */
    void delete() throws SQLException;

    /**
     * This method is used to save an entity
     *
     * @param entity Entity
     * @throws SQLException
     */
    void save(T entity) throws SQLException;

    /**
     * This method is used to edit an entity
     *
     * @param entity Entity
     * @throws SQLException
     */
    void edit(T entity) throws SQLException;

    /**
     * This method is used to remove an entity
     *
     * @param entity Entity
     * @throws SQLException
     */
    void remove(T entity) throws SQLException;

}
