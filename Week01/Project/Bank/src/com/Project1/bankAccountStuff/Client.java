package com.Project1.bankAccountStuff;

public class Client extends User{
	/**
	 * Determines status of cleint's account. 0 = approval pending, 1 = active account, 2 = locked
	 */
	private int status; 
	
	/**
	 * Holds total amount stored in user's account
	 */
	private double accountAmount;
	
	/**
	 * Constructor for creating Client object
	 * @param username		Contains username for client
	 * @param password		Contains password for client
	 */
	Client(String username, String password) {
		
	}
	
	/**
	 * Add given amount to the Client's account
	 * @param depositAmount			Amount to be deposited into Client's account
	 */
	public void deposit(double depositAmount) {
		
	}
	
	/**
	 * Subtract given amount from the Client's account
	 * @param withdrawAmount		Amount to be withdrawn from Clien'ts account
	 */
	public void withdraw(double withdrawAmount) {
		
	}
	
	
}
