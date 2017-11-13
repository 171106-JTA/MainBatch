package com.minibank.controller;

import java.util.Scanner;

import org.apache.log4j.Logger;

/**
 * UserInterface class, containing the main method for the project, and 
 * containing functionality for the user to interact with the Bank and 
 * UsersDatabase classes via the Controller class. Contains only the 
 * data and function calls that the user needs to see. Contains all the 
 * console output the user will see, and all the user-input prompts for 
 * the user. 
 * @author sjgillet
 *
 */
public class UserInterface {

	private static Scanner sc; 
	private static String curUser = ""; 
	private static int tamperingAttempts = 0; 

	public static final Logger logger = Logger.getLogger(UserInterface.class); 

	public static void main(String[] args) {

		//launching, set up variables
		Controller.initialize();
		Consts.initialize();

		sc = new Scanner(System.in);

		String cmd = ""; 

		/*
		 * ========== LANDING STAGE ========== 
		 */
		do {
			System.out.println("===== *.^' Welcome to Emerald City Bank *'.^ =====");


			do { // ========== LOGIN STAGE ========== 
				//get user command
				cmd = promptEntry(Consts.COMMAND_SYNTAX, "Login | Create Account | Exit\n"); 

				//login user behavior
				if(strCompare(cmd, Consts.LOGIN_CMD)) {
					String userID = promptEntry(Consts.USERNAME_SYNTAX, "User ID: ");
					String pw = promptEntry(Consts.PASSWORD_SYNTAX, "Password: "); 

					logger.info(userID + " attempted login.");

					if(Controller.checkCredentials(userID, pw) != 1) {
						printMsg("Incorrect userID/password. Please try again.");
						logger.info(userID + " failed login");
						continue; 						
					}
					else if(Controller.checkCredentials(userID, pw) == 1)
					{
						if(Controller.getUserStatus(userID) == 0) {
							printMsg("This account has not been activated yet. Please try again later."); 
							continue; 
						}
						curUser = userID;
						logger.trace(curUser + " logged on.");
						break; //successful login
					}					
				}

				//Create User behavior
				if(strCompare(cmd, Consts.CREATE_USER_CMD)) {
					String firstName = promptEntry(Consts.LEGAL_NAME_SYNTAX, "First Name: ");
					String lastName = promptEntry(Consts.LEGAL_NAME_SYNTAX, "Last Name: ");
					String userID = promptEntry(Consts.USERNAME_SYNTAX, "Enter a user ID for yourself: ");
					logger.trace("Creating user account."); 
					if(Controller.userExists(userID)) {
						printMsg("User ID already exists. Please choose another.");
						logger.trace("UserID already exists.");
						continue; 
					}
					String pw = promptEntry(Consts.PASSWORD_SYNTAX, "Enter a password for your account:" );
					String pwcheck = promptEntry(Consts.PASSWORD_SYNTAX, "Re-Enter Password: ");

					if(pw.equals(pwcheck)) {
						printMsg("\nYour credentials: \nName: " + firstName + " " + lastName + 
								"\nUser ID: " + userID + "\nPassword: ********"); 

						do {
							String ans = promptEntry(Consts.YES_NO_SYNTAX, "Confirm Y/N");
							if(strCompare(ans, Consts.AFFIRMATIVE_CMD)) {
								Controller.createUser(firstName, lastName, userID, pw);
								printMsg("\nUser account created. Thank you for banking with Emerald City Bank!");
								printMsg("Your account must be activated before you can use it. Please come back later."); 
								logger.info("Created new user account: " + userID);
								break; 								
							}
							if(strCompare(ans,Consts.NEGATIVE_CMD))
							{
								logger.trace("Confirmation declined. Aborted user account creation");
								printMsg("Canceling account creation"); 
								break; 
							}
						} while(true); 
					}
					else {
						printMsg("Passwords do not match. Aborting"); 
						logger.trace("Passwords do not match. Aborted user account creation");
					}

				}


				if(strCompare(cmd, Consts.EXIT_CMD))  {
					printMsg("Exiting...");
					logger.info("Program exit");
					//clear variables
					return; 
				}

			}while(true); //========== END LOGIN STAGE ========== 



			//========== BEGIN TRANSACTION STAGE ========== 
			do {	
				String prompt = Controller.checkAdmin(curUser) == 1 ? Consts.ADMIN_TRANSACTION_PROMPT : Consts.USER_TRANSACTION_PROMPT;
				cmd = promptEntry(Consts.COMMAND_SYNTAX, "\n" + prompt + "Transaction: "); 

				if(Controller.getUserStatus(curUser) == -1)
					printMsg("***This account is locked. You may not make any transactions with your bank accounts, except balance inquiries."); 


				//logout behavior
				if(strCompare(cmd, Consts.LOGOUT_CMD)) {
					printMsg("Have a nice day! Logging you out..."); 
					logger.info(curUser + " logged out");
					//clear variables
					curUser = ""; 
					tamperingAttempts = 0; 
					break; 
				}

				//balance inquiry behavior
				if(strCompare(cmd, Consts.BALANCE_INQ_CMD)) {
					logger.trace(curUser + " CMD: Balance Inquiry");

					String acctNum = promptEntry(Consts.ACCT_NUM_SYNTAX, "Enter the account number for the account to check balance: ");
					int v = Controller.verifyOwnership(curUser, acctNum);
					if(v < 1) {
						if((v = displayAcctIncorrect(v)) == -1)
							break; //tampering flagged too many times, locking and logging out
						else continue; 
					}

					int status = Controller.getAcctStatus(acctNum);
					double val = Controller.balanceInquiry(acctNum);

					String str = ""; 
					if(status == 0) 
						str = "This account is not yet activated. Please try again later when it has been activated.";
					else str = "Account #" + acctNum + ": Balance " + val; 
					if(status == -1)
						str += "\n---This account has been LOCKED by an administrator. While it is locked, no deposits" + 
								"or withdrawels may be made to or from it."; 
					printMsg(str); 
					logger.info(curUser + " inquired balance on account #" + acctNum + " successsfully");

				}

				//deposit behavior
				if(strCompare(cmd, Consts.DEPOSIT_CMD)) {
					if(Controller.getUserStatus(curUser) == -1) { //locked user
						printMsg("This user account is locked. You may not make transactions while this account is locked.");
						logger.trace("Locked user " + curUser + " attempted deposit");
						continue; 
					}

					logger.trace(curUser + " CMD: Deposit");

					//get and check account number
					String acctNum = promptEntry(Consts.ACCT_NUM_SYNTAX, "Enter the account number for the account you wish to deposit to: ");
					int v = Controller.verifyOwnership(curUser, acctNum);
					if(v < 1) {
						if((v = displayAcctIncorrect(v)) == -1)
							break; //tampering flagged too many times, locking and logging out
						else continue; 
					}


					double amt = Double.parseDouble(promptEntry(Consts.DBL_2_DECIMALS_SYNTAX, "Enter amount to deposit: ")); 
					int rslt = Controller.depositFunds(acctNum, amt);
					switch(rslt) {
					case 1: printMsg("successfully deposited " + amt + " into your account. Current balance: " + Controller.balanceInquiry(acctNum)); 
					logger.info(curUser + " deposited on account #" + acctNum + " successfully");
					break;
					
					case 0: printMsg("Incorrect account number. Please ensure you have the correct account number and try again."); break; 
					case -1: printMsg("This account is not yet active. Please try again after your bank account has been activated."); 
					case -2: printMsg("This banking account has been locked. You may not make any transactions with it but a balance inquiry while it is locked"); break; 
					}

					continue; 
				}

				//withdraw behavior
				if(strCompare(cmd, Consts.WITHDRAW_CMD)) {
					if(Controller.getUserStatus(curUser) == -1) { //locked user
						printMsg("This user account is locked. You may not make transactions while this account is locked.");
						logger.trace("Locked user " + curUser + " attempted withdraw");
						continue; 
					}
					logger.trace(curUser + " CMD: Withdraw");
					String acctNum = promptEntry(Consts.ACCT_NUM_SYNTAX, "Enter the account number for the account you wish to withdraw from: ");
					int v = Controller.verifyOwnership(curUser, acctNum);
					if(v < 1) {
						if((v = displayAcctIncorrect(v)) == -1)
							break; //tampering flagged too many times, locking and logging out
						else continue; 
					}

					double amt = Double.parseDouble(promptEntry(Consts.DBL_2_DECIMALS_SYNTAX, "Enter amount to withdraw: ")); 
					int rslt = Controller.withdrawFunds(acctNum, amt);
					if(rslt < 1)
					{
						if(rslt == 0)
							printMsg("Could not access account: Incorrect bank account number."); 
						else if(rslt == -1)
							printMsg("Could not access account: Account inactive. Try again later.");
						else if(rslt == -2)
							printMsg("Could not access account: Account has been locked."); 
						else if(rslt == -3)
							printMsg("Insufficient Funds");
					}
					else  {
						double balance = Controller.balanceInquiry(acctNum);
						printMsg("successfully withdrew funds. Current balance: " + balance); 
						logger.info(curUser + " withdrew funds on account #" + acctNum + " successfully");
					}
					
					

					continue; 
				}

				//transfer funds behavior
				if(strCompare(cmd, Consts.TRANSFER_CMD)) {
					if(Controller.getUserStatus(curUser) == -1) { //locked user
						printMsg("This user account is locked. You may not make transactions while this account is locked.");
						logger.trace("Locked user " + curUser + " attempted transfer");
						continue; 
					}

					logger.trace(curUser + " CMD: Transfer Funds");
					String acctNumFrom = promptEntry(Consts.ACCT_NUM_SYNTAX, "Enter the account number for the account you wish to transfer from: ");
					int v = Controller.verifyOwnership(curUser, acctNumFrom);
					if(v < 1) {
						if((v = displayAcctIncorrect(v)) == -1)
							break; //tampering flagged too many times, locking and logging out
						else continue; 
					}

					String acctNumTo = promptEntry(Consts.ACCT_NUM_SYNTAX, "Enter the account number for the account you wish to transfer to: "); 
					v = Controller.verifyOwnership(curUser, acctNumTo);
					if(v < 1) {
						if((v = displayAcctIncorrect(v)) == -1)
							break; //tampering flagged too many times, locking and logging out
						else continue; 
					}

					double amt = Double.parseDouble(promptEntry(Consts.DBL_2_DECIMALS_SYNTAX, "Enter amount to transfer: "));					

					int rslt = Controller.withdrawFunds(acctNumFrom, amt);
					if(rslt < 1)
					{
						if(rslt == 0)
							printMsg("Could not access account: Incorrect bank account number."); 
						else if(rslt == -1)
							printMsg("Could not access account: Account inactive. Try again later.");
						else if(rslt == -2)
							printMsg("Could not access account: Account has been locked."); 
						else if(rslt == -3)
							printMsg("Insufficient Funds");
						continue; 
					}

					rslt = Controller.depositFunds(acctNumTo, amt);

					if(rslt ==  1) {
						printMsg("successfully transfered " + amt + " from account #"+ acctNumFrom + " to account #" + acctNumTo +
								". Current balances: #" + acctNumFrom + ": " + Controller.balanceInquiry(acctNumFrom) + "\t" + 
								"#" + acctNumTo + ": " + Controller.balanceInquiry(acctNumTo));
						logger.info(curUser + " transfered funds from account #" + acctNumFrom + 
								"to account #" + acctNumTo + " successfully");
					}
					else {
						switch(rslt) {
						case 0: printMsg("Incorrect account number. Please ensure you have the correct account number and try again."); break; 
						case -1: printMsg("This account is not yet active. Please try again after your bank account has been activated."); 
						case -2: printMsg("This banking account has been locked. You may not make any transactions with it but a balance inquiry while it is locked"); break; 
						}
						Controller.depositFunds(acctNumFrom, amt);
					}

					continue; 
				}




				//open account behavior
				if(strCompare(cmd, Consts.OPEN_ACCT_CMD)) {
					if(Controller.getUserStatus(curUser) == -1) { //locked user
						printMsg("This user account is locked. You may not make transactions while this account is locked.");
						logger.trace("Locked user " + curUser + " attempted opening bank account");
						continue; 
					}
					logger.trace(curUser + " CMD: Open New Bank Account");
					String answer = promptEntry(Consts.YES_NO_SYNTAX, "Open new banking account? Y/N: ");
					if(strCompare(answer, Consts.NEGATIVE_CMD)) {
						printMsg("Alright, maybe another day.");
						continue; 
					}

					String acctNum = Controller.createBankAcct(curUser); 
					printMsg("Thank you for opening an account with Emerald City Bank! Your account number is "
							+ acctNum + ". "
							+ "A banking administrator will first have to activate your new account before you "
							+ "can begin banking.");
					logger.info(curUser + " opened account #" + acctNum + " successfully");

					continue; 
				}

				//close account behavior
				if(strCompare(cmd, Consts.CLOSE_ACCT_CMD)) {
					if(Controller.getUserStatus(curUser) == -1) { //locked user
						printMsg("This user account is locked. You may not make transactions while this account is locked.");
						logger.trace("Locked user " + curUser + " attempted closing a bank account");
						continue; 
					}

					logger.trace(curUser + " CMD: Close Bank Account");
					String acctNum = promptEntry(Consts.ACCT_NUM_SYNTAX, "Enter the account number for the account you wish to close: "); 
					int v = Controller.verifyOwnership(curUser, acctNum);
					if(v < 1) {
						if((v = displayAcctIncorrect(v)) == -1)
							break; //tampering flagged too many times, locking and logging out
						else continue; 
					}

					double balance = Controller.balanceInquiry(acctNum); //get balance of the account
					String balanceMsg = ""; //overdrawn accounts owe the bank money
					if(balance < 0.0)
						balanceMsg = "On this account, you still owe " + Double.toString(balance) + 
						"Emerald City Bank will contact you soon to resolve this debt."; 
					else balanceMsg = "This account's balance is " + Double.toString(balance); 

					String answer = promptEntry(Consts.YES_NO_SYNTAX, "You are about to close account number " + acctNum + 
							balanceMsg + "\nAre you sure? Y/N"); 
					if(strCompare(answer, Consts.AFFIRMATIVE_CMD)) {
						String pw = promptEntry(Consts.PASSWORD_SYNTAX, "Please Re-Enter password: ");

						int rslt = Controller.checkCredentials(curUser, pw); 
						switch(rslt) {
						case 1: printMsg("Thank you for banking with Emerald City Bank. We hope to see you again.");
						logger.info(curUser + " closed account #" + acctNum + " successfully");
						Controller.closeBankAcct(acctNum);
						break;

						case -1: printMsg("Incorrect Password. Canceling transaction."); break;
						case 0: printMsg("Unexpected authentication error: Did not match userID."); break; 
						}

						continue; 
					}
					else {
						printMsg("Canceling transaction. Thank you for continuing to bank with Emerald City"); 
						//reset variables
						continue; 
					}
				}

				//Admin Activate User behavior
				if(strCompare(cmd, Consts.ACTIVATE_USER_CMD)) {
					if(Controller.getUserStatus(curUser) == -1) { //locked user
						printMsg("This admin account is locked. You may not perform transactions or admin actions "
								+ "while this account is locked.");
						logger.trace("Locked user " + curUser + " attempted activating a user");
						continue; 
					}

					logger.trace("Admin " + curUser + " CMD: Activate User");
					if(Controller.checkAdmin(curUser) == -1) {
						printMsg("Invalid Command."); 
						continue; 
					}

					String userID = promptEntry(Consts.USERNAME_SYNTAX, "Enter userID for user account to activate: "); 
					int rslt = Controller.activateUser(userID); 
					switch(rslt) {
					case 1: printMsg("successfully activated user " + userID);
					logger.info("Admin " + curUser + " activated user " + userID); 
					break; 
					
					case 0: printMsg("Could not identify user account with that userID");  break; 
					}

					continue; 
				}

				//Admin Activate Acct behavior
				if(strCompare(cmd, Consts.ACTIVATE_ACCT_CMD)) {
					if(Controller.getUserStatus(curUser) == -1) { //locked user
						printMsg("This admin account is locked. You may not perform transactions or admin actions "
								+ "while this account is locked.");
						logger.trace("Locked user " + curUser + " attempted activating a bank account");
						continue; 
					}
					logger.trace("Admin " + curUser + " CMD: Activate Bank Account");

					if(Controller.checkAdmin(curUser) == -1) {
						printMsg("Invalid Command."); 
						continue; 
					}

					String acctNum = promptEntry(Consts.ACCT_NUM_SYNTAX, "Enter account number to activate/unlock: "); 
					int rslt = Controller.activateBankAcct(acctNum);
					switch(rslt) {
					case 1: printMsg("successfully activated account #" + acctNum);
					logger.info("Admin " + curUser + " activated bank account #" + acctNum); 
					break; 
					case 0: printMsg("Could not identify bank account with that account number."); break; 					
					}

					continue; 
				}

				//Admin Lock User behavior
				if(strCompare(cmd, Consts.LOCK_USER_CMD)) {
					if(Controller.getUserStatus(curUser) == -1) { //locked user
						printMsg("This admin account is locked. You may not perform transactions or admin actions "
								+ "while this account is locked.");
						logger.trace("Locked user " + curUser + " attempted locking a user");
						continue; 
					}
					
					logger.trace("Admin " + curUser + " CMD: Lock User");
					if(Controller.checkAdmin(curUser) == -1) {
						printMsg("Invalid Command."); 
						continue; 
					}

					String userID = promptEntry(Consts.USERNAME_SYNTAX, "Enter user's ID to lock: "); 
					int rslt = Controller.lockUser(userID); 
					switch(rslt) {
					case 1: printMsg("successfully locked user " + userID);
					logger.info("Admin " + curUser + " locked user " + userID); 
					break; 
					//TODO 
					}

					continue; 
				}

				//Admin Lock Acct behavior
				if(strCompare(cmd, Consts.LOCK_ACCT_CMD)) {
					if(Controller.getUserStatus(curUser) == -1) { //locked user
						printMsg("This admin account is locked. You may not perform transactions or admin actions "
								+ "while this account is locked.");
						logger.trace("Locked user " + curUser + " attempted locking a bank account");
						continue; 
					}
					
					logger.trace("Admin " + curUser + " CMD: Lock Bank Account");
					if(Controller.checkAdmin(curUser) == -1) {
						printMsg("Invalid Command."); 
						continue; 
					}

					String acctNum = promptEntry(Consts.ACCT_NUM_SYNTAX, "Enter accoutn number for account to lock: ");
					int rslt = Controller.lockAcct(acctNum);
					switch(rslt) {
					case 1: printMsg("successfully locked account #" + acctNum); 
					logger.info("Admin " + curUser + " locked bank account #" + acctNum); break; 
					case 0: printMsg("Did not find account with that account number."); break; 
					//TODO
					}

					continue; 
				}

				//Admin Promote User behavior
				if(strCompare(cmd, Consts.PROMOTE_ADMIN_CMD)) {
					if(Controller.getUserStatus(curUser) == -1) { //locked user
						printMsg("This admin account is locked. You may not perform transactions or admin actions "
								+ "while this account is locked.");
						logger.trace("Locked user " + curUser + " attempted promoting a user");
						continue; 
					}
					
					if(Controller.checkAdmin(curUser) == -1) {
						printMsg("Invalid Command."); 
						continue; 
					}

					String userID = promptEntry(Consts.USERNAME_SYNTAX, "Enter user's ID to promote to Administrator: "); 

					String pw = promptEntry(Consts.PASSWORD_SYNTAX, "Re-Enter Password: ");
					int rslt = Controller.checkCredentials(curUser,pw);
					if(rslt < 1) {
						printMsg("Incorrect password. Cancelling transaction."); 
						continue; 
					}

					rslt = Controller.promoteAdmin(userID);
					switch(rslt) {
					case 1: printMsg("successfully promoted " + userID + " to administrator role");  
					logger.info("Admin " + curUser + " promoted user " + userID); 
					break; 
					//TODO
					}


					continue; 					
				}

			}while(true); // ========== END TRANSACTION STAGE ========== 

		}while(true);//========== END LOOP LANDING STAGE ========== 




	} //end main method

