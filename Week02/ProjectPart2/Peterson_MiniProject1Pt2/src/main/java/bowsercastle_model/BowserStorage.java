package bowsercastle_model;

import static bowsercastle_model.CloseStreams.close;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.PriorityQueue;
import java.util.Queue;

import org.apache.log4j.Logger;

/**
 * A Singleton class used for logging messages and retrieving/storing data with the DB.
 * @author Alex
 */
public class BowserStorage implements BowserStorable {
	private Logger logger;
	private Queue<User> users;
	private static BowserStorage bowserStorage;

	private BowserStorage() {
		logger = Logger.getLogger(BowserCastle.class);
		users = new PriorityQueue<>(new UserComparator());
		getUsersFromDB();
	}
	
	/**Retrieve our singleton bowser storage object.*/
	public static BowserStorage getBowserStorage() {
		if (bowserStorage == null) {
			bowserStorage = new BowserStorage();
		}
		return bowserStorage;
	}
	
	/**
	 * Retrieve all of our users from our DB.
	 */
	private void getUsersFromDB() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try(Connection conn = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM users";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			//traverse through result set and create each user from the data and add it to the set
			while (rs.next()) {
				final int id = rs.getInt("user_id");
				final String name = rs.getString("user_name");
				final int password = rs.getInt("user_password");
				final Role role = Role.getRole(rs.getString("user_role"));
				final int coins = rs.getInt("user_coins");
				final int level = rs.getInt("user_level");
				
				final User user = new User(name, password, role);
				user.setId(id);
				user.setCoins(coins);
				user.setLevel(level);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
		}
	}

	/**
	 * @return the queue of users
	 */
	public Queue<User> getUsers() {
		return users;
	}

	/**
	 * Insert a user into the BowserDB. Into the users table.
	 * @param user to be inserted into DB
	 */
	public void insertUser(final User user) {
		ResultSet rs = null;
		CallableStatement cs = null;
		PreparedStatement ps = null;
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			int max_id = 0, id = 0;
			String sql = "";
			
			if (users.isEmpty()) { //if there are no users in the table
				id = 1;				
			} else { //get the max_id from the DB
				
				ps = conn.prepareStatement("SELECT MAX(USER_ID) FROM USERS");
				rs = ps.executeQuery();
				if (rs.next()) max_id = rs.getInt(1);
				id = max_id + 1;
			}
			
			sql = "{call insert_user_procedure(?,?,?,?,?,?)}";
			cs = conn.prepareCall(sql);
			
			final String name = user.getName();
			final int password = user.getHashedPassword();
			final String role = user.getRole().name();
			final int coins = user.getCoins();
			final int level = user.getLevel();
			
			cs.setInt(1, id);
			cs.setString(2, name);
			cs.setInt(3, password);
			cs.setInt(4, coins);
			cs.setInt(5, level);
			cs.setString(6, role);
			cs.executeQuery();
			
			users.add(user);
			log(name + " has been created with an id of " + id);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(cs);
			close(rs);
		}
	}
	
	public void updateCoins(final User user) {
		
		
	}

	/**
	 * Send a message to the log
	 * @param message to be logged
	 */ 
	public void log(final String message) {
		logger.info(message);
	}
}