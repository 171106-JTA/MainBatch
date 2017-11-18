package com.revature.BankAccount;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.server.SocketSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.dao.UserDaoImplement;

//import d3.revature.logging.LoggingExample;

//import com.Project1.bankAccountStuff.User;

/**
 * Main class controlling application
 * 
 * @author Evan
 *
 */
public class Driver {
	private static final String databaseFile = "database.txt"; // File containing database
	private HashMap<String, User> db;
	private User currentUser;
	protected UserDaoImplement dao; 
	
	final static Logger logger = Logger.getLogger(Driver.class);

	// Status values for user accounts
	private static final int status_approvalPending = 0;
	private static final int status_active = 1;
	private static final int status_locked = 2;
	private static final int permission_client = 0;
	private static final int permission_admin = 1;
	// Used the tutorials listed below for the ObjectInputStream and
	// ObjectOutputStream
	// http://www.studytonight.com/java/serialization-and-deserialization.php
	// http://www.tutorialspoint.com/java/io/objectinputstream_readobject.htm
	// Date Accessed: 11/10/2017
	ObjectInputStream ois;
	ObjectOutputStream oos;

	/*
	 * To Do's: Add a Username field to the User class. Propogate this change
	 * through all relevant functions in Main. For login() function, allow user to
	 * enter 'q' or something to go back. Disable 'q' as a valid password. Convert
	 * if-else chains to switch statements (To practice working with switch
	 * statements). Clean up console output with clear-console statements. Refactor
	 * database setup in the unit tests to abstrace the databae setup
	 */

	/**
	 * Main method
	 * 
	 * @param args
	 *            User input (not used for this application)
	 */
	public static void main(String[] args) {
		Driver mp = new Driver();
		mp.db = new HashMap<String, User>();
		
		//Basic database insert!!!!
		mp.dao = new UserDaoImplement();
//		String tmp = "y";
//		mp.dao.createUser(new User(tmp, tmp, tmp, tmp, tmp, 0, 0, 0));
//		
		
		boolean exit = false;
		
		logger.trace("Application Start");

		// Read database from the file
		try {
			logger.trace("Reading Database");
			mp.readDatabase(mp.databaseFile);
		} catch (IOException e1) {
			logger.error("Error trying to close the ObjectInputStream while reading the database.", e1);
			e1.printStackTrace();
		} // To Do: Any finally statement?

		// Print at the start of the program
		System.out.println("Welcome");

		// Loop over the Main Menu, until user decides to stop
		while (!exit) {
			String userChoice = mp.mainMenu();
			exit = mp.controlLogic(userChoice);
		}

		// Save the database to the 'database.txt' file. Erase whatever is in the file
		// currently
		// To Do: Might be able to abstract this try/catch statement with the one at
		// readDatabase() call
		try {
			logger.trace("Ending Applicatin. Saving Database to file");
			mp.saveDb();
		} catch (IOException e) {
			logger.error("Error while closing ObjectOutputStream when saving database", e);
			e.printStackTrace();
			// To D o: Return message with catch statement???
		} // To Do: Any finally statement?
	}

	/**
	 * Controls execution of program after the main menu
	 * 
	 * @param userChoice
	 *            The choice of the user from the main menu
	 * @return Return TRUE if
	 */
	public boolean controlLogic(String userChoice) {
		boolean exit = false;

		if (userChoice.equals("1")) {
			boolean loggedIn = login();

			// If successfully logged in, check the user's status
			if (loggedIn) {
				// Only advance if status = 'status_active'
				int status = currentUser.getStatus();
				if (status == status_approvalPending) {
					System.out.println("Account Approval Pending");
					logger.trace("User's account is not approved");
				} else if (status == status_locked) {
					System.out.println("Account Locked");
					logger.trace("User's account is locked");
					// If login was successful, call either the User or Admin menu, depending on
					// permission level
				} else if (status == status_active) {
					System.out.println("Successfully Logged In!");
					int permissions = currentUser.getPermissions();
					if (permissions == permission_client) {
						logger.trace("User is logged in as a Client");
						System.out.println("Client Permission");
						clientMenu();
					} else if (permissions == permission_admin) {
						logger.trace("User is logged in as an Admin");
						System.out.println("Admin Permision");
						adminMenu();
					}
				} else {
					// To Do: Put throw statement here with custom error message
					System.out.println("FATAL ERROR: Should NEVER See This!!!!");
					logger.error("FATAL ERROR: Should NEVER See This!!!!");
					System.exit(1);
				}
			} else {
				logger.trace("User is not logged in");
				System.out.println("NOT Logged In");
			}
		} else if (userChoice.equals("2")) {
			createNewClientAccount();
		} else if (userChoice.equals("3")) {
			System.out.println("Closing Program");
			exit = true;
		} else if (userChoice.equals("42")) { // Testing user creation
			default_database();
			// sample_database_1();
			// sample_database_2();

			// Print database. For coding purposes
			System.out.println("The Database!!!!: ");
			for (String key : db.keySet()) {
				System.out.println(db.get(key));
			}
		} else {
			// Add counter and exit after 5 incorrect attempts
			System.out.println("Not an option");
		}

		return exit;
	}

