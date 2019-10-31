package com.epam.servicedesk.database;

import com.epam.servicedesk.entity.Group;
import com.epam.servicedesk.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.util.ConstantForApp.CANNOT_DELETE_ENTITY_BY_MYSQL;
import static com.epam.servicedesk.util.ConstantForDAO.*;

public class GroupDAO extends AbstractDAO<Group, Long> {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private final ConnectionPool connectionPool = ConnectionPool.getUniqueInstance();

    @Override
    public String getSelectQuery() {
        return GET_ALL_GROUP;
    }

    @Override
    public String getQueryById() {
        return GET_GROUP_BY_ID;
    }

    @Override
    public String getCreateQuery() {
        return ADD_GROUP;
    }

    @Override
    public String getUpdateQuery() {
        return UPDATE_GROUP;
    }

    @Override
    protected void prepareStatementForSet(Group group, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, group.getName());
    }

    @Override
    protected void prepareStatementForUpdate(Group group, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, group.getName());
        preparedStatement.setLong(2, group.getId());
    }

    @Override
    protected Group parseResultSet(Group group, ResultSet resultSet) throws SQLException {
        group.setId(resultSet.getLong("GROUP_ID"));
        group.setName(resultSet.getString("GROUP_NAME"));
        return group;
    }

    @Override
    protected Group create() {
        Group group = new Group();
        return group;
    }

    @Override
    public void delete(Group group) throws SQLException, ConnectionException {
        Connection connection = connectionPool.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_GROUP)) {
            connection.setAutoCommit(false);
            deleteStringsFromUserGroup(group, connection);
            preparedStatement.setLong(1, group.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DELETE_ENTITY_BY_MYSQL, e);
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            connectionPool.putback(connection);
        }
    }

    public Group getByName(String name) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        Group group = new Group();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_GROUP_BY_NAME)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    parseResultSet(group, resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DOWNLOAD_ENTITY_BY_NAME_FROM_MYSQL, e);
        } finally {
            connectionPool.putback(connection);
        }
        return group;
    }

    public void deleteStringsFromUserGroup(Group group, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FROM_USER_GROUP)) {
            preparedStatement.setLong(1, group.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DELETE_ENTITY_BY_MYSQL, e);
        }
    }
}
