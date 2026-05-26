package day6;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {

    public Connection connect() {

        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "100104";

        try {
            Connection conn =
                    DriverManager.getConnection(url, username, password);

            return conn;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection Failed");

            return null;
        }
    }
}