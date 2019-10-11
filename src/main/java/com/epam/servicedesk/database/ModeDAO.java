package com.epam.servicedesk.database;

import com.epam.servicedesk.entity.Mode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.servicedesk.util.ConstantForDAO.*;

public class ModeDAO {
    ConnectionPool connectionPool = ConnectionPool.getUniqueInstance();

    public void add(Mode mode) {
        Connection connection = connectionPool.retrieve();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(ADD_MODE);
            preparedStatement.setString(1, mode.getName());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
    }

    public List<Mode> getAllMode() {
        Connection connection = connectionPool.retrieve();
        List<Mode> modes = new ArrayList<>();
        Mode mode = null;
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(GET_ALL_MODE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                mode = new Mode();
                mode.setId(resultSet.getLong("MODE_ID"));
                mode.setName(resultSet.getString("MODE_NAME"));
                modes.add(mode);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return modes;
    }

    public Mode getById(long id) {
        Connection connection = connectionPool.retrieve();
        Mode mode = new Mode();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(GET_MODE_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                mode.setId(resultSet.getLong("STATUS_ID"));
                mode.setName(resultSet.getString("STATUS_NAME"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return mode;
    }

    public void updateMode(Mode mode) {
        Connection connection = connectionPool.retrieve();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(UPDATE_MODE);
            preparedStatement.setString(1, mode.getName());
            preparedStatement.setLong(2, mode.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
    }
}
