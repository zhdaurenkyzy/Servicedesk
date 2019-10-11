package com.epam.servicedesk.database;

import com.epam.servicedesk.entity.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.servicedesk.util.ConstantForDAO.*;

public class LevelDAO {
    ConnectionPool connectionPool = ConnectionPool.getUniqueInstance();

    public void add(Level level) {
        Connection connection = connectionPool.retrieve();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(ADD_LEVEL);
            preparedStatement.setString(1, level.getName());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
    }

    public List<Level> getAllLevel() {
        Connection connection = connectionPool.retrieve();
        List<Level> levels = new ArrayList<>();
        Level level = null;
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(GET_ALL_LEVEL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                level = new Level();
                level.setId(resultSet.getLong("LEVEL_ID"));
                level.setName(resultSet.getString("LEVEL_NAME"));
                levels.add(level);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return levels;
    }

    public Level getById(long id) {
        Connection connection = connectionPool.retrieve();
        Level level = new Level();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(GET_LEVEL_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                level.setId(resultSet.getLong("LEVEL_ID"));
                level.setName(resultSet.getString("LEVEL_NAME"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return level;
    }
}