	public void default_database() {
		String firstName = "Evan";
		String lastName = "West";
		String middleInitial = "A";
		String username = "evanwest";
		String password = "password";
		int permissions = 1;
		int status = 1;
		int accountAmount = 0;
		User default_user_1 = new User(firstName, lastName, middleInitial, username, password, permissions, status,
				accountAmount);

		db.put(default_user_1.getUsername(), default_user_1);
	}

	/**
	 * Read serialized HashMap object from file and store in the 'db' member object.
	 * 
	 * @param fileName
	 *            Name of file containing serialized HashMap database
	 * @throws IOException
	 *             Throws this exception if input stream cannot be closed (in
	 *             finally statement)
	 */
	@SuppressWarnings("unchecked")
	public void readDatabase(String fileName) throws IOException {
		try {
			// Read in File
			ois = new ObjectInputStream(new FileInputStream(fileName));

			/*
			 * To Do: There is a warning generated here because casting the 'Object'
			 * returned from readObject to a HashMap. See the link below for a possible fix
			 * https://stackoverflow.com/questions/262367/type-safety-unchecked-cast
			 */
			db = (HashMap<String, User>) ois.readObject();

			// Print database. For coding purposes
			// System.out.println("The Database!!!!: ");
			// for (String key : db.keySet()) {
			// System.out.println(this.db.get(key));
			// }
		} catch (ClassNotFoundException e) {
			// To Do: Fill in
			e.printStackTrace();
		} finally {
			// Close input stream
			if (ois != null) {
				ois.close();
			}
		}
	}

	/**
	 * Displays initial menu
	 * 
	 * @return Contains user's choice from the initial menu
	 */
	private String mainMenu() {
		System.out.println("=======================================");
		System.out.println("Main Menu");
		System.out.println("=======================================");
		System.out.println("1) Login");
		System.out.println("2) Create New Account");
		System.out.println("3) Exit");
		System.out.print("Choice: ");

		String userInput = getUserInput();
		// Build input validation later
		logger.trace("Main Menu: user choice = " + userInput);
		return userInput;
		// StringTokenizer st = new StringTokenizer(s);
	}

	/**
	 * Get username and password from user and compare against the database
	 * 
	 * @return True - successful login. False - Too many login attempts, end program
	 */
	private boolean login() {
		String username = loginInstructions_username();
		String password = loginInstructions_password();
		return loginLogic(username, password);
	}

	private String loginInstructions_username() {
		System.out.println("Username: "); // re-enter password at some point
		return getUserInput();
	}

	private String loginInstructions_password() {
		System.out.println("Password: "); // re-entered password at some point
		return getUserInput();
	}

	public boolean loginLogic(String username, String password) {
		boolean loggedIn = false;
		
		User user = dao.checkIfUserExists(username, password);
//		System.out.println("The User: " + user);
		
		if(user != null) {
			this.currentUser = user;

			// Signal that the user is logged in
			// Note: Do this, even though a user with status = 0 or 2 is not technically
			// logged in. These cases are handled in the controlLogic() function
			loggedIn = true;
		} else {
			logger.trace("Incorrect Username or Password");
			System.out.println("Incorrect Username or Password");
		}
		
//		if (this.db.containsKey(username)) {
//			if (password.equals(this.db.get(username).getPassword())) {
//				// Fetch the current user's information from the database
//				this.currentUser = new User(this.db.get(username));
//
//				// Signal that the user is logged in
//				// Note: Do this, even though a user with status = 0 or 2 is not technically
//				// logged in
//				// This case is handled in the controlLogic() function
//				loggedIn = true;
//			} else {
//				logger.trace("incorrect password");
//				System.out.println("Incorrect password");
//			}
//		} else {
//			logger.trace("No user by the given id");
//			System.out.println("No user by that name\n\n");
//		}

		return loggedIn;
	}

	/**
	 * Allows user to create a new client account
	 */
	public void createNewClientAccount() {
		User newUser = getNewClientInfo();
		addNewClientToDatabase(newUser);
	}

