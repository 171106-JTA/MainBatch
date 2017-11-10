package com.revature.ui;

import java.util.HashMap;

import com.revature.users.User;

public class AdminTool {
	
	public static void showAllUser(HashMap<String, User> users) {
		
		System.out.println("User : Approval Status : Ban Status : Access Level");
		for(String u: users.keySet()) {
			System.out.println(u + " : " + users.get(u).isApproved() + " : " + users.get(u).isBanned() + " : " + users.get(u).getAccess_level());
		}
	}
	
	public static void approveUser(HashMap<String, User> users) {
		System.out.println("Approval Screen");
		String input = "";
		User u;
		System.out.print("Enter user name to be approved: ");
		input = UserInterface.readInput();
		u = users.get(input);
		
		if(u == null) {
			System.out.println("User not found.");
		}
		
		if(u.getAccess_level() == 2) {
			System.out.println("You cannot approve another admin");
			return;
		}
		
		System.out.print("To approve, enter a; To disprove, enter d --->");
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
		
		System.out.print("Enter user name: ");
		input = UserInterface.readInput();
		u = users.get(input);
		if(u == null) {
			System.out.println("User not found.");
		}
		if(u.getAccess_level() == 2) {
			System.out.println("You cannot ban another admin");
			return;
		}
		
		System.out.print("To ban, enter b; To unban, enter u --->");
		input = UserInterface.readInput();
		switch(input) {
			case "b":
				u.setBanned(true);
				break;
			case "u":
				u.setBanned(false);
		}
	}
	
	public static void promoteUser(HashMap<String, User> users) {
		System.out.println("Promote Screen");
		String input = "";
		User u;
		
		System.out.print("Enter user name: ");
		input = UserInterface.readInput();
		u = users.get(input);
		
		if(u == null) {
			System.out.println("User not found.");
		}
		
		if(u.getAccess_level() == 2) {
			System.out.println("You cannot promote another admin");
			return;
		}
		
		u.setAccess_level(2);
		System.out.println(u.getName() + " has been promoted to admin");
	}
}
