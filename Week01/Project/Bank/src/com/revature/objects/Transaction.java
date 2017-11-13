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

import java.io.Serializable;
import java.util.Date;

import com.revature.display.MyDisplays;

public class Transaction implements Serializable {
	/*
	 * A transaction is made on 1 account at a time
	 * The customer, bank or another actor can append the account.
	 * (interest or fees charges by the bank and  deposits from an employer
	 * or a payment to a merchant...
	 * */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1776015280585744590L;
	//Properties
	private int id = 1;
	private Date date;
	private String details;
	private double amount;			//>0 for deposits and <0 for withdrawals
	private String accountNber;		
	protected static int nberOfTransactions;

	//Constructor
	public Transaction(Date date, String details, double amount, String accountNber) {
		this.id = ++nberOfTransactions;
		this.date = date;
		this.details = details;
		this.amount = amount;
		this.accountNber = accountNber;
	}

	//Getters and Setters
	public int getId() {
		return id;
	}

	public Date getDate() {
		return date;
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

	@SuppressWarnings("rawtypes")
	@Override
	public String toString() {
		return id + " - " + new MyDisplays().formatDate(date, "MM/dd/yyyy") + " - " + details 
				+ " - " + amount + " - " + accountNber;
	}


}