	private User getNewClientInfo() {
		// Get username and password
		// Check username against existing usernames
		// If unique, add information
		@SuppressWarnings("resource")
		Scanner aScanner = new Scanner(System.in);

		// Variables to hold user's input
		String firstName, lastName, middleInitial, password, username;

		System.out.println("First Name: ");
		firstName = aScanner.nextLine();

		System.out.println("Last Name: ");
		lastName = aScanner.nextLine();

		System.out.println("Middle Initial: ");
		middleInitial = aScanner.nextLine();

		System.out.println("Username: "); // re-enter password at some point
		username = aScanner.nextLine();

		System.out.println("Password: "); // re-entered password at some point
		password = aScanner.nextLine();

		User newUser = new User(firstName, lastName, middleInitial, username, password);

		return newUser;
	}

	public void addNewClientToDatabase(User newUser) {
		// To Do: Duplicate check. And test case for this duplicate check
		// db as a key
		// To Do: Run the object serialization through a ?hash function?

		if (db.containsKey(newUser.getUsername())) {
			System.out.println("User already exists");
		} else {
			logger.trace("Adding new user to database");
			db.put(newUser.getUsername(), newUser);
		}
	}

	/**
	 * Get user's response to menus
	 * 
	 * @return Returns user's choice
	 */
	private String getUserInput() {
		@SuppressWarnings("resource")
		Scanner aScanner = new Scanner(System.in);
		return aScanner.nextLine().toString();
	}

	////////////////////////////////////////////////////////
	// Admin Functionality
	////////////////////////////////////////////////////////
	/**
	 * Display menu for Admins, get the Admin's choice, and validate the Admin's
	 * choice
	 */
	private void adminMenu() {
		logger.trace("Diplaying admin menu");
		boolean loop = true;

		String[] options = new String[] { "1", "2", "3", "4", "5" };
		List<String> validOptions = Arrays.asList(options);

		while (loop) {
			// Get input from Admin
			boolean validInput = false;
			int loopLimitCounter = 0;
			final int loopLimit = 5;
			String userInput = null;
			while (!validInput) {
				displayAdminMenu();
				userInput = getUserInput();
				logger.trace("user input: " + userInput);
				validInput = validOptions.contains(userInput);
				if (!validInput) {
					if (loopLimitCounter < loopLimit) {
						logger.trace("Incorrect Input");
						System.out.println("Not an option");
						loopLimitCounter += 1;
					} else {
						logger.trace("Too many invalid inputs for the Admin Menu");
						System.out.println("Too many invalid optoins");
						loop = false; // End the Administator's menu loop
						validInput = true; // End the validation loop
					}
				}
			}

			if (loop) {
				// To Do: Refactor these functions to extrapolate common functionality
				// If user chose a valid option,
				if (userInput.equals("1")) {
					approveClientAccount();
				} else if (userInput.equals("2")) {
					lockClientAccount();
				} else if (userInput.equals("3")) {
					unlockClientAccount();
				} else if (userInput.equals("4")) {
					promoteClientToAdmin();
				} else if (userInput.equals("5")) {
					loop = false;
				} else {
					System.out.println("FATAL ERROR!!!! Should not see this. IN adminMenu()");
				}
			}
		}
	}

	/**
	 * Display the possible actions for Admins
	 */
	private void displayAdminMenu() {
		System.out.println("=======================================");
		System.out.println("Administrator Menu");
		System.out.println("=======================================");
		System.out.println("1) Approve Client Accounts");
		System.out.println("2) Lock Client Accounts");
		System.out.println("3) Unlock Client Accounts");
		System.out.println("4) Promote Clients to Administrators");
		System.out.println("5) Logout");
		System.out.print("Choice: ");
	}

	/**
	 * Functionality for admins to unlock client accounts
	 */
	public void unlockClientAccount() {
		displayLockedAccounts();
		String input = getUserInput(); // Get admin's input for account to unlock
//		getAndUnlockAccount(input);
		
		checkAndChangeStatusAndPermissions(input, status_locked, permission_client, status_active, permission_client);
	}

	/**
	 * Display all currently locked accounts (i.e. the accounts able to be unlocked)
	 */
	private void displayLockedAccounts() {
		System.out.println("Usernames for currently locked accounts");

		List<String> lockedUserAccounts = getAListOfUsers(status_locked, permission_client);
		for (String item : lockedUserAccounts) {
			System.out.println(item);
		}
		System.out.println("\n");
		System.out.println("Enter Account To Lock: ");
	}

