package com.epam.servicedesk.database;

import com.epam.servicedesk.entity.Project;
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
import static com.epam.servicedesk.util.ConstantForApp.CANNOT_DELETE_ENTITY_BY_MYSQL;
import static com.epam.servicedesk.util.ConstantForDAO.*;

public class ProjectDAO extends AbstractDAO<Project, Long> {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private final ConnectionPool connectionPool = ConnectionPool.getUniqueInstance();

    @Override
    public String getSelectQuery() {
        return null;
    }

    @Override
    public String getQueryById() {
        return GET_PROJECT_BY_ID;
    }

    @Override
    public String getCreateQuery() {
        return ADD_PROJECT;
    }

    @Override
    public String getUpdateQuery() {
        return UPDATE_PROJECT;
    }

    @Override
    protected void prepareStatementForSet(Project project, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, project.getName());
    }

    @Override
    protected void prepareStatementForUpdate(Project project, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, project.getName());
        preparedStatement.setLong(2, project.getId());
    }

    @Override
    protected Project parseResultSet(Project project, ResultSet resultSet) throws SQLException {
        project.setId(resultSet.getLong("PROJECT_ID"));
        project.setName(resultSet.getString("PROJECT_NAME"));
        project.setState(resultSet.getBoolean("PROJECT_STATE"));
        return project;
    }

    @Override
    protected Project create() {
        Project project = new Project();
        return project;
    }

    public Project getByName(String name) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        Project project = new Project();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PROJECT_BY_NAME)) {
            preparedStatement.setString(1, name);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    project.setId(resultSet.getLong("PROJECT_ID"));
                    project.setName(resultSet.getString("PROJECT_NAME"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DOWNLOAD_ENTITY_BY_NAME_FROM_MYSQL, e);
        }finally {
            connectionPool.putback(connection);
        }
        return project;
    }

    public List<Project> getAllProjectByState(boolean state) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        List<Project> projects = new ArrayList<>();
        Project project = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PROJECT_BY_STATE)) {
            preparedStatement.setBoolean(1, state);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    project = new Project();
                    parseResultSet(project, resultSet);
                    projects.add(project);
                }
            }
        }catch(SQLException e){
            LOGGER.error(CANNOT_DOWNLOAD_LIST_FROM_MYSQL, e);
        }finally {
            connectionPool.putback(connection);
        }
        return projects;
    }

    public void changeProjectState(Project project) throws ConnectionException {
        Connection connection = connectionPool.retrieve();
        try(PreparedStatement preparedStatement = connection.prepareStatement(CHANGE_PROJECT_STATE)) {
            preparedStatement.setBoolean(1, project.isState());
            preparedStatement.setLong(2, project.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error(CANNOT_UPDATE_ENTITY_IN_MYSQL, e);
        }finally {
            connectionPool.putback(connection);
        }
    }

    @Override
    public void delete(Project project) throws SQLException, ConnectionException {
        Connection connection = connectionPool.retrieve();
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PROJECT)) {
            connection.setAutoCommit(false);
            deleteUserFromUserProjectAndUser(project, connection);
            preparedStatement.setLong(1, project.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(CANNOT_DELETE_ENTITY_BY_MYSQL, e);
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            connectionPool.putback(connection);
        }
    }

    public void deleteUserFromUserProjectAndUser(Project project, Connection connection) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_FROM_USER_PROJECT_AND_USER)) {
            preparedStatement.setLong(1, project.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error(CANNOT_DELETE_ENTITY_BY_MYSQL, e);
        }
    }
}
