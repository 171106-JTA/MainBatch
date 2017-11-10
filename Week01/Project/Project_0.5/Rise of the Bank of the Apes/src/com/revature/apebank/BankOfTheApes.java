package com.revature.apebank;

import java.util.HashMap;

import com.revature.data.ProcessData;
import com.revature.ui.UserInterface;
import com.revature.ui.newUser;
import com.revature.users.User;

public class BankOfTheApes {
	private HashMap<String, User> users;
	static int instanceCount = 0;
	private static BankOfTheApes bota;
	
	private BankOfTheApes() {
		this.users = ProcessData.unserialize();
		User admin = new User("a!joe", "d");
		admin.setAccess_level(2);
		admin.setApproved(true);
		users.put("a!joe", admin);
		instanceCount++;
	}
	
		
	public HashMap<String, User> getUsers() {
		return users;
	}

	public static BankOfTheApes getBank() {
		if(bota == null) {
			bota = new BankOfTheApes();
		}
		return bota;
	}
	
	public void displayOperationScreen(int option) {
		
		switch(option) {
			case 1:
				UserInterface.oldUserScreen(users);
				break;
			case 2:
				newUser.Screen(users);
				break;
			case 3:
				UserInterface.adminScreen(users);
				break;
			default:
				System.out.println("Option not available. Please try again.");
		}
	}
	
}
