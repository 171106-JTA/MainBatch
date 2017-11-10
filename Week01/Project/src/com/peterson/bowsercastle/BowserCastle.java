package com.peterson.bowsercastle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

import org.apache.log4j.Logger;

/**
 * 
 * @author Alex Peterson
 */
public class BowserCastle {

	private static final int MIN_NAME_CHARS = 3; //minimum amount of characters a user's name must be
	private static final int MAX_NAME_CHARS = 10; //max amount of characters a user's name must be
	private static final String BOWSER_PW = "123"; //default pw for bowser
	private static final String USERS_TXT = "Users.txt"; //text file with user data
	private Queue<User> pQUsers = new PriorityQueue<User>(new UserComparator()); //Collection used to store our users in order of role (King, Admin, Member, Unverified)
	private Random random;
	private Scanner scanner;
	private Logger logger;

	/**
	 * A user has entered the castle.
	 */
	public void enterCastle() {
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
			choice = scanner.nextLine();

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
	@SuppressWarnings("unchecked")
	private void grabUsers() throws IOException {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(USERS_TXT);
			if (fis.available() == 0) {//this is a new file, create King Bowser
				User king = new User("Bowser", BOWSER_PW.hashCode(), Role.KING);
				king.setStars(5000);
				logger.info(king.getName() + " has been created.");
				pQUsers.add(king);
			} else {
				ois = new ObjectInputStream(fis);
				pQUsers.addAll((PriorityQueue<User>) ois.readObject()); //store all of our users in our arraylist
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
		System.out.println("What is your name?");

		String input = scanner.nextLine();
		User user = grabUser(input);
		if (user == null) {//user canceled login screen
			System.out.println("There is no account with this name.");
			return; 	
		} else if (user.getRole() == Role.UNVERIFIED) { //check if the user trying to log in has been verified yet
			System.out.println("Failed to login, this account has not yet been verified.\n");
			return;
		} else if (user.getRole() == Role.LOCKED) { //account is locked by admin
			System.out.println("Failed to login, this account has been locked by an administrator.");
			return;
		}

		if (!requestPassword(user)) return; //user must submit correct password

		/*===============User has logged in successfully and can make decisions================*/
		System.out.println("\n===================================\nWelcome " +
				user.getName() + "(" + user.getRole() + ") " + 
				"You currently have " + user.getCoins() + " coins and " + user.getStars() + " stars.");
		boolean loggedIn = true;
		String choice;
		do {
			displayOptions(user); //display different options depending on the user's role

			choice = scanner.nextLine();

			if (choice.toUpperCase().equals("X")) {//exit
				loggedIn = false;
				System.out.println("You have logged out.\n");
			} else {
				action(user, choice);

				if (!pQUsers.contains(user)) //user died to bowser
					return;
			}
		} while (loggedIn);
	}

	/**
	 * Display options for the user depending on their role.
	 * @param user
	 */
	public void displayOptions(User user) {
		System.out.println("\n====================================");
		if (user.getRole() == Role.ADMIN) {
			System.out.println("(Access = " + user.getRole() + ") What would you like to do?" +
					"\n(0) Deposit coins." +
					"\n(1) Withdraw coins. You have " + user.getCoins() + " coins." +
					"\n(2) Level up and earn " + user.levelUp() + " stars. You have " + user.getStars() + " stars." +
					"\n(3) Attempt to kill King " + pQUsers.peek().getName() +
					"\n(4) Verify a new member." +
					"\n(5) Promote a member to admin" +
					"\n(6) View all members on file" +
					"\n(7) Lock/Unlock a user's account" + 
					"\n(x) Log Out.");
		} else if (user.getRole() == Role.MEMBER) {
			System.out.println("(Access = " + user.getRole() + ") What would you like to do?" +
					"\n(0) Deposit coins." +
					"\n(1) Withdraw coins. You have " + user.getCoins() + " coins." +
					"\n(2) Level up and earn " + user.levelUp() + " stars. You have " + user.getStars() + " stars." + 
					"\n(3) Attempt to kill King " + pQUsers.peek().getName() +
					"\n(x) Log Out.");
		} else if (user.getRole() == Role.KING) {
			System.out.println("(Access = " + user.getRole() + ") What would you like to do?" +
					"\n(0) Deposit coins." +
					"\n(1) Withdraw coins. You have " + user.getCoins() + " coins." +
					"\n(2) Level up and earn " + user.levelUp() + " stars. You have " + user.getStars() + " stars." +
					"\n(3) Verify a new member." +
					"\n(4) Promote a member to admin" +
					"\n(5) View all members on file" +
					"\n(6) Lock/Unlock a user's account" + 
					"\n(x) Log Out.");
		}
	}

	/**
	 * Helper method handling logged in member's input. A member has restricted access.
	 * @param user committing the action
	 * @param choice of member
	 */
	private void action(final User user, final String choice) {
		switch(user.getRole()) {
		case ADMIN:
			adminAction(user, choice);
			break;
		case KING:
			kingAction(user, choice);
			break;
		case MEMBER:
			memberAction(user, choice);
			break;
		default:
			break;
		}
	}

	/**
	 * Helper method delegating member's actions
	 * @param member
	 * @param choice
	 */
	private void memberAction(User member, String choice) {
		switch(choice) {
		case "0": //deposit coins
			depositCoins(member);
			break;
		case "1": //withdraw coins
			withdrawCoins(member);
			break;
		case "2": //work for coins, users earn more or less depending on their role
			levelUp(member);
			break;
		case "3": // kill bowser
			if (member != pQUsers.peek()) killBowser(member);
			else System.out.println("You are king.");
			break;
		default:
			System.out.println("Please enter a valid response");
			return;
		}
	}

	/**
	 * Helper method handling logged in admin's input. E.g. deposit, withdraw, verify an account, etc.
	 * @param admin committing the action
	 * @param choice of admin
	 */
	private void adminAction(final User admin, final String choice) {
		switch(choice) {
		case "0": //deposit coins
			depositCoins(admin);
			break;
		case "1": //withdraw coins
			withdrawCoins(admin);
			break;
		case "2": //work for coins, users earn more or less depending on their role
			levelUp(admin);
			break;
		case "3": // kill bowser
			if (admin != pQUsers.peek()) killBowser(admin);
			else System.out.println("You are king and cannot kill yourself.");
			break;
		case "4": //verify a new member
			if (admin.getRole() == Role.ADMIN || admin.getRole() == Role.KING)
				verifyAccount();
			else
				System.out.println("You must be an admin to verify a user.");
			break;
		case "5": //promote a new member
			if (admin.getRole() == Role.ADMIN || admin.getRole() == Role.KING) 
				promoteAccount();
			else 
				System.out.println("You do not have access to promote someone to admin.\n");
			break;
		case "6": //view all members on file
			if (admin.getRole() == Role.ADMIN || admin.getRole() == Role.KING) {
				displayAllUsers();
			} else
				System.out.println("You must be an admin to view user data.\n");
			break;
		case "7": //lock an account
			if (admin.getRole() == Role.ADMIN || admin.getRole() == Role.KING)
				lockUnlockAccount();
			break;
		default:
			System.out.println("Please enter a valid response");
			return;
		}
	}

	/**
	 * Helper method handling logged in king's input. E.g. deposit, withdraw, verify an account, etc.
	 * @param king committing the action
	 * @param choice of king
	 */
	private void kingAction(final User king, final String choice) {
		switch(choice) {
		case "0": //deposit coins
			depositCoins(king);
			break;
		case "1": //withdraw coins
			withdrawCoins(king);
			break;
		case "2": //work for coins, users earn more or less depending on their role
			levelUp(king);
			break;
		case "3": //verify a new member
			if (king.getRole() == Role.ADMIN || king.getRole() == Role.KING)
				verifyAccount();
			else
				System.out.println("You must be an admin to verify a user.");
			break;
		case "4": //promote a new member
			if (king.getRole() == Role.ADMIN || king.getRole() == Role.KING) 
				promoteAccount();
			else 
				System.out.println("You do not have access to promote someone to admin.\n");
			break;
		case "5": //view all members on file
			if (king.getRole() == Role.ADMIN || king.getRole() == Role.KING) {
				displayAllUsers();
			} else
				System.out.println("You must be an admin to view user data.\n");
			break;
		case "6": //lock an account
			if (king.getRole() == Role.ADMIN || king.getRole() == Role.KING)
				lockUnlockAccount();
			break;
		default:
			System.out.println("Please enter a valid response");
			return;
		}
	}



	/**
	 * A user can deposit coins into their account.
	 * @param user that is depositing
	 */
	private void depositCoins(User user) {
		System.out.println("=================================" +
				"\nHow many coins would you like to deposit? Or hit 'X' to cancel.");
		boolean depositComplete = false;

		do {
			int deposit = 0;
			final String input = scanner.nextLine();

			if (input.toUpperCase().equals("X")) return;//user canceled

			/*Exception handling if the user enters something that isn't a number */
			try {
				deposit = Integer.parseInt(input);
			} catch(NumberFormatException e) {
				System.out.println("Please enter a number.");
				continue;
			}

			if (deposit < 0) 
				System.out.println("You must deposit an amount of at least zero. Or hit 'X' to cancel.");
			else {
				depositComplete = true;
				user.setCoins(user.getCoins() + deposit);
				System.out.println("You have submitted " + deposit +  " and now have a total of " +
						user.getCoins() + " coins");
				logger.info(user.getName() + " has deposited " + deposit + " coins");
			}
		} while(!depositComplete);
	}

	/**
	 * Helper method that increases a member's coins.
	 * @param user that is going to work
	 */
	private void levelUp(final User user) {
		final int stars = user.levelUp(); //calculate how many coins the user earned
		user.setStars(user.getStars() + stars);	
		System.out.println("You just earned " + stars + " stars! " +
				"You now have a total of " + user.getStars() + " stars");
	}

	/**
	 * A user can withdraw coins from their account.
	 */
	private void withdrawCoins(final User user) {
		System.out.println("====================");
		System.out.println("\nYou have " + user.getCoins() + ". How many coins would you like to withdraw?");

		boolean transactionComplete = false;
		int withdrawCoins = 0;

		do { //ask the user for coins until they enter the correct amount
			try {
				withdrawCoins = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Enter a number.");
				continue;
			}

			if (withdrawCoins > user.getCoins()) {
				System.out.println("You do not have that many coins, enter an amount up to " + user.getCoins());
				logger.warn(user.getName() + " attempted to withdraw " + withdrawCoins + " when they only had " + 
						user.getCoins() + " in their account.");
			} else if (withdrawCoins < 0) {
				System.out.println("You must unter an amount of coins > 0");
				logger.warn(user.getName() + " attempted to withdraw " + withdrawCoins + ".");
			} else {
				transactionComplete = true;
				user.setCoins(user.getCoins() - withdrawCoins);
				logger.info(user.getName() + " has withdrawn " + withdrawCoins);
				System.out.println("You have withdrawn " + withdrawCoins + 
						" from your account and now have a total of " + user.getCoins());
			}
		} while (!transactionComplete);
	}

	/**
	 * Helper method that asks the user for their password.
	 * @param user trying to log in
	 * @return true if the user has successfully entered the correct password
	 */
	private boolean requestPassword(User user) {
		System.out.println(user.getName() + "? You say? What is your password?");
		String password = scanner.nextLine();
		while (password.hashCode() != user.getHashedPassword()) {
			System.out.println("That password is incorrect. Enter again or enter 'x' to leave.");
			password = scanner.nextLine();
			if (password.toUpperCase().equals("X")) { //the user has failed to enter their password and has chosen to leave
				return false;
			}
		}
		return true;
	}

	/**
	 * Displays to an admin all of the users on file and their data.
	 */
	private void displayAllUsers() {
		System.out.println("\n=============Members==============" +
				"\nName      Role      Balance");
		for (User user : pQUsers) { //display each user in order of Role (King, admin, member, non-member)
			System.out.println(user.getName() + " | " + "(" + user.getRole() + ") |" + " Balance: " + user.getCoins() + " coins");
		}
		System.out.println("==================================\n");
	}


	/**
	 * Method that promotes a user from member to admin. Only admin or king have access to do this.
	 */
	private void promoteAccount() {
		final List<User> members = new ArrayList<>();

		for (User user : pQUsers)
			if (user.getRole() == Role.MEMBER)
				members.add(user);

		if (members.isEmpty()) { //check if there are any members eligible for promotion
			System.out.println("There are no members to promote.");
			return;
		}

		/*============Grab the account to be verified by the user's input.=======*/
		String input = "";
		User userSelected = null;
		do {
			System.out.println("=====================================" +
					"\nEnter the user's name from the list provided who you want to verify. Or hit 'X' to cancel.");
			for (User user : members) //display the member
				System.out.println(user.getName() + " (" + user.getRole() + ")");

			input = scanner.nextLine();

			userSelected = grabUser(input); //grabs the user with that name

			if (input.toUpperCase().equals("X")) return;
		} while (userSelected == null || userSelected.getRole() != Role.MEMBER);

		/*===========Promote the account=============*/
		userSelected.setRole(Role.ADMIN);
		System.out.println(userSelected.getName() + " is now an admin of Bowser's Castle.");
		logger.info(userSelected.getName() + " has been promoted to an admin");
		System.out.println("=====================================");		
	}

	/**
	 * An admin has requested to verify a user.
	 * Displays all unverified users, and the admin can verify who they want.
	 */
	private void verifyAccount() {
		final List<User> usersToPromoteOrVerify = new ArrayList<>();

		for (User user : pQUsers) 
			if (user.getRole() == Role.UNVERIFIED) //grab all unverified users
				usersToPromoteOrVerify.add(user);

		if (usersToPromoteOrVerify.isEmpty()) { //check if there are any members eligible for verification/promotion
			System.out.println("All members are currently verified.");
			return;
		}


		/*============Grab the account to be verified by the user's input.=======*/
		String input = "";
		User userSelected = null;
		do {
			System.out.println("=====================================" +
					"\nEnter the user's name from the list provided who you want to verify. Or hit 'X' to cancel");
			for (User user : usersToPromoteOrVerify)
				System.out.println(user.getName() + " (" + user.getRole() + ")"); //display the unverified user
			input = scanner.nextLine();

			userSelected = grabUser(input);

			if (input.toUpperCase().equals("X")) return;
		} while (userSelected == null);

		/*=============Verify the account=============*/
		userSelected.setRole(Role.MEMBER);
		System.out.println(userSelected.getName() + " is now a verified member of Bowser's Castle.");
		logger.info(userSelected.getName() + " has been verified and is now a member"); //log that the user has been verified

		System.out.println("=====================================");		
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
			name = scanner.nextLine();
			if (grabUser(name) != null) {
				System.out.println("That username is already taken, try another. Or enter 'x' to cancel.");
			} else if (name.toUpperCase().equals("X")) {
				return;
			} else if (name.length() < MIN_NAME_CHARS) {
				System.out.println("Your username must be at least " + MIN_NAME_CHARS + " characters. Or enter 'x' to cancel.");
			} else if (name.length() > MAX_NAME_CHARS) {
				System.out.println("Your name cannot be more than " + MAX_NAME_CHARS + " characters.");
			} else {
				validUsername = true;
			}
		} while (!validUsername);

		System.out.println("This name will do just fine. Please enter your password. Or hit 'X' to cancel.");
		boolean validPassword = false;
		String password = "";
		do { //user's password must be at least 3 characters
			password = scanner.nextLine(); 
			if (password.toUpperCase().equals("X"))
				return;
			else if (password.length() < MIN_NAME_CHARS) {
				System.out.println("Your password must be at least " + MIN_NAME_CHARS + " characters.");
			} else if (password.length() > MAX_NAME_CHARS) {
				System.out.println("Your password cannot be more than " + MAX_NAME_CHARS + " characters.");
			} else {
				validPassword = true;
			}
		} while (!validPassword);

		final int hashed_password = password.hashCode(); //store the password's hashcode and not the actual password
		System.out.println("Thank you and welcome " + name + ". Your account is restricted until approved by an admin.");

		pQUsers.add(new User(name, hashed_password, Role.UNVERIFIED)); //add our new user to our arraylist
		logger.info(name + " has just created an account with Bowser's Castle\n");
	}

	/**
	 * Upon exiting the program, serialize our queue of users.
	 * @throws IOException 
	 */
	private void serializeUsers() throws IOException {
		ObjectOutputStream oos = null;
		FileOutputStream fls = null;
		try {
			fls = new FileOutputStream(USERS_TXT);
			oos = new ObjectOutputStream(fls);
			oos.writeObject(pQUsers); //serialize our queue of users
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fls != null) fls.close(); //close our input and output streams
			if (oos != null) oos.close();
		}
	}

