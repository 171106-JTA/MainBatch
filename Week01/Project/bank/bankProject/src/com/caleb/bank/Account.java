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
public class Account implements java.io.Serializable {

	private float balance;
	private ArrayList<String> previousTransactions = new ArrayList<String>();
	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private Date date = new Date(System.currentTimeMillis());
	private boolean isBlocked;

	/**
	 * Used to deposit money into an account
	 * @param amount
	 */
	public void deposit(float amount) {
		balance += amount;
		previousTransactions.add("Deposit of " + amount + "$ on: " + dateFormat.format(date));
	}

	/**
	 * used to withdraw money from an account
	 * @param amount
	 */
	public void withdraw(float amount) {
		balance -= amount;
		previousTransactions.add("Withdrawl of " + amount + "$ on: " + dateFormat.format(date));

	}

	/** 
	 * Used to get the balance of the account
	 * @return float
	 */
	public float getBalance() {
		return balance;
	}

	/**
	 * Used to check if the account is blocked
	 * @return boolean
	 */
	public boolean isBlocked() {
		return isBlocked;
	}

	/**
	 * Blocks the account
	 */
	public void block() {
		isBlocked = true;
	}

	/**
	 * unblocks the account
	 */
	public void unBlock() {
		isBlocked = false;
	}

	/**
	 * gets all previous transactions
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getPreviousTransActions() {
		return previousTransactions;
	}

}
