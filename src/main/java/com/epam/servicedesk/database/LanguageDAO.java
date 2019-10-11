package com.epam.servicedesk.database;

import com.epam.servicedesk.entity.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.servicedesk.util.ConstantForDAO.*;

public class LanguageDAO {
    ConnectionPool connectionPool = ConnectionPool.getUniqueInstance();

    public void add(Language language) {
        Connection connection = connectionPool.retrieve();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(ADD_LANGUAGE);
            preparedStatement.setString(1, language.getName());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
    }

    public List<Language> getAllLanguage() {
        Connection connection = connectionPool.retrieve();
        List<Language> languages = new ArrayList<>();
        Language language = null;
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(GET_ALL_LANGUAGE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                language = new Language();
                language.setId(resultSet.getLong("LANGUAGE_ID"));
                language.setName(resultSet.getString("LANGUAGE_NAME"));
                language.setLocal(resultSet.getString("LANGUAGE_LOCAL"));
                languages.add(language);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return languages;
    }

    public Language getById(long id) {
        Connection connection = connectionPool.retrieve();
        Language language = new Language();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(GET_LANGUAGE_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                language.setId(resultSet.getLong("LANGUAGE_ID"));
                language.setName(resultSet.getString("LANGUAGE_NAME"));
                language.setLocal(resultSet.getString("LANGUAGE_LOCAL"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        connectionPool.putback(connection);
        return language;
    }
}