	/**
	 * Functionality for admins to lock a client accounts
	 */
	public void lockClientAccount() {
		displayUnlockedAccounts();
		String input = getUserInput(); // Get Admins choice of account to lock
//		getAndLockAccount(input); // If account
		checkAndChangeStatusAndPermissions(input, status_active, permission_client, status_locked, permission_client);
	}

	/**
	 * Display unlocked accounts (i.e. accounts that can be locked)
	 */
	private void displayUnlockedAccounts() {
		System.out.println("Usernames for currently unlocked accounts");

		List<String> unlockedUserAccounts = getAListOfUsers(status_active, permission_client);
		for (String item : unlockedUserAccounts) {
			System.out.println(item);
		}
		System.out.println("\n");
		System.out.println("Enter Account To Lock: ");
	}

	/**
	 * Get user id from Admin and approve the given client's account
	 */
	public void approveClientAccount() {
		displayAccountsNeedingApproval();
		String input = getUserInput();
//		getAndApproveAccount(input);
		checkAndChangeStatusAndPermissions(input, status_approvalPending, permission_client, status_active, permission_client);
	}

	/**
	 * Display accounts that need approving
	 */
	private void displayAccountsNeedingApproval() {
		System.out.println("Usernames for accounts needing approval");

		List<String> approvalPendingAccounts = getAListOfUsers(status_approvalPending, permission_client);
		for (String item : approvalPendingAccounts) {
			System.out.println(item);
		}
		System.out.println("\n");
		System.out.println("Enter Account To Approve: ");
	}

	/**

	/**
	 * Allows admins to promote clients to admin status
	 */
	public void promoteClientToAdmin() {
		displayClients();
		String input = getUserInput();
//		getAndPromoteClient(input);
		
		//Only promote active user accounts. May want to refactor this at some point
		checkAndChangeStatusAndPermissions(input, status_active, permission_client, status_active, permission_admin);
	}

	/**
	 * Display all clients in the databae (i.e. all entries in the database that are
	 * client and not admin)
	 */
	private void displayClients() {
		System.out.println("Usernames for accounts needing approval");

		List<String> approvalPendingAccounts = getAListOfUsers(status_active, permission_client);
		for (String item : approvalPendingAccounts) {
			System.out.println(item);
		}
		System.out.println("\n");
		System.out.println("Enter Account To Approve: ");
	}

	/**
	 * Get a list of users from the database that meet the status and permissions requirements
	 * @param status		Holds the status of users to fetch from the database
	 * @param permissions	Holds the permission of the users to fetch from the database
	 * @return				Returns a list of strings containing the usernames of all users 
	 * 						that meet the status and permission requirements
	 */
	private List<String> getAListOfUsers(final int status, final int permissions) {
		// Loop through db and search for users meetings the specified conditions
		// To Do: Find a better way to do this than O(n)
		List<String> accountClients = new ArrayList<String>();
		for (String key : this.db.keySet()) {
			User user = this.db.get(key);
			if (user.getPermissions() == permissions && user.getStatus() == status) {
				accountClients.add(key);
			}
		}
		return accountClients;
	}	
	
	/**
	 * Change status and permissions of a user, if existing status and permission conditions exist
	 * @param input 				Username of account to change
	 * @param currentStatus 		The current expected status
	 * @param currentPermission		The current expected permission
	 * @param newStatus				The status to be stored if the conditions are met
	 * @param newPermission			The permission to be stored if the conditions are met
	 */
	private void checkAndChangeStatusAndPermissions(String input, final int currentStatus, final int currentPermission, 
		final int newStatus, final int newPermission) {		
		User user = this.db.get(input);

		// Check if designated user exists and currently has the desired status and desired permission\
		// If so, set the user's status and password to the newStatus and newPermissions
		// Note: nested if-statements allows unique error message based on situation)
		if (user != null) {
			if (user.getStatus() == currentStatus && user.getPermissions() == currentPermission) {
				//Set the new status and permissions and save the changes to the database 
				user.setStatus(newStatus);
				user.setPermissions(newPermission);
				this.db.put(user.getUsername(), user);
				
				//Log results
				System.out.println("Account Changed!");
				logger.trace("Changing account of user: " + user.getUsername());
			} else { //Account cannot be locked (either because Admin, already locked, or currently not approved)
				logger.trace("Account: " + user.getUsername() + " cannot be changed");
				System.out.println("Account: " + user.getUsername() + " cannot be changed");
			}
		} else { //Account does not exist 
			logger.trace("Account: " + input + " is not in the database");
			System.out.println("User is not in the database");
		}
	}

