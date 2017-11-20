package com.caleb.bank;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;
import java.util.stream.*; 
import java.util.function.*; 

/**
 * The BankModel class is responsible for encapsulating the data within the bank.
 * This class is used as a function, it takes requests in from the BankController class and produces
 * output that is displayed by the BankView class. This class also houses all the data to be
 * serialized. This class is also used for utility functions such as returning users who are
 * not admins so the admin can make a selection on who to promote. 
 * @author calebschumake
 *
 */

class BankModel implements java.io.Serializable {

	private final int USERS_EMAIL = 0, USERS_PASSWORD = 1, USERS_NAME = 2, USERS_PHONE_NUMBER = 3, SMS_NOTIFICATIONS = 4;
	Database database = new Database(); 
	/* Lists to contain all accounts at the bank */ 
	final static Logger logger = Logger.getLogger("GLOBAL");
	
	BankModel() {
		database.init();
	}
	
	/**
	 * Method returns active users who are not admins
	 * @return ArrayList<User>
	 */
	public List<User> getUsersWhoAreNotAdmins() {
		
		return database.getUsersWhoAreNotAdmins(); 
		//return activeUsers.stream().filter(user -> user.isAdmin() == false).collect(Collectors.<User>toList()); 

	}
	
	/**
	 * This method is called to check wheather a user already has a loan
	 * @param email
	 * @return boolean
	 */
	public boolean checkIfUserAlreadyHasLoan(String email) {
		
		return database.checkIfUserAlreadyHasLoan(email); 
		
	}
	
	
	/**
	 * This method checks if a users requested loan amount is 
	 * too large
	 * @param amount
	 * @return boolean
	 */
	public boolean checkIfLoanIsTooBig(int amount) {
		
		if (amount > 20000) {
			
			return true; 
			
		} else {
			
			return false; 
			
		}
		
	}
	
	
	/**
	 * Checks if the loan amount is too small,
	 * this method returns true if it is and false 
	 * if it is not
	 * @param amount
	 * @return boolean
	 */
	public boolean checkIfLoanIsToosmall(int amount) {
		
		if (amount < 3000) {
			
			return true; 
			
		} else {
			
			return false; 
			
		}
		
	}
	
	/**
	 * This method makes a payment on user loans. It takes
	 * the account email and the amount to be paid
	 * and returns a boolean if the transaction was successful
	 * @param email
	 * @param amount
	 * @return boolean
	 */
	public boolean makePaymentOnLoan(String email, float amount) {
		
		if (amount <= 0) {
			
			return false; 
			
		}
		
		return database.makePaymentOnLoan(email, amount); 
		
	}
	
	/**
	 * This method compares against a users credit score
	 * to see how big of a loan they can recieve 
	 * @param email
	 * @param amount
	 * @return boolean
	 */
	public boolean applyForLoan(String email, int amount) {
		
		
		return database.applyForLoan(email, amount); 


	}
	
	/**
	 * Checks if user is not an admin and then makes them one 
	 * @param email
	 * @return boolean
	 */
	public boolean promoteUserToAdmin(String email, SMSNotificationServer notificationServer) {
		
		return database.promoteUserToAdmin(email, notificationServer); 
	}

	/**
	 * This method is used to unblock previously blocked users, and if they are signed up for SMS notifications
	 * it sends them a text
	 * @param email
	 * @return boolean
	 */
	public boolean unblockUser(String email, SMSNotificationServer notificationServer) {
		
		return database.unblockUser(email, notificationServer); 

	}
	
	/**
	 * Simple utility method used to get a user 
	 * @param email
	 * @return User
	 */
	public User getUser(String email, boolean createAccount) {
		
		return database.getUser(email, createAccount); 
	}
	
	/**
	 * This method is used to withdraw funds for the user 
	 * @param amount
	 * @param email
	 * @return boolean
	 */
	public boolean withDrawFunds(float amount, String email) {
		
		
		return database.withDrawFunds(amount, email); 


	}
	
	/**
	 * This method is used to deposit funds into the users account
	 * @param amountToDeposit
	 * @param email
	 */
	public void depositFunds(float amount, String email) {
		
		database.depositFunds(amount, email);

	}
	
	
	/**
	 * Utility method to get active users  
	 * @return ArrayList<User>
	 */
	public List<User> getActiveUsers() {
		return database.getActiveUsers();
	}
	
	/**
	 * Method used to get pending accounts
	 * @return ArrayList<User>
	 */
	public List<User> getPendingAccounts() {
		return database.getPendingAccounts(); 
	}
	
	/**
	 * Method is used to recieve admins name 
	 * @param email
	 * @return String
	 */
	public String getAdminName(String email) {
		
		return database.getAdminName(email);

	}

	/**
	 * Remove users from the pending state and add them to the active state
	 * @param email
	 */
	public void approveAccount(String email, SMSNotificationServer notificationServer) {
		
		database.approveAccount(email, notificationServer);
		
	}
	
	/**
	 * Method to check if a users account can be approved
	 * @param email
	 * @return boolean
	 */
	public boolean canApproveUserAccount(String email) {
		
		return database.canApproveUserAccount(email); 

	}

	/**
	 * Method creates a new user and places them on the approval waiting list.
	 * @param userInformation
	 * @return
	 */
	public void placeUserOnPendingApprovalList(String[] userInformation) {
		
		database.placeUserOnPendingApprovalList(userInformation);

		
	}
	
	protected void finalize () {
		database.cleanUp();
	}
	
	/**
	 * Check to see if the customer is on the pending list 
	 * @param userInformation
	 * @return boolean
	 */
	public boolean isOnPendingList(String[] userInformation) {

		
		if(database.isOnPendingList(userInformation)) {
			return true; 
		}

		return false;
	}
	
	/**
	 * Checks if user is on active list 
	 * @param userInformation
	 * @return boolean
	 */
	public boolean isOnActiveList(String[] userInformation) {

		
		if(database.isOnActiveList(userInformation)) {
			return true; 
		}
		
		return false;
		
	}

	/**
	 * This method is used to validate user input and return user information if
	 * found
	 * @param userInformation
	 * @return Admin
	 */
	public Admin signInAdmin(String[] userInformation) {

		return database.signInAdmin(userInformation); 
	}

	/**
	 * Utility function to block a users account and send SMS message to user 
	 * @param email
	 * @return boolean
	 */
	public boolean blockUserAccount(String email, SMSNotificationServer notificationServer) {	
		
		return database.blockUserAccount(email, notificationServer);

	}


}
