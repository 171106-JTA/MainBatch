package com.emeraldbank.controller;

import java.util.ArrayList;
import java.util.List;

import com.emeraldbank.accounts.Acct;
import com.emeraldbank.accounts.Bank;
import com.emeraldbank.dao.EmeraldBankDao;
import com.emeraldbank.dao.EmeraldBankDaoImpl;
import com.emeraldbank.users.User;
import com.emeraldbank.users.UsersDataBase;


/**
 * Controller class, containing methods for the UserInterface to call for 
 * all functions used by the user. Controller uses these method calls 
 * to call methods in other classes to implement their functionality and 
 * perform transactions
 * @author sjgillet
 *
 */
public class Controller {

	private static Controller ctlr; 
	private static UsersDataBase udb; 
	private static Bank bank; 
	private static EmeraldBankDao dao;
	/**
	 * Initializes the controller class's references to the 
	 * usersDataBase and Bank classes, and instantiates a reference 
	 * to itself for the Controller class to use
	 */
	public static void initialize() { 
		if(ctlr == null)
			ctlr = new Controller(); 
//		if(udb == null)
//			udb = UsersDataBase.getUsersDataBase();
//		if(bank == null)
//			bank = Bank.getBank();		
		//		for(Acct acct : bank.getAllAccts().values()) {
		//			udb.getUser(acct.getOwnerID()).addAcct(acct.getAcctNum()); 
		//		}
		dao = new EmeraldBankDaoImpl(); 

	}


	//=========================================

	/**
	 * Calls method from UsersDataBase to check if a user with the 
	 * given ID is in the database
	 * @param userID - the userID of the user to check for 
	 * @return returns true if the user's ID is found in the 
	 * user database's keyset, false otherwise
	 */
	public static boolean userExists(String userID) {
		//return udb.userExists(userID);
		User u = dao.getUserByLoginId(userID, false); 
		return (u != null); 
	}	

	/**
	 * Calls method from UsersDatabase to verify the given user's ID 
	 * and password
	 * @param userID - the user's ID to search for and check against
	 * @param password - the password passed to check against the user's ID
	 * @return - 1 if the userID and password match for login credentials.
	 * returns 0 if userID was not found.
	 * returns -1 if the userID was found but the password did not match.
	 */
	public static int checkCredentials(String userID, String password) {
		//return udb.checkUserPassword(userID, password);
		User u = dao.getUserByLoginId(userID, false); 
		if(u == null)
			return 0; 
		if(u.getPassword().equals(password))
			return 1;
		else return -1; 
	}

	/**
	 * Checks if the User with the given userID is flagged as an administrator
	 * @param userID - the userID to search UsersDataBase for
	 * @return - 1 if the user is an administrator, -1 if not.
	 * returns 0 if the userID was not found
	 */
	public static int checkAdmin(String userID) {
//		return udb.checkUserAdmin(userID);
		return dao.getUserIsAdminByLoginId(userID); 
	}

	/**
	 * Calls Bank's method to verify if the given userID is the owner of 
	 * the given account
	 * @param userID - the user's ID to check against for ownership
	 * @param acctNum - the account to check ownership for
	 * @return 1 if the user is the account's owner.
	 * 0 if the account was not found.
	 * -1 if the account was found, but the userID given is not the account owner.
	 */
	public static int verifyOwnership(String userID, String acctNum) {
		//		if(bank.acctExists(acctNum)) {
		//			if(userID.equals(bank.getAcctOwnerID(acctNum)))
		//				return 1; //verified account ownership
		//			else return -1; //apparent tampering attempt
		//		}
		//		else return 0; //incorrect account number
		Acct a = dao.getAcctByAcctNum(acctNum); 
		User u = dao.getUserByLoginId(userID); 
		if(a == null || u == null)
			return 0; 
		if(a.getOwnerID().equals(u.getUserID()))
				return 1;
		else return -1; 


	}

	/**
	 * Calls Bank's methods to deposit funds into the given user account
	 * @param acctNum - the account to deposit to
	 * @param amt - amount of money to deposit into account
	 * @return - 1 if the deposit was successful.
	 * 0 if the account was not found.
	 * -1 if the account is not yet activated.
	 * -2 if the account is locked.
	 * -4 unexpected error
	 */
	public static int depositFunds(String acctNum, double amt) {
		//		return bank.deposit(acctNum, amt);
		int status = dao.getBankAcctStatus(acctNum);
		if(status < 0)	//-1 for inactive, -2 for locked
			return status; 
		double b = dao.getAcctBalance(acctNum); 

		double newB = dao.depositIntoAcct(acctNum, amt);
		if(newB != b + amt)
			return -4;

		return 1; //successful operation	

	}

