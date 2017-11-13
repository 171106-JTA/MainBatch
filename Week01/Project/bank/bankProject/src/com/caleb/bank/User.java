package com.caleb.bank;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;



/**
 * This Class represents a user that has an account at a bank. The User has a name, email, password an Account object and
 * a isAdmin boolean if they are promoted to an admin. 
 * @author calebschumake
 *
 */
class User implements java.io.Serializable {
	
	private Random rand = new Random();

	private String email, name, password, telephoneNumber;
	private int creditScore; 
	private float loanBalance; 
	Account account;
	private boolean isAdmin, isSignedUpForSMSNotifications, hasLoan; 
	
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
	
	/**
	 * returns the users phone number
	 * @return String
	 */
	public String getPhoneNumber() {
		return telephoneNumber; 
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
	 * this method returns the users loan balance
	 * @return float
	 */
	public float getLoanBalance() {
		return loanBalance; 
	}
	
	/**
	 * this method is called when the user wants to make 
	 * a payment on their loan
	 * @param amount
	 * @return boolean
	 */
	public boolean makePaymentOnLoan(float amount) {
		
		/* If they payment the user wants to make 
		 * sends their account into the negative
		 * deny them
		 */
		if (account.getBalance() < amount) {
			
			return false; 
			
		} else {
			
			account.withdraw(amount);
			loanBalance -= amount; 
			return true; 
			
		}
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
	 * this method sets the loanBalance instance variable
	 * @param amount
	 */
	public void setLoanBalance(float amount) {
		loanBalance = amount; 
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
				+ telephoneNumber + ", creditScore=" + creditScore + ", loanBalance=" + loanBalance + ", isAdmin="
				+ isAdmin + ", isSignedUpForSMSNotifications=" + isSignedUpForSMSNotifications + ", hasLoan=" + hasLoan
				+ "]";
	}

	/**
	 * Checks if a user is an admin
	 * @return boolean 
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

}
