package com.revature.ui;

import java.text.DecimalFormat;
import java.util.HashMap;

import com.revature.users.User;

public class Account extends UserInterface{
	
	/**
	 * Displays account screen for regular users. Transaction options include: withdrawal and deposits
	 * 
	 * @param users HashMap<String, User> used to gather user information for transactions
	 */
	public static void Screen(HashMap<String, User> users) {
		User u = UserInterface.loginScreen(users);
		DecimalFormat df = new DecimalFormat("#0.00");
		
		if(u == null) {
			return;
		}
		
		System.out.println("Welcome Back!");
		int option = 0;
		while(option != 4) {
			System.out.println();
			System.out.println("You Have " + df.format(u.getBalance()) + " Bananas");
			System.out.println("1. Withdraw");
			System.out.println("2. Deposit");
			System.out.println("3. Loan");
			System.out.println("4. Log Out");
			System.out.print("Enter Option: ");
			option = UserInterface.readIntInput();
			
			
			if(option == 1) {
				Withdraw.Screen(u);
			}
			else if(option == 2) {
				Deposit.Screen(u);
			}
			else if(option == 3) {
				ApplyLoan.Screen(u);
			}
			else if(option > 4 || option < 1) {
				System.out.println("Invalid option. Please try again.");
			}
		}	
		System.out.println("Thank you. Have a nice day! Hail Ceasar!");
		UserInterface.cleanUp(users);
	}
}
