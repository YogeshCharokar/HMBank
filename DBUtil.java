package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    // private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database";
    // private static final String USERNAME = "root";
    // private static final String PASSWORD = "yogesh";

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/payxpert", "root", "yogesh");
                System.out.println("Connected to the database");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                System.err.println("Failed to connect to the database");
            }
        }
        return connection;
    }

    // public static void closeConnection() {
    //     if (connection != null) {
    //         try {
    //             connection.close();
    //             System.out.println("Connection closed");
    //         } catch (SQLException e) {
    //             e.printStackTrace();
    //             System.err.println("Failed to close the database connection");
    //         }
    //     }
    // }
}