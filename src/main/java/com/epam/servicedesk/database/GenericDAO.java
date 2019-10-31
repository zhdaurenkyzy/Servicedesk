package com.epam.servicedesk.database;

import com.epam.servicedesk.exception.ConnectionException;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<E, PK> {

    void add(E entity) throws ConnectionException;

    void update(E entity) throws ConnectionException;

    void delete(E entity) throws SQLException, ConnectionException;

    E getById(long id) throws ConnectionException;

    List<E> getAll() throws ConnectionException;
}
