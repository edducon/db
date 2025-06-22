package org.db.kursovoi.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Singleton: общее JDBC-соединение */
public final class Database {

    private static final String URL  = "jdbc:mysql://localhost:3306/std_2740_kurs";
    private static final String USER = "root";
    private static final String PASS = "";

    private static Connection connection;

    private Database() { }                       // new запрещён

    public static synchronized Connection get() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASS);
        }
        return connection;
    }
}
