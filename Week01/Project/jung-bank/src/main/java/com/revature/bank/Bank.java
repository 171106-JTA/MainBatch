package com.revature.bank;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;
import com.revature.account.UserAccount;
import com.revature.account.UserLevel;

public class Bank {

	private String username;
	private String password;
	
	private int userChoice;
	static public boolean bankOn;

	// Define constants to eliminate magic numbers;
	public static final int CREATE = 1;
	public static final int LOG_IN = 2;
	public static final int QUIT = 3;

	// Object input stream and etc. for reading files
	static ObjectInputStream ois;
	public static final String FILE_NAME = "user_accounts.ser";
	public static ArrayList<UserAccount> userAccounts;

	// Set up for logging, scanning and streaming
	protected final static Logger logger = Logger.getLogger(Bank.class);
	public static ObjectOutputStream oos;
	Scanner sc = new Scanner(System.in);

	/**
	 * The main method.
	 *
	 * @param args
	 *
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Jung Bank \n\n");

		// set up and load data
		Bank myBank = new Bank();
		bankOn = true;
		userAccounts = new ArrayList<>();
		UserSession mySession = new UserSession();
		userAccounts = myBank.openFile(userAccounts);

		do {
			myBank.mainMenu();
			while (myBank.userChoice < CREATE || myBank.userChoice > QUIT) {
				System.out.println("Please select number from one of the choice and press /'Enter/'");
				myBank.mainMenu();
			}

			switch (myBank.getUserChoice()) {
			case CREATE:
				myBank.createAccount();
				break;
			case LOG_IN:
				mySession.openSession();
				break;
			case QUIT:
				myBank.quit();
				break;
			}
		} while (bankOn); // To quit will be from picking QUIT from switch statement

	}

	/**
	 * Open file.
	 *
	 * @param userAccounts
	 *            the user accounts
	 * @return the array list
	 */
	@SuppressWarnings("unchecked")
	// checked using if, didn't work, reinforce later;;
	public ArrayList<UserAccount> openFile(ArrayList<UserAccount> userAccounts) {

		try {
			ois = new ObjectInputStream(new FileInputStream(FILE_NAME));
			userAccounts = new ArrayList<>();
			// part where annotation was referring to
			userAccounts = (ArrayList<UserAccount>) ois.readObject();
			// for debug mode, loop lists all the user account objects 
//			for (int i = 0; i < userAccounts.size(); i++) {
//				System.out.println(userAccounts.get(i));
//			}
		} catch (IOException e) {
			// caught when opening file goes wrong
			// e.printStackTrace();
			logger.warn("It's likely that userlist files doesn't exist yet\nbut if its not that, do panic.");
		} catch (ClassNotFoundException e) {
			// caught when reading object goes wrong
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					// try close and not throws exception with function
					e.printStackTrace();
				}
			}
		}

		return userAccounts;
	}
	
	/**
	 * Search Array list for duplicate username.
	 *
	 * @param username
	 *            the username
	 * @return true, if successful
	 */
	public boolean checkDupUsername(String username) {
		for (UserAccount user : userAccounts) {
			if (user.getUsername().equals(username)) {
				System.out.println("username exists! ");
				return true;
			}
		}
		return false;
	}

	/**
	 * Takes in input from user and write newly created UserAccount object to the
	 * serialization file.
	 */
	public void createAccount() {
		String validInfo = "no";
		UserAccount newUser = new UserAccount();
		String firstName;
		String lastName;

		while (!validInfo.equals("yes")) {
			System.out.println("CREATE_ACCOUNT");

			do {
				System.out.print("username: ");
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
			newUser.setPin(sc.nextLine());

			// take yes or no, yes can be cased anyways but must be typed "YES"
			// come back to implement function that only takes yes + y variants and no + n
			// variants,
			System.out.println(newUser + "\n\nAre these informations correct?(yes/no)");
			validInfo = sc.nextLine();
			validInfo = validInfo.toLowerCase();

		}
		logger.trace("Account created.");
		userAccounts.add(newUser);
		updateUserList();
	}

	/**
	 * Prints out main menu selection and returns user input.
	 *
	 * @return User choice value for corresponding integer
	 */
	public void updateUserList() {
		try {
			oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
			oos.writeObject(userAccounts);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Prints main menu, nothing more nothing less.
	 */
	public void mainMenu() {
		System.out.println("Please enter the number that corresponds to your needs.");
		System.out.println("1) CREATE AN ACCOUNT");
		System.out.println("2) LOG_IN");
		System.out.println("3) QUIT");
		setUserChoice(sc.nextInt());
		sc.nextLine();
	}

	/**
	 * Quit.
	 */
	public void quit() {
		System.out.print("Thank you for banking with us, Good bye.");
		bankOn = false;
	}

	// Getters and Setters below.
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserChoice() {
		return userChoice;
	}

	public void setUserChoice(int userChoice) {
		this.userChoice = userChoice;
	}

}
