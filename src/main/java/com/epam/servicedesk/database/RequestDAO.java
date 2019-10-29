package com.epam.servicedesk.database;

import com.epam.servicedesk.entity.Request;
import com.epam.servicedesk.entity.RequestState;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.enums.Priority;
import com.epam.servicedesk.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.epam.servicedesk.util.ConstantForApp.*;
import static com.epam.servicedesk.util.ConstantForApp.CANNOT_DOWNLOAD_LIST_FROM_MYSQL;
import static com.epam.servicedesk.util.ConstantForDAO.*;

public class RequestDAO extends AbstractDAO<Request, Long> {
    private final ConnectionPool connectionPool = ConnectionPool.getUniqueInstance();
    private static final Logger LOGGER = LogManager.getRootLogger();

    private static String VIEW_REQUEST_TABLE = "SELECT request.REQUEST_ID, request.REQUEST_THEME, status.STATUS_NAME ," +
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
            "     ON request.REQUEST_AUTHOR_OF_DECISION=author_of_decision.USER_ID ";

    public static final String GET_VIEW_ALL_REQUEST_CLIENT_ID = VIEW_REQUEST_TABLE + " WHERE request.CLIENT_USER_ID = ? ORDER BY request.REQUEST_ID DESC";
    public static final String GET_VIEW_ALL_REQUEST_BY_AUTHOR_OF_CREATION_ID = VIEW_REQUEST_TABLE + " WHERE request.REQUEST_AUTHOR_OF_CREATION = ? ORDER BY request.REQUEST_ID DESC";
    public static final String GET_VIEW_ALL_REQUEST_BY_STATUS_ID = VIEW_REQUEST_TABLE + " WHERE (request.CLIENT_USER_ID=? or request.ENGINEER_USER_ID=? or request.REQUEST_AUTHOR_OF_CREATION= ?) and request.REQUEST_STATUS_ID=? ORDER BY request.REQUEST_ID DESC";
    public static final String GET_VIEW_ALL_REQUEST_BY_ENGINEER_ID = VIEW_REQUEST_TABLE + " WHERE request.ENGINEER_USER_ID = ? ORDER BY request.REQUEST_ID DESC";
    public static final String GET_VIEW_ALL_REQUEST = VIEW_REQUEST_TABLE + " WHERE request.CLIENT_USER_ID=?  or request.ENGINEER_USER_ID =? or request.REQUEST_AUTHOR_OF_CREATION=? ORDER BY request.REQUEST_ID DESC";

    @Override
    public String getSelectQuery() {
        return null;
    }

    @Override
    public String getQueryById() {
        return GET_REQUEST_BY_ID;
    }

    @Override
    public String getCreateQuery() {
        return ADD_REQUEST;
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    protected void prepareStatementForSet(Request request, PreparedStatement preparedStatement) throws SQLException {
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
    }

    @Override
    protected void prepareStatementForUpdate(Request request, PreparedStatement preparedStatement) {

    }

    @Override
    protected Request parseResultSet(Request request, ResultSet resultSet) throws SQLException {
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

    @Override
    protected Request create() {
        Request request = new Request();
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

    public void addDecisionByRequestById(Request request, User user) throws SQLException, ConnectionException {
        Connection connection = connectionPool.retrieve();
            try(PreparedStatement preparedStatement =connection.prepareStatement(ADD_DECISION)) {
                connection.setAutoCommit(false);
                HistoryDAO historyDAO = new HistoryDAO();
                historyDAO.fieldsComparisonAndAddHistory(request, connection, user);
                preparedStatement.setString(1, request.getDecision());
                preparedStatement.setLong(2, request.getAuthorOfDecisionId());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(request.getDateOfDecision()));
                preparedStatement.setLong(4, request.getStatusId());
                preparedStatement.setLong(5, request.getId());
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                LOGGER.error(CANNOT_ADD_NEW_ENTITY_BY_MYSQL, e);
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
                connectionPool.putback(connection);
            }
    }

    public void updateRequestById(Request request, User user) throws SQLException, ConnectionException {
        Connection connection = connectionPool.retrieve();
            try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_REQUEST)) {
                connection.setAutoCommit(false);
                HistoryDAO historyDAO = new HistoryDAO();
                historyDAO.fieldsComparisonAndAddHistory(request, connection, user);
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
                LOGGER.error(CANNOT_UPDATE_ENTITY_IN_MYSQL, e);
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
                connectionPool.putback(connection);
            }
    }