	/**
	 * Calls Bank's methods to withdraw funds from the given user account
	 * @param acctNum - the account to withdraw from
	 * @param amt - amount of money to withdraw from account
	 * @return - 1 if the withdrawal was successful.
	 * 0 if the account was not found.
	 * -1 if the account is not yet activated.
	 * -2 if the account is locked.
	 * -3 if there are insufficient funds
	 * -4 unexpected error
	 */
	public static int withdrawFunds(String acctNum, double amt) {
		//		return bank.withdraw(acctNum, amt);
		int status = dao.getBankAcctStatus(acctNum);
		if(status < 0)	//-1 for inactive, -2 for locked
			return status; 
		double b = dao.getAcctBalance(acctNum); 
		if(b < amt)
			return -3; //insufficient funds to withdraw

		double newB = dao.withdrawFromAcct(acctNum, amt);
		if(newB != b - amt)
			return -4;

		return 1; //successful operation		
	}


	/**
	 * Checks the balance of a given bank account via Bank's methods
	 * @param acctNum - the account to check the balance of
	 * @return - the balance of the given bank account.
	 * Returns -1.0 if the account is not found.
	 */
	public static double balanceInquiry(String acctNum) {
		//return bank.balanceInquiry(acctNum);
		return dao.getAcctBalance(acctNum); 
	}

	/**
	 * Checks the given user account's active status via the UsersDataBase methods
	 * @param userID - the user to check the status of
	 * @return - 1 if the user is active and can perform actions.
	 * 0 if the user is not yet activated.
	 * -1 if the user account is locked.
	 */
	public static int getUserStatus(String userID) {
//		return udb.getStatus(userID);
		return dao.getUserStatus(userID); 
	}

	/**
	 * Checks the given user account's active status via sql query
	 * @param userID - the user to check the status of 
	 * @return - "ACTIVE" if the user is active and can perform actions.
	 * "INACTIVE" if the user is not yet activated.
	 * "LOCKED" if the user account is locked.
	 * "" if the account was not found; 
	 */
	public static String getUserStatusString(String userID) {
		return dao.getUserStatusString(userID);

	}

	/**
	 * Checks the given bank account's active status via Bank's methods
	 * @param acctNum - the account to get the status of
	 * @return - 1 if the account is active and usable.
	 * 0 if the account is not yet activated.
	 * -1 if the account is locked.
	 * -2 if the account was not found. 
	 */
	public static int getAcctStatus(String acctNum) {
		//		return bank.getStatus(acctNum); 
		return dao.getBankAcctStatus(acctNum); 
	}

	/**
	 * Checks the given bank account's activity status via database query,
	 * returning the string representation of its status
	 * @param acctNum - the account to get the status of 
	 * @return - The string representation of its status;
	 * "ACTIVE" if the account is active and usable. 
	 * "INACTIVE" if the account is not yet activated.
	 * "LOCKED" if the account is locked.
	 * "" if the account was not found. 
	 */
	public static String getAcctStatusString(String acctNum) {
		//		return bank.getAcctStatusString(acctNum); 
		return dao.getBankAcctStatusString(acctNum); 
	}


	/**
	 * Gets the string representation of the all the user's bank accounts. This
	 * method uses the default behavior, calling an overloaded version with
	 * the default flags, truncated and redacted. This displays the data
	 * such that only the first few are displayed if there are many accounts, and 
	 * the first few digits of the account numbers are hidden from the console. 
	 * @param userID - The login id of the user whose accounts to be fetched
	 * @return - a String containing an informative summary of the user's bank
	 * accounts. 
	 */
	public static String getUserPortfolio(String userID) {
		return getUserPortfolio(userID, true, true); 
	}
	
	/**
	 * Gets the string representation of all of the user's bank accounts.
	 * Accepts flags to determine whether the data is cut short if there are
	 * many accounts to list, and if the account numbers will be partially hidden. 
	 * @param userID - the login id of the user whose bank accounts are to be fetched.
	 * @param truncated - If true, the first few (usually 5) bank accounts are displayed, 
	 * then '..' follows if there are more, before cutting off the string representation
	 * @param redacted - If true, partially hides the account numbers of the bank accounts
	 * displayed
	 * @return - The string representation of the summary of the user's bank accounts. 
	 */
	public static String getUserPortfolio(String userID, boolean truncated, boolean redacted) {
		List<Acct> accts = dao.getPortfolioByLoginId(userID); 
		
		String portfolio = userID + " Account Summary:\n"; 
		if(accts.isEmpty()) {
			portfolio += "***You do not have any bank accounts with Emerald City Bank."
					+ "Use 'Open' to start banking!"; 
			return portfolio; 
		}
		portfolio += String.format("%12s", "Account # |");
		portfolio += String.format("%14s", "Balance |");
		portfolio += String.format("%10s", "Status"); 
		
		int i = 0; 
		for(Acct a : accts) {
			String acctNumString = (redacted) 
					? a.getAcctNum().substring(0,Consts.ACCOUNT_NUMBER_REDACT_LENGTH).replaceAll(".", "*") + 
							a.getAcctNum().substring(Consts.ACCOUNT_NUMBER_REDACT_LENGTH)
					: a.getAcctNum(); 
			portfolio += String.format("\n%12s", "#" + acctNumString + " |");
			portfolio += String.format("%14s", "$" + String.format("%.2f", a.getBalance()) + " |");
			portfolio += String.format("%10s", a.getStatusString());	
			if(truncated && ++i == 5) {
				portfolio += String.format("\n%12s", ".. |");
				portfolio += String.format("\n%14s", ".. |");
				portfolio += String.format("\n%10s", "..");
				break; 
			}
		}

		return portfolio; 
	}

