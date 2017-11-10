package com.revature.ui;

import com.revature.users.User;

public class Withdraw {
	
	public static void Screen(User user) {
		System.out.println("Withdrawing");
		while(true) {
			System.out.println("Please enter withdrawl amount: ");
			double amount = Integer.parseInt(UserInterface.readInput());
			double prevAmount = user.getBalance();
			double currAmount = prevAmount - amount;
			if(currAmount > 0) {
				user.setBalance(currAmount);
				System.out.println("$" + currAmount + " has been withdrawn.");
				break;
			}
			System.out.println("Invalid input. Please try again.");
		
		}
		return;
	}
}
