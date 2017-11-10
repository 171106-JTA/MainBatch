package com.revature.apebank;

import java.util.HashMap;

import com.revature.data.ProcessData;
import com.revature.ui.Account;
import com.revature.ui.Admin;
import com.revature.ui.NewUser;
import com.revature.users.User;

public class BankOfTheApes {
	private HashMap<String, User> users;
	static int instanceCount = 0;
	private static BankOfTheApes bota;
	
	private BankOfTheApes() {
		this.users = ProcessData.unserialize();
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

	public static BankOfTheApes getBank() {
		if(bota == null) {
			bota = new BankOfTheApes();
		}
		return bota;
	}
	
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