	////////////////////////////////////////////////////////
	// Client Functionality
	////////////////////////////////////////////////////////
	/**
	 * Display menu for Client's, get Clien'ts choice, and validate Client's choice
	 */
	private void clientMenu() {
		logger.trace("Diplaying client menu");
		boolean loop = true;

		String[] options = new String[] { "1", "2", "3", "4" };
		List<String> validOptions = Arrays.asList(options);

		while (loop) {
			// Get input from Client
			boolean validInput = false;
			int loopLimitCounter = 0;
			final int loopLimit = 5;
			String userInput = null;
			while (!validInput) {
				displayClientMenu();
				userInput = getUserInput();
				logger.trace("user input: " + userInput);
				validInput = validOptions.contains(userInput);
				if (!validInput) {
					if (loopLimitCounter < loopLimit) {
						logger.trace("Incorrect Input");
						System.out.println("Not an option");
						loopLimitCounter += 1;
					} else {
						logger.trace("Too many invalid options");
						System.out.println("Too many invalid optoins");
						loop = false; // End the Administator's menu loop
						validInput = true; // End the validation loop
					}
				}
			}

			if (loop) {
				// To Do: Refactor these functions to extrapolate common functionality
				// If user chose a valid option,
				if (userInput.equals("1")) {
					System.out.println("Option 1");
					displayAccountBalance();
				} else if (userInput.equals("2")) {
					System.out.println("Option 2");
					deposit();
				} else if (userInput.equals("3")) {
					System.out.println("Option 3");
					withdraw();
				} else if (userInput.equals("4")) {
					System.out.println("Logging Out");
					loop = false;
				} else {
					logger.error("FATAL ERROR!!!! Should not see this. IN adminMenu()");
					System.out.println("FATAL ERROR!!!! Should not see this. IN adminMenu()");
				}
			}
		}

	}

	/**
	 * Client menu
	 */
	private void displayClientMenu() {
		System.out.println("=======================================");
		System.out.println("Client Menu");
		System.out.println("=======================================");
		System.out.println("1) Print Account Balance");
		System.out.println("2) Deposit");
		System.out.println("3) Withdraw");
		System.out.println("4) Logout");
		System.out.print("Choice: ");
	}

	/**
	 * Print the account balance for the current user
	 */
	private void displayAccountBalance() {
		System.out.println("Current Balance: " + currentUser.getAccountAmount());
	}

	/**
	 * Get and deposit an amount from the user
	 */
	private void deposit() {
		String temp = depositInstructions();
		try {
			double amount = Double.parseDouble(temp);

			setAmount(amount);
		} catch (NumberFormatException ne) {
			System.out.println("Not a valid number");
		}
	}

	private String depositInstructions() {
		System.out.println("Enter amount to deposit: ");
		return (getUserInput());
	}

	public void setAmount(double amount) {
		amount += currentUser.getAccountAmount();
		currentUser.setAccountAmount(amount);

		this.db.put(currentUser.getUsername(), currentUser);
	}

	/**
	 * Get and withdraw an amount from the user
	 */
	private void withdraw() {
		System.out.println("Enter amount to withdraw: ");
		String temp = getUserInput();

		try {
			double amount = Double.parseDouble(temp);
			double curAccount = currentUser.getAccountAmount();

			// Check if the withdraw amount is valid
			if (curAccount - amount > 0) {
				amount = curAccount - amount;
				currentUser.setAccountAmount(amount);
				this.db.put(currentUser.getUsername(), currentUser);
			} else {
				System.out.println("Not enough in your account to withraw $" + amount);
			}
		} catch (NumberFormatException ne) {
			System.out.println("Not a valid number");
		}
	}

	/**
	 * Saves the database to a file at the end of the application
	 * 
	 * @throws IOException
	 *             Thrown when the ObjectOutputStream could not be closed
	 */
	public void saveDb() throws IOException {
		// To Do: Don't pass in database! use this.db to access!!!!!
		try {
			oos = new ObjectOutputStream(new FileOutputStream(databaseFile));
			oos.writeObject(this.db);
			oos.close();
		} catch (IOException e) {
			// To Do: Return message with catch statement???
			e.printStackTrace();
		} finally {
			if (oos != null) {
				oos.close();
			}
		}
	}

	////////////////////////////////////////////////////////
	// Functions for Unit Testing
	////////////////////////////////////////////////////////
	public HashMap<String, User> getDb() {
		return this.db;
	}

	public void addUserToDb(User user) {
		db.put(user.getUsername(), user);
	}

	public void setDb(HashMap<String, User> newDb) {
		db = newDb;
	}

	public void setCurrentUser(User user) {
		currentUser = user;
	}

	public User getCurrentUser() {
		return currentUser;
	}
}
