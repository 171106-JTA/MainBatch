package com.revature.main;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.dao.jBankDAO;
import com.revature.dao.jBankDAOImpl;
import com.revature.model.account.User;
import com.revature.model.account.UserLevel;

public class Driver {

	// Static constants to eliminate magic numbers;
	private static final int CREATE = 1;
	private static final int LOG_IN = 2;
	private static final int QUIT = 3;

	// Bank session status vars
	private static boolean bankOn;
	private int userChoice;
	private static ArrayList<User> userAccounts;

	// Set up for logging, scanning and streaming
	public final static Logger logger = Logger.getLogger(Driver.class);
	public static Scanner sc = new Scanner(System.in);
	public static ObjectOutputStream oos;

	public static void main(String... args) throws Exception {
		jBankDAO dao = new jBankDAOImpl();
		//Connection conn = ConnectionUtil.getConnection();
		System.out.println("hello");
		dao.getAllUser();
		
		
		Driver bankSession = new Driver();
		setBankOn(true);
		userAccounts = new ArrayList<>();

		do {
			bankSession.welcomePrompt();
			switch (bankSession.getUserChoice()) {
			case CREATE:
				bankSession.createAccount();
				break;
			case LOG_IN:
				bankSession.openSession();
				break;
			case QUIT:
				bankSession.quit();
				break;
			}
		} while (bankOn); // To quit will be from picking QUIT from switch statement
	}

	private void openSession() {
		// TODO Auto-generated method stub

	}

	private void createAccount() {
		String validInfo = "no";
		User newUser = new User();
		String firstName;
		String lastName;
		jBankDAO dao = new jBankDAOImpl();

		while (!validInfo.equals("yes")) {

			System.out.println("CREATE_ACCOUNT");
			do {
				System.out.print("username:>");
				newUser.setUsername(sc.nextLine());
			} while (checkDupUsername(newUser.getUsername()));

			System.out.print("password: ");
			newUser.setPassword(sc.nextLine());

			// saved in way that only first character of names are capitalized
			System.out.print("first name: ");
			firstName = sc.nextLine().toLowerCase();
			firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
			newUser.setFirstName(firstName);

			// For purpose of debugging only
			if (newUser.getUsername().equals("admin")) {
				System.out.println("set to admin!!");
				newUser.setUserLevel(UserLevel.ADMIN);
			}

			System.out.print("last name: ");
			lastName = sc.nextLine().toLowerCase();
			lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
			newUser.setLastName(lastName);

			// add regex later
			System.out.println("PIN: ");
			newUser.setPin(sc.nextInt());
			sc.nextLine();
			// take yes or no, yes can be cased anyways but must be typed "YES"
			// come back to implement function that only takes yes + y variants and no + n
			// variants,
			System.out.println(newUser + "\n\nAre these informations correct?(yes/no)");
			validInfo = sc.nextLine();
			validInfo = validInfo.toLowerCase();
		}
		logger.trace("Account created.");
		dao.createUser(newUser);
		dao.commitQuery();
	}

	public void welcomePrompt() {
		int temp;
		System.out.println("----------------------------------------------------------------");
		System.out.println("> Welcome to jBank, How can we assist you today?\n");
		System.out.println("1> CREATE AN ACCOUNT");
		System.out.println("2> LOG_IN");
		System.out.println("3> QUIT\n");

		do {
			System.out.println("> Choose a number from the list.");
			System.out.print("> ");
			while (!sc.hasNextInt()) {
				System.out.println("> Please enter a number!");
				sc.nextLine();
			}
			temp = sc.nextInt();
		} while (temp < CREATE || temp > QUIT);
		setUserChoice(temp);
		sc.nextLine();
	}

	public boolean checkDupUsername(String username) {
		for (User user : userAccounts) {
			if (user.getUsername().equals(username)) {
				System.out.println("username exists!");
				return true;
			}
		}
		return false;
	}

	/**
	 * Quit.
	 */
	public void quit() {
		System.out.print("Thank you for banking with us, Good bye.");
		bankOn = false;
	}

	public static boolean isBankOn() {
		return bankOn;
	}

	public static void setBankOn(boolean bankStatus) {
		bankOn = bankStatus;
	}

	public int getUserChoice() {
		return userChoice;
	}

	public void setUserChoice(int userChoice) {
		this.userChoice = userChoice;
	}

}
