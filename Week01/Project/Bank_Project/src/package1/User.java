package package1;

import java.io.*;
import org.apache.log4j.Logger;

//User implements Serializable to allow User object to be serialized
public class User implements Serializable {

	// User data fields
	private double balance = 0;
	private boolean isLocked = false;
	private boolean isAdmin = false;
	private boolean isApproved = false;
	private String username;
	private String password;

	// TODO: is approved

	final static Logger logger = Logger.getLogger(User.class); // Create a logger

	// Defined constructor for creating a User object
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	// Getter for balance
	public double getBalance() {
		return balance;
	}

	// Setter for balance
	public void setBalance(double balance) {
		this.balance = balance;
	}

	// Getter for isAdmin
	public boolean isAdmin() {
		return isAdmin;
	}

	// Setter for isAdmin
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	// Getter for username
	public String getUsername() {
		return username;
	}

	// Setter for username
	public void setUsername(String username) {
		this.username = username;
	}

	// Getter for isLocked
	public boolean isLocked() {
		return isLocked;
	}

	// Setter for isLocked
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	// Getter for password
	public String getPassword() {
		return password;
	}

	// Setter for password
	public void setPassword(String password) {
		this.password = password;
	}

	// Getter for isApproved
	public boolean isApproved() {
		return isApproved;
	}

	// Setter for isApproved
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	//Method to approve a User
	public void approveUser(User user) {
		// Execute if calling User object is not an admin
		if (!this.isAdmin()) {
			System.out.println("Admin status required for this action!");
			logger.info("Admin status required for this action!");
			return;
		}
		// Execute if passed in User object is not an admin
		if (!user.isAdmin()) {
			user.setApproved(true);
			System.out.println("User has been approved!");
			logger.info("User has been approved!");
			return;
		}
		// Execute commands below if cases above are not true
		System.out.println("User is already approved!");
		logger.info("User is already approved!");
	}

	// Method to promote a User to an admin if conditions are met
	public void promoteUser(User user) {
		// Execute if calling User object is not an admin
		if (!this.isAdmin()) {
			System.out.println("Admin status required for this action!");
			logger.info("Admin status required for this action!");
			return;
		}
		// Execute if passed in User object is not an admin
		if (!user.isAdmin()) {
			user.setAdmin(true);
			System.out.println("User promoted to an admin!");
			logger.info("User promoted to an admin!");
			return;
		}
		// Execute commands below if cases above are not true
		System.out.println("User is already an admin!");
		logger.info("User is already an admin!");
	}

	// Method to promote a User to an admin if conditions are met
	public void lockUser(User user) {
		// Execute if calling object is not an admin
		if (!this.isAdmin()) {
			System.out.println("Admin status required for this action!");
			logger.info("Admin status required for this action!");
			return;
		}
		// Execute if passed in user is not locked (if isLocked is equal to false)
		if (!user.isLocked()) {
			user.setLocked(true);
			System.out.println("User has been locked!");
			logger.info("User has been locked!");
			return;
		}
		// Execute commands below if cases above are not true
		System.out.println("User is already locked!");
		logger.info("User is already locked!");
	}

	// Method to unlock a User if conditions are met
	public void unlockUser(User user) {
		// Execute if calling User object is not an admin
		if (!this.isAdmin()) {
			System.out.println("Admin status required for this action!");
			logger.info("Admin status required for this action!");
			return;
		}
		// Execute if passed in User object is not already locked
		if (user.isLocked()) {
			user.setLocked(false);
			System.out.println("User has been unlocked!");
			logger.info("User has been unlocked!");
			return;
		}
		// Execute if above conditional conditions do not apply
		System.out.println("User is not locked!");
		logger.info("User is not locked!");
	}

	// Deposit method to pass money into the account balance.
	// Updates balance variable and returns a String.
	public String deposit(double depositAmount) {
		// Execute if passed in argument is less than 0
		if (depositAmount <= 0) {
			logger.info("Deposit failed. Deposit amount must be greater than 0!");
			return "Deposit failed. Deposit amount must be greater than 0!";
		}
		//Execute this method if user is not approved
		if (!this.isApproved()) {
			logger.info("User is not yet approved!");
			return "User is not yet approved!";
		}
		//Execute this method if user is not approved
		if (this.isLocked()) {
			logger.info("User is locked!");
			return "User is locked!";
		}
		// Execute if passed in argument value is valid
		this.setBalance(this.getBalance() + depositAmount);
		logger.info("Deposit completed. New balance = $" + this.getBalance());
		return "Deposit completed. New balance = $" + this.getBalance();
	}

	// Withdraw method to take money out of the account balance.
	// Updates balance variable and returns a String.
	public String withdraw(double withdrawAmount) {
		// Execute if passed in argument is less than 0
		if (withdrawAmount <= 0) {
			logger.info("Withdraw failed. Withdraw amount must be greater than 0!");
			return "Withdraw failed. Withdraw amount must be greater than 0!";
		}
		//Execute this method if user is not approved
		if (!this.isApproved()) {
			logger.info("User is not yet approved!");
			return "User is not yet approved!";
		}
		//Execute this method if user is not approved
		if (this.isLocked()) {
			logger.info("User is locked!");
			return "User is locked!";
		}
		// Execute if passed in argument is greater than available balance
		if (this.getBalance() - withdrawAmount < 0) {
			logger.info("Withdraw failed. Specified withdraw amount is greater than current balance!");
			return "Withdraw failed. Specified withdraw amount is greater than current balance!";
		}
		// Execute if above conditional statements do not apply. Update balance.
		this.setBalance(this.getBalance() - withdrawAmount);
		logger.info("Withdraw completed. New balance = $" + this.getBalance());
		return "Withdraw completed. New balance = $" + this.getBalance();
	}

	// Overridden toString method to display a User object in a descriptive format
	@Override
	public String toString() {
		return "User [username=" + username + ", Admin?=" + isAdmin + ", Locked?=" + isLocked + ", Approved?=" + isApproved + "\n]";
	}

}
