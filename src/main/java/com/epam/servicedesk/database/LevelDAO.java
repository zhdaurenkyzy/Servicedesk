package com.epam.servicedesk.database;

import com.epam.servicedesk.entity.Level;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.servicedesk.util.ConstantForDAO.*;

public class LevelDAO extends AbstractDAO<Level, Long> {

    @Override
    public String getSelectQuery() {
        return GET_ALL_LEVEL;
    }

    @Override
    public String getQueryById() {
        return GET_LEVEL_BY_ID;
    }

    @Override
    public String getCreateQuery() {
        return ADD_LEVEL;
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    protected void prepareStatementForSet(Level level, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, level.getName());
    }

    @Override
    protected void prepareStatementForUpdate(Level level, PreparedStatement preparedStatement) throws SQLException {

    }

    @Override
    protected Level parseResultSet(Level level, ResultSet resultSet) throws SQLException {
        level.setId(resultSet.getLong("LEVEL_ID"));
        level.setName(resultSet.getString("LEVEL_NAME"));
        return level;
    }

    @Override
    protected Level create() {
        Level level = new Level();
        return level;
    }

    @Override
    public void delete(Level entity) {

    }
}
