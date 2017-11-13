package com.revature.ui;

import com.revature.users.User;

public class ApplyLoan extends UserInterface {
	
	public static void Screen(User user) {
		System.out.println("\nLoan Application");
		System.out.println("1. Show Loan Status");
		System.out.println("2. Apply Loan");
		System.out.println("3. Repay Loan");
		System.out.print("Enter option: ");
		int input = UserInterface.readIntInput();
		
		switch(input) {
			case 1:
				showLoan(user);
				break;
			case 2:
				applyLoan(user);
				break;
			case 3:
				break;
			default:
				System.out.println("Invalid Input.");
		}
	}
	
	private static void showLoan(User u) {
		if(u.getLoan().getAmount() == 0) {
			System.out.println("No loans in system.");
		}
		else {
			System.out.println(u.getLoan());
		}
	}
	
	private static void applyLoan(User u) {
		if(u.getLoan().getAmount() != 0) {
			System.out.println("Loan already in system");
			return;
		}
		System.out.print("Enter loan amount: ");
		double d = UserInterface.readDouble();
		u.getLoan().setAmount(d);
		System.out.println("Loan Request of " + d + " has been received.");
		UserInterface.startLogging(u.getName() + " has applied for a loan of " + d);
	}
}
