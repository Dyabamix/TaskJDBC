package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL= "jdbc:mysql://localhost:3306/myfirstdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "admin";

    public static Connection getConnection(){
        Connection connection = null;

        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException th) {
            th.printStackTrace();
        }
        return connection;
    }
}
