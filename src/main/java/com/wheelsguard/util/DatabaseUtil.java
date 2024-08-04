package com.wheelsguard.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/Bicycle_Shop";
    private static final String SQLSERVER_URL = "jdbc:sqlserver://localhost:1434;databaseName=Bicycle_Shop";
    private static final String MYSQL_USER = "root";
    private static final String SQLSERVER_USER = "oadeleke";

    private static final String MYSQL_PASSWORD = System.getenv("SQL_PASSWORD");
    private static final String SQLSERVER_PASSWORD = System.getenv("SQL_PASSWORD");

    public static Connection getMySQLConnection() throws SQLException {
        return DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASSWORD);
    }

    public static Connection getSQLServerConnection() throws SQLException {
        return DriverManager.getConnection(SQLSERVER_URL, SQLSERVER_USER, SQLSERVER_PASSWORD);
    }
}
