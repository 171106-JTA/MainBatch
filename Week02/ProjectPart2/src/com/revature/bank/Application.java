package com.revature.bank;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.DAO.AdminDAO;
import com.revature.DAO.UserDAO;
import com.revature.DAO.UserDAOImpl;

public class Application {

	public static String LINE_SEPARATOR = "==============================================";
	public static final int MAX_LOGIN_ATTEMPTS = 4;
	public static String DATA_FILE_NAME = "dataStore.ser";
	final static Logger logger = Logger.getLogger(Application.class);
	
	private User _loggedInUser;
	
	private UserDAO _userDao;
	private AdminDAO _adminDao;	// might not see use here. 
	
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
		// initialize all the DAOs
		_userDao = new UserDAOImpl();
		// display welcome screen
		printWelcomeScreen();
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
		_userDao.create(new User(username, password));
		System.out.println("Your account has been created and is pending admin approval!");
	}
	
	/**
	 * prints the screen for the user to interact with. 
	 * NOTE: This will only work for a logged in User.
	 */
	public void printUserScreen()
	{
		if(_loggedInUser == null) return;
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
	 */
	private boolean authenticateUser(String name, String pass)
	{	
		boolean userAuthenticated = false;
		// look through the data store of users to find the user with name of user and pass of pass
		User user = new User(name, pass);
		// use the UserDAO to see if the user is in the database
		_loggedInUser = _userDao.authenticate(user);
		// user is authenticated iff _loggedInUser is no longer null
		userAuthenticated = (_loggedInUser != null);
		return userAuthenticated;
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
