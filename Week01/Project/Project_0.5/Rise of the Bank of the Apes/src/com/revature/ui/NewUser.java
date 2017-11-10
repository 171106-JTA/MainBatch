package com.revature.ui;

import java.util.HashMap;

import com.revature.users.User;

public class NewUser {
	
	public static int Screen(HashMap<String, User> users) {
		String user;
		String password;
		while(true) {
			System.out.println("Username: ");
			user = UserInterface.readInput();
			if(users.containsKey(user)) {
				System.out.println("Username taken. Please try again.");
			}
			else {
				break;
			}
		}
		System.out.println("Password: ");
		password = UserInterface.readInput();
		users.put(user, new User(user, password));
		
		return 0;
	}
}
