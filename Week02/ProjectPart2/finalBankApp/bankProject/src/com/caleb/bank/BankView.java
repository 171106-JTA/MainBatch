package com.caleb.bank;

import java.util.Scanner;
import java.util.*;

/**
 * The BankView class is the UI for the bank application. This prints ALL messages
 * and all data users need to see to the screen. This class is also used for 
 * user input and passes the input to the controller. 
 * @author calebschumake
 *
 */
class BankView {

	/*
	 * These constants are used to index an array of user information that is passed
	 * between model, view and controller
	 */
	private final int USERS_EMAIL = 0, USERS_PASSWORD = 1, USERS_NAME = 2, USERS_PHONE_NUMBER = 3, SMS_NOTIFICATIONS = 4;
	private Scanner input = new Scanner(System.in);

	/** 
	 * Prompts the user if they are a new or old user
	 * @return boolean 
	 */
	public boolean isCustomerNew() {

		System.out.println("********** Welcome to Calebs banking app **********");
		System.out.println("Hi, are you a new user? (enter 'yes' or 'no'): ");
		String userResponse = input.nextLine();

		if (userResponse.equalsIgnoreCase("yes")) {
			return true;
		} else if (userResponse.equalsIgnoreCase("no")) {
			return false;
		} else {

			// prompt user to enter correct value
			boolean incorrectInput = true;
			while (incorrectInput) {
				System.out.println("Your input is incorrect, please eneter 'yes' or 'no': ");
				userResponse = input.next();
				if (userResponse.equalsIgnoreCase("yes"))
					return true;
				if (userResponse.equalsIgnoreCase("no"))
					return false;
			}
		}

		/* compiler happy */
		return true;

	}
	
	/**
	 * This method displays a screen to inform a user they are blocked
	 * @param user
	 */
	public void showBlockedUserMessage(User user) {
		System.out.println("********** Sorry " + user.getName() + ", your account is currently blocked. **********");
	}

	/**
	 * This method presents the admins login portal. This method prompts the admin
	 * what actions he/she wants to perform and passes this information back to the
	 * controller
	 * @param adminName
	 * @return int
	 */
	public int showAdminScreen(String adminName) {

		int usersChoice = 0; 

		System.out.println("********** Hi " + adminName + " welcome to the admin portal **********");

		System.out.println(
				"Please enter 1, 2, 3, 4 or 5 to perform the following: \n 1. Block users \n 2. Approve pending accounts \n 3. Promote user to admin \n 4. Unblock user \n 5. log-out");
		
		try {
			
			usersChoice = input.nextInt();
			
		} catch (InputMismatchException e) {
			
			System.out.println("Invalid input.");
			
		}
		

		/*
		 * nextInt() does not consume the last \n character in the input stream so I did
		 * it manually
		 */
		input.nextLine();
		switch (usersChoice) {
		case 1: /* Block users */
			return 1;
		case 2: /* Approve pending accounts */
			return 2;
		case 3: /* Promote user to admin */
			return 3;
		case 4: /* Unblock user account */
			return 4;
		case 5:
			return 5; 
		}

		return 6; /* If user hits something that is not an option */ 

	}
	
	/**
	 * This method is used to show the admin a screen that lists all the users that are currently blocked.
	 * This method prompts the admin to enter the user they want to unblock. 
	 * @param activeUsers
	 * @return
	 */
	public String showUnblockUserScreen(List<User> activeUsers) {
		System.out.println("********** Welcome to the unblock user screen **********");
		System.out
				.println("Please enter the email of the user you wish to unblock (press enter if none are present): ");

		for (User user : activeUsers) {
			if (user.isBlocked())
				System.out.println(user.toString());
		}

		String userToUnblock = input.nextLine();

		return userToUnblock;
	}
	