	/**
	 * prints a message to stdout when called
	 * @param msg - the message to print to stdout, as a String
	 */
	public static void printMsg(String msg) {
		System.out.println(msg);
	}

	/**
	 * Displays the parameterized message and then gets the next line input from the user
	 * @param msg - prompt message to print to stdout to user
	 * @return - the next string entered by the user after the prompt
	 */
	public static String promptEntry(String msg) {
		System.out.print(msg);
		String entry = sc.nextLine();
		return entry;
	}

	/**
	 * Helper method, sends execution to one of several promptEntry methods,
	 * depending on the flag passed to this method, each with its own syntax
	 * to check the prompted user-entered data against to check for input errors.
	 * Repeats prompt until either user follows syntax for input or enters an exit command
	 * @param syntaxFlag - defines the syntax to check the user input against; defines
	 * which promptEntry method this method sends execution to
	 * @param msg - prompt message to print to stdout to the user
	 * @return - the next string entered by the user following the prompt
	 */
	public static String promptEntry(int syntaxFlag, String msg) {
		//TODO: Make prompts with their own spell checking for each prompt type,
		//allow for the option to quit out of a prompt with 'q' or 'exit'
		if(syntaxFlag == Consts.DBL_2_DECIMALS_SYNTAX)
			return promptEntryDouble(msg); 
		return promptEntry(msg); 
	}
	
