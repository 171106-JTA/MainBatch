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

import java.util.Date;

import com.revature.generalmethods.GeneralFunctions;

//import com.revature.display.MyDisplays;

public class Bank_account{
	/*
	 * Class to handle the bank accounts
	 * */
	
	//Properties
	private String accountNber;
	private Date creationDate;
	private int accTypeID = 1;
	private int isActive = 1;	//Turns to false when the account is closed or the loan paid off.
	private int customerID;
	private double balance = 0.0;		//Will eventually disappear since it can be computed.

	//Constructor
	public Bank_account(String accountNber, Date creationDate, int accTypeID, int isActive, int customerID, double balance) {
		this.accountNber = accountNber;
		this.creationDate = creationDate;
		this.accTypeID = accTypeID;
		this.isActive = isActive;
		this.customerID = customerID;
		this.balance = balance;
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

	public int isActive() {
		return isActive;
	}
	public void activate() {
		this.isActive = 1;
	}
	public void desactivate() {
		this.isActive = 0;
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
				"\nCreation Date:\t" + new GeneralFunctions<Integer>().formatDate(creationDate, "MM/dd/yyyy") + 
				"\nAccount TypeID:\t" + accTypeID  + "\nActive:\t\t" + isActive + "\ncustomerID:\t" + customerID;
	}
	
	
}
