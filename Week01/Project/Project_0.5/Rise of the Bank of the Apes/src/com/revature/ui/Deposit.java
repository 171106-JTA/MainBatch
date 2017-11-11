package com.revature.ui;

import com.revature.users.User;

public class Deposit {
	
	/**
	 * Displays deposit screen for regular users. Provides checks for correct inputs.
	 * 
	 * @param user User object to set deposit amount into
	 */
	public static void Screen(User user) {
		System.out.println("Depositing");
		System.out.print("Please enter amount to be deposited: ");
		//TODO Fix the datatype
		double amount = UserInterface.readNumberInput();
		if(amount < 0) {
			System.out.println("Invalid input.");
			return;
		}
		double prevAmount = user.getBalance();
		double currAmount = prevAmount + amount;
		user.setBalance(currAmount);
	}
}
