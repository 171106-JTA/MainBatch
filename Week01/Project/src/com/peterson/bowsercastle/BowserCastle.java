package com.peterson.bowsercastle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * Class used to perform functionality of Bowser's castle.
 * Users can deposit coins, withdraw coins, and attack bowser. If they succeed they are king. If they fail they die.
 * Admin can view all users on file, verify users, promote users, and lock/unlock users.
 * @author Alex Peterson
 */
public class BowserCastle {

	private static final int MIN_NAME_CHARS = 3; //minimum amount of characters a user's name must be
	private static final int MAX_NAME_CHARS = 10; //max amount of characters a user's name must be
	private static final String BOWSER_PW = "123"; //default pw for bowser
	private static final String USERS_TXT = "Users.txt"; //text file with user data

	/**Collection used to store our users in order of role (King, Admin, Member, Unverified)*/
	private Queue<User> pQUsers = new PriorityQueue<User>(new UserComparator()); 

	private Random random;
	private Scanner scanner;

	/**A separate class used for logging info and for serialization.*/
	private UserStorage userStorage;
	
	/**This class will handle transactions.*/
	private Vault vault;

	/**
	 * A user has entered the castle.
	 */
	public void enterCastle() {
		vault = new Vault(); //object used for transactions as well as loan storage
		random = new Random();
		scanner = new Scanner(System.in);
		userStorage = new UserStorage(); //used to serialize user data as well as for logging info

		try {
			pQUsers = userStorage.grabUsers(USERS_TXT); //deserialize all of our users

			if (pQUsers.isEmpty()) //there are no users on file, create king Bowser
				createAccount("Bowser", BOWSER_PW, Role.KING);

			welcome(); //greet the user and display options

			userStorage.serializeUsers(USERS_TXT, pQUsers);  //upon exiting save everything
			vault.serializeLoans();
			vault.serializeWaitingLoans(); //save all loans data
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The "main menu" of the castle.
	 * Users can create a new account, log in to their current account, or exit.
	 */
	private void welcome() {

		boolean exit = false;
		String choice;
		System.out.println("Welcome to " + pQUsers.peek().getName() + "'s castle."); //welcome them to the king's castle
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
				"You currently have " + user.getCoins() + " coins and you are level " + user.getLevel() + ".");
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
		System.out.println("====================================\n" +
				"(Access = " + user.getRole() + ") What would you like to do?");

		/*Display options depending on the user's role.*/
		if (user.getRole() == Role.ADMIN) {
			System.out.println("(0) Deposit coins." +
					"\n(1) Withdraw coins. You have " + user.getCoins() + " coins." +
					"\n(2) Level up and earn " + user.levelUp() + " levels. You are level " + user.getLevel() + "." +
					"\n(3) Attempt to kill King " + pQUsers.peek().getName() +
					"\n(4) Verify a new member." +
					"\n(5) Promote a member to admin" +
					"\n(6) View all members on file" +
					"\n(7) Lock/Unlock a user's account" + 
					"\n(8) View and approve or deny a loan application" + 
					"\n(9) Apply for a loan." +
					"\n(x) Log Out.");
		} else if (user.getRole() == Role.MEMBER) {
			System.out.println("(0) Deposit coins." +
					"\n(1) Withdraw coins. You have " + user.getCoins() + " coins." +
					"\n(2) Level up and earn " + user.levelUp() + " levels. You are level " + user.getLevel() + "." + 
					"\n(3) Attempt to kill King " + pQUsers.peek().getName() +
					"\n(4) Apply for a loan." +
					"\n(x) Log Out.");
		} else if (user.getRole() == Role.KING) {
			System.out.println("(0) Deposit coins." +
					"\n(1) Withdraw coins. You have " + user.getCoins() + " coins." +
					"\n(2) Level up and earn " + user.levelUp() + " levels. You are level " + user.getLevel() + "." +
					"\n(3) Verify a new member." +
					"\n(4) Promote a member to admin" +
					"\n(5) View all members on file" +
					"\n(6) Lock/Unlock a user's account" + 
					"\n(7) View and approve or deny a loan application" + 
					"\n(8) Apply for a loan." +
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
			if (member != pQUsers.peek()) killKing(member);
			else System.out.println("You are king.");
			break;
		case "4"://apply  for loan
			applyForLoan(member);
			break;
		default:
			System.out.println("Please enter a valid response");
			return;
		}
	}

	/**
	 * Users may apply for loans and get more coins.
	 * @param member applying
	 */
	private void applyForLoan(User member) {
		System.out.println("=================================" +
				"\nHow many coins would you like to borrow? Or hit 'X' to cancel.");
		boolean amountAccepted = false;

		int loan = 0;
		do {
			amountAccepted = true;
			final String input = scanner.nextLine();

			if (input.toUpperCase().equals("X")) return;//user canceled

			/*Exception handling if the user enters something that isn't a number */
			try {
				loan = Integer.parseInt(input);
			} catch(NumberFormatException e) {
				System.out.println("Please enter a number.");
				amountAccepted = false;
				continue;
			}
		} while (!amountAccepted);

		vault.applyForLoan(member, loan);
		System.out.println("Thank you for your application for a loan of " + loan + ". We will get back to you shortly.");
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
			if (admin != pQUsers.peek()) killKing(admin);
			else System.out.println("You are king and cannot kill yourself.");
			break;
		case "4": //verify a new member
			verifyAccount();
			break;
		case "5": //promote a new member
			promoteAccount();
			break;
		case "6": //view all members on file
			displayAllUsers();
			break;
		case "7": //lock an account
			lockUnlockAccount();
			break;
		case "8":
			viewLoans(); //view and approve/deny loans
			break;
		case "9":
			applyForLoan(admin);
			break;
		default:
			System.out.println("Please enter a valid response");
			return;
		}
	}

	/**
	 * Admin can see all loans 
	 */
	private void viewLoans() { 
		final Map<String,Integer> loansWaiting = vault.getLoansWaiting(); //grab the loans waiting to be approved
		final Map<String,Integer> loansApproved = vault.getLoans(); //grab the loans that are already approved
		
		final Set<String> keysOfWaitingLoans = loansWaiting.keySet();
		final Set<String> keysOfLoans = loansApproved.keySet();
		
		//print all loans already approved
		System.out.println("=========================\nApproved loans:");
		for (String name : keysOfLoans) 
			System.out.println(name + " " + loansApproved.get(name)); //print name and amount borrowed
		
		if (keysOfWaitingLoans.isEmpty()) { //exit if there are no loans to be approved
			System.out.println("There are no loans to be approved.");
			return;
		}
		
		/*=========User selects user of loan to approve or deny.===========*/
		User userSelected = null;
		do {
			System.out.println("===========================\n"
					+ "Loans to be approved: Select a user to approve or deny. Or hit 'X' to cancel.");
			for (String name : keysOfWaitingLoans) 
				System.out.println(name + " " + loansWaiting.get(name)); //print the user applying and amount

			final String input = scanner.nextLine().toUpperCase(); //grab the input of the user

			if (input.toUpperCase().equals("X")) //cancel
				return;

			userSelected = grabUser(input); //grab the user applying for the loan

			if (userSelected == null) { //there is no user with the name inputted
				System.out.println("Select a user from the list or hit 'X' to cancel.");
			}
		} while (userSelected == null);

		/*==============Admin then approves or denies the loan==============*/
		boolean answerPicked = false;
		do { 
			answerPicked = true;

			System.out.println("Enter 'A' to accept the loan, or 'D' to deny the loan.");
			String input = scanner.nextLine().toUpperCase();

			if (input.equals("A"))
				vault.acceptLoan(userSelected); //accept the user's loan
			else if (input.equals("D")) {
				System.out.println(userSelected.getName() + "'s loan of " + loansWaiting.get(userSelected) + " has been denied.");
				vault.denyLoan(userSelected); //deny the loan
			} else 
				answerPicked = false;

		} while(!answerPicked);
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
			verifyAccount();
			break;
		case "4": //promote a new member
			promoteAccount();
			break;
		case "5": //view all members on file
			displayAllUsers();
			break;
		case "6": //lock an account
			lockUnlockAccount();
		case "7":
			viewLoans();
			break;
		case "8":
			applyForLoan(king);
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
		final Map<String, Integer> loan = vault.getLoans();

		boolean payLoan = false;
		if (loan.containsKey(user)) { //if the user has a loan on file
			System.out.println("You appear to have a loan on file of " + loan.get(user) + " coins." +
							   "\nWould you like your deposit directed towards the loan?");
			payLoan = true;
		}
		
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

			Transaction message = null;
			if (payLoan) {
				message = vault.payLoan(user, deposit);
			} else {
				message = vault.depositCoins(user, deposit);
			}
			
			if (message == Transaction.FAIL) 
				System.out.println("You must deposit an amount of at least zero. Or hit 'X' to cancel.");
			else {
				depositComplete = true;

				System.out.println("You have submitted " + deposit +  " and now have a total of " +
						user.getCoins() + " coins");
				userStorage.log(user.getName() + " has deposited " + deposit + " coins");
			}
		} while(!depositComplete);
	}

