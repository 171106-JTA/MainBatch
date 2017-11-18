package com.revature.BankAccount;

import java.io.Serializable;

public class User implements Serializable { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//////////////////////////////////////////////////////////////
	//Member Variables
	//////////////////////////////////////////////////////////////
	
	/**
	 * SSN for client. This is the unique identifier
	 */
	private String username;
	
	/**
	 * First name of client
	 */
	private String firstName; 
	
	/**
	 * Last name of client
	 */
	private String lastName; 
	
	/**
	 * Middle initial of client
	 */
//	private char middleInitial;
	private String middleInitial;
	
	/**
	 * Password of client
	 */
	private String password;
	
	/**
	 * Holds level of permissions for user. 0 = Client, 1 = Admin
	 */
	private int permissions;
	
	/**
	 * Determines status of cleint's account. 0 = approval pending, 1 = active account, 2 = locked
	 */
	private int status; 
	
	/**
	 * Holds total amount stored in user's account
	 */
	private double accountAmount;
	
	//////////////////////////////////////////////////////////////
	//Constructors
	//////////////////////////////////////////////////////////////
	/**
	 * Initializes a new account, accepting the username and password for the new account
	 * @param name 		username for new account
	 * @param password 	password for new account
	 */
	public User(String firstName, String lastName, String middleInitial, String username, String password) {
		this.firstName = firstName; 
		this.lastName = lastName;
		this.middleInitial = middleInitial;
		this.username = username; //Deep copy or shallow copy??? Check this!!!
		this.password = password;
		this.permissions = 0; //Initialize new accounts to Client level permissions
		this.status = 0; //Initialize new accounts to require approval
		this.accountAmount = 0; //Initialize new account amounts to 0
	}
	
	public User(String firstName, String lastName, String middleInitial, String username, String password, 
			int permissions, int status, double accountAmount) {
		this.firstName = firstName; 
		this.lastName = lastName;
		this.middleInitial = middleInitial;
		this.username = username; //Deep copy or shallow copy??? Check this!!!
		this.password = password;
		this.permissions = permissions; //Initialize new accounts to Client level permissions
		this.status = status; //Initialize new accounts to require approval
		this.accountAmount = accountAmount; //Initialize new account amounts to 0
	}
	
	public User(User user) {
		this.firstName = user.getFirstName(); 
		this.lastName = user.getLastName();
		this.middleInitial = user.getMiddleInitial();
		this.username = user.getUsername(); //Deep copy or shallow copy??? Check this!!!
		this.password = user.getPassword();
		this.permissions = user.getPermissions(); //Initialize new accounts to Client level permissions
		this.status = user.getStatus(); //Initialize new accounts to require approval
		this.accountAmount = user.getAccountAmount(); //Initialize new account amounts to 0
	}

	//////////////////////////////////////////////////////////////
	//Getters and Setters
	//////////////////////////////////////////////////////////////
	/**
	 * Fetch the first name of the user
	 * @return Returns the first name of the user
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Set the first name of the user
	 * @param firstName 	Contains the new first name of the user
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Fetch the last name of the user
	 * @return Returns the last name of the user
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Set the last name of the user
	 * @param name 	Contains the new last name of the user
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Fetch the middle initial of the user
	 * @return Returns the middle initial of the user
	 */
	public String getMiddleInitial() {
		return middleInitial;
	}
	
	/**
	 * Set the middle initial of the user
	 * @param middleInitial		A char containing the middle initial of the user
	 */
	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}
	
	/**
	 * Fetch the status of the user. 
	 * @return		Returns the status of the user
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * Set the status of the user
	 * @param status		An int containing the new status of the user
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * Fetch the current amount in the account
	 * @return		Returns an integer containing the current amount in the account
	 */
	public double getAccountAmount() {
		return accountAmount;
	}
	
	/**
	 * Change the amount in the account
	 * @param accountAmount		A double containing the new amount for the account
	 */
	public void setAccountAmount(double accountAmount) {
		this.accountAmount = accountAmount;
	}
	
	/**
	 * Fetch the username for the user
	 * @return Returns the Social Security Number for the user
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Set the username for the user
	 * @param username 		A String containing the social security number for the user
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Get the user's password
	 * @return Returns a user's password
	 */
	public String getPassword( ) {
		return this.password;
	}
	
	/**
	 * Change the user's password
	 * @param password		A String containing the user's password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Fetch the permissions for the user
	 * @return		Returns an integer containing the permissions for the user
	 */
	public int getPermissions() {
		return permissions;
	}
	
	/**
	 * Set the permissions for the user
	 * @param permissions		An int containing the permissions for the user
	 */
	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", middleInitial=" + middleInitial + ", password=" + password + ", permissions=" + permissions
				+ ", status=" + status + ", accountAmount=" + accountAmount + "]";
	}	 
}

