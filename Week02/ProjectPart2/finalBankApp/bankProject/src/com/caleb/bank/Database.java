package com.caleb.bank;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {

	private ResultSet rs;
	private Connection conn;
	private Statement st;
	private String query;
	private final int email = 0, password = 1, name = 2, phoneNumber = 3, isSignedUpforSMSNotifications = 4;

	public void init() {

		try {

			conn = ConnectionUtil.getConnection();

		} catch (SQLException e) {

			System.out.println("Could not establish connection to the database. ");
			e.printStackTrace();

		}

	}

	public void placeUserOnPendingApprovalList(String[] userInformation) {

		query = "INSERT INTO pending_user VALUES(" + "'" + userInformation[email] + "'" + "," + "'"
				+ userInformation[password] + "'" + "," + "'" + userInformation[name] + "'" + "," + "'"
				+ userInformation[phoneNumber] + "'" + "," + "'" + userInformation[isSignedUpforSMSNotifications] + "'"
				+ ")";

		try {

			st = conn.createStatement();
			rs = st.executeQuery(query);

			/* Create an account for the user */
			query = "INSERT INTO account_table VALUES(" + "'" + userInformation[email] + "'" + ", " + "'0'" + "," + "'"
					+ "null" + "'" + "," + "'" + "0" + "')";
			rs = st.executeQuery(query);

		} catch (SQLException e) {

			System.out.println("Could not place user on pending approval list");
			e.printStackTrace();

		}

	}

	public boolean isOnPendingList(String[] userInformation) {

		try {

			query = "SELECT * FROM pending_user";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				if (rs.getString(email + 1).equalsIgnoreCase(userInformation[email])) {
					return true;
				}
			}

		} catch (SQLException e) {

			System.out.println("Something went wrong, cannot display if user is on pending list.");
			e.printStackTrace();

		}

		return false;
	}

	public boolean isOnActiveList(String[] userInformation) {

		try {

			query = "SELECT * FROM users_demo";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				if (rs.getString(email + 1).equalsIgnoreCase(userInformation[email])) {
					return true;
				}
			}

		} catch (SQLException e) {

			System.out.println("Something went wrong, cannot display if user is on active list.");
			e.printStackTrace();

		}

		return false;
	}

	public Admin signInAdmin(String[] userInformation) {

		try {

			query = "SELECT * FROM admin_table";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				if (rs.getString(email + 1).equalsIgnoreCase(userInformation[email])
						&& rs.getString(password + 1).equalsIgnoreCase(userInformation[password])) {
					return new Admin(userInformation[name], userInformation[email], userInformation[password]);
				}
			}

		} catch (SQLException e) {

			System.out.println("Something went wrong, cannot sign-in admin.");
			e.printStackTrace();

		}

		return null;

	}

	public String getAdminName(String userEmail) {

		try {

			query = "SELECT * FROM admin_table";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			while (rs.next()) {
				if (rs.getString(email + 1).equalsIgnoreCase(userEmail)) {
					return rs.getString(name + 1);
				}
			}

		} catch (SQLException e) {

			System.out.println("Something went wrong, cannot display admin name.");
			e.printStackTrace();

		}

		return "";

	}

	public List<User> getActiveUsers() {

		List<User> users = new ArrayList<User>();

		try {

			query = "SELECT * FROM users_demo";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			/* reconstruct users */
			while (rs.next()) {
				users.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));

			}

		} catch (SQLException e) {

			System.out.println("Something went wrong, cannot get active user list.");
			e.printStackTrace();

		}

		return users;

	}

	public void getNewResultSet() {

		;

	}

	public boolean blockUserAccount(String userEmail, SMSNotificationServer notificationServer) {

		try {

			query = "SELECT * FROM users_demo";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {

				/* if we find user, then block them */
				if (rs.getString(email + 1).equalsIgnoreCase(userEmail)) {

					query = "UPDATE users_demo SET is_blocked=" + "'True'" + " WHERE email=" + "'" + userEmail + "'";
					rs = st.executeQuery(query);
					return true;

				}

			}

		} catch (SQLException e) {

			System.out.println("Something went wrong, could not block account");
			e.printStackTrace();

		}

		return false;

	}

	public List<User> getPendingAccounts() {

		List<User> users = new ArrayList<User>();

		try {

			query = "SELECT * FROM pending_user";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			/* reconstruct users */
			while (rs.next()) {
				users.add(
						new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));

			}

		} catch (SQLException e) {

			System.out.println("Something went wrong, cannot retrieve pending accounts.");
			e.printStackTrace();

		}

		return users;
	}

	public boolean canApproveUserAccount(String userEmail) {

		try {

			query = "SELECT * FROM pending_user";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {

				/* if we find user, then block them */
				if (rs.getString(email + 1).equalsIgnoreCase(userEmail)) {

					return true;

				}

			}

		} catch (SQLException e) {

			System.out.println("Something went wrong, can not find user to approve account");
			e.printStackTrace();

		}

		return false;
	}

	public void approveAccount(String userEmail, SMSNotificationServer notificationServer) { // purposeful error

		String getUserTable = "SELECT * FROM pending_user";
		query = "DELETE FROM pending_user WHERE email=" + "'" + userEmail + "'";
		User temp = null;

		try {

			st = conn.createStatement();
			rs = st.executeQuery(getUserTable);

			while (rs.next()) {

				/*
				 * If we find the users information, sign them up and remove them from pending
				 * table and add them to active table
				 */
				if (rs.getString("email").equalsIgnoreCase(userEmail)) {

					temp = new User(rs.getString(email + 1), rs.getString(password + 1), rs.getString(name + 1),
							rs.getString(phoneNumber + 1), rs.getString(isSignedUpforSMSNotifications + 1));
					rs = st.executeQuery(query);
					query = "INSERT INTO users_demo VALUES(" + "'" + temp.getEmail() + "'" + "," + "'" + temp.getName()
							+ "'" + "," + "'" + temp.getPassword() + "'" + "," + "'" + temp.getPhoneNumber() + "'" + ","
							+ "'" + temp.getCreditScore() + "'" + "," + "'" + temp.isAdmin() + "'" + "," + "'"
							+ temp.isSignedUpForSMSNotifications() + "'" + "," + "'" + temp.hasLoan() + "'" + "," + "'"
							+ temp.isBlocked() + "')";
					rs = st.executeQuery(query);

				}

			}

		} catch (SQLException e) {

			;

		}

	}

	public List<User> getUsersWhoAreNotAdmins() {

		List<User> users = new ArrayList<User>();

		try {

			query = "SELECT * FROM users_demo WHERE is_admin='false'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

			/* reconstruct users */
			while (rs.next()) {
				users.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));

			}

		} catch (SQLException e) {

			System.out.println("Something went wrong, cannot retrieve users who are not admins.");
			e.printStackTrace();

		}

		return users;
	}

	public boolean promoteUserToAdmin(String userEmail, SMSNotificationServer notificationServer) {

		User temp = null;
		try {

			query = "SELECT * FROM users_demo";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			System.out.println("The useremail passed into admin function is: " + userEmail);
			while (rs.next()) {

				/* if we find user, promote them to admin */
				if (rs.getString(email + 1).equalsIgnoreCase(userEmail)) {

					query = "UPDATE users_demo SET is_admin=" + "'true'" + " WHERE email=" + "'" + userEmail + "'";
					rs = st.executeQuery(query);

					/* slect user that is promoted and insert them into admin table */
					query = "SELECT * FROM users_demo WHERE email=" + "'" + userEmail + "'";
					rs = st.executeQuery(query);
					rs.next();
					query = "INSERT INTO admin_table VALUES(" + "'" + rs.getString(1) + "'" + ", " + "'"
							+ rs.getString(2) + "'" + ", " + "'" + rs.getString(3) + "'" + ")";
					query += "";

					st.executeQuery(query);

					query = "SELECT * FROM users_demo WHERE email=" + "'" + userEmail + "'";
					st = conn.createStatement();
					rs = st.executeQuery(query);
					rs.next();

					/* find user, reconstruct user object */
					if (rs.getString(email + 1).equalsIgnoreCase(userEmail)) {

						temp = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
								rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));

					}

					/* If they are signed up for notifications send them one */
					if (temp.isSignedUpForSMSNotifications()) {
						notificationServer.setPhoneNumber(temp.getPhoneNumber());
						notificationServer
								.setTextMessage("Hi " + temp.getName() + ", you havee been promoted to admin level");
						notificationServer.sendNotification();
					}

					return true;

				}

			}

		} catch (SQLException e) {

			System.out.println("Something went wrong, could not promote user to admin");
			e.printStackTrace();

		}

		return false;

	}

	public boolean unblockUser(String userEmail, SMSNotificationServer notificationServer) {

		try {

			query = "SELECT * FROM users_demo";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {

				/* if we find user, then block them */
				if (rs.getString(email + 1).equalsIgnoreCase(userEmail)) {

					query = "UPDATE users_demo SET is_blocked=" + "'false'" + " WHERE email=" + "'" + userEmail + "'";
					rs = st.executeQuery(query);
					return true;

				}

			}

		} catch (SQLException e) {

			System.out.println("Something went wrong, could not unblock account");
			e.printStackTrace();

		}

		return false;

	}

	public User getUser(String userEmail, boolean createAccount) {

		try {

			query = "SELECT * FROM users_demo WHERE email=" + "'" + userEmail + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			rs.next();

			/* if we find user contruct them and return them */
			if (rs.getString(email + 1).equalsIgnoreCase(userEmail)) {

				User temp = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));

				/* get users account information from account table and set their account */
				if (createAccount) {
					query = "SELECT * FROM account_table WHERE email=" + "'" + userEmail + "'";
					st = conn.createStatement();
					rs = st.executeQuery(query);
					rs.next(); /* advance pointer */
					temp.account.setBalance(rs.getFloat(2));
					temp.account.setLoanBalance(rs.getFloat(4));
				}

				return temp;

			}

		} catch (SQLException e) {

			System.out.println("Something went wrong, could not return user");
			e.printStackTrace();

		}

		return null;

	}

	public boolean withDrawFunds(float amount, String userEmail) {

		User temp = null;
		boolean withdrawWasSuccessfull;

		try {

			query = "SELECT * FROM users_demo WHERE email=" + "'" + userEmail + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			rs.next();

			/* find user, reconstruct user object */
			if (rs.getString(email + 1).equalsIgnoreCase(userEmail)) {

				temp = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));

			}

			/* get users account information from account table and set their account */
			query = "SELECT * FROM account_table WHERE email=" + "'" + userEmail + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			rs.next(); /* advance pointer */
			temp.account.setBalance(rs.getFloat(2));
			temp.account.setLoanBalance(rs.getFloat(4));

			/* withdraw the money that needs to be withdrawn */
			withdrawWasSuccessfull = temp.account.withdraw(amount);

			/* write back to their account */
			if (withdrawWasSuccessfull) {
				query = "UPDATE account_table SET balance=" + "'" + temp.account.getBalance() + "'" + "WHERE email="
						+ "'" + temp.getEmail() + "'";
				st = conn.createStatement();
				rs = st.executeQuery(query);
				return true;
			}

		} catch (SQLException e) {

			System.out.println("Something went wrong, could not return user");
			e.printStackTrace();

		}

		return false;

	}

	public void depositFunds(float amount, String userEmail) {

		User temp = null;
		boolean returnValue;

		try {

			query = "SELECT * FROM users_demo WHERE email=" + "'" + userEmail + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			rs.next();

			/* find user, reconstruct user object */
			if (rs.getString(email + 1).equalsIgnoreCase(userEmail)) {

				temp = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));

			}

			/* get users account information from account table and set their account */
			query = "SELECT * FROM account_table WHERE email=" + "'" + userEmail + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			rs.next(); /* advance pointer */
			temp.account.setBalance(rs.getFloat(2));
			temp.account.setLoanBalance(rs.getFloat(4));

			/* deposit cash */
			temp.account.deposit(amount);

			/* write back to their account */
			query = "UPDATE account_table SET balance=" + "'" + temp.account.getBalance() + "'" + "WHERE email=" + "'"
					+ temp.getEmail() + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);

		} catch (SQLException e) {

			System.out.println("Something went wrong, could not deposit funds.");
			e.printStackTrace();

		}

	}

	public boolean applyForLoan(String userEmail, int amount) {
		User temp = null;

		try {

			query = "SELECT * FROM users_demo WHERE email=" + "'" + userEmail + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			rs.next();

			/* find user, reconstruct user object */
			if (rs.getString(email + 1).equalsIgnoreCase(userEmail)) {

				temp = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));

			}

			/* get users account information from account table and set their account */
			query = "SELECT * FROM account_table WHERE email=" + "'" + userEmail + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			rs.next(); /* advance pointer */
			temp.account.setBalance(rs.getFloat(2));
			temp.account.setLoanBalance(rs.getFloat(4));

			/* Loan criteria */

			int creditScore = temp.getCreditScore();

			if (creditScore <= 320) {

				return false;

			} else if ((creditScore > 320) && (creditScore <= 650) && (amount <= 3000)) {

				temp.setHasLoan();
				temp.account.setLoanBalance(amount);
				query = "UPDATE account_table SET loan_balance=" + "'" + temp.account.getLoanBalance() + "'"
						+ " WHERE email=" + "'" + temp.getEmail() + "'";
				rs = st.executeQuery(query);
				query = "UPDATE users_demo SET has_loan=" + "'" + temp.hasLoan() + "'" + " WHERE email=" + "'"
						+ temp.getEmail() + "'";
				rs = st.executeQuery(query);

				return true;

			} else if ((creditScore > 650) && (creditScore <= 750) && (amount <= 8000)) {

				temp.setHasLoan();
				temp.account.setLoanBalance(amount);
				query = "UPDATE account_table SET loan_balance=" + "'" + temp.account.getLoanBalance() + "'"
						+ " WHERE email=" + "'" + temp.getEmail() + "'";
				rs = st.executeQuery(query);
				query = "UPDATE users_demo SET has_loan=" + "'" + temp.hasLoan() + "'" + " WHERE email=" + "'"
						+ temp.getEmail() + "'";
				rs = st.executeQuery(query);
				return true;

			} else if ((creditScore > 750) && (amount <= 20000)) {

				temp.setHasLoan();
				temp.account.setLoanBalance(amount);
				query = "UPDATE account_table SET loan_balance=" + "'" + temp.account.getLoanBalance() + "'"
						+ " WHERE email=" + "'" + temp.getEmail() + "'";
				rs = st.executeQuery(query);
				query = "UPDATE users_demo SET has_loan=" + "'" + temp.hasLoan() + "'" + " WHERE email=" + "'"
						+ temp.getEmail() + "'";
				rs = st.executeQuery(query);
				return true;

			}

		} catch (SQLException e) {

			System.out.println("Something went wrong, could not deposit funds.");
			e.printStackTrace();

		}

		return false;

	}

	public boolean checkIfUserAlreadyHasLoan(String userEmail) {
		User temp = null;

		try {

			query = "SELECT * FROM users_demo WHERE email=" + "'" + userEmail + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			rs.next();

			/* find user, reconstruct user object */
			if (rs.getString(email + 1).equalsIgnoreCase(userEmail)) {

				temp = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));

			}

			if (temp.hasLoan())
				return true;

		} catch (SQLException e) {

			System.out.println("Something went wrong, could not deposit funds.");
			e.printStackTrace();

		}

		return false;

	}

	public boolean makePaymentOnLoan(String userEmail, float amount) {

		User temp = null;

		try {

			query = "SELECT * FROM users_demo WHERE email=" + "'" + userEmail + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			rs.next();

			/* find user, reconstruct user object */
			if (rs.getString(email + 1).equalsIgnoreCase(userEmail)) {

				temp = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9));

			}

			/* get users account information from account table and set their account */
			query = "SELECT * FROM account_table WHERE email=" + "'" + userEmail + "'";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			rs.next(); /* advance pointer */
			temp.account.setBalance(rs.getFloat(2));
			temp.account.setLoanBalance(rs.getFloat(4));

			boolean successfullPayment = temp.account.makePaymentOnLoan(amount);
			if (successfullPayment) {
				if (temp.account.getLoanBalance() == 0)
					temp.doesNotHaveLoan();
			}

			if (successfullPayment) {

				query = "UPDATE account_table SET loan_balance=" + "'" + temp.account.getLoanBalance() + "'" + ","
						+ "balance=" + "'" + temp.account.getBalance() + "'" + " WHERE email=" + "'" + temp.getEmail()
						+ "'";
				rs = st.executeQuery(query);
				query = "UPDATE users_demo SET has_loan=" + "'" + temp.hasLoan() + "'" + "WHERE email=" + "'"
						+ temp.getEmail() + "'";
				rs = st.executeQuery(query);

				return true;
			}

		} catch (SQLException e) {

			System.out.println("Something went wrong, could not deposit funds.");
			e.printStackTrace();

		}

		return false;

	}

	public void cleanUp() {

		try {

			rs.close();
			conn.close();
			st.close();

		} catch (SQLException e) {

			System.out.println("Could not close resources properly.");
			e.printStackTrace();

		}

	}

}