	/**
	 * This method is used to present a user with all the options he/she can perform.
	 * This method prompts the user to enter an option that they want to do.
	 * @param user
	 * @return
	 */
	public int showUserScreen(User user) {

		int usersChoice = 0; 

		System.out.println("********** Hi " + user.getName() + " welcome to the user portal **********");
		System.out.println("Your current balance is: " + user.account.getBalance());
		if (user.hasLoan()) {
			System.out.println("Your current loan balance is: " + user.account.getLoanBalance());
		} else {
			
			System.out.println("You currently do not have any loans.");
			
		}

		System.out.println(
				"Please enter 1, 2, 3 or 4 to perform the following: \n 1. Withdraw funds \n 2. Deposit funds \n 3. Show previous transactions \n "
				+ "4. Apply for loan \n 5. Make payment on loan \n 6. Log-out");
		try {
			
			usersChoice = input.nextInt();
			
		} catch (InputMismatchException e) {
			
			System.out.println("Invalid input.");
			
		}
		

		/*
		 * nextInt() does not consume the last \n character in the input stream so I did
		 * it manually
		 */
		input.nextLine();
		switch (usersChoice) {
		case 1: /* Withdraw funds */
			return 1;
		case 2: /* Deposit funds */
			return 2;
		case 3: /* Show all previous transactions */
			return 3;
		case 4: /* Apply for a loan */
			return 4;
		case 5: /* Make a mayment on a loan */ 
			return 5; 
		case 6: /* Log-out */ 
			return 6; 
		default:
			break; 
		}

		return 7; /* If user types in a character that is not a proper menu item */ 

	}
	
	/**
	 * Prompts the user to make payemnt on their loan
	 * @return float
	 */
	public float showMakePaymentOnLoanScreen() {
		System.out.println("********** Welcome to the payment screen **********");
		
		System.out.println("Please enter the amount you want to pay on your loan");
		
		float amount = 0; 
		
		try {
			
			amount = input.nextInt(); 
			return amount; 
			
		} catch (InputMismatchException e) {
			
			System.out.println("Invalid input.");
			return -1; 
			
		}
		
	
	}
	
	/**
	 * Displays payment was successfull
	 */
	public void showSuccessfullPaymentMessage() {
		
		System.out.println("********** Your payment was successfull **********");
		
	}
	
	/**
	 * Displays payment was not successfull
	 */
	public void showPaymentWasNotSuccessfullMessage() {
		
		System.out.println("********** Your payment was not successfull **********");
	}
	
	/**
	 * Prompts user to enter the amount of loan they are requesting
	 * @return int
	 */
	public int showApplyForLoanScreen() {
		
		System.out.println("********** Hi, welcome to the loan application screen **********");
		System.out.println("Please enter the size of loan you would like to apply for: ");
		
		
		int userInput = 0; 
		
		try {
			
			userInput = input.nextInt(); 
			
		} catch (InputMismatchException e) {
			
			System.out.println("Invalid input.");
			return -1; 
			
		}
		
		return userInput; 
		
	}
	
	/**
	 * Displays that the loan requested is too smalle
	 */
	public void showLoanIsTooSmallMessage() {
		System.out.println("********** We do not service loans smaller than $3000 **********");
	}
	
	/**
	 * Displays that the user has been denied a loan
	 */
	public void showUserHasBeenDeniedLoanMessage() {
		System.out.println("********** We regret to inform you that you have been denied a loan **********");
	}
	
	/** Displays that the user has been approved for the
	 * requested loan amount
	 */
	public void showUserHasBeenApprovedForLoanMessage() {
		System.out.println("********** Congrats! you have been approved for the loan you have requested **********");
	}
	
	/**
	 * Displays that the user already has a loan
	 */
	public void showUserAlreadyHasLoanMessage() {
		System.out.println("********** You already have a loan. **********");
	}
	
	/**
	 * Displays that the loan amount is too large
	 */
	public void showLoanAmmountIsTooBigMessage() {
		System.out.println("********** We do not service loans greater than $20000 **********");
	}
	
	/**
	 * This method is used to show users all of their previous bank
	 * transactions
	 * @param user
	 */
//	public void showPreviousTransactions(User user) {
//		List<String> previousTransactions = user.account.get
//		for (String transaction : previousTransactions) {
//			System.out.println(transaction);
//		}
//
//		System.out.println("Please press enter to return to previous screen");
//		input.nextLine();
//	}
	
