package com.epam.servicedesk.database;

import com.epam.servicedesk.entity.Request;
import com.epam.servicedesk.entity.RequestState;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.enums.Priority;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.epam.servicedesk.util.ConstantForDAO.*;

public class RequestDAO {
    private static String view = "SELECT request.REQUEST_ID, request.REQUEST_THEME, status.STATUS_NAME ," +
            " request.REQUEST_PRIORITY_ID, engineer_group.GROUP_NAME, engineer.USER_NAME as ENGINEER_NAME, project.PROJECT_NAME, client.USER_NAME as CLIENT_NAME," +
            " author_of_creation.USER_NAME as AUTHOR_OF_CREATION_NAME, request.REQUEST_DATE_OF_CREATION, author_of_decision.USER_NAME as AUTHOR_OF_DECISION_NAME , request.REQUEST_DATE_OF_DECISION " +
            " FROM  request  LEFT OUTER JOIN status" +
            "     ON  request.REQUEST_STATUS_ID =status.STATUS_ID " +
            "   LEFT OUTER JOIN engineer_group " +
            "     ON  request.ENGINEER_GROUP_ID=engineer_group.GROUP_ID " +
            "     LEFT OUTER JOIN user as engineer " +
            "     ON request.ENGINEER_USER_ID=engineer.USER_ID " +
            "     LEFT OUTER JOIN project " +
            "     ON  request.PROJECT_ID=project.PROJECT_ID " +
            " LEFT OUTER JOIN user as client" +
            "     ON request.CLIENT_USER_ID=client.USER_ID " +
            "     LEFT OUTER JOIN user as author_of_creation " +
            "     ON request.REQUEST_AUTHOR_OF_CREATION=author_of_creation.USER_ID " +
            "     LEFT OUTER JOIN user as author_of_decision " +
            "     ON request.REQUEST_AUTHOR_OF_DECISION=author_of_decision.USER_ID";

    public static final String GET_VIEW_ALL_REQUEST_CLIENT_ID = view + " WHERE request.CLIENT_USER_ID = ?";
    public static final String GET_VIEW_ALL_REQUEST_BY_AUTHOR_OF_CREATION_ID = view + " WHERE request.REQUEST_AUTHOR_OF_CREATION = ?";
    public static final String GET_VIEW_ALL_REQUEST_BY_STATUS_ID = view + " WHERE (request.CLIENT_USER_ID=? or request.ENGINEER_USER_ID=? or request.REQUEST_AUTHOR_OF_CREATION= ?) and request.REQUEST_STATUS_ID=?";
    public static final String GET_VIEW_ALL_REQUEST_BY_ENGINEER_ID = view + " WHERE request.ENGINEER_USER_ID = ?";
    public static final String GET_VIEW_ALL_REQUEST = view + " WHERE request.CLIENT_USER_ID=?  or request.ENGINEER_USER_ID =? or request.REQUEST_AUTHOR_OF_CREATION=?";

    ConnectionPool connectionPool = ConnectionPool.getUniqueInstance();

