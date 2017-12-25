package com.revature.bank;

import java.io.Serializable;

/**
 * Object which holds the user's account information. Each user has a password and username and is marked on whether or not they
 * are an admin. The account also is marked as approved or not and as locked or unlocked.
 * @author Xavier Garibay
 *
 */
public class User implements Serializable {
	private boolean admin;
	private boolean approved;
	private boolean lock;
	private String username;
	private String password;
	private double balance;
	private double loan;
	
	/**
	 * Creates a new user with given username and password. The new account is not an admin, not locked and not approved
	 * The balance and loan of a new account is 0
	 * @param uName - username to be used
	 * @param pWord - password to be used
	 */
	public User(String uName, String pWord) {
		admin=false;
		approved=false;
		lock=false;
		balance=0;
		loan=0;
		username=uName;
		password=pWord;
	}

	/**
	 * Gets current loan amount
	 * @return current loan amount
	 */
	public double getLoan() {
		return loan;
	}

	/**
	 * Set accounts loan amount
	 * @param loan - amount loan is to be set to
	 */
	public void setLoan(double loan) {
		this.loan = loan;
	}

	/**
	 * Increase balance by input amount
	 * @param money - amount balance should increase
	 */
	public void deposit(int money)
	{
		balance+=money;		
	}
	
	/**
	 * Checks if account has enough funds to withdraw. If the account does then the balance is lowered by the input amount
	 * @param money - the amount the account will potentially be lowered by
	 */
	public void withdraw(int money)
	{
		if(money<=balance)
		{
			balance-=money;
		}
		else//if account does not have enough funds
		{
			System.out.println("Your account does not have enough funds \nPlease deposit more funds or withdraw a smaller amount");
		}
	}
	/**
	 * Checks if user is an admin
	 * @return whether or not user is an admin
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * Marks user as an admin
	 */
	public void makeAdmin() {
		admin = true;
	}

	/**
	 * Checks if the user is approved for an account
	 * @return whether or not the account is approved
	 */
	public boolean isApproved() {
		return approved;
	}

	/**
	 * Mark account as approved
	 */
	public void Approve() {
		approved = true;
	}
	
	/**
	 * Checks if account is locked
	 * @return whether or not the account is locked
	 */
	public boolean getLock() {
		return lock;
	}
	
	/**
	 * Switches state of lock and reports whether or not the account is locked
	 */
	public void switchLock() {
		if(lock==false)
		{
			lock = true;
			System.out.println(username + " 's account is now locked");
		}
		else
		{
			lock= false;
			System.out.println(username + " 's account is now unlocked");
		}
	}

	/**
	 * Gets the account's username
	 * @return account username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the account's password
	 * @return account password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the account's balance
	 * @return account balance
	 */
	public double getBalance() {
		return balance;
	}

}
