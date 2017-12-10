package com.revature.beans;

import java.io.Serializable;

import com.revature.dao.AccountDaoImpl;
import com.revature.dao.CustomerDaoImpl;

/**
 * @author Matthew
 * Class models Bank Account
 */
public class Account implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int acctNumber;
	private double balance;

	private AccountDaoImpl dao = new AccountDaoImpl();
	
	public Account(int acctNumber, double balance) {
		this.acctNumber = acctNumber;
		this.balance = balance;
	}
	
	public Account(double balance) {
		this.acctNumber = dao.getIndex() + 1000100;
		this.balance = balance;
	}

	/**
	 * Deposits amount in the account
	 * @param amt
	 * A double that represents the amount of money being deposited
	 */
	public String deposit(double amt){
		String result = "";
		if (amt < 0){
			result = "negAmt";
		} else{
			balance += amt;
		}
		return result;
	}
	
	/**
	 * Withdraws amount from the account.
	 * The calling method must ensure the the amount to be withdraw is a positive 
	 * 	number (>0), and that the amount is not greater than the current account
	 *  balance.	
	 * @param amt
	 * A double that represents the amount of money being withdrawn
	 */
	public String withdraw(double amt){
		String result = "";
		if (balance == 0){
			result = "acctBalanceZero";
		} else if (amt > balance){
			result = "amt2High";
		} else if (amt < 0){
			result = "negAmt";
		} else{
			balance -= amt;
		}
		return result;
	}
	
	/**
	 * getter for Account Number
	 * @return
	 * Account Number
	 */
	public int getAcctNumber() {
		return acctNumber;
	}

	/**
	 * setter for Account Number
	 * @param acctNumber
	 * int that will be set as the Account Number
	 */
	public void setAcctNumber(int acctNumber) {
		this.acctNumber = acctNumber;
	}

	/**
	 * getter for Account Balance
	 * @return
	 * double representing the amount of money in the Account in dollars
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * setter for Account Balance
	 * @param balance
	 * double that will be set as the Account Balance
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * Overwritten toString
	 */
	@Override
	public String toString() {
		return "Account [account number: " + acctNumber + 
				", balance: " + balance + "]";
	}

	/**
	 * Overwritten equals
	 */
	public boolean equals(Account acct) {
		boolean result = false;
		if (this.acctNumber == acct.acctNumber){
			result = true;
		}
		return result;
	}

}