package com.epam.servicedesk.database;

import com.epam.servicedesk.entity.History;
import com.epam.servicedesk.entity.Request;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.enums.Priority;
import com.epam.servicedesk.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.util.ConstantForDAO.*;

public class HistoryDAO {

    private final ConnectionPool connectionPool = ConnectionPool.getUniqueInstance();
    private static final Logger LOGGER = LogManager.getRootLogger();

    private void historyPreparedStatement(Request request, PreparedStatement preparedStatement, Connection connection, String columnName, User user) throws SQLException {
        Map<String, String> valueBeforeUpdateMap = new HashMap<>();
        Map<String, String> valueAfterUpdateMap = new HashMap<>();
        Request oldRequest = getOldRequest(request.getId(), connection);
        initMapBeforeUpdate(valueBeforeUpdateMap, oldRequest);
        initMapAfterUpdate(valueAfterUpdateMap, oldRequest, request);
        preparedStatement.setLong(1, request.getId());
        preparedStatement.setString(2, columnName);
        preparedStatement.setString(3, valueBeforeUpdateMap.get(columnName));
        preparedStatement.setString(4, valueAfterUpdateMap.get(valueBeforeUpdateMap.get(columnName)));
        preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
        preparedStatement.setLong(6, user.getId());
    }

    protected History parseResultSet(History history, ResultSet resultSet) throws SQLException {
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
        return history;
    }