    @Override
    public void delete(Request request) throws SQLException, ConnectionException {
        Connection connection = connectionPool.retrieve();
            try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REQUEST)) {
                connection.setAutoCommit(false);
                HistoryDAO historyDAO = new HistoryDAO();
                historyDAO.deleteHistoryByRequestId(request, connection);
                preparedStatement.setLong(1, request.getId());
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

    public List<RequestState> getAllRequestView() throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        List<RequestState> requestStates = new ArrayList<>();
        RequestState requestState = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FIELDS_OF_ALL_REQUEST);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                requestState = new RequestState();
                requestStateResultSet(requestState, resultSet);
                requestStates.add(requestState);
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DOWNLOAD_LIST_FROM_MYSQL, e);
        }
        connectionPool.putback(connection);
        return requestStates;
    }

    public List<RequestState> getAllByUser(long id, String sql) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        List<RequestState> requestStates = new ArrayList<>();
        RequestState requestState = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    requestState = new RequestState();
                    requestStateResultSet(requestState, resultSet);
                    requestStates.add(requestState);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DOWNLOAD_LIST_FROM_MYSQL, e);
        }
        connectionPool.putback(connection);
        return requestStates;
    }

    public List<RequestState> getAllRequest(long userId, String sql) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        List<RequestState> requestStates = new ArrayList<>();
        RequestState requestState = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, userId);
            preparedStatement.setLong(3, userId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    requestState = new RequestState();
                    requestStateResultSet(requestState, resultSet);
                    requestStates.add(requestState);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DOWNLOAD_LIST_FROM_MYSQL, e);
        }
        connectionPool.putback(connection);
        return requestStates;
    }

    public List<RequestState> getAllRequestByStatus(long userId, long statusId, String sql) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        List<RequestState> requestStates = new ArrayList<>();
        RequestState requestState = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, userId);
            preparedStatement.setLong(3, userId);
            preparedStatement.setLong(4, statusId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    requestState = new RequestState();
                    requestStateResultSet(requestState, resultSet);
                    requestStates.add(requestState);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DOWNLOAD_LIST_FROM_MYSQL, e);
        }
        connectionPool.putback(connection);
        return requestStates;
    }

    public List<RequestState> searchRequestByUser(String column, long userId, long statusId, String searchCriteria, String searchString, long searchId) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        List<RequestState> requestStates = new ArrayList<>();
        RequestState requestState = null;
        try(CallableStatement callableStatement = connection.prepareCall(SEARCH_BY_USER)) {
            callableStatement.setString(1, column);
            callableStatement.setLong(2, userId);
            callableStatement.setLong(3, statusId);
            callableStatement.setString(4, searchCriteria);
            callableStatement.setString(5, searchString);
            callableStatement.setLong(6, searchId);
            try(ResultSet resultSet = callableStatement.executeQuery()) {
                while (resultSet.next()) {
                    requestState = new RequestState();
                    requestStateResultSet(requestState, resultSet);
                    requestStates.add(requestState);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DOWNLOAD_LIST_FROM_MYSQL, e);
        }
        connectionPool.putback(connection);
        return requestStates;
    }

    public List<RequestState> searchRequestByOperator(String searchCriteria, String searchString, long searchId) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        List<RequestState> requestStates = new ArrayList<>();
        RequestState requestState = null;
        try(CallableStatement callableStatement = connection.prepareCall(SEARCH_BY_OPERATOR)) {
            callableStatement.setString(1, searchCriteria);
            callableStatement.setString(2, searchString);
            callableStatement.setLong(3, searchId);
            try(ResultSet resultSet = callableStatement.executeQuery()) {
                while (resultSet.next()) {
                    requestState = new RequestState();
                    requestStateResultSet(requestState, resultSet);
                    requestStates.add(requestState);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DOWNLOAD_LIST_FROM_MYSQL, e);
        }
        connectionPool.putback(connection);
        return requestStates;
    }
}