    private void preparedStatementSet(Request request, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, request.getTheme());
        preparedStatement.setString(2, request.getDescription());
        preparedStatement.setLong(3, request.getStatusId());
        preparedStatement.setLong(4, request.getLevelId());
        preparedStatement.setLong(5, request.getModeId());
        preparedStatement.setLong(6, request.getPriority().getId());
        preparedStatement.setLong(7, request.getGroupId());
        preparedStatement.setLong(8, request.getEngineerId());
        preparedStatement.setLong(9, request.getProjectId());
        preparedStatement.setLong(10, request.getClientId());
        preparedStatement.setLong(11, request.getAuthorOfCreationId());
        preparedStatement.setTimestamp(12, Timestamp.valueOf(request.getDateOfCreation()));
        preparedStatement.setString(13, request.getDecision());
        preparedStatement.setLong(14, request.getAuthorOfDecisionId());
        if (request.getDateOfDecision() != null) {
            preparedStatement.setTimestamp(15, Timestamp.valueOf(request.getDateOfDecision()));
        }
    }

    private Request requestResultSet(Request request, ResultSet resultSet) throws SQLException {
        request.setId(resultSet.getLong("REQUEST_ID"));
        request.setTheme(resultSet.getString("REQUEST_THEME"));
        request.setDescription(resultSet.getString("REQUEST_DESCRIPTION"));
        request.setStatusId(resultSet.getLong("REQUEST_STATUS_ID"));
        request.setLevelId(resultSet.getLong("REQUEST_LEVEL_ID"));
        request.setModeId(resultSet.getLong("REQUEST_MODE_ID"));
        request.setPriority(Priority.getPriority(resultSet.getLong("REQUEST_PRIORITY_ID")));
        request.setGroupId(resultSet.getLong("ENGINEER_GROUP_ID"));
        request.setEngineerId(resultSet.getLong("ENGINEER_USER_ID"));
        request.setProjectId(resultSet.getLong("PROJECT_ID"));
        request.setClientId(resultSet.getLong("CLIENT_USER_ID"));
        request.setAuthorOfCreationId(resultSet.getLong("REQUEST_AUTHOR_OF_CREATION"));
        request.setDateOfCreation(LocalDateTime.from(resultSet.getTimestamp("REQUEST_DATE_OF_CREATION").toLocalDateTime()));
        request.setDecision(resultSet.getString("REQUEST_DECISION"));
        request.setAuthorOfDecisionId(resultSet.getLong("REQUEST_AUTHOR_OF_DECISION"));
        if (resultSet.getTimestamp("REQUEST_DATE_OF_DECISION") != null) {
            request.setDateOfDecision(LocalDateTime.from(resultSet.getTimestamp("REQUEST_DATE_OF_DECISION").toLocalDateTime()));
        }
        return request;
    }
    private RequestState requestStateResultSet(RequestState requestState, ResultSet resultSet) throws SQLException {
        requestState.setRequestId(resultSet.getLong("REQUEST_ID"));
        requestState.setRequestTheme(resultSet.getString("REQUEST_THEME"));
        requestState.setStatusName(resultSet.getString("STATUS_NAME"));
        requestState.setRequestPriorityId(resultSet.getLong("REQUEST_PRIORITY_ID"));
        requestState.setGroupName(resultSet.getString("GROUP_NAME"));
        requestState.setEngineerName(resultSet.getString("ENGINEER_NAME"));
        requestState.setProjectName(resultSet.getString("PROJECT_NAME"));
        requestState.setClientName(resultSet.getString("CLIENT_NAME"));
        requestState.setAuthorCreationName(resultSet.getString("AUTHOR_OF_CREATION_NAME"));
        requestState.setRequestDateOfCreation(LocalDateTime.from(resultSet.getTimestamp("REQUEST_DATE_OF_CREATION").toLocalDateTime()));
        requestState.setAuthorOfDecisionName(resultSet.getString("AUTHOR_OF_DECISION_NAME"));
        if (resultSet.getTimestamp("REQUEST_DATE_OF_DECISION") != null) {
            requestState.setRequestDateOfDecision(LocalDateTime.from(resultSet.getTimestamp("REQUEST_DATE_OF_DECISION").toLocalDateTime()));
       }
        return requestState;
    }

    public void addNewRequest(Request request) {
        Connection connection = connectionPool.retrieve();
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement(ADD_REQUEST);
            preparedStatement.setString(1, request.getTheme());
            preparedStatement.setString(2, request.getDescription());
            preparedStatement.setLong(3, request.getStatusId());
            preparedStatement.setLong(4, request.getLevelId());
            preparedStatement.setLong(5, request.getModeId());
            preparedStatement.setLong(6, request.getPriority().getId());
            preparedStatement.setLong(7, request.getGroupId());
            preparedStatement.setLong(8, request.getEngineerId());
            preparedStatement.setLong(9, request.getProjectId());
            preparedStatement.setLong(10, request.getClientId());
            preparedStatement.setLong(11, request.getAuthorOfCreationId());
            preparedStatement.setTimestamp(12, Timestamp.valueOf(request.getDateOfCreation()));
            preparedStatement.setString(13, request.getDecision());
            preparedStatement.setLong(14, request.getAuthorOfDecisionId());
            preparedStatement.setTimestamp(15, null);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.putback(connection);
    }

    public Request getRequestById(long id) {
        Connection connection = connectionPool.retrieve();
        Request request = new Request();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(GET_REQUEST_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                requestResultSet(request, resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return request;
    }

    public void addDecisionByRequestById(Request request, User user) {
        HistoryDAO historyDAO = new HistoryDAO();
        Connection connection = connectionPool.retrieve();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
        try {
            historyDAO.fieldComparisonAndAddHistory(request, connection, user);
            preparedStatement = connection.prepareStatement(ADD_DECISION);
            preparedStatement.setString(1, request.getDecision());
            preparedStatement.setLong(2, request.getAuthorOfDecisionId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(request.getDateOfDecision()));
            preparedStatement.setLong(4, request.getStatusId());
            preparedStatement.setLong(5, request.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.putback(connection);
        }
    }

    public void updateRequestById(Request request, User user) {
        HistoryDAO historyDAO = new HistoryDAO();
        Connection connection = connectionPool.retrieve();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            try {
                historyDAO.fieldComparisonAndAddHistory(request, connection, user);
                preparedStatement = connection.prepareStatement(UPDATE_REQUEST);
                preparedStatement.setString(1, request.getTheme());
                preparedStatement.setString(2, request.getDescription());
                preparedStatement.setLong(3, request.getStatusId());
                preparedStatement.setLong(4, request.getLevelId());
                preparedStatement.setLong(5, request.getModeId());
                preparedStatement.setLong(6, request.getPriority().getId());
                preparedStatement.setLong(7, request.getGroupId());
                preparedStatement.setLong(8, request.getEngineerId());
                preparedStatement.setLong(9, request.getProjectId());
                preparedStatement.setLong(10, request.getClientId());
                preparedStatement.setString(11, request.getDecision());
                if (request.getAuthorOfDecisionId() != 0) {
                    preparedStatement.setLong(12, request.getAuthorOfDecisionId());
                }
                else preparedStatement.setLong(12, 0);
                if (request.getDateOfDecision() != null) {
                    preparedStatement.setTimestamp(13, Timestamp.valueOf(request.getDateOfDecision()));
                }
                else preparedStatement.setTimestamp(13, null);
                preparedStatement.setLong(14, request.getId());
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.putback(connection);
        }
    }

    public void deleteRequestById(Request request) {
        Connection connection = connectionPool.retrieve();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            try {
                HistoryDAO historyDAO = new HistoryDAO();
                historyDAO.deleteHistoryByRequestId(request, connection);
                preparedStatement = connection.prepareStatement(DELETE_REQUEST);
                preparedStatement.setLong(1, request.getId());
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.putback(connection);
        }
    }

    public List<RequestState> getAllRequestView() {
        Connection connection = connectionPool.retrieve();
        List<RequestState> requestStates = new ArrayList<>();
        RequestState requestState = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(GET_ALL_FIELDS_OF_ALL_REQUEST);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                requestState = new RequestState();
                requestStateResultSet(requestState, resultSet);
                requestStates.add(requestState);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return requestStates;
    }
    public List<RequestState> getAll(long id, String sql) {
        Connection connection = connectionPool.retrieve();
        List<RequestState> requestStates = new ArrayList<>();
        RequestState requestState = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                requestState = new RequestState();
                requestStateResultSet(requestState, resultSet);
                requestStates.add(requestState);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return requestStates;
    }


    public List<RequestState> getAllRequest(long userId, String sql) {
        Connection connection = connectionPool.retrieve();
        List<RequestState> requestStates = new ArrayList<>();
        RequestState requestState = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, userId);
            preparedStatement.setLong(3, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                requestState = new RequestState();
                requestStateResultSet(requestState, resultSet);
                requestStates.add(requestState);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return requestStates;
    }

    public List<RequestState> getAllRequestSts(long userId,long statusId, String sql) {
        Connection connection = connectionPool.retrieve();
        List<RequestState> requestStates = new ArrayList<>();
        RequestState requestState = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, userId);
            preparedStatement.setLong(3, userId);
            preparedStatement.setLong(4, statusId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                requestState = new RequestState();
                requestStateResultSet(requestState, resultSet);
                requestStates.add(requestState);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return requestStates;
    }
}

