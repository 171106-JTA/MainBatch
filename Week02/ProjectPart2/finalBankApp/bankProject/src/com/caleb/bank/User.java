package com.caleb.bank;

import java.util.Hashtable;
import java.util.Random;
import com.rebuild.object.RebuildObjectTools;



/**
 * This Class represents a user that has an account at a bank. The User has a name, email, password an Account object and
 * a isAdmin boolean if they are promoted to an admin. 
 * @author calebschumake
 *
 */
class User implements java.io.Serializable {
	
	Hashtable<String, Boolean> hashtable = new Hashtable<String, Boolean>(); 
	
	private Random rand = new Random();

	private String email, name, password, telephoneNumber;
	private int creditScore; 
	Account account;
	private boolean isAdmin, isSignedUpForSMSNotifications, hasLoan, isBlocked; 
	
	/**
	 * User constructor that initializes the email, name, and password of the User
	 * @param email
	 * @param name
	 * @param password
	 */
	User(String email, String name, String password, String telephoneNumber) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.telephoneNumber = telephoneNumber; 
		this.creditScore = (rand.nextInt(850 - 400) + 1) + 400;
		account = new Account();
	}
	
	User(String email, String name, String password, String telephoneNumber, String isSignedUpForSMS) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.telephoneNumber = telephoneNumber; 
		this.creditScore = (rand.nextInt(850 - 400) + 1) + 400;
		this.isSignedUpForSMSNotifications = RebuildObjectTools.convertFromStringToBoolean(isSignedUpForSMS);
		account = new Account();
	}
	
	/* Constructor to rebuild an object from database */ 
	User(String email, String name, String password, String telephoneNumber, int creditScore, String isAdmin, String isSignedUpForSMS, String hasLoan, String isBlocked) { 
		this.email = email;
		this.name = name;
		this.password = password;
		this.telephoneNumber = telephoneNumber; 
		this.creditScore = creditScore; 
		this.isAdmin = RebuildObjectTools.convertFromStringToBoolean(isAdmin); 
		this.isSignedUpForSMSNotifications = RebuildObjectTools.convertFromStringToBoolean(isSignedUpForSMS);
		this.hasLoan = RebuildObjectTools.convertFromStringToBoolean(hasLoan);
		this.isBlocked = RebuildObjectTools.convertFromStringToBoolean(isBlocked);
		account = new Account();
	}
	
	/**
	 * returns the users phone number
	 * @return String
	 */
	public String getPhoneNumber() {
		return telephoneNumber; 
	}
	
	public void blockUser() {
		isBlocked = true; 
	}
	
	public boolean isBlocked() {
		return isBlocked; 
	}
	
	/**
	 * returns true if the user has a current loan
	 * and false if they do not
	 * @return boolean
	 */
	public boolean hasLoan() {
		return hasLoan; 
	}
	
	/**
	 * this method sets the hasLoan boolean 
	 * variable to true
	 */
	public void setHasLoan() {
		hasLoan = true; 
	}
	
	/**
	 * this method sets the hasLoan instance variable to false
	 */
	public void doesNotHaveLoan() {
		hasLoan = false; 
	}
	
	
	/**
	 * this method sets the users phone number
	 * @param phoneNumber
	 */
	public void setPhoneNumber (String phoneNumber) {
		telephoneNumber = phoneNumber; 
	}
	
	/**
	 * this method returns the credit score of the user
	 * @return int
	 */
	public int getCreditScore() {
		return creditScore; 
	}
	
	/**
	 * this method sets the isSignedUpForSMSNotifications 
	 * variable to true
	 */
	public void signUpForSMSNotifications() {
		isSignedUpForSMSNotifications = true; 
	}
	
	/**
	 * This method returns the instance varibale 
	 * isSignedUpForSMSNotifications 
	 * @return boolean
	 */
	public boolean isSignedUpForSMSNotifications() {
		return isSignedUpForSMSNotifications; 
	}

	/**
	 * Getter to get users email
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Getter to get users name
	 * @return String
	 */
	public String getName() {
		return name;
	}

	
	
	/**
	 * Getter to get users password
	 * @return String
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * promotes a user to an admin
	 */
	public void makeAdmin() {
		isAdmin = true;
	}
	
	

	@Override
	public String toString() {
		return "User [email=" + email + ", name=" + name + ", password=" + password + ", telephoneNumber="
				+ telephoneNumber + ", creditScore=" + creditScore + ", isAdmin=" + isAdmin
				+ ", isSignedUpForSMSNotifications=" + isSignedUpForSMSNotifications + ", hasLoan=" + hasLoan
				+ ", isBlocked=" + isBlocked + "]";
	}

	/**
	 * Checks if a user is an admin
	 * @return boolean 
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

}
