package com.revature.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.log.LogUtil;
import com.revature.util.ConnectionUtil;

/**
 * open/close connection to: 1. login/confirm user existence 2. create new user
 * in db
 */
public class Driver {
	/**
	 * Defines static variables used throughout program, e.g. input scanner, User
	 * object array list
	 */
	public static List<User> userList = new ArrayList<User>(); // static? w/in main?
	public static Scanner scanner = new Scanner(System.in);
	// static final int UNAPPROVED = 0;
	// static final int APPROVED = 1;
	// static final int BLOCKED = 2;
	// static final int DELETE = 3;
	// static Cereal cereal = new Cereal();

	/**
	 * Starts program by loading populated arraylist if it exists and prompting
	 * login or account creation. Directs users to basic functionality upon
	 * successful login; prevents them from acessing their account if unapproved by
	 * admin or blocked; prevents duplicate usernames.
	 * 
	 * @param args
	 * @throws Exception
	 *             generated when .ser file doesn't exist.
	 */
	public static void main(String[] args) throws Exception {

		boolean running = true;
		// User OG = new User("mnlwsn", "master", 1000, true, 1);
		// userList.add(OG);

		System.out.println("Welcome to the National Bank of Second Chances; this is your third~!");
		while (running) {
			System.out.println("\n> login  > create  > quit");
			String input = scanner.next();
			// int attempts = 2;

			switch (input) {
			case "login":
				/*
				 * option 1: allow 3 attempts to login, block acct afterward (harder, while loop
				 * to iterate through rs of all usr/pswrd in db, nested if loops option 2: allow
				 * infinite attempts to login, w keyword 'exit' to leave attempt loop (easier)
				 */
				try (Connection conn = ConnectionUtil.getConnection()) {
					String sql = "SELECT * FROM bank_users WHERE username=? AND userpassword=?";
					// String sql = "SELECT username, userpassword FROM bank_users";// WHERE
					// username=? AND userpassword=?";
					PreparedStatement ps = null;
					ResultSet rs = null;

					System.out.print("> username: ");
					String username = scanner.next();
					System.out.print("> password: ");
					String password = scanner.next();

					ps = conn.prepareStatement(sql);
					ps.setString(1, username);
					ps.setString(2, password);

					rs = ps.executeQuery();
					while (rs.next()) { // returns false and skips loop if rs is empty, i.e. user dne; if true, should
										// only return one record
						Integer active = Integer.parseInt(rs.getString(5));
						if (active == 1)
							Account.displayBalance();
						else if (active == 0)
							System.out.println("Account has not yet been activated. Try again later.");
						else if (active == 2)
							System.out.println("Account has been blocked; wait for reactivation.");
						else if (active == 3)
							System.out.println("Your account has been marked for deletion and is no longer active.");

						continue;
					}
					System.out.println( "\nWrong credentials provided, or account does not yet exist. To create a new one, type 'create'");
					continue;
				}

			// ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––

			case "create":
				User newUser = new User();
				System.out.println("\nPlease choose a username:");
				input = scanner.next();
				while (!isAvailable(input)) {
					System.out.println("\nUsername is already taken; please choose another:");
					input = scanner.next();
				}
				newUser.setUserID(input);
				System.out.println("\nPlease input a password to finish account creation:");
				input = scanner.next();
				while (!standardMet(input)) {
					System.out.println("\nPasswords must be at least 5 characters long. Try again:");
					input = scanner.next();
				}
				newUser.setPswrd(input);
				userList.add(newUser);
				System.out.println(
						"\nThanks for creating a new account. Your account is under review and will be approved shortly. Attempt logging in then.");
				continue;

			// ––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––

			case "quit":
				break;

			default:
				System.out.println("\nPlease input a valid prompt: ");
				continue;
			}

			running = false;
		}

	}



	/**
	 * Checks userid availability/validity
	 * 
	 * @param input
	 *            from console
	 * @return true if available
	 */
	private static boolean isAvailable(String input) {
		while (!standardMet(input)) {
			System.out.println("\nUsername must be at least 5 characters.");
			input = scanner.next(); // check for spaces?
		}
		if (User.returnExisting(input).getUserID().equals(" "))
			return true; // if return existing returns empty id, means user dne previously
		else
			return false; // may allow users w same 5 letters but diff cases
	}

	/**
	 * Checks password validity
	 * 
	 * @param input
	 *            from console
	 * @return true if input >= 5 characters
	 */
	private static boolean standardMet(String input) {
		if (input.length() < 5)
			return false;
		return true;
	}
}
// option 3
// =======================================================================
// if (username.equals(rs.getString(2))) {
// if(password.equals(rs.getString(3))) Account.displayBalance(null);
// }

// String storedusrnm = rs.getString(2);
// String storedpswrd = rs.getString(3);

// while (!username.equals(storedusrnm) || !password.equals(storedpswrd)) {
// System.out.println("Incorrect username or password; you have " + attempts +
// ((--attempts != 0) ? " attempts remaining." : " attempt remaining."));
// if (attempts == -1) {
// System.out.println("\nToo many wrong attempts, account is now locked. Please
// try again later.");
// //how to set blocked col status for current record?
// }
// System.out.println("\n> username:");
// username = scanner.next();
// System.out.println("\n> password:");
// password = scanner.next();
// }
// ==================================================================================