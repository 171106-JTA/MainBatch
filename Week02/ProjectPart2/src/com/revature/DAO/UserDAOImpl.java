package com.revature.DAO;

import static com.revature.util.CloseStreams.close;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.revature.bank.Account;
import com.revature.bank.Accounts;
import com.revature.bank.User;
import com.revature.bank.Users;
import com.revature.util.ConnectionUtil;

public class UserDAOImpl implements UserDAO {
	public static Map<String, String> actions;
	public static final int USER_NOT_FOUND = -1;
	
	public UserDAOImpl()
	{
		// initialize the actions
		if (actions == null)
		{
			actions = new HashMap<>();
			actions.put(User.ACTIVE, "activate");
			actions.put(User.LOCKED, "lock");
			actions.put(User.FLAGGED, "flag");
			actions.put(User.BANNED, "ban");
		}
	}
	
	@Override
	/**
	 * authenticates the user
	 * @param user : the User to try to authenticate
	 * @return {@code user} if the authentication went successful, or null 
	 */
	public User authenticate(User user) {
		CallableStatement cs = null;
		
		boolean hasValidCredentials = false;
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "{? = call find_user_by_credentials(?,?)}";
			cs = conn.prepareCall(sql);
			cs.setString(2, user.getName().trim());
			cs.setString(3, user.getPass().trim());
			cs.registerOutParameter(1, java.sql.Types.INTEGER);
			cs.execute();
			// get the id from the CallableStatement
			int foundID = cs.getInt(1);
			hasValidCredentials = (foundID != USER_NOT_FOUND);
			// if user has valid credentials
			if (hasValidCredentials)
			{
				// we get the id from the ResultSet and give it to user
				user.setUserID(foundID);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(cs);
		}
		return hasValidCredentials ? user : null;
	}

