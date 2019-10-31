package com.epam.servicedesk.database;

import com.epam.servicedesk.entity.Group;
import com.epam.servicedesk.entity.Project;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.enums.Role;
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
import static com.epam.servicedesk.util.ConstantForApp.CANNOT_UPDATE_ENTITY_IN_MYSQL;
import static com.epam.servicedesk.util.ConstantForDAO.*;

public class UserDAO extends AbstractDAO<User, Long> {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private final ConnectionPool connectionPool = ConnectionPool.getUniqueInstance();

    @Override
    public String getSelectQuery() {
        return GET_ALL_USERS;
    }

    @Override
    public String getQueryById() {
        return GET_USER_BY_ID;
    }

    @Override
    public String getCreateQuery() {
        return ADD_USER;
    }

    @Override
    public String getUpdateQuery() {
        return UPDATE_USER;
    }

    @Override
    protected void prepareStatementForSet(User user, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPosition());
        preparedStatement.setString(3, user.getPhone());
        preparedStatement.setString(4, user.getMobile());
        preparedStatement.setString(5, user.getMail());
        preparedStatement.setString(6, user.getLogin());
        preparedStatement.setString(7, user.getPassword());
        preparedStatement.setLong(8, user.getUserRole().getId());
    }

    @Override
    protected void prepareStatementForUpdate(User user, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPosition());
        preparedStatement.setString(3, user.getPhone());
        preparedStatement.setString(4, user.getMobile());
        preparedStatement.setString(5, user.getMail());
        preparedStatement.setString(6, user.getPassword());
        preparedStatement.setLong(7, user.getUserRole().getId());
        preparedStatement.setLong(8, user.getId());
    }

    @Override
    protected User parseResultSet(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getLong("USER_ID"));
        user.setName(resultSet.getString("USER_NAME"));
        user.setPosition(resultSet.getString("USER_POSITION"));
        user.setPhone(resultSet.getString("USER_PHONE"));
        user.setMobile(resultSet.getString("USER_MOBILE"));
        user.setMail(resultSet.getString("USER_MAIL"));
        user.setLogin(resultSet.getString("USER_LOGIN"));
        user.setPassword(resultSet.getString("USER_PASSWORD"));
        user.setUserRole(Role.getRole(resultSet.getLong("USER_ROLE")));
        return user;
    }

    protected User idNameResultSet(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getLong("USER.USER_ID"));
        user.setName(resultSet.getString("USER.USER_NAME"));
        return user;
    }

    @Override
    protected User create() {
        User user = new User();
        return user;
    }

    @Override
    public void delete(User user) throws SQLException, ConnectionException {
        Connection connection = connectionPool.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {
            connection.setAutoCommit(false);
            deleteUserFromGroup(user, connection);
            deleteUserFromProject(user, connection);
            if (user.getUserRole() == Role.CLIENT) {
                updateRequestWhenDeleteClient(user, connection);
            }
            preparedStatement.setLong(1, user.getId());
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

    public void deleteUserFromGroup(User user, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_FROM_GROUP)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DELETE_ENTITY_BY_MYSQL, e);
        }
    }

    public void deleteUserByGroupId(long groupId, List<User> users) throws SQLException, ConnectionException {
        Connection connection = connectionPool.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_GROUP_ID)) {
            connection.setAutoCommit(false);
            for (User user : users) {
                preparedStatement.setLong(1, user.getId());
                preparedStatement.setLong(2, groupId);
                preparedStatement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DELETE_ENTITY_BY_MYSQL, e);
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            connectionPool.putback(connection);
        }
    }

    public void deleteUserFromProject(User user, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_FROM_PROJECT)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DELETE_ENTITY_BY_MYSQL, e);
        }
    }

    public User getByLogin(String login) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        User user = new User();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    parseResultSet(user, resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DOWNLOAD_ENTITY_FROM_MYSQL, e);
        } finally {
            connectionPool.putback(connection);
        }
        return user;
    }

    public List<User> getUsersByRole() throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        List<User> users = new ArrayList<>();
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS_BY_ROLE);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                user = new User();
                parseResultSet(user, resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DOWNLOAD_LIST_FROM_MYSQL, e);
        } finally {
            connectionPool.putback(connection);
        }
        return users;
    }

    public List<User> getALLClient() throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        ProjectDAO projectDAO = new ProjectDAO();
        List<User> users = new ArrayList<>();
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CLIENT);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("USER_ID"));
                user.setName(resultSet.getString("USER_NAME"));
                user.setPosition(resultSet.getString("USER_POSITION"));
                user.setPhone(resultSet.getString("USER_PHONE"));
                user.setMobile(resultSet.getString("USER_MOBILE"));
                user.setMail(resultSet.getString("USER_MAIL"));
                user.setProject(projectDAO.getById(resultSet.getLong("PROJECT_ID")));
                users.add(user);
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DOWNLOAD_LIST_FROM_MYSQL, e);
        } finally {
            connectionPool.putback(connection);
        }
        return users;
    }

    public List<User> getAllUserByGroupId(long id) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        List<User> users = new ArrayList<>();
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USER_BY_GROUP_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    user = new User();
                    idNameResultSet(user, resultSet);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DOWNLOAD_LIST_FROM_MYSQL, e);
        } finally {
            connectionPool.putback(connection);
        }
        return users;
    }

    public List<User> getAllClientByProjectId(long id) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        List<User> users = new ArrayList<>();
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CLIENT_BY_PROJECT_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    user = new User();
                    idNameResultSet(user, resultSet);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DOWNLOAD_LIST_FROM_MYSQL, e);
        } finally {
            connectionPool.putback(connection);
        }
        return users;
    }

    public List<User> getAllUserWithoutGroup(long id) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        List<User> users = new ArrayList<>();
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USER_WITHOUT_GROUP)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    user = new User();
                    idNameResultSet(user, resultSet);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DOWNLOAD_LIST_FROM_MYSQL, e);
        } finally {
            connectionPool.putback(connection);
        }
        return users;
    }

    public Long getProjectIdByUserId(Long id) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        Long projectId = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PROJECT_ID_BY_USER_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    projectId = resultSet.getLong("PROJECT_ID");
                }
            }
        } catch (SQLException e) {
            LOGGER.error(NOT_FOUND_ENTITY_ID_IN_MYSQL, e);
        } finally {
            connectionPool.putback(connection);
        }
        return projectId;
    }

    public void addUserToProject(User user, Project project) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ALL_USER_TO_PROJECT)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, project.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_ADD_NEW_ENTITY_BY_MYSQL, e);
        } finally {
            connectionPool.putback(connection);
        }
    }

    public void addListUserToGroup(Group group, List<User> users) throws SQLException, ConnectionException {
        Connection connection = connectionPool.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_LIST_USER_TO_GROUP)) {
            connection.setAutoCommit(false);
            for (User user : users) {
                preparedStatement.setLong(1, user.getId());
                preparedStatement.setLong(2, group.getId());
                preparedStatement.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_ADD_NEW_ENTITY_BY_MYSQL, e);
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            connectionPool.putback(connection);
        }
    }

    public void updateUserInProject(User user, Project project) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_IN_PROJECT)) {
            preparedStatement.setLong(1, project.getId());
            preparedStatement.setLong(2, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_UPDATE_ENTITY_IN_MYSQL, e);
        } finally {
            connectionPool.putback(connection);
        }
    }

    public void updateRequestWhenDeleteClient(User user, Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_REQUEST_WHEN_DELETE_CLIENT)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_UPDATE_ENTITY_IN_MYSQL, e);
        }
    }
}