	/**
	 * This method is used show the withdraw screen.
	 * This method prompts the user for the amount of money they want to withdraw.
	 * @return float
	 */
	public float showWithdrawScreen() {
		System.out.println("********** Hi welcome to the withdraw screen **********");
		System.out.println("Please enter the amount you want to withdraw: ");

		float amountToWithdraw = 0; 
		
		try {
			amountToWithdraw = input.nextFloat();
		} catch (InputMismatchException e) {
			System.out.println("Invalid input.");
		}

		return amountToWithdraw;
	}
	
	/**
	 * This method is used show the deposit screen.
	 * This method prompts the user for the amount of money they want to deposit.
	 * @return float
	 */
	public float showDepositScreen() {

		System.out.println("********** Hi welcome to the deposit screen **********");
		System.out.println("Please enter the amount you want to deposit: ");
		
		float amountToDeposit = 0;
		
		try {
			amountToDeposit = input.nextFloat();
		} catch (InputMismatchException e) {
			System.out.println("Invalid input.");
		}
		
		
		return amountToDeposit;
	}
	/**
	 * This method is used show the admin which users they can promote.
	 * This method prompts the admin for the email of the user they want to 
	 * select. 
	 * @return String
	 */
	public String showPromoteScreen(List<User> usersThatCanBePromoted) {
		System.out.println("********** Below are accounts that you can promote **********");
		for (User user : usersThatCanBePromoted) {
			System.out.println(user.toString());
		}

		if (!usersThatCanBePromoted.isEmpty()) {
			System.out.println("\nPlease enter the email of the user you wish to promote: ");
		} else {
			System.out.println("There are currently no users to promote, press enter to exit.");

		}

		String email = input.nextLine();
		return email;

	}

	/**
	 * This method is used show the block user screen. This method lists all the users the admin can currently block. 
	 * This method prompts the admin for the user they want to block
	 * @return String
	 */
	public String showBlockUserScreen(List<User> activeUsers, String currentAdmin) {
		System.out.println("********** Below are accounts that you can block **********");

		for (User user : activeUsers) {
			if ((!user.isBlocked()) && (!user.getEmail().equalsIgnoreCase(currentAdmin))) // make sure user cannot
																								// block themself
				System.out.println(user.toString());
		}

		System.out.println(
				"\nPlease enter the email of the user you wish to block (press enter to return to previous screen or if none are present): ");
		String userInput = input.nextLine();
		return userInput;
	}
	
	/**
	 * This method is used show the accounts that are on the approval list. 
	 * This method prompts the admin to enter the account they wish to approve. 
	 * @return String
	 */
	public String showApproveAccountsScreen(List<User> pendingAccounts) {
		System.out.println("********** Below are accounts that you can approve **********");

		for (User user : pendingAccounts)
			System.out.println(user.toString());

		System.out.println(
				"\nPlease enter the email of the user you wish to approve (press enter to return to previous screen or if none are present): ");
		String userInput = input.nextLine();
		return userInput;
	}

	/**
	 * This method is used to prompt the user for their email, password, name, telephone number and if they want SMS notifications
	 * sign up. 
	 * @return String[] 
	 */
	public String[] getSignUpInfo() {

		String[] usersInformation = new String[5];

		System.out.println("********** Sign up for your new account! **********");
		
		System.out.println("\nPlease enter your email address: ");
		usersInformation[USERS_EMAIL] = input.nextLine();

		System.out.println("Please enter a password: ");
		usersInformation[USERS_PASSWORD] = input.nextLine();

		System.out.println("Please enter your first and last name: ");
		usersInformation[USERS_NAME] = input.nextLine();
		
		String regexStr = "^[0-9]{11}$";
		System.out.println("Please enter your telephone number: ");
		String userInput = input.nextLine(); 
		
		while(!(userInput.matches(regexStr))) {
			
			System.out.println("Not a valid phone number, please enter your telephone number: ");
			userInput = input.nextLine(); 
			
		}
		usersInformation[USERS_PHONE_NUMBER] = userInput; 
		
		 
		
		System.out.println("Would you like to sign up for SMS notifications? (Please enter yes or no):");
		usersInformation[SMS_NOTIFICATIONS] = input.nextLine(); 
		
		return usersInformation;

	}
	
