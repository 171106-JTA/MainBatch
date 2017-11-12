package com.revature.BankAccount;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

//import com.Project1.bankAccountStuff.User;

public class Driver {
	private static final String databaseFile = "database.txt"; // File containing database
	private HashMap<String, User> db;
	private User currentUser;

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
	 * statements). Clean up consol output with clear-console statements
	 */

	public static void main(String[] args) {
		Driver mp = new Driver();
		mp.db = new HashMap<String, User>();
		boolean exit = false;

		// Read database from the file
		try {
			mp.readDatabase(mp.databaseFile);
		} catch (IOException e1) {
			// To Do: Expand exception handling?
			e1.printStackTrace();
		} // To Do: Any finally statement?

		// Print at the start of the program
		System.out.println("Welcome");

		// Loop over the Main Menu
		while (!exit) {
			String userChoice = mp.mainMenu();
			// To Do: Refactor the control statements to a separate function
			// (i.e. No control statements in main
			if (userChoice.equals("1")) {
				boolean loggedIn = mp.login();

				// If successfully logged in, check the user's status
				if (loggedIn) {
					// Check status of user
					int status = mp.currentUser.getStatus();
					if (status == status_approvalPending) {
						System.out.println("Account Approval Pending");
					} else if (status == status_locked) {
						System.out.println("Account Locked");
						// If login was successful, call either the User or Admin menu, depending on
						// permission level
					} else if (status == status_active) {
						System.out.println("Successfully Logged In!");
						int permissions = mp.currentUser.getPermissions();
						if (permissions == permission_client) {
							System.out.println("Client Permission");
							// mp.clientMenu();
						} else if (permissions == permission_admin) {
							System.out.println("Admin Permision");
							mp.adminMenu();
						}
					} else {
						// To Do: Put throw statement here with custom error message
						System.out.println("FATAL ERROR #1: Should NEVER See This!!!!");
						System.exit(1);
					}
				} else {
					System.out.println("NOT Logged In");
				}
			} else if (userChoice.equals("2")) {
				mp.createNewClientAccount();
			} else if (userChoice.equals("3")) {
				System.out.println("Closing Program");
				exit = true;
			} else if (userChoice.equals("42")) { // Testing user creation
				String firstName = "Evan";
				String lastName = "West";
				String middleInitial = "A";
				String ssn = "0";
				String password = "0";
				int permissions = 0;
				int status = 0;
				int accountAmount = 0;
				User default_user_1 = new User(firstName, lastName, middleInitial, ssn, password, permissions, status,
						accountAmount);

				firstName = "Evan";
				lastName = "West";
				middleInitial = "A";
				ssn = "01";
				password = "01";
				permissions = 0;
				status = 0;
				accountAmount = 0;
				User default_user_1_b = new User(firstName, lastName, middleInitial, ssn, password, permissions, status,
						accountAmount);

				firstName = "Evan";
				lastName = "West";
				middleInitial = "A";
				ssn = "02";
				password = "02";
				permissions = 0;
				status = 0;
				accountAmount = 0;
				User default_user_1_c = new User(firstName, lastName, middleInitial, ssn, password, permissions, status,
						accountAmount);

				firstName = "A";
				lastName = "A";
				middleInitial = "A";
				ssn = "1";
				password = "1";
				permissions = 0;
				status = 1;
				accountAmount = 0;
				User default_user_2 = new User(firstName, lastName, middleInitial, ssn, password, permissions, status,
						accountAmount);

				firstName = "B";
				lastName = "B";
				middleInitial = "B";
				ssn = "2";
				password = "2";
				permissions = 0;
				status = 2;
				accountAmount = 0;
				User default_user_3 = new User(firstName, lastName, middleInitial, ssn, password, permissions, status,
						accountAmount);

				firstName = "Admin";
				lastName = "Admin";
				middleInitial = "A";
				ssn = "001";
				password = "001";
				permissions = 1;
				status = 1;
				accountAmount = 0;
				User default_user_4 = new User(firstName, lastName, middleInitial, ssn, password, permissions, status,
						accountAmount);

				mp.db.put(default_user_1.getSsn(), default_user_1);
				mp.db.put(default_user_2.getSsn(), default_user_2);
				mp.db.put(default_user_3.getSsn(), default_user_3);
				mp.db.put(default_user_4.getSsn(), default_user_4);
				mp.db.put(default_user_1_b.getSsn(), default_user_1_b);
				mp.db.put(default_user_1_c.getSsn(), default_user_1_c);

				// Print database. For coding purposes
				System.out.println("The Database!!!!: ");
				for (String key : mp.db.keySet()) {
					System.out.println(mp.db.get(key));
				}
			} else {
				// Add counter and exit after 5 incorrect attempts
				System.out.println("Not an option");
			}
		}

		// Save the database to the 'database.txt' file. Erase whatever is in the file
		// currently
		// To Do: Might be able to abstract this try/catch statement with the one at
		// readDatabase() call
		try {
			mp.saveDb();
		} catch (IOException e) {
			e.printStackTrace();
			// To Do: Return message with catch statement???
		} // To Do: Any finally statement?
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
	private void readDatabase(String fileName) throws IOException {
		try {
			// Read in File
			ois = new ObjectInputStream(new FileInputStream(databaseFile));

			/*
			 * To Do: There is a warning generated here because casting the 'Object'
			 * returned from readObject to a HashMap. See the link below for a possible fix
			 * https://stackoverflow.com/questions/262367/type-safety-unchecked-cast
			 */
			this.db = (HashMap<String, User>) ois.readObject();

			// Print database. For coding purposes
			System.out.println("The Database!!!!: ");
			for (String key : db.keySet()) {
				System.out.println(this.db.get(key));
			}
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

		Scanner aScanner = new Scanner(System.in);
		String userInput = aScanner.nextLine();
		// Build input validation later

		return userInput;
		// StringTokenizer st = new StringTokenizer(s);
	}

	/**
	 * Get username and password from user and compare against the database
	 * 
	 * @return True - successful login. False - Too many login attempts, end program
	 */
	private boolean login() {
		Scanner aScanner = new Scanner(System.in);
		String snn;
		String password;
		boolean loggedIn = false;

		System.out.println("Social Security Number: "); // re-enter password at some point
		String ssn = aScanner.nextLine();

		if (this.db.containsKey(ssn)) {
			boolean correctPassword = false; // Control's execution of password loop
			final int maxPasswordLoop = 5; // Maximum number of password retries

			// Password loop
			for (int i = 0; i < maxPasswordLoop && !correctPassword; i++) {
				System.out.println("Password: "); // re-entered password at some point
				password = aScanner.nextLine();
				if (password.equals(this.db.get(ssn).getPassword())) {
					// Stop the loop
					correctPassword = true;

					// Signal that the user is logged in
					loggedIn = true;

					// Fetch the current user's information from the database
					this.currentUser = new User(this.db.get(ssn));
				} else {
					System.out.println("Incorrect password");
				}
			}

			// Print message is user exceeded the number of password tries
			if (!correctPassword) {
				System.out.println("Too many incorrect tries\n");
			}
		} else {
			System.out.println("No user by that name\n\n");
		}

		return loggedIn;
	}

	/**
	 * Allows user to create a new client account
	 */
	private void createNewClientAccount() {
		// Get username and password
		// Check username against existing usernames
		// If unique, add information
		Scanner aScanner = new Scanner(System.in);

		// Variables to hold user's input
		// to do: Convert ssn to 9 digits, eventually
		// to do: Convert middleInitial to char
		String firstName, lastName, middleInitial, password, ssn;

		System.out.println("First Name: ");
		firstName = aScanner.nextLine();

		System.out.println("Last Name: ");
		lastName = aScanner.nextLine();

		System.out.println("Middle Initial: ");
		middleInitial = aScanner.nextLine();

		System.out.println("Social Security Number: "); // re-enter password at some point
		ssn = aScanner.nextLine();

		System.out.println("Password: "); // re-entered password at some point
		password = aScanner.nextLine();

		User newUser = new User(firstName, lastName, middleInitial, ssn, password);

		System.out.println(newUser.toString());

		// To Do: Duplicate check. And test case for this duplicate check
		// To Do: Run the ssn key through a Hash function so that it's not stored in the
		// db as a key
		// To Do: Run the object serialization through a ?hash function?
		this.db.put(newUser.getSsn(), newUser);

		// Print database. For coding purposes
		System.out.println("The Database!!!!: ");
		for (String key : db.keySet()) {
			System.out.println(this.db.get(key));
		}
	}

	/**
	 * Get user's response to menus
	 * 
	 * @return Returns user's choice
	 */
	private String getUserInput() {
		Scanner aScanner = new Scanner(System.in);
		return aScanner.nextLine().toString();
	}

	/**
	 * Display login menu, get user's choice, and validate user's choice
	 * 
	 * @return Returns user's choice
	 */
	private int loginMenu() {
		return 0;
	}

	/**
	 * Display menu for Admins, get the Admin's choice, and validate the Admin's
	 * choice
	 */
	private void adminMenu() {
		boolean loop = true;

		String[] options = new String[] { "1", "2", "3", "4", "5" };
		List<String> validOptions = Arrays.asList(options);

		while (loop) {
			//Get input from Admin
			boolean validInput = false;
			int loopLimitCounter = 0;
			final int loopLimit = 5;
			String userInput = null;
			while (!validInput) {
				displayAdminMenu();
				userInput = getUserInput();
				validInput = validOptions.contains(userInput);
				if (!validInput) {
					if (loopLimitCounter < loopLimit) {
						System.out.println("Not an option");
						loopLimitCounter += 1;
					} else {
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
					approveClientAccount();
				} else if (userInput.equals("2")) {
					System.out.println("Option 2");
					lockClientAccount();
				} else if (userInput.equals("3")) {
					System.out.println("Option 3");
					unlockClientAccount();
				} else if (userInput.equals("4")) {
					System.out.println("Option 4");
					promoteClientToAdmin();
				} else if (userInput.equals("5")) {
					System.out.println("Logging Out");
					loop = false;
				} else {

					System.out.println("FATAL ERROR!!!! Should not see this. IN adminMenu()");
				}
			}
		}
	}

	/**
	 * Display the actions for Admins
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
	 * Display menu for Client's, get Clien'ts choice, and validate Client's choice
	 */
	private void clientMenu() {

	}

	////////////////////////////////////////////////////////
	// Admin Functionality
	////////////////////////////////////////////////////////

	/**
	 * Functionality for admins to unlock client accounts
	 */
	public void unlockClientAccount() {
		boolean loop = true;
		int loopCounter = 0;
		final int maxLoopIteration = 5;

		// Input validation loop.
		// The Admin is limited to 'maxLoopIteration' tries before being sent
		// back to the previous menu
		while (loop && loopCounter < maxLoopIteration) {
			displayLockedAccounts();
			loop = !getAndUnlockAccount(); // loop while the user input is invalid

			// Count number of incorrect inputs
			if (loop) {
				loopCounter += 1;
			}
		}

		// Print appropriate message, if incorrect input limit is reached
		if (loopCounter == maxLoopIteration) {
			System.out.println("Too many invalid tries");
		}
	}

	/**
	 * Display all currently locked accounts (i.e. the accounts able to be unlocked) 
	 */
	private void displayLockedAccounts() {
		System.out.println("SSN's for currently locked accounts");

		List<String> lockedUserAccounts = getLockedAccounts();
		for (String item : lockedUserAccounts) {
			System.out.println(item);
		}
		System.out.println("\n");
		System.out.println("Enter Account To Lock: ");
	}
	
	/**
	 * Get all locked accounts from the internal database
	 * @return Returns a list containing the SSNs of all locked client accounts
	 */
	private List<String> getLockedAccounts() {
		// Loop through db and search for unlocked user accounts
		// To Do: Find a better way to do this than O(n)
		List<String> lockedUserAccounts = new ArrayList<String>();
		for (String key : this.db.keySet()) {
			User user = this.db.get(key);
			if (user.getStatus() == status_locked && user.getPermissions() == permission_client) {
				lockedUserAccounts.add(key);
			}
		}

		return lockedUserAccounts;
	}
	
	/**
	 * Get an account SSN from the admin, validate the input, and unlock the specified account
	 * @return Returns TRUE is the SSN was valid and FALSE if not
	 */
	private boolean getAndUnlockAccount() {
		boolean validUser = false;
		String input = getUserInput();
		User user = this.db.get(input);

		// Check if designated user exists and actually is currently locked.
		// Note: nested if-statements for error message purposes
		// (i.e. allows for unique error message based on situation)
		if (user != null) {
			if (user.getStatus() == status_locked && user.getPermissions() == permission_client) {
				user.setStatus(status_active);
				this.db.put(user.getSsn(), user);
				validUser = true;
				System.out.println("Account Unlocked!");
			} else {
				System.out.println("Account: " + user.getSsn() + " cannot be unlocked");
			}
		} else {
			System.out.println("User is not in the database");
		}

		return validUser;
	}

	/**
	 * Functionality for admins to lock a client accounts
	 */
	public void lockClientAccount() {
		boolean loop = true;
		int loopCounter = 0;
		final int maxLoopIteration = 5;

		// Input validation loop.
		// The Admin is limited to 'maxLoopIteration' tries before being sent
		// back to the previous menu
		while (loop && loopCounter < maxLoopIteration) {
			displayUnlockedAccounts();
			loop = !getAndLockAccount(); // loop while the user input is invalid

			// Count number of incorrect inputs
			if (loop) {
				loopCounter += 1;
			}
		}

		// Print appropriate message, if incorrect input limit is reached
		if (loopCounter == maxLoopIteration) {
			System.out.println("Too many invalid tries");
		}

	}

	/**
	 * Display unlocked accounts (i.e. accounts that can be locked)
	 */
	private void displayUnlockedAccounts() {
		System.out.println("SSN's for currently unlocked accounts");

		List<String> unlockedUserAccounts = getUnlockedAccounts();
		for (String item : unlockedUserAccounts) {
			System.out.println(item);
		}
		System.out.println("\n");
		System.out.println("Enter Account To Lock: ");
	}

	/**
	 * Fetch unlocked accounts from the internal database
	 * 
	 * @return Returns a list containing ID's of currently unlocked accounts
	 */
	private List<String> getUnlockedAccounts() {
		// Loop through db and search for unlocked user accounts
		// To Do: Find a better way to do this than O(n)
		List<String> unlockedUserAccounts = new ArrayList<String>();
		for (String key : this.db.keySet()) {
			User user = this.db.get(key);
			if (user.getStatus() == status_active && user.getPermissions() == permission_client) {
				unlockedUserAccounts.add(key);
			}
		}

		return unlockedUserAccounts;
	}

	/**
	 * Get and validate the Admin's choice of client to lock. Lock the client's
	 * account
	 * 
	 * @return Returns TRUE if the admins choses a valid user and False otherwise
	 */
	private boolean getAndLockAccount() {
		boolean validUser = false;
		String input = getUserInput();
		User user = this.db.get(input);

		// Check if designated user exists and actually is currently unlocked.
		// Note: nested if-statements for error message purposes
		// (i.e. allows for unique error message based on situation)
		if (user != null) {
			if (user.getStatus() == status_active && user.getPermissions() == permission_client) {
				user.setStatus(status_locked);
				this.db.put(user.getSsn(), user);
				validUser = true;
				System.out.println("Account Locked!");
			} else {
				System.out.println("Account: " + user.getSsn() + " cannot be locked");
			}
		} else {
			System.out.println("User is not in the database");
		}

		return validUser;
	}

	/**
	 * Get user id from Admin and approve the given client's account
	 */
	public void approveClientAccount() {
		boolean loop = true;
		int loopCounter = 0;
		final int maxLoopIteration = 5;

		// Input validation loop.
		// The Admin is limited to 'maxLoopIteration' tries before being sent
		// back to the previous menu
		while (loop && loopCounter < maxLoopIteration) {
			displayAccountsNeedingApproval();
			loop = !getAndApproveAccount(); // loop while the user input is invalid

			// Count number of incorrect inputs
			if (loop) {
				loopCounter += 1;
			}
		}

		// Print appropriate message, if incorrect input limit is reached
		if (loopCounter == maxLoopIteration) {
			System.out.println("Too many invalid tries");
		}
	}

	/**
	 * Display accounts that need approving
	 */
	private void displayAccountsNeedingApproval() {
		System.out.println("SSNs for accounts needing approval");

		List<String> approvalPendingAccounts = getApprovalPendingAccounts();
		for (String item : approvalPendingAccounts) {
			System.out.println(item);
		}
		System.out.println("\n");
		System.out.println("Enter Account To Approve: ");
	}

	/**
	 * Fetch the accounts that need approving from the internal database
	 * 
	 * @return Returns a list of strings containing the user accounts that need
	 *         approving
	 */
	private List<String> getApprovalPendingAccounts() {
		// Loop through db and search for accounts needing approval
		// To Do: Find a better way to do this than O(n)
		List<String> ssnNeedingApproval = new ArrayList<String>();
		for (String key : this.db.keySet()) {
			if (this.db.get(key).getStatus() == status_approvalPending) {
				ssnNeedingApproval.add(key);
			}
		}

		return ssnNeedingApproval;
	}

	/**
	 * Allow the admin to specify which account to approve Validate the admin's
	 * input
	 * 
	 * @return Returns TRUE if the admin's input is valid and FALSE if the input is
	 *         invalid
	 */
	private boolean getAndApproveAccount() {
		boolean validUser = false;
		String input = getUserInput();
		User user = this.db.get(input);

		// Check if designated user exists and actually needs to be approved.
		// Note: nested if-statements for error message purposes
		// (i.e. allows for unique error message based on situation)
		if (user != null) {
			if (user.getStatus() == status_approvalPending) {
				user.setStatus(status_active);
				this.db.put(user.getSsn(), user);
				validUser = true;
				System.out.println("Account Approved!");
			} else {
				System.out.println("Account: " + user.getSsn() + " does not need to be approved");
			}
		} else {
			System.out.println("User is not in the database");
		}

		return validUser;
	}

	/**
	 * Allows admins to promote clients to admin status
	 */
	public void promoteClientToAdmin() {
		boolean loop = true;
		int loopCounter = 0;
		final int maxLoopIteration = 5;

		// Input validation loop.
		// The Admin is limited to 'maxLoopIteration' tries before being sent
		// back to the previous menu
		while (loop && loopCounter < maxLoopIteration) {
			displayClients();
			loop = !getAndPromoteClient(); // loop while the user input is invalid

			// Count number of incorrect inputs
			if (loop) {
				loopCounter += 1;
			}
		}

		// Print appropriate message, if incorrect input limit is reached
		if (loopCounter == maxLoopIteration) {
			System.out.println("Too many invalid tries");
		}
	}
	
	/**
	 * Display all clients in the databae (i.e. all entries in the database that are client and not admin)
	 */
	private void displayClients() {
		System.out.println("SSNs for accounts needing approval");

		List<String> approvalPendingAccounts = getClients();
		for (String item : approvalPendingAccounts) {
			System.out.println(item);
		}
		System.out.println("\n");
		System.out.println("Enter Account To Approve: ");
	}
	
	/**
	 * Fetch all clients in the database
	 * @return Returns a list containing the SSNs of all clients in the database
	 */
	private List<String> getClients() {
		// Loop through db and search for all clients
		// To Do: Find a better way to do this than O(n)
		List<String> ssnClients = new ArrayList<String>();
		for (String key : this.db.keySet()) {
			if (this.db.get(key).getPermissions() == permission_client) {
				ssnClients.add(key);
			}
		}

		return ssnClients;
	}
	
	/**
	 * Get a SSN from the admin, validate the input, and promote that client to an admin
	 * @return Returns TRUE if the specified user is a client and FALSE if specified user is invalid 
	 * (that is, does not exist in the database, or already an admin)
	 */
	private boolean getAndPromoteClient() {
		boolean validUser = false;
		String input = getUserInput();
		User user = this.db.get(input);

		// Check if designated user exists and is a client.
		// Note: nested if-statements for error message purposes
		// (i.e. allows for unique error message based on situation)
		if (user != null) {
			if (user.getPermissions() == permission_client) {
				user.setPermissions(permission_admin);
				this.db.put(user.getSsn(), user);
				validUser = true;
				System.out.println("Account Promoted to Admin!");
			} else {
				System.out.println("Account: " + user.getSsn() + " is already an admin");
			}
		} else {
			System.out.println("User is not in the database");
		}

		return validUser;
	}

	////////////////////////////////////////////////////////
	// Client Functionality
	////////////////////////////////////////////////////////
	/**
	 * Add given amount to the Client's account
	 * 
	 * @param depositAmount
	 *            Amount to be deposited into Client's account
	 */
	public void deposit(double depositAmount) {
		
	}

	/**
	 * Subtract given amount from the Client's account
	 * 
	 * @param withdrawAmount
	 *            Amount to be withdrawn from Clien'ts account
	 */
	public void withdraw(double withdrawAmount) {

	}

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
}
