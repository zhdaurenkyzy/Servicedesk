package com.epam.servicedesk.util;

public final class ConstantForDAO {
    public static final String GET_ALL_FIELDS_OF_ALL_REQUEST = "SELECT request.REQUEST_ID, request.REQUEST_THEME, status.STATUS_NAME ," +
            " request.REQUEST_PRIORITY_ID, engineer_group.GROUP_NAME, engineer.USER_NAME as ENGINEER_NAME, project.PROJECT_NAME, client.USER_NAME as CLIENT_NAME," +
            " author_of_creation.USER_NAME as AUTHOR_OF_CREATION_NAME, request.REQUEST_DATE_OF_CREATION, author_of_decision.USER_NAME as AUTHOR_OF_DECISION_NAME , request.REQUEST_DATE_OF_DECISION " +
            " FROM  request  LEFT OUTER JOIN status" +
            "     ON  request.REQUEST_STATUS_ID =status.STATUS_ID " +
            "   LEFT OUTER JOIN engineer_group " +
            "     ON  request.ENGINEER_GROUP_ID=engineer_group.GROUP_ID " +
            "     LEFT OUTER JOIN user as engineer " +
            "     ON request.ENGINEER_USER_ID=engineer.USER_ID " +
            "     LEFT OUTER JOIN project " +
            "     ON  request.PROJECT_ID=project.PROJECT_ID " +
            " LEFT OUTER JOIN user as client" +
            "     ON request.CLIENT_USER_ID=client.USER_ID " +
            "     LEFT OUTER JOIN user as author_of_creation " +
            "     ON request.REQUEST_AUTHOR_OF_CREATION=author_of_creation.USER_ID " +
            "     LEFT OUTER JOIN user as author_of_decision " +
            "     ON request.REQUEST_AUTHOR_OF_DECISION=author_of_decision.USER_ID ORDER BY request.REQUEST_ID DESC";
    public static final String ADD_GROUP = "INSERT INTO ENGINEER_GROUP (GROUP_NAME) VALUES (?)";
    public static final String GET_GROUP_BY_NAME = "SELECT * FROM ENGINEER_GROUP WHERE GROUP_NAME=?";
    public static final String GET_ALL_GROUP = "SELECT * FROM ENGINEER_GROUP ORDER BY GROUP_NAME";
    public static final String GET_GROUP_BY_ID = "SELECT * FROM ENGINEER_GROUP WHERE GROUP_ID=?";
    public static final String UPDATE_GROUP = "UPDATE ENGINEER_GROUP SET GROUP_NAME =? WHERE GROUP_ID=?";
    public static final String DELETE_GROUP = "DELETE FROM ENGINEER_GROUP WHERE GROUP_ID=?";
    public static final String DELETE_FROM_USER_GROUP = "DELETE FROM USER_GROUP WHERE GROUP_ID=?";
    public static final String GET_REQUEST_BY_REQUEST_ID = "SELECT * FROM REQUEST WHERE REQUEST_ID=?";
    public static final String ADD_INTO_HISTORY = "INSERT INTO HISTORY (REQUEST_ID, COLUMN_NAME, COLUMN_VALUE_BEFORE, COLUMN_VALUE_AFTER, DATE_OF_CHANGE, USER_ID) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String GET_HISTORY_BY_REQUEST_ID = "SELECT * FROM HISTORY WHERE REQUEST_ID=?";
    public static final String DELETE_HISTORY = "DELETE FROM HISTORY WHERE REQUEST_ID=?";
    public static final String GET_ALL_LANGUAGE = "SELECT * FROM LANGUAGE";
    public static final String GET_LANGUAGE_BY_ID = "SELECT * FROM LANGUAGE WHERE LANGUAGE_ID=?";
    public static final String ADD_LEVEL = "INSERT INTO LEVEL (LEVEL_NAME) VALUES (?)";
    public static final String GET_ALL_LEVEL = "SELECT * FROM LEVEL";
    public static final String GET_LEVEL_BY_ID = "SELECT * FROM LEVEL WHERE LEVEL_ID=?";
    public static final String ADD_MODE = "INSERT INTO MODE (MODE_NAME) VALUES (?)";
    public static final String GET_ALL_MODE = "SELECT * FROM MODE";
    public static final String GET_MODE_BY_ID = "SELECT * FROM MODE WHERE MODE_ID=?";
    public static final String GET_MODE_BY_NAME = "SELECT * FROM MODE WHERE MODE_NAME=?";
    public static final String UPDATE_MODE = "UPDATE MODE SET MODE_NAME =? WHERE MODE_ID=?";
    public static final String DELETE_MODE = "DELETE FROM MODE WHERE MODE_ID=?";
    public static final String ADD_PROJECT = "INSERT INTO PROJECT (PROJECT_NAME, PROJECT_STATE) VALUES (?, true)";
    public static final String GET_PROJECT_BY_ID = "SELECT * FROM PROJECT WHERE PROJECT_ID=?";
    public static final String GET_PROJECT_BY_NAME = "SELECT * FROM PROJECT WHERE PROJECT_NAME=?";
    public static final String GET_ALL_PROJECT_BY_STATE = "SELECT * FROM PROJECT WHERE PROJECT_STATE=? ORDER BY PROJECT_NAME";
    public static final String UPDATE_PROJECT = "UPDATE PROJECT SET PROJECT_NAME=? WHERE PROJECT_ID=?";
    public static final String CHANGE_PROJECT_STATE = "UPDATE PROJECT SET PROJECT_STATE = ? WHERE PROJECT_ID=?";
    public static final String DELETE_PROJECT = "DELETE FROM PROJECT WHERE PROJECT_ID=?";
    public static final String DELETE_USER_FROM_USER_PROJECT_AND_USER = "DELETE USER_PROJECT, USER FROM USER_PROJECT," +
            " USER WHERE USER_PROJECT.USER_ID=USER.USER_ID AND PROJECT_ID=?";
    public static final String ADD_STATUS = "INSERT INTO STATUS (STATUS_NAME) VALUES (?)";
    public static final String GET_ALL_STATUS = "SELECT * FROM STATUS";
    public static final String GET_STATUS_BY_ID = "SELECT * FROM STATUS WHERE STATUS_ID=?";
    public static final String ADD_REQUEST = "INSERT INTO REQUEST (REQUEST_THEME, REQUEST_DESCRIPTION, REQUEST_STATUS_ID," +
            " REQUEST_LEVEL_ID, REQUEST_MODE_ID, REQUEST_PRIORITY_ID, ENGINEER_GROUP_ID, ENGINEER_USER_ID, PROJECT_ID, CLIENT_USER_ID," +
            " REQUEST_AUTHOR_OF_CREATION, REQUEST_DATE_OF_CREATION, REQUEST_DECISION,REQUEST_AUTHOR_OF_DECISION, REQUEST_DATE_OF_DECISION)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_REQUEST_BY_ID = "SELECT * FROM REQUEST WHERE REQUEST_ID=?";
    public static final String ADD_DECISION = "UPDATE REQUEST SET  REQUEST_DECISION=?, REQUEST_AUTHOR_OF_DECISION=?," +
            " REQUEST_DATE_OF_DECISION=?, REQUEST_STATUS_ID=? WHERE REQUEST_ID=?";
    public static final String UPDATE_REQUEST = "UPDATE REQUEST SET REQUEST_THEME=?, REQUEST_DESCRIPTION=?, REQUEST_STATUS_ID=?," +
            " REQUEST_LEVEL_ID=?, REQUEST_MODE_ID=?, REQUEST_PRIORITY_ID=?, ENGINEER_GROUP_ID=?, ENGINEER_USER_ID=?, PROJECT_ID=?," +
            " CLIENT_USER_ID=?, REQUEST_DECISION=?, REQUEST_AUTHOR_OF_DECISION=?, REQUEST_DATE_OF_DECISION=? WHERE REQUEST_ID=?";
    public static final String DELETE_REQUEST = "DELETE FROM REQUEST WHERE REQUEST_ID=?";
    public static final String ADD_USER = "INSERT INTO USER(USER_NAME, USER_POSITION, USER_PHONE, USER_MOBILE, USER_MAIL," +
            " USER_LOGIN, USER_PASSWORD, USER_ROLE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_ALL_USERS = "SELECT * FROM USER ORDER BY USER_NAME";
    public static final String GET_USER_BY_ID = "SELECT * FROM USER WHERE USER_ID=?";
    public static final String GET_USER_BY_LOGIN = "SELECT * FROM USER WHERE USER_LOGIN=?";
    public static final String UPDATE_USER = "UPDATE USER SET USER_NAME=?, USER_POSITION=?, USER_PHONE=?, USER_MOBILE=?, USER_MAIL=?," +
            " USER_PASSWORD=?, USER_ROLE=? WHERE USER_ID=?";
    public static final String UPDATE_USER_REQUEST_WHEN_DELETE_CLIENT = "UPDATE REQUEST SET CLIENT_USER_ID=null WHERE CLIENT_USER_ID=?";
    public static final String DELETE_USER = "DELETE FROM USER WHERE USER_ID=?";
    public static final String GET_USERS_BY_ROLE = "SELECT * FROM USER WHERE USER_ROLE=0 OR USER_ROLE =1";
    public static final String GET_ALL_CLIENT = "SELECT * FROM USER LEFT JOIN USER_PROJECT ON USER.USER_ID=USER_PROJECT.USER_ID WHERE USER.USER_ROLE=2";
    public static final String DELETE_USER_FROM_GROUP = "DELETE FROM USER_GROUP WHERE USER_ID=?";
    public static final String DELETE_USER_BY_GROUP_ID = "DELETE FROM USER_GROUP WHERE USER_ID=? AND GROUP_ID=?";
    public static final String GET_ALL_USER_BY_GROUP_ID = "SELECT USER.USER_ID, USER.USER_NAME FROM USER LEFT JOIN" +
            " USER_GROUP ON USER.USER_ID=USER_GROUP.USER_ID WHERE GROUP_ID =?";
    public static final String GET_ALL_CLIENT_BY_PROJECT_ID = "SELECT USER.USER_ID, USER.USER_NAME FROM USER LEFT JOIN USER_PROJECT" +
            " ON USER.USER_ID=USER_PROJECT.USER_ID WHERE PROJECT_ID =?";
    public static final String ALL_USER_TO_PROJECT = "INSERT INTO USER_PROJECT(USER_ID, PROJECT_ID) VALUES (?, ?)";
    public static final String GET_PROJECT_ID_BY_USER_ID = "SELECT PROJECT_ID FROM USER_PROJECT WHERE USER_ID=?";
    public static final String DELETE_USER_FROM_PROJECT = "DELETE FROM USER_PROJECT WHERE USER_ID=?";
    public static final String UPDATE_USER_IN_PROJECT = "UPDATE USER_PROJECT SET PROJECT_ID=? WHERE USER_ID=?";
    public static final String GET_ALL_USER_WITHOUT_GROUP = "SELECT USER.USER_ID, USER.USER_NAME FROM USER LEFT JOIN USER_GROUP ON" +
            " USER.USER_ID=(SELECT USER_GROUP.USER_ID WHERE GROUP_ID =?) WHERE USER_GROUP.USER_ID IS NULL AND (USER_ROLE=0 OR USER_ROLE=1)";
    public static final String ADD_LIST_USER_TO_GROUP = "INSERT INTO USER_GROUP(USER_ID, GROUP_ID) VALUES (?, ?)";
    public static final String SEARCH_BY_USER = "{call servicedesk.searchByUserId(?, ?, ?, ?, ?, ?)}";
    public static final String SEARCH_BY_OPERATOR = "{call servicedesk.searchByOperator(?, ?, ?)}";
}
