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
				repayLoan(user);
				break;
			default:
				System.out.println("Invalid Input.");
		}
	}
	
	/**
	 * Shows the loan amount and status
	 * 
	 * @param u the user
	 */
	private static void showLoan(User u) {
		if(u.getLoan().getAmount() == 0) {
			System.out.println("No loans in system.");
		}
		else {
			System.out.println(u.getLoan());
		}
	}
	
	/**
	 * Takes the user input for loan and creates the request.
	 * Loan is then logged
	 * 
	 * @param u the user applying for a loan
	 */
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
	
	/**
	 * The method automatically deducts the loan amount for the user account when enough money is present.
	 * Otherwise the repayment is rejected
	 * 
	 * @param u The user, who will have their money deducted
	 */
	private static void repayLoan(User u) {
		double currAmount = u.getBalance();
		double loanAmount = u.getLoan().getAmount();
		double afterAmount = 0;
		if(loanAmount > currAmount) {
			System.out.println("Not enough money in Account. Cannot repay loan.");
			return;
		}
		afterAmount = currAmount - loanAmount;
		u.setBalance(afterAmount);
		//Resets the loan back to 0
		u.getLoan().setAmount(0);
	}
}
