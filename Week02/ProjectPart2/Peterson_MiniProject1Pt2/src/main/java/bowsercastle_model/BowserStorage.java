package bowsercastle_model;

import static bowsercastle_model.CloseStreams.close;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
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
	
	/**Collections used to store users and their loans.*/
	private Map<Integer, Integer> loans;
	private Map<Integer, Integer> loansWaiting;

	private BowserStorage() {
		logger = Logger.getLogger(BowserCastle.class);
		users = new PriorityQueue<>(new UserComparator());
		loans = new HashMap<Integer, Integer>();
		loansWaiting = new HashMap<Integer, Integer>();
		retrieveUsersFromDB();
		retrieveLoansFromDB();
	}

	/**Retrieve our singleton bowser storage object.*/
	public static BowserStorage getBowserStorage() {
		if (bowserStorage == null) {
			bowserStorage = new BowserStorage();
		}
		return bowserStorage;
	}

	/**
	 * Grab the loans from the DB.
	 */
	private void retrieveLoansFromDB() {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try(Connection conn = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM USER_LOANS";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			//traverse through result set and create each user from the data and add it to the set
			while (rs.next()) {
				final int id = rs.getInt("user_id");
				final int amount = rs.getInt("loan_amount");
				final String status = rs.getString("loan_status");

				if (status.toUpperCase().equals("APPROVED")) {
					loans.put(id, amount);
				} else {
					loansWaiting.put(id, amount);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
		}
	}

	/**
	 * Retrieve all of our users from our DB.
	 */
	private void retrieveUsersFromDB() {
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

	/**
	 * 
	 */
	public void updateCoins(final User user) {
		ResultSet rs = null;
		CallableStatement cs = null;

		try(Connection conn = ConnectionUtil.getConnection()) {

			String sql = "{call update_coins_procedure(?, ?)}";
			cs = conn.prepareCall(sql);

			cs.setInt(1, user.getId());
			cs.setInt(2, user.getCoins());
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(cs);
			close(rs);
		}
	}

	/**
	 * Send a message to the log
	 * @param message to be logged
	 */ 
	public void log(final String message) {
		logger.info(message);
	}

	/**
	 * Update a user's level in our DB.
	 */
	@Override
	public void updateLevel(User user) {
		ResultSet rs = null;
		CallableStatement cs = null;

		try(Connection conn = ConnectionUtil.getConnection()) {

			String sql = "{call update_level_procedure(?, ?)}";
			cs = conn.prepareCall(sql);

			cs.setInt(1, user.getId());
			cs.setInt(2, user.getLevel());
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(cs);
			close(rs);
		}
	}

	/**
	 * Update a user's role in our DB.
	 */
	@Override
	public void updateRole(User user) {
		ResultSet rs = null;
		CallableStatement cs = null;

		try(Connection conn = ConnectionUtil.getConnection()) {

			String sql = "{call update_role_procedure(?, ?)}";
			cs = conn.prepareCall(sql);

			cs.setInt(1, user.getId());
			cs.setString(2, user.getRole().name());
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(cs);
			close(rs);
		}
	}

	/**
	 * Delete a user from our DB.
	 */
	@Override
	public void deleteUser(User user) {

		if (user.getRole() == Role.KING) {
			users.poll();
		} else {
			PriorityQueue<User> temp = new PriorityQueue<User>(new UserComparator());
			for (User tempUser : bowserStorage.getUsers()) //add all members to our temp data structure except the dead user
				if (tempUser != user) 
					temp.add(tempUser);

			users.clear();
			users.addAll(temp);
		}

		ResultSet rs = null;
		CallableStatement cs = null;

		try(Connection conn = ConnectionUtil.getConnection()) {

			String sql = "{call delete_user_procedure(?)}";
			cs = conn.prepareCall(sql);

			cs.setInt(1, user.getId());
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(cs);
			close(rs);
		}
	}

	private void updateLoans(User user, int amount, String status) {
		ResultSet rs = null;
		CallableStatement cs = null;

		try(Connection conn = ConnectionUtil.getConnection()) {

			String sql = "{call update_loans_procedure(?, ?, ?)}";
			cs = conn.prepareCall(sql);

			cs.setInt(1, user.getId());
			cs.setInt(2, amount);
			cs.setString(3, status);
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(cs);
			close(rs);
		}
	}

	/**
	 * User deposits funds into their account
	 * @param user
	 * @param coins
	 * @return if the transaction was successful
	 */
	public Transaction depositCoins(User user, int coins) {
		if (coins > 0) {
			user.setCoins(user.getCoins() + coins);
			return Transaction.SUCCESS;
		}
		return Transaction.FAIL;
	}

	/**
	 * User takes money from their account
	 * @param user
	 * @param coins
	 * @return if the transaction was successful
	 */
	public Transaction withdrawCoins(User user, int coins) {
		if (coins <= user.getCoins()){
			user.setCoins(user.getCoins() - coins);
			return Transaction.SUCCESS;
		}
		return Transaction.FAIL;
	}

	/**
	 * 
	 * @param user
	 */
	public void levelUp(final User user) {
		user.levelUp();	
	}

	/**
	 * Members may apply for a loan.
	 * @param member
	 * @param amount
	 */
	public void applyForLoan(User member, int amount) {
		loansWaiting.put(member.getId(), amount);
		createLoans(member, amount, "UNAPPROVED");
	}

	private void createLoans(User user, int amount, String status) {
		ResultSet rs = null;
		CallableStatement cs = null;

		try(Connection conn = ConnectionUtil.getConnection()) {

			String sql = "{call insert_loans_procedure(?, ?, ?)}";
			cs = conn.prepareCall(sql);

			cs.setInt(1, user.getId());
			cs.setInt(2, amount);
			cs.setString(3, status);
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(cs);
			close(rs);
		}
	}

	/**
	 * The member's loan has been accepted and is on file.
	 * @param member
	 */
	public void acceptLoan(final User member) {
		int borrowedCoins = loansWaiting.get(member.getId());
		member.setCoins(member.getCoins() + borrowedCoins); //add the coins to the user's account
		loans.put(member.getId(), borrowedCoins); //user's loan is stored
		loansWaiting.remove(member.getId());
		updateLoans(member, borrowedCoins, "APPROVED");
	}

	/**Remove the user from the loans collection.*/
	public void denyLoan(User member) {
		loansWaiting.remove(member);
		deleteLoan(member);
	}

	/**
	 * Delete a user's loan from the DB.
	 * @param user
	 */
	private void deleteLoan(User user) {

		CallableStatement cs = null;

		try(Connection conn = ConnectionUtil.getConnection()) {

			String sql = "{call delete_user_loan(?)}";
			cs = conn.prepareCall(sql);

			cs.setInt(1, user.getId());
			cs.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(cs);
		}
		
	}

	public Map<Integer, Integer> getLoansWaiting() {
		return loansWaiting;
	}

	public Map<Integer, Integer> getLoans() {
		return loans;
	}

	/**
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public Map<String, Integer> grabWaitingLoans() throws IOException {
		Map<String,Integer> loans = new HashMap<>();

		return loans;
	}

	/**
	 * A user is depositing coins towards their loan.
	 * @param user
	 * @param deposit
	 * @return if the transaction was successful or failed
	 */
	public Transaction payLoan(User user, int deposit) {
		if (deposit < 0) return Transaction.FAIL;
		int loan = loans.get(user);

		int amountOwed = loan - deposit;

		if (amountOwed < 0) {//the user has deposited more coins than they owe
			amountOwed = -amountOwed;
			user.setCoins(user.getCoins() + amountOwed);
			loans.remove(user);
			deleteLoan(user);
		} else {
			loans.put(user.getId(), amountOwed); //the user has made a deposit less than what they owe
			updateLoans(user, amountOwed, "APPROVED");
		}

		return Transaction.SUCCESS;
	}
}