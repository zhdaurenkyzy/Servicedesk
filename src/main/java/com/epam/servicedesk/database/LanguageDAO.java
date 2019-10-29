package com.epam.servicedesk.database;

import com.epam.servicedesk.entity.Language;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.servicedesk.util.ConstantForDAO.*;

public class LanguageDAO extends AbstractDAO<Language, Long> {

    @Override
    public String getSelectQuery() {
        return GET_ALL_LANGUAGE;
    }

    @Override
    public String getQueryById() {
        return GET_LANGUAGE_BY_ID;
    }

    @Override
    public String getCreateQuery() {
        return null;
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    protected void prepareStatementForSet(Language language, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, language.getName());
        preparedStatement.setString(2, language.getLocal());
        preparedStatement.setLong(3, language.getId());
    }

    @Override
    protected void prepareStatementForUpdate(Language language, PreparedStatement preparedStatement) throws SQLException {

    }

    @Override
    protected Language parseResultSet(Language language, ResultSet resultSet) throws SQLException {
        language.setId(resultSet.getLong("LANGUAGE_ID"));
        language.setName(resultSet.getString("LANGUAGE_NAME"));
        language.setLocal(resultSet.getString("LANGUAGE_LOCAL"));
        return language;
    }

    @Override
    protected Language create() {
        Language language = new Language();
        return language;
    }

    @Override
    public void delete(Language language) {

    }
}
