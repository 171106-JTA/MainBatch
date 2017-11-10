package com.revature.ui;

import java.util.HashMap;

import com.revature.users.User;

public class AdminTool {
	
	public static void showAllUser(HashMap<String, User> users) {
		
		System.out.println("User : Approval Status: Ban Status");
		for(String u: users.keySet()) {
			System.out.println(u + " : " + users.get(u).isApproved() + " : " + users.get(u).isBanned());
		}
	}
	
	public static void approveUser(HashMap<String, User> users) {
		System.out.println("Approval Screen");
		String input = "";
		User u;
		System.out.println("Enter user name to be approved: ");
		input = UserInterface.readInput();
		u = users.get(input);
		
		if(u == null) {
			System.out.println("User not found.");
		}
		System.out.println("To approve, enter a; To disprove, enter d");
		input = UserInterface.readInput();
		switch(input) {
			case "a":
				u.setApproved(true);
				break;
			case "d":
				u.setApproved(false);
		}
	}
	
	public static void banUser(HashMap<String, User> users) {
		System.out.println("Ban Screen");
		String input = "";
		User u;
		
		System.out.println("Enter user name: ");
		input = UserInterface.readInput();
		u = users.get(input);
		if(u == null) {
			System.out.println("User not found.");
		}
		System.out.println("To ban, enter b; To unban, enter u");
		input = UserInterface.readInput();
		switch(input) {
			case "b":
				u.setBanned(true);
				break;
			case "u":
				u.setBanned(false);
		}
	}
	
	public static void promoteUser() {
		
	}
}
