package fr.slophil.ultimatestorageplus.entities.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface Repository<T> {

    void hydrate(Connection connection);

    void create() throws SQLException;

    void delete() throws SQLException;

    void save(T entity) throws SQLException;

    void edit(T entity) throws SQLException;

    void remove(T entity) throws SQLException;

}
