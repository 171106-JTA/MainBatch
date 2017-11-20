package com.bankoftheapes.ui;

import java.text.DecimalFormat;

import com.bankoftheapes.dao.QueryUtil;
import com.bankoftheapes.user.BankAccount;
import com.bankoftheapes.user.User;

public class Withdraw {
	
	/**
	 * The screen for withdrawing funds. It prevents the user from withdrawing more than
	 * what is present in his/her account
	 * 
	 * @param ba - BankAccount object holding the amount of a customer
	 * @param username - username of the customer
	 * @param qu - object that contains the needed instance variables to communicate with the server
	 */
	public static void Screen(BankAccount ba, String username, QueryUtil qu) {
		
		System.out.println("Withdrawing");
		System.out.print("Please enter withdrawal amount: ");
		double amount = UserInterface.readDouble();
		DecimalFormat df = new DecimalFormat("#0.00");
		//Checks to see if the user entered a negative number or letter
		if(amount < 0) {
			System.out.println("Invalid input. Please try again.");
			return;
		}
		double prevAmount = ba.getAmount();
		double currAmount = prevAmount - amount;
		if(currAmount >= 0) {
			ba.setAmount(currAmount);
			System.out.println(df.format(amount) + " Banana(s) has been withdrawn.");
			//Logs withdrawal of user 
			UserInterface.startLogging(username + " has withdrawn " + amount + " Banana(s)");
			qu.updateAccountAmount(ba);
			return;
		}
		System.out.println("Invalid input. Please try again.");
		
		return;
	}
}