	/**
	 * tries to add a User to the database
	 * @param	user : the user to attempt to add to the database. Must have name, password, and state. 
	 */
	@Override
	public void create(User user) {
		CallableStatement cs = null;
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "{call create_user(?,?,?)}";
			cs = conn.prepareCall(sql);
			cs.setString(1, user.getName());
			cs.setString(2, user.getPass());
			cs.setString(3, user.getState());
			cs.executeQuery();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(cs);
		}
	}

	/** gets a user by the specified User ID
	 * @param id : the {@code user_id} to retrieve by
	 * @return The {@code User} having that id, or null if not found
	 */
	@Override
	public User getUserById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		// open connection to the database
		try (Connection conn = ConnectionUtil.getConnection())
		{
			// set up the SQL query/PreparedStatement and get ready to execute it
			// have to do inner joins because the users table contains foreign key constraints
			String sql = "SELECT * FROM Users_View WHERE User_ID = ?";	
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			// execute the query
			rs = ps.executeQuery();
			// fetch the result set into user
			String name = "", pass = "", state = "";
			Accounts accounts = new Accounts();
			while (rs.next())
			{
				/* ResultSet rows will be unique in the user name,password,state but have different Account data.
				 * Thus, when we iterate over the ResultSet, we simply get each set of Account data from the ResultSet
				 * and append that Object to Accounts, to instantiate User with.
				 */
				// first, we get the name,pass,state of the User if we haven't already
				if (name.isEmpty()) name = rs.getString("USER_NAME");
				if (pass.isEmpty()) pass = rs.getString("USER_PASS");
				if (state.isEmpty()) state = rs.getString("STATE_NAME");
				// next, we get the Account data and append it to Accounts
				accounts.add(new Account(rs.getInt("ACCOUNT_ID"),
						rs.getDouble("ACCOUNT_BALANCE")));
				user = new User(name, pass, accounts, state);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			close(rs);
			close(ps);
		}
		
		return user;
	}

	/**
	 * Updates a User. 
	 * @param user: The {@code User} to update
	 * @return The number of rows effected.
	 */
	@Override
	public int update(User user) {
		CallableStatement cs = null;
		int rowsEffected = 0;
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "{call update_user(?,?,?,?)}";
			cs = conn.prepareCall(sql);
			cs.setInt(1, user.getUserID());
			cs.setString(2, user.getName());
			cs.setString(3, user.getPass());
			cs.setString(4, user.getState());

			rowsEffected = cs.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			close(cs);
		}
		
		return rowsEffected;
	}

	/**
	 * Deletes a User.
	 * @param user : The {@code User} to delete.
	 * @return The number of rows effected.
	 */
	@Override
	public int delete(User user) {
		PreparedStatement ps = null;
		int rowsEffected = 0;
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "DELETE FROM users WHERE user_id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, user.getUserID());
			rowsEffected = ps.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(ps);
		}
		return rowsEffected;
	}

	/**
	 * Activates a User.
	 * @param user : The {@code User} to activate.
	 * @return The number of rows effected.
	 */
	@Override
	public int activate(User user) {
		return takeActionByID(user, actions.get(User.ACTIVE));
	}

	/**
	 * Locks a User.
	 * @param user : The {@code User} to lock.
	 * @return The number of rows effected.
	 */
	@Override
	public int lock(User user) {
		return takeActionByID(user, actions.get(User.LOCKED));
	}

	/**
	 * Flags a User.
	 * @param user : The {@code User} to flag.
	 * @return The number of rows effected.
	 */
	@Override
	public int flag(User user) {
		return takeActionByID(user, actions.get(User.FLAGGED));
	}

	/**
	 * Bans a User.
	 * @param user : The {@code User} to ban.
	 * @return The number of rows effected.
	 */
	@Override
	public int ban(User user) {
		return takeActionByID(user, actions.get(User.BANNED));
	}
	
	/**
	 * Takes a specified action by id
	 * @param user   : the user to take action on
	 * @param action : the action to do (must be in {@code actions} map)
	 * @return the number of rows effected
	 */
	private int takeActionByID(User user, String action) throws IllegalArgumentException
	{
		// make sure that action is anything in the actions
		if ((!actions.containsKey(action)) && (!actions.containsValue(action))) 
			throw new IllegalArgumentException("action must be in actions."); 
		CallableStatement cs = null;
		int rowsEffected = 0;
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = String.format("{call %s_by_id(?)}", 
					actions.containsValue(action) ? action : actions.get(action));
			cs = conn.prepareCall(sql);
			cs.setInt(1, user.getUserID());
			rowsEffected = cs.executeUpdate();
			// update the user Object, too
			if (actions.containsKey(action)) user.setState(action);
			if (actions.containsValue(action)) 
				user.setState((String)new ArrayList<String>(actions.keySet()).stream().filter(x -> actions.get(x) != null).toArray()[0]);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(cs);
		}
		return rowsEffected;
	}

	/** 
	 * @return All users in the database.
	 */
	@Override
	public Users getAllUsers() {
		Statement stmt = null; 
		ResultSet rs = null;
		Users users = null;
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM Users_View";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			users = new Users();
			while (rs.next())
			{
				users.add(new User(rs.getString(2),
						rs.getString(3),
						rs.getString(4)));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			// always close your resources!
			close(rs);
			close(stmt);
		}
		return users;
	}
	
	public Users getAllBaseUsers()
	{
		Statement stmt = null; 
		ResultSet rs = null;
		Users users = null;
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM Base_Users_View";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			users = new Users();
			while (rs.next())
			{
				users.add(new User(rs.getString(2),
						rs.getString(3),
						rs.getString(4)));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			// always close your resources!
			close(rs);
			close(stmt);
		}
		return users;
	}

	/**
	 * Gets all users by state.
	 * @param state : The state of all the users.
	 * @return All users having that state.
	 */
	@Override
	public Users getUsersByState(String state) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Users users = null;
		
		try (Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT * FROM users_view WHERE state_name = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, state);
			rs = ps.executeQuery();
			
			users = new Users();
			while (rs.next()) {
				users.push(new User(rs.getString(2),
						rs.getString(3),
						rs.getString(4)));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(ps);
			close(rs);
		}
		return users;
	}

	/**
	 * Not implemented yet. Don't use it. Instead, use {@code getAllUsers().findByUsername(username) } 
	 */
	@Override
	public User findUserByUsername(String username) {
		// TODO: Implement this 
		return null;
	}
	
	// TODO: determine whether or not to move these methods to AccountsDAOImpl
	@Override
	public Accounts getAccountsFor(User user) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		// TODO: Implement this
		return null;
	}

	@Override
	public Accounts getAccountsForUserId(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		// TODO: Implement this
		return null;
	}


}