    private void addNewHistory(Request request, Connection connection, String columnName, User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_INTO_HISTORY)) {
            historyPreparedStatement(request, preparedStatement, connection, columnName, user);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_ADD_NEW_ENTITY_BY_MYSQL, e);
        }
    }

    private Request getOldRequest(long id, Connection connection) {
        History history = new History();
        Request oldRequest = new Request();
        history.setRequest(oldRequest);
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_REQUEST_BY_REQUEST_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    parseResultSet(history, resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DOWNLOAD_ENTITY_FROM_MYSQL, e);
        }
        return history.getRequest();
    }

    public List<History> getByRequestId(Request request) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        List<History> histories = new ArrayList<>();
        History history = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_HISTORY_BY_REQUEST_ID)) {
            preparedStatement.setLong(1, request.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DOWNLOAD_LIST_FROM_MYSQL, e);
        }
        connectionPool.putback(connection);
        return histories;
    }

    public void deleteHistoryByRequestId(Request request, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_HISTORY)) {
            preparedStatement.setLong(1, request.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DELETE_ENTITY_BY_MYSQL, e);
        }
    }

    public void fieldsComparisonAndAddHistory(Request request, Connection connection, User user) {
        Request oldRequest = getOldRequest(request.getId(), connection);
        String columnName = EMPTY_STRING;
        if (!(oldRequest.getTheme().equals(request.getTheme()))) {
            columnName = "REQUEST_THEME";
            addNewHistory(request, connection, columnName, user);
        }
        if (!oldRequest.getDescription().equals(request.getDescription())) {
            columnName = "REQUEST_DESCRIPTION";
            addNewHistory(request, connection, columnName, user);
        }
        if (oldRequest.getStatusId() != request.getStatusId()) {
            columnName = "REQUEST_STATUS_ID";
            addNewHistory(request, connection, columnName, user);
        }
        if (oldRequest.getLevelId() != request.getLevelId()) {
            columnName = "REQUEST_LEVEL_ID";
            addNewHistory(request, connection, columnName, user);
        }
        if (oldRequest.getModeId() != request.getModeId()) {
            columnName = "REQUEST_MODE_ID";
            addNewHistory(request, connection, columnName, user);
        }
        if (oldRequest.getPriority() != request.getPriority()) {
            columnName = "REQUEST_PRIORITY_ID";
            addNewHistory(request, connection, columnName, user);
        }
        if (oldRequest.getGroupId() != request.getGroupId()) {
            columnName = "ENGINEER_GROUP_ID";
            addNewHistory(request, connection, columnName, user);
        }
        if (oldRequest.getEngineerId() != request.getEngineerId()) {
            columnName = "ENGINEER_USER_ID";
            addNewHistory(request, connection, columnName, user);
        }
        if (oldRequest.getProjectId() != request.getProjectId()) {
            columnName = "PROJECT_ID";
            addNewHistory(request, connection, columnName, user);
        }
        if (oldRequest.getClientId() != request.getClientId()) {
            columnName = "CLIENT_USER_ID";
            addNewHistory(request, connection, columnName, user);
        }
        if (!(oldRequest.getDecision().equals(request.getDecision()))) {
            columnName = "REQUEST_DECISION";
            addNewHistory(request, connection, columnName, user);
        }
        if (oldRequest.getAuthorOfDecisionId() != request.getAuthorOfDecisionId()) {
            columnName = "REQUEST_AUTHOR_OF_DECISION";
            addNewHistory(request, connection, columnName, user);
        }
        if (oldRequest.getDateOfDecision() != request.getDateOfDecision()) {
            columnName = "REQUEST_DATE_OF_DECISION";
            addNewHistory(request, connection, columnName, user);
        }
    }

    private void initMapBeforeUpdate(Map map, Request oldRequest) {
        map.put("REQUEST_THEME", oldRequest.getTheme());
        map.put("REQUEST_DESCRIPTION", String.valueOf(oldRequest.getDescription()));
        map.put("REQUEST_STATUS_ID", String.valueOf(oldRequest.getStatusId()));
        map.put("REQUEST_LEVEL_ID", String.valueOf(oldRequest.getLevelId()));
        map.put("REQUEST_MODE_ID", String.valueOf(oldRequest.getModeId()));
        map.put("REQUEST_PRIORITY_ID", String.valueOf(oldRequest.getPriority()));
        map.put("ENGINEER_GROUP_ID", String.valueOf(oldRequest.getGroupId()));
        map.put("ENGINEER_USER_ID", String.valueOf(oldRequest.getEngineerId()));
        map.put("PROJECT_ID", String.valueOf(oldRequest.getProjectId()));
        map.put("CLIENT_USER_ID", String.valueOf(oldRequest.getClientId()));
        map.put("REQUEST_DECISION", oldRequest.getDecision());
        map.put("REQUEST_AUTHOR_OF_DECISION", String.valueOf(oldRequest.getAuthorOfDecisionId()));
        map.put("REQUEST_DATE_OF_DECISION", String.valueOf(oldRequest.getDateOfDecision()));
    }

    private void initMapAfterUpdate(Map map, Request oldRequest, Request request) {
        map.put(oldRequest.getTheme(), request.getTheme());
        map.put(String.valueOf(oldRequest.getDescription()), String.valueOf(request.getDescription()));
        map.put(String.valueOf(oldRequest.getStatusId()), String.valueOf(request.getStatusId()));
        map.put(String.valueOf(oldRequest.getLevelId()), String.valueOf(request.getLevelId()));
        map.put(String.valueOf(oldRequest.getModeId()), String.valueOf(request.getModeId()));
        map.put(String.valueOf(oldRequest.getPriority()), String.valueOf(request.getPriority()));
        map.put(String.valueOf(oldRequest.getGroupId()), String.valueOf(request.getGroupId()));
        map.put(String.valueOf(oldRequest.getEngineerId()), String.valueOf(request.getEngineerId()));
        map.put(String.valueOf(oldRequest.getProjectId()), String.valueOf(request.getProjectId()));
        map.put(String.valueOf(oldRequest.getClientId()), String.valueOf(request.getClientId()));
        map.put(oldRequest.getDecision(), request.getDecision());
        map.put(String.valueOf(oldRequest.getAuthorOfDecisionId()), String.valueOf(request.getAuthorOfDecisionId()));
        map.put(String.valueOf(oldRequest.getDateOfDecision()), String.valueOf(request.getDateOfDecision()));
    }
}