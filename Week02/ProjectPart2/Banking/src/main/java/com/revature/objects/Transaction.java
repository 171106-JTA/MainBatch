/**
 * Class Transaction
 * 
 *This class allows us to keep track of the movements on the accounts
 *It helps compute the balance.
 * 
 * @Instructor Ryan Lessley
 * @author Mahamadou
 * @Version 1.0
 * @Date 11/13/2017
 * 
 */
package com.revature.objects;

import java.util.Date;

import com.revature.generalmethods.GeneralFunctions;

public class Transaction {
	/*
	 * A transaction is made on 1 account at a time
	 * The customer, bank or another actor can append the account.
	 * (interest or fees charges by the bank and  deposits from an employer
	 * or a payment to a merchant...
	 * */
	
	/**
	 * 
	 */

	//Properties
	private int transactID = 1;
	private Date transactDate;
	private String details;
	private double amount;			//>0 for deposits and <0 for withdrawals
	private String accountNber;		
	protected static int nberOfTransactions;

	//Constructor
	public Transaction(int id, Date date, String details, double amount, String accountNber) {
		this.transactID = id;
		this.transactDate = date;
		this.details = details;
		this.amount = amount;
		this.accountNber = accountNber;
	}

	public Transaction(Date date, String details, double amount, String accountNber) {
		this.transactDate = date;
		this.details = details;
		this.amount = amount;
		this.accountNber = accountNber;
	}

	//Getters and Setters
	public int getId() {
		return transactID;
	}

	public Date getDate() {
		return transactDate;
	}

	public String getDetails() {
		return details;
	}

	public double getAmount() {
		return amount;
	}

	public String getAccountNber() {
		return accountNber;
	}

	@Override
	public String toString() {
		return transactID + " - " + new GeneralFunctions<Integer>().formatDate(transactDate, "MM/dd/yyyy") + " - " + details 
				+ " - " + amount + " - " + accountNber;
	}


}
