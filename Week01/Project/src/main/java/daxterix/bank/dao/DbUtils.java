package daxterix.bank.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
    private static final String url = System.getenv("BANKDBUR");
    private static final String username = System.getenv("BANKDBU");
    private static final String password = System.getenv("BANKDBP");

    static{
        try {
            Class.forName ("oracle.jdbc.OracleDriver");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("could not connect to oracle driver; exiting");
            System.exit(0);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public static void close(AutoCloseable closeable) {
        try {
            if (closeable != null)
                closeable.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
