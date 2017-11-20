package com.caleb.bank;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * The account class represents a users bank account. Instances of this class should ONLY 
 * be used within a User class. This class provides methods to withdraw, deposit, get previous transactions,
 * and determine if the account is blocked.
 * @author calebschumake
 *
 */
public class Account {

	private float balance;
	private float loanBalance; 

	/**
	 * Used to deposit money into an account
	 * @param amount
	 */
	public void deposit(float amount) {
		balance += amount;
		
	}
	
	public void setBalance(float amount) {
		balance = amount; 
	}

	/**
	 * used to withdraw money from an account
	 * @param amount
	 */
	public boolean withdraw(float amount) {
		if (amount <= balance) {
			balance -= amount;
			return true; 
		}
		return false; 
	

	}

	/** 
	 * Used to get the balance of the account
	 * @return float
	 */
	public float getBalance() {
		return balance;
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
		
		/* If the payment the user wants to make 
		 * sends their account into the negative
		 * deny them
		 */
		if (loanBalance < amount) {
			
			return false; 
			
		} else {
			
			balance -= amount; 
			loanBalance -= amount; 
			return true; 
			
		}
	}
	
	/**
	 * this method sets the loanBalance instance variable
	 * @param amount
	 */
	public void setLoanBalance(float amount) {
		loanBalance = amount; 
	}
}
