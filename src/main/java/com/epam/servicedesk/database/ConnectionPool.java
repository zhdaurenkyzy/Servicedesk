package com.epam.servicedesk.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.epam.servicedesk.util.ConstantForApp.CONNECTION_NOT_FOUND;

public class ConnectionPool {

    private static final ConnectionPool UNIQUE_INSTANCE = new ConnectionPool();
    private final BlockingQueue<Connection> CONNECTION_QUEUE = new ArrayBlockingQueue<>(5);
    private static final String CLASS_FOR_NAME = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/servicedesk?serverTimezone=Asia/Almaty&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private ConnectionPool() {
        try {
            Class.forName(CLASS_FOR_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 1; i <= 5; i++) {
            try {
                CONNECTION_QUEUE.put(getConnection());
            } catch (InterruptedException e) {
                e.printStackTrace();
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
            e.printStackTrace();
        }
        return connection;
    }

    public Connection retrieve() {
        Connection connection = null;
        try {
            connection = CONNECTION_QUEUE.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void putback(Connection connection) {
        if (connection != null) {
            try {
                CONNECTION_QUEUE.put(connection);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println(CONNECTION_NOT_FOUND);
        }
    }
}