	private void killBowser(User user) {
		double chance = (double) user.getStars() / (double) pQUsers.peek().getStars();
		chance *= 100;
		System.out.println("Are you sure you want to kill bowser? You only have a " + chance + "% to succeed. (Y/N)");

		boolean validInput;
		do {
			String input = scanner.nextLine();
			validInput = true;

			if (input.toUpperCase().equals("N")) {
				System.out.println("Chicken!!");
				return;
			} else if (input.toUpperCase().equals("Y")) {
				double attack = random.nextInt() % 100;
				if (attack < chance) {
					System.out.println("You have defeated bowser and are now king!");
					pQUsers.poll(); //remove bowser
					user.setRole(Role.KING);
				} else { //delete the user from the PriorityQueue
					List<User> temp = new ArrayList<User>();
					for (User tempUser : pQUsers) //add all members to our temp data structure except the dead user
						if (tempUser != user) 
							temp.add(tempUser);

					pQUsers.clear();
					pQUsers.addAll(temp);
					System.out.println("You are dead and your account has been deleted.");
				}
			} else {
				validInput = false;
			}
		} while (!validInput);
	}

	/**
	 * Admin may lock or unlock member accounts.
	 */
	private void lockUnlockAccount() {

		final List<User> members = new ArrayList<>();
		for (User user : pQUsers) {
			if (user.getRole() == Role.MEMBER || user.getRole() == Role.LOCKED) 
				members.add(user);
		}

		if (members.isEmpty()) {
			System.out.println("There are no members to lock or unlock");
			return;
		}

		/*Display the members to the user, receive the user's input.*/
		User userSelected = null;
		boolean finished = false;
		do {
			System.out.println("=====================================" +
					"\nWhich account would you like to lock or unlock? Or hit 'X' to cancel.");

			for (User user : members) //display the members
				System.out.println(user.getName() + " (" + user.getRole() + ")");

			String input = scanner.next();
			if (input.toUpperCase().equals("X")) //cancel
				return;

			userSelected = grabUser(input);

			/*Check for accounts that exist, then check if the user is unlocking or locking an account*/
			if (userSelected == null) {
				System.out.println("There is no account with that name.");
			} else if (userSelected.getRole() == Role.LOCKED) {
				finished = true;
				userSelected.setRole(Role.MEMBER);
				System.out.println(userSelected.getName() + "'s account has now been unlocked.");
			} else if (userSelected.getRole() == Role.MEMBER){
				finished = true;
				userSelected.setRole(Role.LOCKED);
				System.out.println(userSelected.getName() + "'s account has now been locked.");
			} else {
				System.out.println("Please enter a name from the list provided.");
			}
		} while (!finished);
	}
}