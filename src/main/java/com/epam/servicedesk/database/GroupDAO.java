package com.epam.servicedesk.database;

import com.epam.servicedesk.entity.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.servicedesk.util.ConstantForDAO.*;

public class GroupDAO {

    ConnectionPool connectionPool = ConnectionPool.getUniqueInstance();

    public void add(Group group){
        Connection connection = connectionPool.retrieve();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(ADD_GROUP);
            preparedStatement.setString(1, group.getName());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
    }

    public Group getByName(String name) {
        Connection connection = connectionPool.retrieve();
        Group group = new Group();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(GET_GROUP_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                group.setId(resultSet.getLong("GROUP_ID"));
                group.setName(resultSet.getString("GROUP_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return group;
    }

    public List<Group> getAllGroup(){
        Connection connection = connectionPool.retrieve();
        List<Group> groups = new ArrayList<>();
        Group group = null;
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(GET_ALL_GROUP);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                group = new Group();
                group.setId(resultSet.getLong("GROUP_ID"));
                group.setName(resultSet.getString("GROUP_NAME"));
                groups.add(group);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return groups;
    }

    public Group getById(long id){
        Connection connection = connectionPool.retrieve();
        Group group = new Group();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(GET_GROUP_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                group.setId(resultSet.getLong("GROUP_ID"));
                group.setName(resultSet.getString("GROUP_NAME"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return group;
    }

    public void updateGroup(Group group){
        Connection connection = connectionPool.retrieve();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(UPDATE_GROUP);
            preparedStatement.setString(1, group.getName());
            preparedStatement.setLong(2, group.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
    }

    public void deleteGroup(Group group){
        Connection connection = connectionPool.retrieve();
        PreparedStatement preparedStatement = null;
        try{
            deleteStringsFromUserGroup(group, connection);
            preparedStatement = connection.prepareStatement(DELETE_GROUP);
            preparedStatement.setLong(1, group.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
    }

    public void deleteStringsFromUserGroup(Group group, Connection connection){
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(DELETE_FROM_USER_GROUP);
            preparedStatement.setLong(1, group.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
