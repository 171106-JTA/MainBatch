package com.revature.ui;

import com.revature.users.User;

public class Withdraw {
	
	/**
	 * The screen for withdrawing funds. It prevents the user from withdrawing more than
	 * what is present in his/her account
	 * 
	 * @param user - User object in order to deduct withdrawal amount from
	 */
	public static void Screen(User user) {
		System.out.println("Withdrawing");
			System.out.print("Please enter withdrawal amount: ");
			double amount = UserInterface.readNumberInput();
			//Checks to see if the user entered a negative number or letter
			if(amount < 0) {
				System.out.println("Invalid input. Please try again.");
				return;
			}
			double prevAmount = user.getBalance();
			double currAmount = prevAmount - amount;
			if(currAmount >= 0) {
				user.setBalance(currAmount);
				System.out.println(currAmount + " Banana(s) has been withdrawn.");
				//Logs withdrawal of user 
				UserInterface.startLogging(user.getName() + " has withdrawn " + amount + " Banana(s)");
				return;
			}
			System.out.println("Invalid input. Please try again.");
		
		return;
	}
}
