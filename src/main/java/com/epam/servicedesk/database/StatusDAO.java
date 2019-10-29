package com.epam.servicedesk.database;

import com.epam.servicedesk.entity.Status;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.servicedesk.util.ConstantForDAO.*;

public class StatusDAO extends AbstractDAO<Status, Long> {

    @Override
    public String getSelectQuery() {
        return GET_ALL_STATUS;
    }

    @Override
    public String getQueryById() {
        return GET_STATUS_BY_ID;
    }

    @Override
    public String getCreateQuery() {
        return ADD_STATUS;
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    protected void prepareStatementForSet(Status status, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, status.getName());
    }

    @Override
    protected void prepareStatementForUpdate(Status status, PreparedStatement preparedStatement) throws SQLException {

    }

    @Override
    protected Status parseResultSet(Status status, ResultSet resultSet) throws SQLException {
        status.setId(resultSet.getLong("STATUS_ID"));
        status.setName(resultSet.getString("STATUS_NAME"));
        return status;
    }

    @Override
    protected Status create() {
        Status status = new Status();
        return status;
    }

    @Override
    public void delete(Status status) {

    }
}
