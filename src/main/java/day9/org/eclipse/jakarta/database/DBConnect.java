package day9.org.eclipse.jakarta.database;

import java.sql.*;

public class DBConnect {

    public Connection connect() {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "100104";

        try {
            return DriverManager.getConnection(url, username, password);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection Failed");

            return null;
        }
    }
}