	/**
	 * This method displays the message that the users account is already active
	 * @param userInformation
	 */
	public void displayAlreadyActiveMessage(String[] userInformation) {
		System.out.println(
				"********** Dear " + userInformation[USERS_NAME] + ", your account is already active. **********");
	}

	/**
	 * This method is used to inform a user that their account is pending approval 
	 * @param userInformation
	 */
	public void displayPendingApprovalMessage(String[] userInformation) {
		System.out.println(
				"********** Dear " + userInformation[USERS_NAME] + ", your account is pending approval **********");
	}
	
	/**
	 * Method dispplays the user used invalid login credentials
	 */
	public void showIncorrectLoginCredentialsMessage() {
		System.out.println("Incorrect log-in credentials.");
	}

	/**
	 * This method displays the message that the user has been granted admin privileges 
	 */
	public void showUserHasBeenGrantedAdminPrivlegesMessage() {
		System.out.println("User has been granted admin privileges");
	}

	/**
	 * This method displays a message that the user does not exist 
	 */
	public void showUserDoesNotExistMessage() {
		System.out.println("That user does not exist.\n");
	}
	
	/**
	 * Displays that there are no users to promote
	 */
	public void thereAreNoUsersToPromoteMessage() {
		System.out.println("There are no users to promote. \n");
	}

	/**
	 * Displays that the user has been unblocked
	 */
	public void showUserHasBeenUnblockedMessage() {
		System.out.println("User has been unblocked");
	}
	
	/**
	 * Shows that there is no user to unblock
	 */
	public void showNoUserToUnblockMessage() {
		System.out.println("No user to unblock.");
	}
	
	/**
	 * Displays goodbye message when user logs out
	 */
	public void showGoodbyeMessage() {
		System.out.println("\nGoodbye.");
	}
	
	/**
	 * Displays successfull withdraw message
	 */
	public void showWithdrawSuccessMessage() {
		System.out.println("Withdraw was succesfull.");
	}
	
	/**
	 * Displays insufficient funds message
	 */
	public void showInsuffecientFundsMessage() {
		System.out.println("Insufficient funds.");
	}

	/**
	 * Displays that a deposit has been completed 
	 */
	public void showDepositCompleteMessage() {
		System.out.println("Deposit Complete.");
	}
	
	/**
	 * Displays message that a user has been blocked
	 */
	public void showUserWasBlockedMessage() {
		System.out.println("User was blocked.\n");
	}
	
	/**
	 * Displays message that the account was successfully activated
	 */
	public void showAccountWasSuccessfullyActivatedMessage() {
		System.out.println("Account successfully activated.\n");
	}
	
	/**
	 * Displays message that user is on the pending approval list
	 */
	public void showUserIsOnWaitingListMessage() {
		System.out.println("Sorry, you are still on the waiting list.");
	}
	
	public void showUserIsNowOnWaitingListMessage() {
		System.out.println("********** Your account is pending approval **********");
	}
	
	/**
	 * Closes the input stream
	 */
	public void cleanUp() {
		input.close(); 
	}
	

	/**
	 * This method is used to get customer information that already have an account.
	 * It returns if the customer is an admin, their email and password
	 * @return String[]
	 */
	public String[] getLoginInfo() {

		final int ADMINISTATOR_PRIVLEGE = 2;
		String[] usersInformation = new String[3];

		System.out.println("********** Log-in to your account **********");

		System.out.println("Are you signing in as an Administrator? ('yes' or 'no')");
		usersInformation[ADMINISTATOR_PRIVLEGE] = input.next();

		System.out.println("Please enter your email address: ");
		usersInformation[USERS_EMAIL] = input.next();

		System.out.println("Please enter a password: ");
		usersInformation[USERS_PASSWORD] = input.next();

		return usersInformation;
	}

}