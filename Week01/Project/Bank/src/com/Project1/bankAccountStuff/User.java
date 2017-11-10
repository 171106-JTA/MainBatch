package com.Project1.bankAccountStuff;

import java.io.Serializable;

public class User { //implements Serializable
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
//	private int[] ssn = new int[9];
	private String ssn;
	
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
	//to do: Convert ssn to a 9 digit integer array
	//to do: Convert middleInitial to char
	User(String firstName, String lastName, String middleInitial, String ssn, String password) {
		this.firstName = firstName; 
		this.lastName = lastName;
		this.middleInitial = middleInitial;
		this.ssn = ssn; //Deep copy or shallow copy??? Check this!!!
		this.password = password;
		this.permissions = 0; //Initialize new accounts to Client level permissions
		this.status = 0; //Initialize new accounts to require approval
		this.accountAmount = 0; //Initialize new account amounts to 0
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
	 * Fetch the Social Security Number for the user
	 * @return Returns the Social Security Number for the user
	 */
	public String getSsn() {
		return ssn;
	}
	
	/**
	 * Set the Social Security Number for the user
	 * @param ssn 		A String containing the social security number for the user
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	@Override
	public String toString() {
//		return "User [ssn=" + Arrays.toString(ssn) + ", firstName=" + firstName + ", lastName=" + lastName
//				+ ", middleInitial=" + middleInitial + ", password=" + password + ", permissions=" + permissions
//				+ ", status=" + status + ", accountAmount=" + accountAmount + "]";
		//ssn is a string for now. Will eventually convert this to a 9-digit number
		return "User [ssn=" + ssn + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", middleInitial=" + middleInitial + ", password=" + password + ", permissions=" + permissions
				+ ", status=" + status + ", accountAmount=" + accountAmount + "]";
	}	 
}
