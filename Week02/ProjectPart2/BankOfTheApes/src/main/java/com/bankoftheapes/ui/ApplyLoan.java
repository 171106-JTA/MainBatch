package com.bankoftheapes.ui;

import java.time.LocalDate;

import com.bankoftheapes.dao.QueryUtil;
import com.bankoftheapes.user.BankAccount;
import com.bankoftheapes.user.Loan;
import com.bankoftheapes.user.User;

public class ApplyLoan extends UserInterface {
	
	public static void Screen(User user, QueryUtil qu) {
		System.out.println("\nLoan Application");
		System.out.println("1. Show Loan Status");
		System.out.println("2. Apply Loan");
		System.out.print("Enter option: ");
		int input = UserInterface.readIntInput();
		
		switch(input) {
			case 1:
				qu.showLoans(user);
				break;
			case 2:
				applyLoan(user, qu);
				break;
			default:
				System.out.println("Invalid Input.");
		}
	}
	
	/**
	 * Takes the user input for loan and creates the request.
	 * Loan is then logged
	 * 
	 * @param u the user applying for a loan
	 */
	private static void applyLoan(User u, QueryUtil qu) {
		Loan loan = null;
		
		if(u.getLoan() != null) {
			System.out.println("Loan already in system");
			return;
		}
		
		System.out.print("Enter loan amount: ");
		double d = UserInterface.readDouble();
		loan = new Loan(d, LocalDate.now().toString());
		
		u.setLoan(loan);
		System.out.println("Loan Request of " + d + " has been received.");
		UserInterface.startLogging(u.getName() + " has applied for a loan of " + d);
		qu.applyLoan(u);
	}
	
}
