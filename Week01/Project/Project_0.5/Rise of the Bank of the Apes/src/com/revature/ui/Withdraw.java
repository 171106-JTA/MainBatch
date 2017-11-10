package com.revature.ui;

import com.revature.users.User;

public class Withdraw {
	
	public static void Screen(User user) {
		System.out.println("Withdrawing");
		while(true) {
			System.out.print("Please enter withdrawal amount: ");
			double amount = UserInterface.readNumberInput();
			if(amount < 0) {
				System.out.println("Invalid input.");
				continue;
			}
			double prevAmount = user.getBalance();
			double currAmount = prevAmount - amount;
			if(currAmount >= 0) {
				user.setBalance(currAmount);
				System.out.println("$" + currAmount + " has been withdrawn.");
				break;
			}
			System.out.println("Invalid input. Please try again.");
		
		}
		return;
	}
}
