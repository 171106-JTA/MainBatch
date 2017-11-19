package com.bankoftheapes.bank;

import java.util.HashMap;

import com.bankoftheapes.dao.QueryUtil;
import com.bankoftheapes.ui.Account;
import com.bankoftheapes.ui.Splash;
import com.bankoftheapes.ui.UserInterface;
import com.bankoftheapes.user.User;


public class BankOfTheApes {
	/* This class is meant to be a singleton 
	 * It is the central bank, so there should not be anymore than one instance of this class
	 */
	static int instanceCount = 0;
	private static BankOfTheApes bota;
	private QueryUtil qu;
	
	private BankOfTheApes() {
		qu = new QueryUtil();
		instanceCount++;
	}

	/**
	 * Allows the creation of a singleton BankOfTheApes object. 
	 *  
	 * @return BankOfTheApes object needed to perform banking operations
	 */
	public static BankOfTheApes getBank() {
		if(bota == null) {
			bota = new BankOfTheApes();
		}
		return bota;
	}
	
	/**
	 * Initializes the banking process for users
	 * A splash screen is displayed to greet users and provide login choices.
	 * 
	 */
	public void setUp() {
		int option = Splash.Screen();
		displayOperationScreen(option);
		
	}
	
	/**
	 * Takes user input and displays a different screen according to that input.
	 * 
	 * @param option - Integer input used to select the requested screen
	 * 
	 */
	public void displayOperationScreen(int option) {
		User u = null;
		
		switch(option) {
			case 1:
				Account.Screen(qu);
				break;
			case 2:
				UserInterface.loginScreen(qu);
				break;
			case 3:
				UserInterface.loginScreen(qu);
				break;
			default:
				System.out.println("Option not available. Please try again.");
		}
	}
}
