package com.emeraldbank.accounts;

import java.io.Serializable;

/**
 * Account object class. Contains methods to generate, instantiate, and manipulate
 * bank accounts.
 * @author sjgillet
 *
 */
public class Acct  implements Serializable, Comparable<Acct>{
	private static final long serialVersionUID = 193559574912571525L;

	private String acctNum; 
	private double balance;
	private String ownerID; 
	private int status; 

	private static final int ACCOUNT_NUMBER_OF_DIGITS = 7; 
	private static final int ACCOUNT_NUMBER_OF_DIGITS_VISIBLE = 4; 

	/**
	 * Constructor for Acct object, taking an owner login ID, and
	 * setting the rest of its fields to default values.
	 * acctNum is generated randomly via the generateAcctNum() method
	 * @param ownerID - login ID for the owner of this bank account
	 */
	public Acct(String ownerID) {
		this.acctNum = generateAcctNum();
		this.balance = 0.0; 
		this.ownerID = ownerID;
		this.status = 0;
	}

	/**
	 * "Default" constructor, setting all values to a default value
	 */
	public Acct()
	{
		this.acctNum = "ACCTNUM";
		this.balance = 0.0; 
		this.ownerID = "OWNER";
		this.status = 0;
	}

	/**
	 * Gets the account number for this bank account
	 * @return - account number
	 */
	public String getAcctNum() {
		return acctNum;
	}
	/**
	 * Sets the account number for this bank account
	 * @param acctNum - account number to set
	 */
	public void setAcctNum(String acctNum) {
		this.acctNum = acctNum;
	}

	/**
	 * Gets the current balance for this account
	 * @return - current balance
	 */
	public double getBalance() {
		return balance;
	}
	/**
	 * Sets the balance for the bank account to the given value
	 * @param balance - the new balance of the account to set to 
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * Gets the login ID of the owner of the account
	 * @return - login ID of the account's owner 
	 */
	public String getOwnerID() {
		return ownerID;
	}
	/**
	 * Sets the owner ID for the account to the given string
	 * @param ownerID - login ID of new owner ofthe account 
	 */
	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}

	/**
	 * Gets the activity status of the bank account
	 * @return - activity status for the account
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
	 * Sets the activity status of the bank account 
	 * @param status - Activity status to set for the account 
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Locks the bank account, setting its activity status to -1. 
	 */
	public void lock() {
		this.status = -1; 
	}

	/**
	 * Activates the bank account, setting its activity status to 1
	 */
	public void activate() {
		status = 1; 
	}

	/**
	 * Adjusts the balance of the bank account by depositing to it
	 * @param amt - The amount to deposit to the bank account's balance
	 */
	public void depositFunds(double amt) {
		this.balance += amt; 
	}

	/**
	 * Adjusts the balance of the bank account by withdrawing from it
	 * @param amt - The amount to withdraw from the bank account's balance
	 */
	public void withdrawFunds(double amt) {
		this.balance -= amt; 
	}

	/**
	 * Generates a random string of 7 digits to assign as the account's number to 
	 * identify it, assigns it to the account's number, and returns it.
	 * @return - The string of digits generated as the account's number
	 */
	private String generateAcctNum() {
		//generates a random string of 7 digits
		String acctNum = ""; 
		for(int i = 0; i < ACCOUNT_NUMBER_OF_DIGITS; i++) 
			acctNum += (char)(Math.random()*10 + 48); 

		return acctNum; 
	}

	/** 
	 * toString method to return a String representation for the bank account object.
	 * @return - a string representation of the acct object
	 */
	public String toString() {
		String s = "Account #";
		int redactedDigits = ACCOUNT_NUMBER_OF_DIGITS - ACCOUNT_NUMBER_OF_DIGITS_VISIBLE;
		for(int i = 0; i < redactedDigits; i++)
			s += "*"; 
		if(redactedDigits < ACCOUNT_NUMBER_OF_DIGITS)
			s += this.acctNum.substring(redactedDigits + 1);		

		s += "\t Owner UserID: " + ownerID + "\t Balance: " + balance + "\tStatus: " +
				(status == 1 ? "Active" : (status == 0 ? "Not yet activated" : (status == -1 ? "Locked" : "ERROR")));
		return s; 				

	}

	@Override
	public int compareTo(Acct a) {
		return this.acctNum.compareTo(a.acctNum);
	}
	
	



}
