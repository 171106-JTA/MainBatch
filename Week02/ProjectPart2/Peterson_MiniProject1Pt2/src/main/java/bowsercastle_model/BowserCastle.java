package bowsercastle_model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import bowsercastle_view.AdminUI;
import bowsercastle_view.KingUI;
import bowsercastle_view.MemberUI;
import bowsercastle_view.UI;

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

	private Random random;
	private Scanner scanner;

	/**A separate class used for logging info and for serialization.*/
	private BowserStorable bowserStorage;

	/**Different UI holding available menus depending on the user.*/
	private UI ui;
	private UI adminUI = new AdminUI();
	private UI memberUI = new MemberUI();
	private UI kingUI = new KingUI();

	/**
	 * A user has entered the castle.
	 */
	public void enterCastle() {
		random = new Random();
		scanner = new Scanner(System.in);
		bowserStorage = BowserStorage.getBowserStorage(); //Singleton object used to interact with our DB

		if (bowserStorage.getUsers().isEmpty()) {//there are no users on file, create king Bowser
			bowserStorage.insertUser(new User("Bowser", BOWSER_PW.hashCode(), Role.KING));
		}
		welcome(); //greet the user and display options
		scanner.close();
	}

	/**
	 * The "main menu" of the castle.
	 * Users can create a new account, log in to their current account, or exit.
	 */
	private void welcome() {

		boolean exit = false;
		String choice;
		System.out.println("Welcome to " + getKing().getName() + "'s castle."); //welcome them to the king's castle
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
				user.getName() + " ID(" + user.getId() + ")" + "(" + user.getRole() + ") " + 
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

				if (!bowserStorage.getUsers().contains(user)) //user died to bowser
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
			ui = adminUI;
		} else if (user.getRole() == Role.MEMBER) {
			ui = memberUI;
		} else if (user.getRole() == Role.KING) {
			ui = kingUI;
		}
		ui.printOptions(getKing());
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
			killKing(member);
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
		if (bowserStorage.getLoans().containsKey(member.getId()) ||
				bowserStorage.getLoansWaiting().containsKey(member.getId())) {
			System.out.println("You already have a loan on file.");
			return;
		}
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

		bowserStorage.applyForLoan(member, loan);
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
			killKing(admin);
			break;
		case "4": //verify a new member
			verifyAccount();
			break;
		case "5": //promote a new member
			promoteAccount();
			break;
		case "6": //view all members on file
			displaybowserStorageUsers();
			break;
		case "7": //lock an account
			lockUnlockAccount();
			break;
		case "8":
			viewApproveDenyLoans(admin); //view and approve/deny loans
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
	private void viewApproveDenyLoans(User user) { 
		final Map<Integer,Integer> loansWaiting = bowserStorage.getLoansWaiting(); //grab the loans waiting to be approved
		final Map<Integer,Integer> loansApproved = bowserStorage.getLoans(); //grab the loans that are already approved

		final Set<Integer> keysOfWaitingLoans = loansWaiting.keySet();
		final Set<Integer> keysOfLoans = loansApproved.keySet();

		//print all loans already approved
		System.out.println("=========================\nApproved loans:" +
				"\nID  Name   Amount");
		for (Integer id : keysOfLoans) 
			System.out.println("(" + id + ") " + grabUserByID(id).getName() + " " + loansApproved.get(id)); //print name and amount borrowed

		if (keysOfWaitingLoans.isEmpty()) { //exit if there are no loans to be approved
			System.out.println("There are no loans to be approved.");
			return;
		}

		/*=========User selects user of loan to approve or deny.===========*/
		User userSelected = null;
		boolean doneSelectingUser = false;
		do {
			System.out.println("===========================\n"
					+ "Loans to be approved: Select a user_id to approve or deny. Or hit 'X' to cancel." +
					"\nID  Name   Amount");
			for (Integer id : keysOfWaitingLoans) 
				System.out.println("(" + id + ") " + grabUserByID(id).getName() + " " + loansWaiting.get(id)); //print the user applying and amount

			final String input = scanner.nextLine().toUpperCase(); //grab the input of the user

			if (input.toUpperCase().equals("X")) //cancel
				return;

			try {
				userSelected = grabUserByID(Integer.valueOf(input)); //grab the user applying for the loan
			} catch(NumberFormatException e) {
				System.out.println("Please enter an id.");
			}
			if (userSelected == null) { //there is no user with the name inputted
				System.out.println("Select a user from the list or hit 'X' to cancel.");
			} else if (userSelected == user) {
				System.out.println("You cannot approve your own loan.");
			} else {
				doneSelectingUser = true;
			}
		} while (!doneSelectingUser);

		/*==============Admin then approves or denies the loan==============*/
		boolean answerPicked = false;
		do { 
			answerPicked = true;

			System.out.println("Enter 'A' to accept the loan, or 'D' to deny the loan.");
			String input = scanner.nextLine().toUpperCase();

			if (input.equals("A"))
				bowserStorage.acceptLoan(userSelected); //accept the user's loan
			else if (input.equals("D")) {
				System.out.println(userSelected.getName() + "'s loan of " + loansWaiting.get(userSelected) + " has been denied.");
				bowserStorage.denyLoan(userSelected); //deny the loan
			} else 
				answerPicked = false;

		} while(!answerPicked);
	}

	private User grabUserByID(int input) {
		for (User user : bowserStorage.getUsers()) {
			if (user.getId() == input) {
				return user;
			}
		}		
		return null; //no user exists with name
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
			displaybowserStorageUsers();
			break;
		case "6": //lock an account
			lockUnlockAccount();
			break;
		case "7":
			viewApproveDenyLoans(king);
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
		final Map<Integer, Integer> loan = bowserStorage.getLoans();

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
				message = bowserStorage.payLoan(user, deposit);
			} else {
				message = bowserStorage.depositCoins(user, deposit);
			}

			if (message == Transaction.FAIL) 
				System.out.println("You must deposit an amount of at least zero. Or hit 'X' to cancel.");
			else {
				depositComplete = true;
				bowserStorage.updateCoins(user); //update DB
				System.out.println("You have submitted " + deposit +  " and now have a total of " +
						user.getCoins() + " coins");
				bowserStorage.log(user.getName() + " has deposited " + deposit + " coins");
			}
		} while(!depositComplete);
	}

	/**
	 * Helper method that increases a member's coins.
	 * @param user that is going to work
	 */
	private void levelUp(final User user) {
		bowserStorage.levelUp(user);
		System.out.println("You are now level " + user.getLevel() + "!");
		bowserStorage.updateLevel(user); //update DB
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

			final Transaction message = bowserStorage.withdrawCoins(user, withdrawCoins); //attempt to withdraw the funds
			if (message == Transaction.FAIL) {
				System.out.println("You do not have that many coins, enter an amount up to " + user.getCoins());
				bowserStorage.log(user.getName() + " attempted to withdraw " + withdrawCoins + " when they only had " + 
						user.getCoins() + " in their account.");
			} else {
				bowserStorage.updateCoins(user);
				bowserStorage.log(user.getName() + " has withdrawn " + withdrawCoins);
				System.out.println("You have withdrawn " + withdrawCoins + 
						" from your account and now have a total of " + user.getCoins());
			}

			if (withdrawCoins > user.getCoins()) {

			} else if (withdrawCoins < 0) {
				System.out.println("You must unter an amount of coins > 0");
				bowserStorage.log(user.getName() + " attempted to withdraw " + withdrawCoins + ".");
			} else {
				transactionComplete = true;


				bowserStorage.log(user.getName() + " has withdrawn " + withdrawCoins);
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
	private void displaybowserStorageUsers() {
		System.out.println("\n=============Members==============" +
				"\nName      Role      Balance");
		for (User user : bowserStorage.getUsers()) { //display each user in order of Role (King, admin, member, non-member)
			System.out.println(user.getName() + " | " + "(" + user.getRole() + ") |" + " Balance: " + user.getCoins() + " coins");
		}
		System.out.println("==================================\n");
	}

	/**
	 * Method that promotes a user from member to admin. Only admin or king have access to do this.
	 */
	private void promoteAccount() {
		final List<User> members = new ArrayList<>();

		for (User user : bowserStorage.getUsers())
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
		bowserStorage.updateRole(userSelected); //update DB
		System.out.println(userSelected.getName() + " is now an admin of Bowser's Castle.");
		bowserStorage.log(userSelected.getName() + " has been promoted to an admin");
		System.out.println("=====================================");		
	}

	/**
	 * An admin has requested to verify a user.
	 * Displays all unverified users, and the admin can verify who they want.
	 */
	private void verifyAccount() {
		final List<User> usersToPromoteOrVerify = new ArrayList<>();

		for (User user : bowserStorage.getUsers()) 
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
		bowserStorage.log(userSelected.getName() + " has been verified and is now a member"); //log that the user has been verified
		bowserStorage.updateRole(userSelected);
		System.out.println("=====================================");		
	}

	/**
	 * Find the user associated by their name
	 * @param name of the user
	 * @return the user object, or null if there is none
	 */
	private User grabUser(final String name) {
		for (User user : bowserStorage.getUsers()) {
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
		bowserStorage.insertUser(new User(name, password.hashCode(), Role.UNVERIFIED)); //insert the user into our DB
	}

	/**@return the current king of the castle*/
	private User getKing() {
		return bowserStorage.getUsers().peek();
	}

	/**
	 * A user is attempting to kill the king
	 * @param user attempting to kill the king
	 */
	private void killKing(User user) {
		BigDecimal chance = new BigDecimal((double) user.getLevel() / (double) getKing().getLevel());
		chance = chance.setScale(2, RoundingMode.UP);
		chance = chance.scaleByPowerOfTen(2);
		System.out.println("Are you sure you want to kill bowser? You only have a " + chance.toString() + "% to succeed. (Y/N)");

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
		if (attack < chance.doubleValue()) {  //user won
			System.out.println("You have defeated bowser and are now king!");
			bowserStorage.log(user.getName() + " has killed king " + getKing() + " and is now King.");
			bowserStorage.deleteUser(getKing());
			user.setRole(Role.KING);
		} else { //delete the user from the PriorityQueue
			bowserStorage.deleteUser(user);
			System.out.println("You are dead and your account has been deleted.");
			bowserStorage.log(user.getName() + " died to bowser");
		}
	}

	/**
	 * Admin may lock or unlock member accounts.
	 */
	private void lockUnlockAccount() {

		/*Find all users that are members or are locked.*/
		final List<User> members = new ArrayList<>();
		for (User user : bowserStorage.getUsers()) {
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
				bowserStorage.updateRole(userSelected);
			} else if (userSelected.getRole() == Role.MEMBER){
				finished = true;
				userSelected.setRole(Role.LOCKED);
				bowserStorage.updateRole(userSelected);
				System.out.println(userSelected.getName() + "'s account has now been locked.");
			} else {
				System.out.println("Please enter a name from the list provided.");
			}
		} while (!finished);
	}
}