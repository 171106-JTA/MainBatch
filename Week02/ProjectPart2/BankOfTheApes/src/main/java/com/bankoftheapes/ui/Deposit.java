package com.bankoftheapes.ui;

import com.bankoftheapes.user.User;

public class Deposit {
	
	/**
	 * Displays deposit screen for regular users. Provides checks for correct inputs.
	 * 
	 * @param user User object to set deposit amount into
	 */
	public static void Screen(User user) {
		System.out.println("Depositing");
		System.out.print("Please enter amount to be deposited: ");
		double amount = UserInterface.readDouble();
		//Prevent users from inputting letters or negative numbers instead
		if(amount < 0) {
			System.out.println("Invalid input. Please try again.");
			return;
		}
		double prevAmount = user.getBalance();
		double currAmount = prevAmount + amount;
		user.setBalance(currAmount);
		//Logs the deposit
		UserInterface.startLogging(user.getName() + " has deposited " + amount + " Banana(s)");
	}
}
