package com.emeraldbank.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.emeraldbank.accounts.Bank;
import com.emeraldbank.controller.Consts;
import com.emeraldbank.controller.Controller;

/**
 * User object class, containing data for user accounts 
 * for logging into this application, and for using it to
 * enact transactions with bank accounts.
 * @author sjgillet
 *
 */
public class User implements Serializable, Comparable<User>{
	
	private String firstName;
	private String lastName;
	private String userID;
	private String password;
	
	private boolean isActive;
	private boolean isAdmin;
	private int status; 
	
	private List<String> accts;
	private String portfolio;
	
	/**
	 * "Default" constructor, taking the name of the new user, 
	 * and the userID and password they will use to login to this application
	 * @param firstName - the first name of the new user
	 * @param lastName - the last name of the new user
	 * @param userID - the login ID for the new user
	 * @param password - the login password for the user
	 */
	public User(String firstName, String lastName, String userID, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userID = userID;
		this.password = password;
		this.isActive = false;
		this.isAdmin = false; 
		this.accts = new ArrayList<String>(); 
	}
	
	public User() {
		this("NULL", "NULL", "NULL", "NULL"); 
	}

	/**
	 * Getter method for user's first name
	 * @return - user's first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Sets the user's first name to firstName
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the user's lastName as a String
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the user's last name to lastName
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the user's login userID as a String
	 * @return
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * Sets the user's login user ID to userID
	 * @param userID
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * Gets the user's login password as a String
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the user's login password to password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Checks if the user's account is active for use
	 * @return - true if the user's account is active for use.
	 * false otherwise
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * Sets the activity status for the user. 
	 * @param status - the status to set for the user. 
	 * 1 to set to active and usable.
	 * 0 to set to unactivated and not usable.
	 * -1 to set to locked and not usable. 
	 */
	public void setStatus(int status) {
		this.status = status; 
		if(status < 1) 
			this.isActive = false;
		else this.isActive = true; 
	}
	
	/**
	 * Gets the activity status for the user
	 * @return - the status for the user. 
	 * 1:active and usable.
	 * 0: unactivated and not usable.
	 * -1: locked and not usable.  
	 */
	public int getStatus() {
		return status; 
	}

	public String getStatusString() {
		switch(status) {
		case 1: return "ACTIVE";
		case 0: return "INACTIVE"; 
		case -1: return "LOCKED"; 
		}
		return "NOT FOUND"; 
	}
	
	/**
	 * Checks if the user is an administrator role
	 * @return - true if the user is an administrator role. 
	 * false otherwise
	 */
	public boolean isAdmin() {
		return isAdmin;
	}
	
	/**
	 * Sets the role of the user. 
	 * @param admin - true to set the user to administrator
	 * role. False to set to user role
	 */
	public void setAdmin(boolean admin) {
		this.isAdmin = admin; 
	}
	
	/**
	 * Adds a given bank account to this user's list of 
	 * owned bank accounts
	 * @param acctNum - number for bank account to add to this 
	 * user 
	 */
	public void addAcct(String acctNum) {
		this.accts.add(acctNum); 
	}
	
	/**
	 * Removes a given bank account from the user, if the user is
	 * closing the bank account 
	 * @param acctNum - number for the bank account to remove 
	 * from this user's list of owned accounts
	 */
	public void removeAcct(String acctNum) {
		this.accts.remove(acctNum); 
	}
	
	/**
	 * Returns the list of owned accounts this user has, 
	 * as a list of account number strings
	 * @return - the list of account number strings
	 */
	public ArrayList<String> getAccts() {
		return (ArrayList<String>) accts; 
	}
	
	
	/**
	 * Overriden toString method, prints the basic information for the user, 
	 * including name and login info, with password redacted. 
	 * @return - the user info as a string
	 */
	public String toString() {
		return "User " + userID + ": \tName: " + firstName + " " + lastName +
				"\tUserID: " + userID + "\tPassword: " + password;
	}
	
	/**
	 * Overriden toString method, prints the basic information for the user, 
	 * including name and login info, with password displayed. 
	 * @return - the user info as a string
	 */
//	public String toStringAdmin() {
//		return "User " + userID + ": \tName: " + firstName + " " + lastName +
//				"\tUserID: " + userID + "\tPassword: " + password;
//	}

	@Override
	public int compareTo(User u) {
		return this.userID.compareTo(u.userID); 
	}
	
	
	
}