	/**
	 * Generates a User object and passes it to the UserDataBase to insert into its 
	 * collection. 
	 * @param firstName - First name of the new user
	 * @param lastName - Last name of the new user
	 * @param userID - login userID of the new user
	 * @param password - login password of the user	 * 
	 */
	public static void createUser(String firstName, String lastName, String userID, String password) {
		User u = new User(firstName, lastName, userID, password);
		createUser(u); 
//		udb.addUser(u);
	}

	/**
	 * Passes along the given user to the UserDataBank for adding into its collection
	 * @param u - The user object to be added
	 */
	public static void createUser(User u) {
//		udb.addUser(u);
		dao.createNewUser(u);
	}

	/**
	 * Sends a request to open a bank account via Bank's methods, and adds
	 * the new account to the user's accounts list.
	 * @param userID - the userID of the user opening a  bank account. 
	 * This userID will be the owner of the generated bank account
	 * @return - the account number of the generated bank account
	 */
	public static String createBankAcct(String userID) {
//		String acctNum =  bank.openAcct(userID);
//		udb.addAccountToUser(acctNum, userID); 
		
		String acctNum = ""; 
		for(int i = 0; i < Consts.ACCOUNT_NUMBER_LENGTH; i++) 
			acctNum += (char)(Math.random()*10 + '0'); 
		
		dao.createNewBankAcct(acctNum, userID); 
		return acctNum; 
	}

	/**
	 * Sends a request to close the bank account with the given 
	 * account number and removes the given account from the owner's 
	 * account list.
	 * @param acctNum - the number for the account to close
	 * @return - the ending balance of the account to be closed
	 */
	public static double closeBankAcct(String acctNum) {
//		udb.getUser(bank.getAcctOwnerID(acctNum)).removeAcct(acctNum);
//		return bank.closeAcct(acctNum);
		
		double balance = dao.getAcctBalance(acctNum);
		dao.closeBankAcct(acctNum);
		return balance; 
		
	}

	/**
	 * Sends a request to activate the given user's user account for use
	 * @param userID - the userID of the user account to be activated 
	 * @return - 1 if user account was activated successfully.
	 * 0 if the user account was not found.
	 */
	public static int activateUser(String userID) {
//		return udb.activateUser(userID);
		return dao.activateUser(userID); 
	}

	/**
	 * Sends a request to activate the given bank account for transactions
	 * @param acctNum - the account number for the  bank account to activate 
	 * @return - 1 if the account activation was successful.
	 * 0 if the account was not found.
	 */
	public static int activateBankAcct(String acctNum) { 
//		return bank.activateAcct(acctNum);
		return dao.activateBankAcct(acctNum);
	}

	/**
	 * Sends a request to lock the given user's user account
	 * to bar it from taking action. Also locks all of that user's 
	 * bank accounts. 
	 * @param userID - The user's ID to search for to lock
	 * @return - 1 if the lock was successful.
	 * 0 if the user's ID or any of their bank accounts were not found.
	 */
	public static int lockUser(String userID) {
//		int rslt = udb.lockUser(userID);
//		if(rslt == 1) { //found and locked userID
//			for(String s : udb.getUser(userID).getAccts())
//				if(lockAcct(s) == 0) //find each acct, lock each as it's found
//					rslt = 0; //an acct was not found
//		}

		int rslt = dao.lockUser(userID);
		return rslt; 
	}

	/**
	 * Sends a request to lock the given bank account, barring
	 * it from transactions
	 * @param acctNum - the number for the account to lock
	 * @return 1 if the lock was successful.
	 * 0 if the account was not found.
	 */
	public static int lockAcct(String acctNum) { 
//		return bank.lockAcct(acctNum); 
		return dao.lockAcct(acctNum);
	}

	/**
	 * Sends a request to promote the given user account to 
	 * administrator role. 
	 * @param userID - the userID of the account to be promoted
	 * @return - 1 if the promotion was successful.
	 * 0 if the user account was not found.
	 */
	public static int promoteAdmin(String userID) {
//		return udb.promoteUser(userID); 
		return dao.promoteToAdmin(userID);
	}



}
