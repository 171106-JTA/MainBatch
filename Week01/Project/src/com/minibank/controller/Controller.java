package com.minibank.controller;

import org.apache.log4j.Logger;

import com.minibank.accounts.Bank;
import com.minibank.users.User;
import com.minibank.users.UsersDataBase;


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

	/**
	 * Initializes the controller class's references to the 
	 * usersDataBase and Bank classes, and instantiates a reference 
	 * to itself for the Controller class to use
	 */
	public static void initialize() { 
		if(ctlr == null)
			ctlr = new Controller(); 
		if(udb == null)
			udb = UsersDataBase.getUsersDataBase();
		if(bank == null)
			bank = Bank.getBank();
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
		return udb.userExists(userID);
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
		return udb.checkUserPassword(userID, password);

	}

	/**
	 * Checks if the User with the given userID is flagged as an administrator
	 * @param userID - the userID to search UsersDataBase for
	 * @return - 1 if the user is an administrator, -1 if not.
	 * returns 0 if the userID was not found
	 */
	public static int checkAdmin(String userID) {
		return udb.checkUserAdmin(userID);
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
		if(bank.acctExists(acctNum)) {
			if(userID.equals(bank.getAcctOwnerID(acctNum)))
				return 1; //verified account ownership
			else return -1; //apparent tampering attempt
		}
		else return 0; //incorrect account number
	}

	/**
	 * Calls Bank's methods to deposit funds into the given user account
	 * @param acctNum - the account to deposit to
	 * @param amt - amount of money to deposit into account
	 * @return - 1 if the deposit was successful.
	 * 0 if the account was not found.
	 * -1 if the account is not yet activated.
	 * -2 if the account is locked.
	 */
	public static int depositFunds(String acctNum, double amt) {
		return bank.deposit(acctNum, amt);
	}

	/**
	 * Calls Bank's methods to withdraw funds from the given user account
	 * @param acctNum - the account to withdraw from
	 * @param amt - amount of money to withdraw from account
	 * @return - 1 if the withdrawal was successful.
	 * 0 if the account was not found.
	 * -1 if the account is not yet activated.
	 * -2 if the account is locked.
	 */
	public static int withdrawFunds(String acctNum, double amt) {

		return bank.withdraw(acctNum, amt); 
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
		return bank.getStatus(acctNum); 
	}

	/**
	 * Checks the given user account's active status via the UsersDataBase methods
	 * @param userID - the user to check the status of
	 * @return - 1 if the user is active and can perform actions.
	 * 0 if the user is not yet activated.
	 * -1 if the user account is locked.
	 */
	public static int getUserStatus(String userID) {
		return udb.getStatus(userID); 
	}

	/**
	 * Checks the balance of a given bank account via Bank's methods
	 * @param acctNum - the account to check the balance of
	 * @return - the balance of the given bank account.
	 * Returns -1.0 if the account is not found.
	 */
	public static double balanceInquiry(String acctNum) {
		return bank.balanceInquiry(acctNum);
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
		udb.addUser(u);
	}

	/**
	 * Passes along the given user to the UserDataBank for adding into its collection
	 * @param u - The user object to be added
	 */
	public static void createUser(User u) {
		udb.addUser(u);
	}
	
	/**
	 * Sends a request to open a bank account via Bank's methods, and adds
	 * the new account to the user's accounts list.
	 * @param userID - the userID of the user opening a  bank account. 
	 * This userID will be the owner of the generated bank account
	 * @return - the account number of the generated bank account
	 */
	public static String createBankAcct(String userID) {
		String acctNum =  bank.openAcct(userID);
		udb.getUser(userID).addAcct(acctNum); 
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
		udb.getUser(bank.getAcctOwnerID(acctNum)).removeAcct(acctNum);
		return bank.closeAcct(acctNum);
	}

	/**
	 * Sends a request to activate the given user's user account for use
	 * @param userID - the userID of the user account to be activated 
	 * @return - 1 if user account was activated successfully.
	 * 0 if the user account was not found.
	 */
	public static int activateUser(String userID) {
		return udb.activateUser(userID);
	}

	/**
	 * Sends a request to activate the given bank account for transactions
	 * @param acctNum - the account number for the  bank account to activate 
	 * @return - 1 if the account activation was successful.
	 * 0 if the account was not found.
	 */
	public static int activateBankAcct(String acctNum) { 
		return bank.activateAcct(acctNum);
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
		int rslt = udb.lockUser(userID);
		if(rslt == 1) { //found and locked userID
			for(String s : udb.getUser(userID).getAccts())
				if(lockAcct(s) == 0) //find each acct, lock each as it's found
					rslt = 0; //an acct was not found
		}
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
		return bank.lockAcct(acctNum); 
	}

	/**
	 * Sends a request to promote the given user account to 
	 * administrator role. 
	 * @param userID - the userID of the account to be promoted
	 * @return - 1 if the promotion was successful.
	 * 0 if the user account was not found.
	 */
	public static int promoteAdmin(String userID) {
		return udb.promoteUser(userID); 
	}



}
