package com.epam.servicedesk.database;

import com.epam.servicedesk.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.servicedesk.util.ConstantForApp.*;

public abstract class AbstractDAO<E, PK> implements GenericDAO<E, PK> {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private final ConnectionPool connectionPool = ConnectionPool.getUniqueInstance();

    public abstract String getSelectQuery();
    public abstract String getQueryById();
    public abstract String getCreateQuery();
    public abstract String getUpdateQuery();

    protected abstract void prepareStatementForSet(E entity, PreparedStatement preparedStatement) throws SQLException;
    protected abstract void prepareStatementForUpdate(E entity, PreparedStatement preparedStatement) throws SQLException;
    protected abstract E parseResultSet(E entity, ResultSet resultSet) throws SQLException;
    protected abstract E create();

    @Override
    public void add(E entity) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        String sql = getCreateQuery();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            prepareStatementForSet(entity, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_ADD_NEW_ENTITY_BY_MYSQL, e);
        }finally {
            connectionPool.putback(connection);
        }
    }

    @Override
    public void update(E entity) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        String sql = getUpdateQuery();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            prepareStatementForUpdate(entity, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_UPDATE_ENTITY_IN_MYSQL, e);
        }finally {
            connectionPool.putback(connection);
        }
    }

    @Override
    public E getById(long id) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        E entity = create();
        String sql = getQueryById();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    parseResultSet(entity, resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(NOT_FOUND_ENTITY_BY_ID_IN_MYSQL, e);
        }finally {
            connectionPool.putback(connection);
        }
        return entity;
    }

    @Override
    public List<E> getAll() throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        List<E> list = new ArrayList<>();
        E entity = null;
        String sql = getSelectQuery();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()) {
                entity = create();
                parseResultSet(entity, resultSet);
                list.add(entity);
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DOWNLOAD_LIST_FROM_MYSQL, e);
        } finally {
            connectionPool.putback(connection);
        }
        return list;
    }
}
