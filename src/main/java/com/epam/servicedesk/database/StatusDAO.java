package com.epam.servicedesk.database;

import com.epam.servicedesk.entity.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.servicedesk.util.ConstantForDAO.*;

public class StatusDAO {
    ConnectionPool connectionPool = ConnectionPool.getUniqueInstance();

    public void add(Status status) {
        Connection connection = connectionPool.retrieve();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(ADD_STATUS);
            preparedStatement.setString(1, status.getName());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
    }

    public List<Status> getAllStatus() {
        Connection connection = connectionPool.retrieve();
        List<Status> statuses = new ArrayList<>();
        Status status = null;
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(GET_ALL_STATUS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                status = new Status();
                status.setId(resultSet.getLong("STATUS_ID"));
                status.setName(resultSet.getString("STATUS_NAME"));
                statuses.add(status);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return statuses;
    }

    public Status getById(long id) {
        Connection connection = connectionPool.retrieve();
        Status status = new Status();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(GET_STATUS_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                status.setId(resultSet.getLong("STATUS_ID"));
                status.setName(resultSet.getString("STATUS_NAME"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return status;
    }
}
