package com.epam.servicedesk.database;

import com.epam.servicedesk.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.epam.servicedesk.util.ConstantForApp.CONNECTION_NOT_FOUND;
import static com.epam.servicedesk.util.ConstantForApp.EMPTY_STRING;

public class ConnectionPool {

    private static final ConnectionPool UNIQUE_INSTANCE = new ConnectionPool();
    private final BlockingQueue<Connection> CONNECTION_QUEUE = new ArrayBlockingQueue<>(5);
    private static final String CONNECTION_POOL_BUNDLE = "connectionPool";
    private static final String CONNECTION_POOL_URL = "url";
    private static final String CONNECTION_POOL_USER = "user";
    private static final String CONNECTION_POOL_PASSWORD = "password";
    private static final String CONNECTION_PULL_INIT_CONNECTION_COUNT = "initConnectionCount";
    private final Locale LOCALE = new Locale(EMPTY_STRING);
    private final ResourceBundle BUNDLE = ResourceBundle.getBundle(CONNECTION_POOL_BUNDLE, LOCALE);
    private final String URL = BUNDLE.getString(CONNECTION_POOL_URL);
    private final String USER = BUNDLE.getString(CONNECTION_POOL_USER);
    private final String PASSWORD = BUNDLE.getString(CONNECTION_POOL_PASSWORD);
    private final int INIT_CONNECTION_COUNT = Integer.parseInt(BUNDLE.getString(CONNECTION_PULL_INIT_CONNECTION_COUNT));
    private static final Logger LOGGER = LogManager.getRootLogger();

    private ConnectionPool() {
        for (int i = 1; i <= INIT_CONNECTION_COUNT; i++) {
            try {
                CONNECTION_QUEUE.put(getConnection());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static ConnectionPool getUniqueInstance() {
        return UNIQUE_INSTANCE;
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            LOGGER.error(CONNECTION_NOT_FOUND, e);
        }
        return connection;
    }

    public Connection retrieve() {
        Connection connection = null;
        try {
            connection = CONNECTION_QUEUE.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void putback(Connection connection) throws ConnectionException {
        if (connection != null) {
            try {
                CONNECTION_QUEUE.put(connection);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            LOGGER.error(CONNECTION_NOT_FOUND);
            throw new ConnectionException();
        }
    }
}
