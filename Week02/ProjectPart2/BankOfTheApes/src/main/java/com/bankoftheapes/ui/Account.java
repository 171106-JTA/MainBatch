package com.bankoftheapes.ui;

import com.bankoftheapes.dao.QueryUtil;
import com.bankoftheapes.user.BankAccount;
import com.bankoftheapes.user.User;

public class Account extends UserInterface{
	
	/**
	 * Displays account screen for regular users. Transaction options include: withdrawal and deposits
	 * 
	 * @param users HashMap<String, User> used to gather user information for transactions
	 */
	public static void Screen(QueryUtil qu) {
		User u = UserInterface.loginScreen(qu);
		
		if(u == null) {
			return;
		}
		
		BankAccount ba = qu.getAccountInfo(u);
		
		System.out.println("Welcome Back!");
		int option = 0;
		while(option != 4) {
			System.out.println();
			System.out.println("You Have " + ba.getAmount() + " Bananas");
			System.out.println("1. Withdraw");
			System.out.println("2. Deposit");
			System.out.println("3. Loan");
			System.out.println("4. Log Out");
			System.out.print("Enter Option: ");
			option = UserInterface.readIntInput();
			
			
			if(option == 1) {
				Withdraw.Screen(ba, u.getName(), qu);
			}
			else if(option == 2) {
				Deposit.Screen(ba, u.getName(), qu);
			}
			else if(option == 3) {
				ApplyLoan.Screen(u);
			}
			else if(option > 4 || option < 1) {
				System.out.println("Invalid option. Please try again.");
			}
		}	
		System.out.println("Thank you. Have a nice day! Hail Ceasar!");
		UserInterface.cleanUp();
	}
}
