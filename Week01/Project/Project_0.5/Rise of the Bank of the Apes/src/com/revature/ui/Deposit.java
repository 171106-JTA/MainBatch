package com.revature.ui;

import com.revature.users.User;

public class Deposit {
	
	public static void Screen(User user) {
		System.out.println("Depositing");
		System.out.println("Please enter amount to be deposited: ");
		//TODO Fix the datatype
		double amount = Integer.parseInt(UserInterface.readInput());
		double prevAmount = user.getBalance();
		double currAmount = prevAmount + amount;
		user.setBalance(currAmount);
		
		return;
	}
}
