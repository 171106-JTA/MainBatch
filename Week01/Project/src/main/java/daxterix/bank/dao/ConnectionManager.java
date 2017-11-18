package daxterix.bank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionManager {
    private String url;
    private String username;
    private String password;


    /**
     * creates connections to the oralcle server at given url using given credentials:
     * username, password
     *
     * @param username
     * @param password
     * @param url
     */
    public ConnectionManager(String username, String password, String url) {
        this.username = username;
        this.password = password;
        this.url = url;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
