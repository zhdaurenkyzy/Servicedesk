package com.epam.servicedesk.database;

import com.epam.servicedesk.entity.History;
import com.epam.servicedesk.entity.Request;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.enums.Priority;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.epam.servicedesk.util.ConstantForDAO.*;

public class HistoryDAO {

    ConnectionPool connectionPool = ConnectionPool.getUniqueInstance();

    private Request getOldRequest(long id, Connection connection) {
        History history = new History();
        Request oldRequest = new Request();
        history.setRequest(oldRequest);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(GET_REQUEST_BY_REQUEST_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                history.getRequest().setId(resultSet.getLong("REQUEST_ID"));
                history.getRequest().setTheme(resultSet.getString("REQUEST_THEME"));
                history.getRequest().setDescription(resultSet.getString("REQUEST_DESCRIPTION"));
                history.getRequest().setStatusId(resultSet.getLong("REQUEST_STATUS_ID"));
                history.getRequest().setLevelId(resultSet.getLong("REQUEST_LEVEL_ID"));
                history.getRequest().setModeId(resultSet.getLong("REQUEST_MODE_ID"));
                history.getRequest().setPriority(Priority.getPriority(resultSet.getLong("REQUEST_PRIORITY_ID")));
                history.getRequest().setGroupId(resultSet.getLong("ENGINEER_GROUP_ID"));
                history.getRequest().setEngineerId(resultSet.getLong("ENGINEER_USER_ID"));
                history.getRequest().setProjectId(resultSet.getLong("PROJECT_ID"));
                history.getRequest().setClientId(resultSet.getLong("CLIENT_USER_ID"));
                history.getRequest().setAuthorOfCreationId(resultSet.getLong("REQUEST_AUTHOR_OF_CREATION"));
                history.getRequest().setDateOfCreation(LocalDateTime.from(resultSet.getTimestamp("REQUEST_DATE_OF_CREATION").toLocalDateTime()));

                history.getRequest().setDecision(resultSet.getString("REQUEST_DECISION"));

                history.getRequest().setAuthorOfDecisionId(resultSet.getLong("REQUEST_AUTHOR_OF_DECISION"));
                if (resultSet.getTimestamp("REQUEST_DATE_OF_DECISION") != null) {
                    history.getRequest().setDateOfDecision(LocalDateTime.from(resultSet.getTimestamp("REQUEST_DATE_OF_DECISION").toLocalDateTime()));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history.getRequest();
    }

    private void historyPreparedStatement(Request request, PreparedStatement preparedStatement, Connection connection, String columnName, User user) throws SQLException {
        HashMap<String, String> columnNameAndValueBeforeUpdate = new HashMap<>();
        Request oldRequest = getOldRequest(request.getId(), connection);
        {
            columnNameAndValueBeforeUpdate.put("REQUEST_THEME", oldRequest.getTheme());
            columnNameAndValueBeforeUpdate.put("REQUEST_DESCRIPTION", String.valueOf(oldRequest.getDescription()));
            columnNameAndValueBeforeUpdate.put("REQUEST_STATUS_ID", String.valueOf(oldRequest.getStatusId()));
            columnNameAndValueBeforeUpdate.put("REQUEST_LEVEL_ID", String.valueOf(oldRequest.getLevelId()));
            columnNameAndValueBeforeUpdate.put("REQUEST_MODE_ID", String.valueOf(oldRequest.getModeId()));
            columnNameAndValueBeforeUpdate.put("REQUEST_PRIORITY_ID", String.valueOf(oldRequest.getPriority()));
            columnNameAndValueBeforeUpdate.put("ENGINEER_GROUP_ID", String.valueOf(oldRequest.getGroupId()));
            columnNameAndValueBeforeUpdate.put("ENGINEER_USER_ID", String.valueOf(oldRequest.getEngineerId()));
            columnNameAndValueBeforeUpdate.put("PROJECT_ID", String.valueOf(oldRequest.getProjectId()));
            columnNameAndValueBeforeUpdate.put("CLIENT_USER_ID", String.valueOf(oldRequest.getClientId()));
            columnNameAndValueBeforeUpdate.put("REQUEST_DECISION", oldRequest.getDecision());
            columnNameAndValueBeforeUpdate.put("REQUEST_AUTHOR_OF_DECISION", String.valueOf(oldRequest.getAuthorOfDecisionId()));
            columnNameAndValueBeforeUpdate.put("REQUEST_DATE_OF_DECISION", String.valueOf(oldRequest.getDateOfDecision()));

        }
        HashMap<String, String> valueAfterUpdate = new HashMap<>();
        {
            valueAfterUpdate.put(oldRequest.getTheme(), request.getTheme());
            valueAfterUpdate.put(String.valueOf(oldRequest.getDescription()), String.valueOf(request.getDescription()));
            valueAfterUpdate.put(String.valueOf(oldRequest.getStatusId()), String.valueOf(request.getStatusId()));
            valueAfterUpdate.put(String.valueOf(oldRequest.getLevelId()), String.valueOf(request.getLevelId()));
            valueAfterUpdate.put(String.valueOf(oldRequest.getModeId()), String.valueOf(request.getModeId()));
            valueAfterUpdate.put(String.valueOf(oldRequest.getPriority()), String.valueOf(request.getPriority()));
            valueAfterUpdate.put(String.valueOf(oldRequest.getGroupId()), String.valueOf(request.getGroupId()));
            valueAfterUpdate.put(String.valueOf(oldRequest.getEngineerId()), String.valueOf(request.getEngineerId()));
            valueAfterUpdate.put(String.valueOf(oldRequest.getProjectId()), String.valueOf(request.getProjectId()));
            valueAfterUpdate.put(String.valueOf(oldRequest.getClientId()), String.valueOf(request.getClientId()));
            valueAfterUpdate.put(oldRequest.getDecision(), request.getDecision());
            valueAfterUpdate.put(String.valueOf(oldRequest.getAuthorOfDecisionId()), String.valueOf(request.getAuthorOfDecisionId()));
            valueAfterUpdate.put(String.valueOf(oldRequest.getDateOfDecision()), String.valueOf(request.getDateOfDecision()));
        }
        preparedStatement.setLong(1, request.getId());
        preparedStatement.setString(2, columnName);
        preparedStatement.setString(3, columnNameAndValueBeforeUpdate.get(columnName));
        preparedStatement.setString(4, valueAfterUpdate.get(columnNameAndValueBeforeUpdate.get(columnName)));
        preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
        preparedStatement.setLong(6, user.getId());
    }

    public void fieldComparisonAndAddHistory(Request request, Connection connection, User user) {
        Request oldRequest = getOldRequest(request.getId(), connection);
        PreparedStatement preparedStatement = null;
        String columnName = "";
        if ((oldRequest.getTheme().equals(request.getTheme())) == false) {
            columnName = "REQUEST_THEME";
            addNewHistory(request, connection, columnName, user);
        }
        if (!oldRequest.getDescription().equals(request.getDescription())) {
            columnName = "REQUEST_DESCRIPTION";
            addNewHistory(request, connection, columnName, user);
        }
        if (oldRequest.getStatusId()!=request.getStatusId()) {
            columnName = "REQUEST_STATUS_ID";
            addNewHistory(request, connection, columnName, user);
        }
        if (oldRequest.getLevelId()!=request.getLevelId()) {
            columnName = "REQUEST_LEVEL_ID";
            addNewHistory(request, connection, columnName, user);
        }
        if (oldRequest.getModeId()!=request.getModeId()) {
            columnName = "REQUEST_MODE_ID";
            addNewHistory(request, connection, columnName, user);
        }
        if (oldRequest.getPriority()!=request.getPriority()) {
            columnName = "REQUEST_PRIORITY_ID";
            addNewHistory(request, connection, columnName, user);
        }
        if (oldRequest.getGroupId()!=request.getGroupId()) {
            columnName = "ENGINEER_GROUP_ID";
            addNewHistory(request, connection, columnName, user);
        }
        if (oldRequest.getEngineerId()!=request.getEngineerId()) {
            columnName = "ENGINEER_USER_ID";
            addNewHistory(request, connection, columnName, user);
        }
        if (oldRequest.getProjectId()!=request.getProjectId()) {
            columnName = "PROJECT_ID";
            addNewHistory(request, connection, columnName, user);
        }
        if (oldRequest.getClientId()!=request.getClientId()) {
            columnName = "CLIENT_USER_ID";
            addNewHistory(request, connection, columnName, user);
        }

            if ((oldRequest.getDecision().equals(request.getDecision()))==false) {
                columnName = "REQUEST_DECISION";
                addNewHistory(request, connection, columnName, user);
            }

        if (oldRequest.getAuthorOfDecisionId()!=request.getAuthorOfDecisionId()) {
            columnName = "REQUEST_AUTHOR_OF_DECISION";
            addNewHistory(request, connection, columnName, user);
        }
        if (request.getDateOfDecision()!=null) {
            columnName = "REQUEST_DATE_OF_DECISION";
            addNewHistory(request, connection, columnName, user);
        }

    }

    private void addNewHistory(Request request, Connection connection, String columnName, User user) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(ADD_INTO_HISTORY);
            historyPreparedStatement(request, preparedStatement, connection, columnName, user);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<History> getByRequestId(Request request) {
        Connection connection = connectionPool.retrieve();
        List<History> histories = new ArrayList<>();
        History history = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(GET_HISTORY_BY_REQUEST_ID);
            preparedStatement.setLong(1, request.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                history = new History();
                history.setId(resultSet.getLong("HISTORY_ID"));
                history.setRequest(request);
                history.getRequest().setId(resultSet.getLong("REQUEST_ID"));
                history.setColumnName(resultSet.getString("COLUMN_NAME"));
                history.setColumnValueBefore(resultSet.getString("COLUMN_VALUE_BEFORE"));
                history.setColumnValueAfter(resultSet.getString("COLUMN_VALUE_AFTER"));
                history.setDateOfChange(LocalDateTime.from(resultSet.getTimestamp("DATE_OF_CHANGE").toLocalDateTime()));
                history.setUserId(resultSet.getLong("USER_ID"));
                histories.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return histories;
    }

    public void deleteHistoryByRequestId(Request request, Connection connection) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(DELETE_HISTORY);
            preparedStatement.setLong(1, request.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
