package com.epam.servicedesk.database;

import com.epam.servicedesk.entity.Mode;
import com.epam.servicedesk.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.servicedesk.util.ConstantForApp.CANNOT_DELETE_ENTITY_BY_MYSQL;
import static com.epam.servicedesk.util.ConstantForApp.CANNOT_DOWNLOAD_ENTITY_BY_NAME_FROM_MYSQL;
import static com.epam.servicedesk.util.ConstantForDAO.*;

public class ModeDAO extends AbstractDAO<Mode, Long> {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private final ConnectionPool connectionPool = ConnectionPool.getUniqueInstance();

    @Override
    public String getSelectQuery() {
        return GET_ALL_MODE;
    }

    @Override
    public String getQueryById() {
        return GET_MODE_BY_ID;
    }

    @Override
    public String getCreateQuery() {
        return ADD_MODE;
    }

    @Override
    public String getUpdateQuery() {
        return UPDATE_MODE;
    }

    @Override
    protected void prepareStatementForSet(Mode mode, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, mode.getName());
    }

    @Override
    protected void prepareStatementForUpdate(Mode mode, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, mode.getName());
        preparedStatement.setLong(2, mode.getId());
    }

    @Override
    protected Mode parseResultSet(Mode mode, ResultSet resultSet) throws SQLException {
        mode.setId(resultSet.getLong("MODE_ID"));
        mode.setName(resultSet.getString("MODE_NAME"));
        return mode;
    }

    @Override
    protected Mode create() {
        Mode mode = new Mode();
        return mode;
    }

    @Override
    public void delete(Mode mode) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_MODE)) {
            preparedStatement.setLong(1, mode.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DELETE_ENTITY_BY_MYSQL, e);
        } finally {
            connectionPool.putback(connection);
        }
    }

    public Mode getByName(String name) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        Mode mode = create();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_MODE_BY_NAME)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    mode.setId(resultSet.getLong("MODE_ID"));
                    mode.setName(resultSet.getString("MODE_NAME"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DOWNLOAD_ENTITY_BY_NAME_FROM_MYSQL, e);
        } finally {
            connectionPool.putback(connection);
        }
        return mode;
    }
}
