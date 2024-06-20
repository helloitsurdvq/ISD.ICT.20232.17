package database;

import java.sql.Connection;
import java.sql.DriverManager;
public class db {
    private static Connection connect;
    public static Connection getConnection() {
        if (connect != null) {
            return connect;
        }
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:aims/src/main/java/database/aims.db";
            connect = DriverManager.getConnection(url);
            System.out.println("Connected to AIMS database successfully.");
        } catch (Exception e) {
            System.out.println("Failed to connect to AIMS database: " + e.getMessage());
        }
        return connect;
    }

    public static void main(String[] args) {
        db.getConnection();
    }
}