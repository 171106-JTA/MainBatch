package com.revature.apebank;

import java.util.HashMap;

import com.revature.ui.UserInterface;
import com.revature.users.User;

public class BankOfTheApes {
	private HashMap<String, User> users;
	
	public void displayOperationScreen(int option) {
		
		switch(option) {
			case 1:
				UserInterface.oldUserScreen(users);
				break;
			case 2:
				UserInterface.adminScreen();
				break;
			default:
				System.out.println("Option not available. Please try again.");
		}
	}
	
	public static void main(String[] args) {
		int option = UserInterface.splashScreen();
		
		BankOfTheApes bota = new BankOfTheApes();
		
		bota.displayOperationScreen(option);
	}
	
}
