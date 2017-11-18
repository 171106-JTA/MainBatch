package bowsercastle_model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author Alex
 */
public class ConnectionUtil {
	
	
	public static Connection getConnection() throws SQLException {
		
		final String props[] = System.getenv("BowserDB").split(";");
		return DriverManager.getConnection(props[1], props[2], props[3]);
	}
}