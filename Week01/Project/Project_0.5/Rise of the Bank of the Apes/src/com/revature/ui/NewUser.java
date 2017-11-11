package com.revature.ui;

import java.util.HashMap;

import com.revature.users.User;

public class NewUser extends UserInterface{
	
	/**
	 * Screen for new account creations. It will checks the username for duplicates 
	 * before creating the account, so no user will be overwritten.
	 * 
	 * @param users HashMap of users to check if username is taken
	 */
	public static void Screen(HashMap<String, User> users) {
		String user;
		String password;
		
		while(true) {
			System.out.print("Username: ");
			user = UserInterface.readInput();
			if(users.containsKey(user)) {
				System.out.println("Username taken. Please try again.");
			}
			else {
				break;
			}
		}
		System.out.print("Password: ");
		password = UserInterface.readInput();
		users.put(user, new User(user, password));
		
		System.out.println("Have a nice day!");
		UserInterface.cleanUp(users);
	}
}
