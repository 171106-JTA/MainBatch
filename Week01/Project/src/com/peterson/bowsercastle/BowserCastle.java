package com.peterson.bowsercastle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

import org.apache.log4j.Logger;

/**
 * 
 * @author Alex Peterson
 *
 */
public class BowserCastle {

	private static final String PROMOTE = "Promote";
	private static final String VERIFY = "Verify";
	private static final int MIN_NAME_CHARS = 3; //minimum amount of characters a user's name must be
	private static final int MAX_NAME_CHARS = 10; //max amount of characters a user's name must be
	private static final String BOWSER_PW = "123"; //default admin pw
	private static final String USERS_TXT = "Users.txt"; //text file with user data
	private Queue<User> pQUsers; //Collection used to store our users in order of role (King, Admin, Member, Unverified)
	private Random random;
	private Scanner scanner;
	private Logger logger;

	/**
	 * A user has entered the castle.
	 */
	public void enterCastle() {
		final Comparator<User> c = new UserComparator(); //the comparator needed to sort the users in the pQ
		pQUsers = new PriorityQueue<User>(c);
		
		logger = Logger.getLogger(BowserCastle.class);
		random = new Random();
		scanner = new Scanner(System.in);

		try {
			grabUsers(); //deserialize user objects from file

			welcome(); //greet the user and display options

			serializeUsers();  //upon exiting save everything
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The "main menu" of the castle.
	 * Users can create a new account log in to their current account or exit.
	 */
	private void welcome() {

		boolean exit = false;
		String choice;
		System.out.println("Welcome to my castle.");
		do {
			System.out.println("What would you like to do?" +
					"\n(0) Create an account," +
					"\n(l) Log in to your account" +
					"\n(X) Leave Bowser's Castle");
			choice = scanner.next();

			if (choice.equals("0")) { //create a new account
				createAccount();
			} else if (choice.equals("1")) {//log in to user account
				login();
			} else if (choice.toUpperCase().equals("X")) {//exit
				exit = true;
				System.out.println("Goodbye");
			} else {
				System.out.println("\nPlease enter a valid choice.");
				continue;
			}
		} while(!exit);
		scanner.close();
	}	

	/**
	 * Deserialize all of our user objects and put them in our arraylist data structure.
	 * @throws IOException 
	 */
	private void grabUsers() throws IOException {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(USERS_TXT);
			if (fis.available() == 0) {//this is a new file, create King Bowser
				User king = new User("Bowser", BOWSER_PW.hashCode(), Role.KING);
				pQUsers.add(king);
			} else {
				ois = new ObjectInputStream(fis);
				pQUsers = (PriorityQueue<User>)ois.readObject(); //store all of our users in our arraylist
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) fis.close();
			if (ois != null) ois.close();
		}
	}

	/**
	 * For a user to login successfully they must have an account that has been verified by an admin,
	 * they must submit their username and corresponding password.
	 * @param scanner to read in user input
	 */
	private void login() {
		System.out.println("I don't recognize you...what is your username?");

		User user = promptName();
		if (user == null) return; //user canceled login screen, or their account has not been verified yet		

		if (!requestPassword(user)) return; //user must submit correct password

		/*===============User has logged in successfully and can make decisions================*/
		System.out.println("Welcome " + user.getName() + "(" + user.getRole() + ")" + 
						   "You currently have " + user.getCoins() + " coins."); 
		boolean loggedIn = true;
		String choice;
		do {
			System.out.println("What would you like to do?" +
					"\n(0) Work and earn " + user.getSalary() + " coins." +
					"\n(1) Withdraw coins." +
					"\n(2) Give me coins." +
					"\n(3) Attempt to kill me." +
					"\n(4) Verify a new member. (admin only)" +
					"\n(5) Promote a member to admin (admin only)" +
					"\n(6) View all members on file (admin only)" +
					"\n(x) Log Out.");
			choice = scanner.next();

			if (choice.toUpperCase().equals("X")) {//exit
				loggedIn = false;
				System.out.println("You have logged out.\n");
				continue;
			}
			
			userAction(user, choice); //helper method delegating the user's choice
		} while (loggedIn);
	}

	/**
	 * 
	 * @param user
	 * @param choice
	 */
	private void userAction(final User user, final String choice) {
		switch(choice.toUpperCase()) {
		case "0": //work for coins, users earn more or less depending on their role
			work(user);
			break;
		case "1": //withdraw coins
			withdrawCoins(user);
			break;
		case "2": //give bowser coins
			System.out.println("How many coins will you give Bowser?");
			break;
		case "3": // kill bowser
			break;
		case "4": //verify a new member
			if (user.getRole() == Role.ADMIN || user.getRole() == Role.KING)
				verifyOrPromoteUsers(VERIFY);
			else
				System.out.println("You must be an admin to verify a user.");
			break;
		case "5": //promote a new member
			if (user.getRole() == Role.ADMIN || user.getRole() == Role.KING) 
				verifyOrPromoteUsers(PROMOTE);
			else 
				System.out.println("You do not have access to promote someone to admin.\n");
			break;
		case "6":
			if (user.getRole() == Role.ADMIN || user.getRole() == Role.KING)
				displayAllUsers();
			else
				System.out.println("You must be an admin to view user data.\n");
			break;
		}
	}

	/**
	 * Helper method that increases a member's coins.
	 * @param user that is going to work
	 */
	private void work(final User user) {
		final int coins = user.getSalary(); //calculate how many coins the user earned
		System.out.println("You just earned " + coins + " coins! " +
				"You now have a total of " + user.getCoins() + " coins");
		user.setCoins(user.getCoins() + coins);		
	}

	/**
	 * 
	 */
	private void withdrawCoins(final User user) {
		System.out.println("====================");
		System.out.println("\nYou have " + user.getCoins() + ". How many coins would you like to withdraw? (Warning these coins will be gone forever)");
		
		boolean transactionComplete = false;
		int withdrawCoins = 0;
		
		do { //ask the user for coins until they enter the correct amount
			withdrawCoins = scanner.nextInt();
			
			if (withdrawCoins > user.getCoins()) {
				System.out.println("You do not have that many coins, enter an amount up to " + user.getCoins());
				logger.warn(user.getName() + " attempted to withdraw " + withdrawCoins + " when they only had " + 
					user.getCoins() + " in their account.");
			} else if (withdrawCoins < 0) {
				System.out.println("You must unter an amount of coins > 0");
				logger.warn(user.getName() + " attempted to withdraw " + withdrawCoins + ".");
			} else {
				transactionComplete = true;
			}
		} while (!transactionComplete);
		
		user.setCoins(user.getCoins() - withdrawCoins);
		logger.info(user.getName() + " has withdrawn " + withdrawCoins);
		System.out.println("You have withdrawn " + withdrawCoins + 
				           " from your account and now have a total of " + user.getCoins());
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	private boolean requestPassword(User user) {
		System.out.println(user.getName() + "? You say? What is your password?");
		String password = scanner.next();
		while (password.hashCode() != user.getHashedPassword()) {
			System.out.println("That password is incorrect. Enter again or enter 'x' to leave.");
			password = scanner.next();
			if (password.toUpperCase().equals("X")) { //the user has failed to enter their password and has chosen to leave
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @return
	 */
	private User promptName() {
		String name = scanner.next();
		
		User user = grabUser(name); //get the user for that username, if the username does not exist user will be null
		while (user == null) {
			System.out.println("There is no account with that username. Enter another name, or enter 'x' to leave.\n");
			name = scanner.next(); //user enters their username
			if (name.toUpperCase().equals("X")) return null;

			user = grabUser(name);
		}
		
		if (user.getRole() == Role.UNVERIFIED) { //check if the user trying to log in has been verified yet
			System.out.println("This account has not yet been verified.\n");
			return null;
		}
		
		return user;
	}

	/**
	 * Displays to an admin all of the users on file and their data.
	 */
	private void displayAllUsers() {
		System.out.println("\n============Members===========" +
						   "\nName      Role      Balance");
		for (User user : pQUsers) { //display each user in order of Role (King, admin, member, non-member)
			System.out.println(user.getName() + " | " + "(" + user.getRole() + ") |" + " Balance: " + user.getCoins() + " coins");
		}
		System.out.println("================================\n");
	}

	/**
	 * An admin has requested to verify a user.
	 * Displays all unverified users, and the admin can verify who they want.
	 */
	private void verifyOrPromoteUsers(String action) {
		System.out.println("=====================");
		System.out.println("Which account would you like to " + action + "?");
		int i = 0;
		final ArrayList<User> usersToPromoteOrVerify = new ArrayList<User>();
		
		for (User user : pQUsers) { //find and display all users that are unverified
			if (action.equals(VERIFY) && user.getRole() == Role.UNVERIFIED) {
				usersToPromoteOrVerify.add(user);
				System.out.println("(" + i + ")" + user.getName() + "(" + user.getRole() + ")"); //display the unverified user
				i++;
			} else if (action.equals(PROMOTE) && user.getRole() == Role.MEMBER) {
				usersToPromoteOrVerify.add(user);
				System.out.println("(" + i + ")" + user.getName() + "(" + user.getRole() + ")"); //display the unverified user
				i++;
			}
		}

		if (usersToPromoteOrVerify.isEmpty()) { //check if there are any members eligible for verification/promotion
			if (action.equals("VERIFIED"))
				System.out.println("All members are currently verified.");
			else 
				System.out.println("There are no members to promote.");
			return;
		}

		String userSelected = scanner.next();
		while (Integer.valueOf(userSelected) > i) {
			System.out.println("Please select a user from the list provided. Or hit 'X' to cancel");
			if (userSelected.toUpperCase().equals("X")) return;
		}

		User user = usersToPromoteOrVerify.get((Integer.valueOf(userSelected)));
		
		//promote user to member or admin
		if (user.getRole() == Role.UNVERIFIED) {
			user.setRole(Role.MEMBER);
			System.out.println(user.getName() + " is now a verified member of Bowser's Castle.");
			logger.info(user.getName() + " has been verified and is now a member"); //log that the user has been verified
		} else if (user.getRole() == Role.MEMBER) {
			user.setRole(Role.ADMIN);
			System.out.println(user.getName() + " is now an admin of Bowser's Castle.");
			logger.info(user.getName() + " has been promoted to an admin");
		}
		
		System.out.println("=====================\n");		
	}

	/**
	 * Find the user associated by their name
	 * @param name of the user
	 * @return the user object
	 */
	private User grabUser(final String name) {
		
		for (User user : pQUsers) {
			if (user.getName().toUpperCase().equals(name.toUpperCase())) {
				return user;
			}
		}		
		return null; //no user exists with name
	}

	/**
	 * A user may create an account with a new name and password, both must be at least 3 characters long.
	 * Said user has restricted access until verified by an admin.
	 */
	private void createAccount() {
		System.out.println("Please enter your new user name.");

		String name = "";
		boolean validUsername = false;
		do { //user must submit a name that is 5 or more characters, and is not already taken
			name = scanner.next();
			if (grabUser(name) != null) {
				System.out.println("That username is already taken, try another. Or enter 'x' to cancel.");
			} else if (name.toUpperCase().equals("X")) {
				return;
			} else if (name.length() < MIN_NAME_CHARS) {
				System.out.println("Your username must be at least " + MIN_NAME_CHARS + " characters. Or enter 'x' to cancel.");
			} else {
				System.out.println("This name will do just fine.");
				validUsername = true;
			}
		} while (!validUsername);

		System.out.println("Please enter your password.");
		boolean validPassword = false;
		String password = "";
		do { //user's password must be at least 3 characters
			password = scanner.next();
			if (password.length() < MIN_NAME_CHARS) {
				System.out.println("Your password must be at least " + MIN_NAME_CHARS + " characters.");
			} else {
				validPassword = true;
			}
		} while (!validPassword);

		final int hashed_password = password.hashCode(); //store the password's hashcode and not the actual password
		System.out.println("Thank you and welcome. Your account is restricted until approved by an admin.");

		pQUsers.add(new User(name, hashed_password, Role.UNVERIFIED)); //add our new user to our arraylist
		logger.info(name + " has just created an account with Bowser's Castle");
	}

	/**
	 * @throws IOException 
	 * 
	 */
	private void serializeUsers() throws IOException {
		ObjectOutputStream oos = null;
		FileOutputStream fls = null;
		try {
			fls = new FileOutputStream(USERS_TXT);
			oos = new ObjectOutputStream(fls);
			oos.writeObject(pQUsers);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fls != null) fls.close();
			if (oos != null) oos.close();
		}
	}

	private static void killBowser() {
		boolean success = false;

		if (success) {

		} else {
			System.out.println("You are dead and your account has been deleted.");
		}

	}


}