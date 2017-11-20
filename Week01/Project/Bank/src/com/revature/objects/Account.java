/**
 * Class Account
 * 
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

public class Account implements Serializable{
	/*
	 * Class to handle the bank accounts
	 * */
	
	private static final long serialVersionUID = 5857116751215858827L;

	//Properties
	private String accountNber;
	private Date creationDate;
	private int accTypeID = 1;
	private boolean isActive = true;	//Turns to false when the account is closed or the loan paid off.
	private int customerID;
	private double balance = 0.0;		//Will eventually disappear since it can be computed.

	//Constructor
	public Account(String accountNber, Date creationDate, int accTypeID, boolean isActive, int customerID) {
		this.accountNber = accountNber;
		this.creationDate = creationDate;
		this.accTypeID = accTypeID;
		this.isActive = isActive;
		this.customerID = customerID;
	}

	//Getters and Setters
	public String getAccountNber() {
		return accountNber;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public int getAccTypeID() {
		return accTypeID;
	}
	public void setAccTypeID(int accTypeID) {
		this.accTypeID = accTypeID;
	}

	public boolean isActive() {
		return isActive;
	}
	public void activate() {
		this.isActive = true;
	}
	public void desactivate() {
		this.isActive = false;
	}

	public int getCustomerID() {
		return customerID;
	}
	
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

		
	@Override
	public String toString() {
		return "Account details \n---------------\nAccount #:\t" + accountNber + "\nBalance:\t" + balance +
				"\nCreation Date:\t" + new MyDisplays<>().formatDate(creationDate, "MM/dd/yyyy") + 
				"\nAccount TypeID:\t" + accTypeID  + "\nActive:\t\t" + isActive + "\ncustomerID:\t" + customerID;
	}
	
	
}
