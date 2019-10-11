package com.epam.servicedesk.database;

import com.epam.servicedesk.entity.Project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.servicedesk.util.ConstantForDAO.*;

public class ProjectDAO {

    ConnectionPool connectionPool = ConnectionPool.getUniqueInstance();

    public void add(Project project) {
        Connection connection = connectionPool.retrieve();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(ADD_PROJECT);
            preparedStatement.setString(1, project.getName());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
    }

    public Project getById(long id) {
        Connection connection = connectionPool.retrieve();
        Project project = new Project();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(GET_PROJECT_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                project.setId(resultSet.getLong("PROJECT_ID"));
                project.setName(resultSet.getString("PROJECT_NAME"));
                project.setState(resultSet.getBoolean("PROJECT_STATE"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return project;
    }

    public Project getByName(String name) {
        Connection connection = connectionPool.retrieve();
        Project project = new Project();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(GET_PROJECT_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                project.setId(resultSet.getLong("PROJECT_ID"));
                project.setName(resultSet.getString("PROJECT_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return project;
    }

    public List<Project> getAllProjectByState(boolean state) {
        Connection connection = connectionPool.retrieve();
        List<Project> projects = new ArrayList<>();
        Project project = null;
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(GET_ALL_PROJECT_BY_STATE);
            preparedStatement.setBoolean(1, state);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                project = new Project();
                project.setId(resultSet.getLong("PROJECT_ID"));
                project.setName(resultSet.getString("PROJECT_NAME"));
                project.setState(resultSet.getBoolean("PROJECT_STATE"));
                projects.add(project);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return projects;
    }

    public void updateProject(Project project){
        Connection connection = connectionPool.retrieve();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(UPDATE_PROJECT);
            preparedStatement.setString(1, project.getName());
            preparedStatement.setLong(2, project.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
    }

    public void changeProjectState(Project project) {
        Connection connection = connectionPool.retrieve();
        PreparedStatement preparedStatement = null;
        try{

            preparedStatement = connection.prepareStatement(CHANGE_PROJECT_STATE);
            preparedStatement.setBoolean(1, project.isState());
            preparedStatement.setLong(2, project.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
    }

    public void deleteProject(Project project) {
        Connection connection = connectionPool.retrieve();
        PreparedStatement preparedStatement = null;
        try{
            deleteUserFromUserProjectAndUser(project, connection);
            preparedStatement = connection.prepareStatement(DELETE_PROJECT);
            preparedStatement.setLong(1, project.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
    }

    public void deleteUserFromUserProjectAndUser(Project project, Connection connection) {
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(DELETE_USER_FROM_USER_PROJECT_AND_USER);
            preparedStatement.setLong(1, project.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
