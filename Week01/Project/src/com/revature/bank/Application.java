package com.revature.bank;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class Application {

	public static String LINE_SEPARATOR = "==============================================";
	public static final int MAX_LOGIN_ATTEMPTS = 4;
	public static String DATA_FILE_NAME = "dataStore.ser";
	final static Logger logger = Logger.getLogger(Application.class);
	
	private DataStore _dataStore;
	private User _loggedInUser;
	
	Scanner scanner;	// for some reason, not package-scoped!

	/**
	 * Starts the application just like any other console app.
	 */
	public Application()
	{
		this(true);
	}
	
	/**
	 * Starts the application with showing the user prompts as a choice.
	 * This constructor is incomplete as I, at the time of writing this, haven't thought up how to handle the other use case
	 * @param showPrompt : whether or not to show the console prompts to the end user (and listen for their input)
	 */
	public Application(boolean showPrompt ) { 
		
		// instantiate everything now
		scanner = new Scanner(System.in);
		// load the data from the data store (in this stage of the project, the disk)
		loadData();
		// display welcome screen
		printWelcomeScreen();
		// save the data
		saveData();
		
	}
	
	public void printWelcomeScreen() {
		System.out.println("Welcome to the Revature banking app!");
		System.out.println(LINE_SEPARATOR);
		System.out.println();
		// print the initial menu
		boolean keepGoing = true;
		while (keepGoing)
		{
			// print the menu
			System.out.println("Enter : \n\n" + 
				"1 for login\n" + 
				"2 for register new user\n" +
				"3 for exit");
			try
			{
				// get the user choice
				int userChoice = Integer.parseInt(scanner.nextLine());
				// if it's valid, this is the last time they're going to see the above menu
				keepGoing = ((userChoice >= 1) && (userChoice <= 3));
				switch (userChoice)
				{
					case 1:
						// if they entered 1, they chose to login
						printLoginScreen();
						return;
					case 2: 
						// if they entered 2, they chose to create user
						printCreateUserPrompt();
						return;
					case 3: 
						System.out.println("You chose to exit. Goodbye!");
						return; 
					default:
						System.out.println("You have entered an invalid choice. Please try again!");
				}
			}
			catch (NumberFormatException nfe)
			{
				System.out.println("You have entered an invalid choice. Please try again!");
			}
		}
	}
	
	public void printCreateUserPrompt()
	{
		String username = "", password = "", confirmPassword = "";
		// print title
		System.out.println("Create account");
		System.out.println(LINE_SEPARATOR + "\n");
		// prompt user for username
		while (username.equals(""))
		{
			System.out.println("Enter username: ");
			if ((username = scanner.nextLine().trim()).equals(""))
				System.out.println("Username cannot be blank. Let's try again.");
		}
		boolean matchingPasswords = false;
		while (!matchingPasswords)
		{
			while (!User.meetsRequirements(password))
			{
				System.out.println("Enter password (cannot be blank): ");
				// TODO: come up with password requirements, and output them here
				password = scanner.nextLine();
				if (!User.meetsRequirements(password))
					System.out.println("Password does not meet requirements. Please try again.");
			}
			System.out.println("Confirm password: ");
			confirmPassword = scanner.nextLine().trim();
			if (!(matchingPasswords = (password.equals(confirmPassword))))
			{
//				System.out.println("Those passwords do not match. Please try again.");
				System.out.println("Those passwords do not match. Continue?");
				// if they chose to exit
				if (!userEnteredYes())
				{
					// notify them that they chose to exit
					System.out.println("You chose to exit. Goodbye!");
					// end the program
					return;
				}
				// otherwise
				else
				{
					// start both password entry and confirmation over again, as if they didn't get the first one right
					password = "";
				}
			}
			
		}
		// create User account, and add it to the data store
		_dataStore.getLockedUsers().add(new User(username, password));
		System.out.println("Your account has been created and is pending admin approval!");
	}
	
	/**
	 * prints the screen for the user to interact with. 
	 * NOTE: This will only work for a logged in User.
	 */
	public void printUserScreen()
	{
		if(_loggedInUser == null) return;
		/* // based on the state of the logged in User
		switch (_loggedInUser.getState())
		{
			// if they're an active user ... 
			case User.ACTIVE:
				// ... show them the screen for doing stuff with their bank account
				System.out.println("Enter:\n\n" + 
					"1 for withdraw money\n" + 
					"2 for deposit money");
		
		
			default: 
				// the default is for nothing to happen
				return;
		}*/
		// we'll try to....
		try
		{
			// ... treat the logged in user as an admin and pull up the admin menu for that logged in user
			AdminMenu am = new AdminMenu((Admin)_loggedInUser);
			am.display();
			am.handleInput();
		}
		// if we couldn't (because logged in user wasn't an Admin) ...
		catch (ClassCastException cce)
		{
			// ... we just show the one for plain ol' Users!
			UserMenu um = new UserMenu(_loggedInUser);
			um.display();
			um.handleInput();
		}
		
	}
	
	/**
	 * Prompts user for login. Throws exception upon multiple failed login attempts.
	 * @throws Exception
	 */
	public void printLoginScreen() { 
		String userName, password;
		int loginAttempts = 0;
		boolean validCredentials = false;
		while ((!validCredentials) && (loginAttempts < MAX_LOGIN_ATTEMPTS))
		{
			if (loginAttempts > 0) System.out.printf("\nNumber of login attempts: %d\n", loginAttempts);
			System.out.println("Please enter user name: ");
			userName = scanner.nextLine();
			System.out.println("Now enter password: ");
			password = scanner.nextLine();
			validCredentials = authenticateUser(userName, password);
			loginAttempts++;
		}
		if (loginAttempts == MAX_LOGIN_ATTEMPTS)
		{
			System.out.println("You have exceeded the allotted login attempts.");
			return;
		}
		// user is now logged in 
		System.out.println("You are logged in!");
		// let's print the user screen
		printUserScreen();
		
	}
	
	/* 
	 * Helper function that authenticates the end user. 
	 * Parameters:
	 * 	• user : (String) the user name
	 * 	• pass : (String) the password
	 * Returns: 
	 * 	• true iff user's credentials are valid, or false iff they're not.
	 * NOTE: passwords are *hashed*.
	 */
	/**
	 * Helper function that authenticates the end user. 
	 * 
	 * @param name : The user name.
	 * @param pass : The password.
	 * @return true iff user's credentials are valid, or false iff they're not.
	 * 
	 * NOTE: passwords are *hashed*.
	 */
	private boolean authenticateUser(String name, String pass)
	{	
		boolean userAuthenticated = false;
		// look through the data store of users to find the user with name of user and pass of pass
		User user = null;
		userAuthenticated = ((user = _dataStore.getActiveUsers().getByName(name)) != null) ||
			((user = _dataStore.getLockedUsers().getByName(name)) != null) ||
			((user = _dataStore.getBannedUsers().getByName(name)) != null) || 
			((user = _dataStore.getAdmins().getAdminByName(name)) != null);
		// if user has not been found, we're done with authentication
		if (!userAuthenticated) return false;
		// compare the password provided with that in the records
		userAuthenticated &= (user.getPass().equals(new User(name, pass).getPass()));
		// if those match, log in the user
		if (userAuthenticated) _loggedInUser = user;
		return userAuthenticated;
	}
		
	/**
	 * loads the DataStore from the disk
	 */
	public void loadData()// throws Exception
	{
		try
		{
			// open up the file(s) for serialization
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE_NAME));
			// load in the DataStore
			_dataStore = (DataStore)ois.readObject();
			// close the file
			ois.close();
			
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		catch (FileNotFoundException fnf)
		{
			_dataStore = new DataStore();
		}
		
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	
	/**
	 * saves the DataStore to the disk
	 */
	public void saveData()// throws Exception
	{
		System.out.println("saving data....");
		// make sure Application and Admins have same data store, by getting the newest one and giving every Admin that one
		_dataStore = findNewestDataStore();
		// update all Admins' DataStores
		for (Admin a : _dataStore.getAdmins())
		{
			a.setDataStore(_dataStore);
		}
		try {
			// open up the files for serialization
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE_NAME));
			// save in the DataStore
			oos.writeObject(_dataStore);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Finds the newest DataStore and returns it
	 * @return the DataStore with the newest (largest) timeStamp. 
	 */
	private DataStore findNewestDataStore()
	{
		// we start with _dataStore
		DataStore newestData = _dataStore;
		// next, let's get the list of Admins from it
		Admins admins = newestData.getAdmins();
		// for every admin in that 
		for (Admin admin : admins)
		{
			// if that Admin's DataStore is newer than newestData
			if (admin.getDataStore().timeStamp > newestData.timeStamp)
				// replace newestData with it
				newestData = admin.getDataStore();
		}
		return newestData;
	}
	
	/**
	 * This method generates a dummy DataStore for direct testing of the methods in this class.
	 * For use in test cases only. 
	 * @return a dummy DataStore for testing purpose.
	 */
	public static DataStore dummyStore()
	{
		// building the list of active Users...
		Users activeUsers = new Users();
		User bobbert = new User("Bobbert", "ryanHasThisStuckInMyHead", User.ACTIVE),
			genericUser = new User("Employee", "CodeLikeA0x426f7373", User.ACTIVE);
		// creating Accounts, and for users, and pushing each user to activeUsers
		Accounts accounts = new Accounts();
		accounts.push(bobbert.createAccount());
		activeUsers.push(bobbert);
		accounts.push(genericUser.createAccount());
		activeUsers.push(genericUser);
		// building the list of locked Users...
		Users lockedUsers = new Users();
		lockedUsers.push(new User("StillWaiting", "IN33dT89s"));
		lockedUsers.push(new User("Seth", "catch45"));
		// building the list of banned Users
		Users bannedUsers = new Users();
		bannedUsers.push(new User("Dysgruntl", "BringEmDown", User.BANNED));
		bannedUsers.push(new User("Haxxor", "l33tAllDaaay", User.BANNED));
		// building list of Admins
		Admins admins = new Admins();	// Admins data structure automatically adds in default admin!
		// building the DataStore
		DataStore data = new DataStore(activeUsers, lockedUsers, bannedUsers, admins, accounts);
		// giving default Admin the DataStore
		data.getAdmins().get(0).setDataStore(data);
		// returning the data
		return data;
	}
	
	/**
	 * Listens for user input and returns true iff the end user entered either "Y", "y", or a string that contains
	 * "yes". Uses white-listing.
	 * @return whether or not the user entered something that can be considered "yes".
	 */
	public static boolean userEnteredYes()
	{
		Scanner scanner = new Scanner(System.in);
		// get next line in input stream, to lower case
		String userInput = scanner.nextLine().trim().toLowerCase();
		// return whether or not that line contains the word "yes"
		return userInput.contains("yes")
				// && (userInput.indexOf("yes") == 0)
				|| (userInput.equals("y"));
	}
	
	public static void main(String[] args) {
		new Application();
	}

}
