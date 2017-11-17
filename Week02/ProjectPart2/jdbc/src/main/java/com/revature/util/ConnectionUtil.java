package com.revature.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static Properties prop;
    private final static String FILENAME = "db.properties";

    public static Connection getConnection() throws SQLException {
        try {
            prop = new Properties();
            prop.load(new FileInputStream(FILENAME));
            String url = prop.getProperty("url");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");
            return DriverManager.getConnection(url, username, password);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
