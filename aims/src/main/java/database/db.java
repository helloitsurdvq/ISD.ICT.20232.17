package database;
import utils.Format;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;
public class db {
    private static Logger LOGGER = Format.getLogger(Connection.class.getName());
    private static Connection connect;
    public static Connection getConnection() {
        if (connect != null) {
            return connect;
        }
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:aims/src/main/java/database/aims.db";
            connect = DriverManager.getConnection(url);
            LOGGER.info("Connected to AIMS database.");
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            LOGGER.severe("Failed to connect to AIMS database: " + e.getMessage());
        }
        return connect;
    }

    public static void main(String[] args) {
        db.getConnection();
    }
}