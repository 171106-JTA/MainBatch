package com.revature.apebank;

import java.util.HashMap;

import com.revature.data.ProcessData;
import com.revature.ui.Account;
import com.revature.ui.Admin;
import com.revature.ui.NewUser;
import com.revature.ui.Splash;
import com.revature.users.User;

public class BankOfTheApes {
	/* This class is mean to be a singleton 
	 * It is the central bank, so there should not be anymore than one instance of this class
	 */
	private HashMap<String, User> users;
	static int instanceCount = 0;
	private static BankOfTheApes bota;
	
	private BankOfTheApes() {
		this.users = ProcessData.unserialize();
		/*
		 * Creation of a default admininstrator
		 */
		if(users.get("a!joe") == null) {
			User admin = new User("a!joe", "d");
			admin.setAccess_level(2);
			admin.setApproved(true);
			users.put("a!joe", admin);
		}
		instanceCount++;
	}
	
	public HashMap<String, User> getUsers() {
		return users;
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
		
		switch(option) {
			case 1:
				Account.Screen(users);
				break;
			case 2:
				NewUser.Screen(users);
				break;
			case 3:
				Admin.Screen(users);
				break;
			default:
				System.out.println("Option not available. Please try again.");
		}
	}
	
}
