package com.epam.servicedesk.database;

import com.epam.servicedesk.entity.Group;
import com.epam.servicedesk.entity.Project;
import com.epam.servicedesk.entity.User;
import com.epam.servicedesk.enums.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.servicedesk.util.ConstantForDAO.*;

public class UserDAO {

    ConnectionPool connectionPool = ConnectionPool.getUniqueInstance();

    private void userPreparedStatementSet(User user, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPosition());
        preparedStatement.setString(3, user.getPhone());
        preparedStatement.setString(4, user.getMobile());
        preparedStatement.setString(5, user.getMail());
        preparedStatement.setString(6, user.getLogin());
        preparedStatement.setString(7, user.getPassword());
        preparedStatement.setLong(8, user.getUserRole().getId());
    }

    private User userResultSet(User user, ResultSet resultSet) throws SQLException {
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

    public void add(User user) {
        Connection connection = connectionPool.retrieve();
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement(ADD_USER);
            userPreparedStatementSet(user, preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.putback(connection);
    }

    public List<User> getAllUsers() {
        Connection connection = connectionPool.retrieve();
        List<User> users = new ArrayList<>();
        User user = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(GET_ALL_USERS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                userResultSet(user, resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return users;
    }

    public User getById(long id) {
        Connection connection = connectionPool.retrieve();
        User user = new User();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(GET_USER_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userResultSet(user, resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return user;
    }

    public User getByLogin(String login) {
        Connection connection = connectionPool.retrieve();
        User user = new User();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(GET_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userResultSet(user, resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return user;
    }

    public void updateUser(User user) {
        Connection connection = connectionPool.retrieve();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_USER);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPosition());
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.setString(4, user.getMobile());
            preparedStatement.setString(5, user.getMail());
            preparedStatement.setString(6, user.getPassword());
            preparedStatement.setLong(7, user.getUserRole().getId());
            preparedStatement.setLong(8, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.putback(connection);
    }

    public void updateRequestWhenDeleteClient(User user, Connection connection) {
        PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(UPDATE_USER_REQUEST_WHEN_DELETE_CLIENT);
                preparedStatement.setLong(1, user.getId());
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public void deleteUser(User user) {
        Connection connection = connectionPool.retrieve();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            try {
                deleteUserFromGroup(user, connection);
                deleteUserFromProject(user, connection);
                if(user.getUserRole()==Role.CLIENT) {
                    updateRequestWhenDeleteClient(user, connection);
                }
                preparedStatement = connection.prepareStatement(DELETE_USER);
                preparedStatement.setLong(1, user.getId());
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

    public List<User> getUsersByRole() {
        Connection connection = connectionPool.retrieve();
        List<User> users = new ArrayList<>();
        User user = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(GET_USERS_BY_ROLE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                userResultSet(user, resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return users;
    }

    public List<User> getALLClient() {
        Connection connection = connectionPool.retrieve();
        ProjectDAO projectDAO = new ProjectDAO();
        List<User> users = new ArrayList<>();
        User user = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(GET_ALL_CLIENT);
            ResultSet resultSet = preparedStatement.executeQuery();
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
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return users;
    }

    public void deleteUserFromGroup(User user, Connection connection) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(DELETE_USER_FROM_GROUP);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUserByGroupId(long groupId, List<User> users) {
        Connection connection = connectionPool.retrieve();
        try {
            connection.setAutoCommit(false);
            try {
                PreparedStatement preparedStatement = null;
                for (User user:users) {
                    preparedStatement = connection.prepareStatement(DELETE_USER_BY_GROUP_ID );
                    preparedStatement.setLong(1, user.getId());
                    preparedStatement.setLong(2, groupId);
                    preparedStatement.executeUpdate();
                }
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

    public List<User> getAllUserByGroupId(long id) {
        Connection connection = connectionPool.retrieve();
        List<User> users = new ArrayList<>();
        User user = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(GET_ALL_USER_BY_GROUP_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("USER.USER_ID"));
                user.setName(resultSet.getString("USER.USER_NAME"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return users;
    }

    public List<User> getAllClientByProjectId(long id) {
        Connection connection = connectionPool.retrieve();
        List<User> users = new ArrayList<>();
        User user = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(GET_ALL_CLIENT_BY_PROJECT_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("USER.USER_ID"));
                user.setName(resultSet.getString("USER.USER_NAME"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return users;
    }

    public void addUserToProject(User user, Project project) {
        Connection connection = connectionPool.retrieve();
        try {
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement(ALL_USER_TO_PROJECT);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, project.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.putback(connection);
    }

    public Long getProjectIdByUserId(Long id) {
        Connection connection = connectionPool.retrieve();
        PreparedStatement preparedStatement = null;
        Long projectId=null;
        try {
            preparedStatement = connection.prepareStatement(GET_PROJECT_ID_BY_USER_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                projectId=resultSet.getLong("PROJECT_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return projectId;
    }

    public void deleteUserFromProject(User user, Connection connection) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(DELETE_USER_FROM_PROJECT);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserInProject(User user, Project project) {
        Connection connection = connectionPool.retrieve();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(UPDATE_USER_IN_PROJECT);
            preparedStatement.setLong(1, project.getId());
            preparedStatement.setLong(2, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.putback(connection);
    }

    public List<User> getAllUserWithoutGroup(long id) {
        Connection connection = connectionPool.retrieve();
        List<User> users = new ArrayList<>();
        User user = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(GET_ALL_USER_WITHOUT_GROUP);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("USER.USER_ID"));
                user.setName(resultSet.getString("USER.USER_NAME"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return users;
    }

    public void addListUserToGroup(Group group, List<User> users) {
        Connection connection = connectionPool.retrieve();
        try {
            connection.setAutoCommit(false);
        try {
            PreparedStatement preparedStatement = null;
            for (User user: users) {
            preparedStatement = connection.prepareStatement(ADD_LIST_USER_TO_GROUP);
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setLong(2, group.getId());
            preparedStatement.executeUpdate();
            }
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

}