	/**
	 * Helper method that increases a member's coins.
	 * @param user that is going to work
	 */
	private void levelUp(final User user) {
		vault.levelUp(user);
		System.out.println("You are now level " + user.getLevel() + "!");
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

			final Transaction message = vault.withdrawCoins(user, withdrawCoins); //attempt to withdraw the funds
			if (message == Transaction.FAIL) {
				System.out.println("You do not have that many coins, enter an amount up to " + user.getCoins());
				userStorage.log(user.getName() + " attempted to withdraw " + withdrawCoins + " when they only had " + 
						user.getCoins() + " in their account.");
			} else {
				userStorage.log(user.getName() + " has withdrawn " + withdrawCoins);
				System.out.println("You have withdrawn " + withdrawCoins + 
						" from your account and now have a total of " + user.getCoins());
			}

			if (withdrawCoins > user.getCoins()) {

			} else if (withdrawCoins < 0) {
				System.out.println("You must unter an amount of coins > 0");
				userStorage.log(user.getName() + " attempted to withdraw " + withdrawCoins + ".");
			} else {
				transactionComplete = true;


				userStorage.log(user.getName() + " has withdrawn " + withdrawCoins);
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
		userStorage.log(userSelected.getName() + " has been promoted to an admin");
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
		userStorage.log(userSelected.getName() + " has been verified and is now a member"); //log that the user has been verified

		System.out.println("=====================================");		
	}

	/**
	 * Find the user associated by their name
	 * @param name of the user
	 * @return the user object, or null if there is none
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
		System.out.println("Thank you and welcome " + name + ". Your account is restricted until approved by an admin.");
		createAccount(name, password, Role.UNVERIFIED);
	}

	/**
	 * Add the new account to our data structure and log the transaction.
	 * @param name of new user
	 * @param password of new user
	 * @param role of new user
	 */
	private void createAccount(String name, String password, Role role) {
		pQUsers.add(new User(name, password.hashCode(), role)); //add our new user to our queue
		userStorage.log(name + " has just created an account with Bowser's Castle\n");
	}

	/**
	 * A user is attempting to kill the king
	 * @param user attempting to kill the king
	 */
	private void killKing(User user) {
		double chance = (double) user.getLevel() / (double) pQUsers.peek().getLevel();
		chance *= 100;
		System.out.println("Are you sure you want to kill bowser? You only have a " + chance + "% to succeed. (Y/N)");

		/*Prompt the user yes or no to fight bowser.*/
		boolean validInput;
		do {
			String input = scanner.nextLine();

			if (input.toUpperCase().equals("N")) {
				System.out.println("Chicken!!");
				return;
			} else if (input.toUpperCase().equals("Y"))
				validInput = true;
			else {
				System.out.println("Enter (Y/N)");
				validInput = false;
			}
		} while (!validInput);
		
		/*Determine if the user died to bowser or defeated bowser.*/
		double attack = random.nextInt() % 100;
		if (attack < chance) {  //user won
			System.out.println("You have defeated bowser and are now king!");
			user.setRole(Role.KING);
			userStorage.log(user.getName() + " has killed king " + pQUsers.peek().getName() + " and is now King.");
			pQUsers.poll(); //remove bowser
		} else { //delete the user from the PriorityQueue
			List<User> temp = new ArrayList<User>();
			for (User tempUser : pQUsers) //add all members to our temp data structure except the dead user
				if (tempUser != user) 
					temp.add(tempUser);

			pQUsers.clear();
			pQUsers.addAll(temp);
			System.out.println("You are dead and your account has been deleted.");
			userStorage.log(user.getName() + " died to bowser");
		}
	}

	/**
	 * Admin may lock or unlock member accounts.
	 */
	private void lockUnlockAccount() {

		/*Find all users that are members or are locked.*/
		final List<User> members = new ArrayList<>();
		for (User user : pQUsers) {
			if (user.getRole() == Role.MEMBER || user.getRole() == Role.LOCKED) 
				members.add(user);
		}

		if (members.isEmpty()) { //there are no members to lock or unlock
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