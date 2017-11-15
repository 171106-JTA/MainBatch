package com.caleb.bank;
import java.util.ArrayList;
import java.util.Hashtable;
import org.apache.log4j.Logger;

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
	/* lookup tables to speed up common operations */ 
	private Hashtable<String, User> lookupActiveUsers = new Hashtable<String, User>(); 
	private Hashtable<String, Admin> lookupAdminTable = new Hashtable<String, Admin>();
	
	/* Lists to contain all accounts at the bank */ 
	private ArrayList<User> activeUsers = new ArrayList<User>();
	private ArrayList<Admin> activeAdmins = new ArrayList<Admin>();
	private ArrayList<User> pendingAccounts = new ArrayList<User>();
	final static Logger logger = Logger.getLogger("GLOBAL");
	
	/**
	 * Method returns active users who are not admins
	 * @return ArrayList<User>
	 */
	public ArrayList<User> getUsersWhoAreNotAdmins() {

		ArrayList<User> usersWhoAreNotAdmins = new ArrayList<User>();
		/* Return a list of users who are not admins */
		for (User user : activeUsers) {

			if ((!user.isAdmin()))
				usersWhoAreNotAdmins.add(user);

		}

		return usersWhoAreNotAdmins;
	}
	
	/**
	 * This method is called to check wheather a user already has a loan
	 * @param email
	 * @return boolean
	 */
	public boolean checkIfUserAlreadyHasLoan(String email) {
		
		if(lookupActiveUsers.containsKey(email)) {
			
			if(lookupActiveUsers.get(email).hasLoan()) {
				
				return true; 
				
			}
			
		}
		return false; 
		
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
		
		if(lookupActiveUsers.containsKey(email)) {
			
			User user =  lookupActiveUsers.get(email); 
			
			if((user.account.getBalance() >= amount) && (amount <= user.getLoanBalance())){
				
				user.makePaymentOnLoan(amount); 
				
				if(user.getLoanBalance() == 0)
					user.doesNotHaveLoan();
				
				return true; 
				
			}
			
		}
		
		return false; 
		
	}
	
	/**
	 * This method compares against a users credit score
	 * to see how big of a loan they can recieve 
	 * @param email
	 * @param amount
	 * @return boolean
	 */
	public boolean applyForLoan(String email, int amount) {

		if (lookupActiveUsers.containsKey(email)) {

			User user = lookupActiveUsers.get(email);

			int creditScore = user.getCreditScore();

			if (creditScore <= 500) {

				return false;

			} else if ((creditScore > 400) && (creditScore <= 650) && (amount <= 3000)) {

				user.setHasLoan();
				user.setLoanBalance(amount);
				return true;

			} else if ((creditScore > 650) && (creditScore <= 750) && (amount <= 8000)) {

				user.setHasLoan();
				user.setLoanBalance(amount);
				return true;

			} else if ((creditScore > 750) && (amount <= 20000)) {

				user.setHasLoan();
				user.setLoanBalance(amount);
				return true;

			}

		}

		return false;

	}
	
	/**
	 * This method creates the first admin that can approve users
	 * @return Admin
	 */
	public Admin getDefaultAdmin() {
		for(Admin admin: activeAdmins) {
			if(admin.getName().equalsIgnoreCase("caleb"))
				return admin; 
		}
		return null; 
	}
	
	/**
	 * Checks if user is not an admin and then makes them one 
	 * @param email
	 * @return boolean
	 */
	public boolean promoteUserToAdmin(String email, SMSNotificationServer notificationServer) {

		if (lookupActiveUsers.containsKey(email)) {

			User user = lookupActiveUsers.get(email);
			/* if user is not an admin, then make them one */
			if (!user.isAdmin()) {

				user.makeAdmin();
				Admin newAdmin = new Admin(user.getName(), user.getEmail(), user.getPassword());
				activeAdmins.add(newAdmin);
				lookupAdminTable.put(newAdmin.getEmail(), newAdmin); 

				if (user.isSignedUpForSMSNotifications()) {

					notificationServer.setPhoneNumber(user.getPhoneNumber());
					String message = "Hi " + user.getName() + ", your account has been promoted to an admin.";
					notificationServer.setTextMessage(message);
					notificationServer.sendNotification();

				}

				return true;
				
			}
			
		}
		
		return false;
	}

	/**
	 * This method is used to unblock previously blocked users, and if they are signed up for SMS notifications
	 * it sends them a text
	 * @param email
	 * @return boolean
	 */
	public boolean unblockUser(String email, SMSNotificationServer notificationServer) {
		
		if (lookupActiveUsers.containsKey(email)) {

			User user = lookupActiveUsers.get(email);

			user.account.unBlock();
			/* send user notification that their account has been unblocked */
			if (user.isSignedUpForSMSNotifications()) {
				notificationServer.setPhoneNumber(user.getPhoneNumber());
				String message = " Hi " + user.getName() + ", your account has been unblocked.";
				notificationServer.setTextMessage(message);
				notificationServer.sendNotification();
			}
			
			return true;
		}

		return false;
	}
	
	/**
	 * Simple utility method used to get a user 
	 * @param email
	 * @return User
	 */
	public User getUser(String email) {
		
		if (lookupActiveUsers.containsKey(email)) {
			
			return lookupActiveUsers.get(email);
			
		}

		/*
		 * Compiler happy
		 */
		return null;
	}
	
	/**
	 * This method is used to withdraw funds for the user 
	 * @param amount
	 * @param email
	 * @return boolean
	 */
	public boolean withDrawFunds(float amount, String email) {

		/* If users request will result in negative balance then deny their request */
		if (lookupActiveUsers.containsKey(email)) {

			User user = lookupActiveUsers.get(email);

			if (!((user.account.getBalance() - amount) < 0)) {
				
				user.account.withdraw(amount);
				logger.trace(user.getName() + " has withdrawn " + amount + "$.");
				return true;
				
			} else {
				
				logger.trace(user.getName() + " has attempted to withdraw passed their limit.");
				return false;
				
			}

		}

		return false;

	}
	
	/**
	 * This method is used to deposit funds into the users account
	 * @param amountToDeposit
	 * @param email
	 */
	public void depositFunds(float amountToDeposit, String email) {

		if (lookupActiveUsers.containsKey(email)) {

			User user = lookupActiveUsers.get(email);

			user.account.deposit(amountToDeposit);

		}
	}
	
	/**
	 * This Method is used to add the default admin
	 */
	public void addDefaultAdmin() {
		Admin defaultAdmin = new Admin("caleb", "caleb", "caleb");
		activeAdmins.add(defaultAdmin);
	}
	
	/**
	 * Utility method to get active users  
	 * @return ArrayList<User>
	 */
	public ArrayList<User> getActiveUsers() {
		return activeUsers;
	}
	
	/**
	 * Method used to get pending accounts
	 * @return ArrayList<User>
	 */
	public ArrayList<User> getPendingAccounts() {
		return pendingAccounts;
	}
	
	/**
	 * Method is used to recieve admins name 
	 * @param email
	 * @return String
	 */
	public String getAdminName(String email) {

		if (lookupAdminTable.containsKey(email)) {

			return lookupAdminTable.get(email).getName(); 
			
		}

		return null;

	}

	/**
	 * Remove users from the pending state and add them to the active state
	 * @param email
	 */
	public void approveAccount(String email, SMSNotificationServer notificationServer) {
		
		int i = 0;
		for (User user : pendingAccounts) {
			
			if (user.getEmail().equalsIgnoreCase(email)) {
				
				if(user.isSignedUpForSMSNotifications()) {
					
					notificationServer.setPhoneNumber(user.getPhoneNumber());
					String message = "Hi " + user.getName() + ", your account has been approved."; 
					notificationServer.setTextMessage(message);
					notificationServer.sendNotification();
				}
				
				break;
			}
			
			i++;
		}
		User temp = pendingAccounts.remove(i); 
		activeUsers.add(temp);
		/* add user to look up table */ 
		lookupActiveUsers.put(temp.getEmail(), temp); 
	}
	
	/**
	 * Method to check if a users account can be approved
	 * @param email
	 * @return boolean
	 */
	public boolean canApproveUserAccount(String email) {
		/* if you find the user to approve, return true */

		if (lookupActiveUsers.containsKey(email)) {

				return true;
			
		}

		return false;
	}

	/**
	 * Method creates a new user and places them on the approval waiting list.
	 * @param userInformation
	 * @return
	 */
	public void placeUserOnPendingApprovalList(String[] userInformation) {
		
		User newUser = new User(userInformation[USERS_EMAIL], userInformation[USERS_NAME], userInformation[USERS_PASSWORD], userInformation[USERS_PHONE_NUMBER]);
		if (userInformation[SMS_NOTIFICATIONS].equalsIgnoreCase("yes")) {
			
			newUser.signUpForSMSNotifications(); 
			
		} 
		
		pendingAccounts.add(newUser);
	}
	
	/**
	 * Check to see if the customer is on the pending list 
	 * @param userInformation
	 * @return boolean
	 */
	public boolean isOnPendingList(String[] userInformation) {
		
		/* check if user is on waiting list already */
		for (User user : pendingAccounts) {

			if (user.getEmail().equals(userInformation[USERS_EMAIL])) {
				return true;
			}

		}

		return false;
	}
	
	/**
	 * Checks if user is on active list 
	 * @param userInformation
	 * @return boolean
	 */
	public boolean isOnActiveList(String[] userInformation) {

		/* check if user is on active list already */
		if(lookupActiveUsers.containsKey(userInformation[USERS_EMAIL])) {
			
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

		/* see if they have an account, if so, return an instance of them */
		if (lookupAdminTable.containsKey(userInformation[USERS_EMAIL])) {

			Admin admin = lookupAdminTable.get(userInformation[USERS_EMAIL]);

			if (admin.getEmail().equalsIgnoreCase(userInformation[USERS_EMAIL])
					&& admin.getPassword().equals(userInformation[USERS_PASSWORD]))
				
				return admin;

		}

		return null;
	}

	/**
	 * Utility function to block a users account and send SMS message to user 
	 * @param email
	 * @return boolean
	 */
	public boolean blockUserAccount(String email, SMSNotificationServer notificationServer) {
		
		if (lookupActiveUsers.containsKey(email)) {

			User user = lookupActiveUsers.get(email);
			user.account.block();
			/* send user notification */
			if (user.isSignedUpForSMSNotifications()) {
				notificationServer.setPhoneNumber(user.getPhoneNumber());
				String message = "Hi " + user.getName() + ", your account has been blocked.";
				notificationServer.setTextMessage(message);
				notificationServer.sendNotification();

			}
			
			return true;
			
		}
		
		return false;
	}

	/**
	 * This method is used to validate user input and return user information if
	 * found
	 * @param userInformation
	 * @return User 
	 */
	public User signInUser(String[] userInformation) {
		
		/* then check if the user is on the activeUsers list */ 
		
		if (lookupActiveUsers.containsKey(userInformation[USERS_EMAIL])) {

			User user = lookupActiveUsers.get(userInformation[USERS_EMAIL]);
			
			if (user.getEmail().equals(userInformation[USERS_EMAIL])
					&& user.getPassword().equals(userInformation[USERS_PASSWORD])) {

				return user;

			}
			
		}

		return null;
	}

}
