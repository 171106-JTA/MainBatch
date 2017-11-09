package com.revature.apebank;

import java.util.HashMap;
import java.util.Scanner;

import com.revature.data.ProcessData;
import com.revature.ui.UserInterface;
import com.revature.users.User;

public class BankOfTheApes {
	private HashMap<String, User> users;
	static int instanceCount = 0;
	private static BankOfTheApes bota;
	
	private BankOfTheApes() {
		this.users = ProcessData.unserialize();
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
				UserInterface.newUserScreen(users);
				break;
			default:
				System.out.println("Option not available. Please try again.");
		}
	}
	
}