	private static String promptEntryDouble(String msg) {
		String entry; 
		do {
			System.out.println(msg);
			entry = sc.nextLine();
			try {
				Double.parseDouble(entry);
				break;
			} catch(NumberFormatException e) {
				if(strCompare(entry, Consts.EXIT_CMD))
					return "0.0"; 
				else printMsg("Invalid number format. Please enter a valid number."); 
			}
		} while(true);
		
		return entry; 
		
	}

	/**
	 * Checks the given string against an array of strings, returning true
	 * if it matches any of the strings in the array.
	 * @param cmd - the string to compare to the array
	 * @param strings - the array of strings to compare to the cmd string
	 * @return - true if the string returns as equal to any of the strings in the array
	 * false otherwise
	 */
	private static boolean strCompare(String cmd, String...strings)
	{
		cmd = cmd.toLowerCase();
		for(String str : strings)
		{
			if(cmd.equals(str))
				return true;
		}
		return false;
	}	

	/**
	 * Displays the error message for entering an incorrect account number for a 
	 * transaction action, with a flag v for severity. If v is -1, the account
	 * number matched an account other than what the user owns, and is flagged as
	 * an attempt at tampering with other accounts.
	 * If the user has gotten enough tampering flags in a session, this method
	 * returns a flag to lock their account and log them out
	 * @param v - flag for severity of improper account entry. If v is 0, it is 
	 * an invalid account number. If v is -1, the given account number matched an 
	 * account not owned by the user and is flagged for tampering
	 * @return flag for whether the user's account should be locked. -1 to lock the 
	 * user's account, 0 otherwise. 
	 */
	private static int displayAcctIncorrect(int v) {
		printMsg("Incorrect account number"); 
		if(v == -1) {
			tamperingAttempts++; 
			if(tamperingAttempts >= Consts.TAMPERING_ATMPTS_ALWD) {
				Controller.lockUser(curUser);
				tamperingAttempts = 0; 
				curUser = ""; 
				printMsg("This account has been locked for suspicious activity. Logging you out."); 
				return -1; //tampering flagged too many times, locking user
			}
		}
		return 0; 
	}

}
