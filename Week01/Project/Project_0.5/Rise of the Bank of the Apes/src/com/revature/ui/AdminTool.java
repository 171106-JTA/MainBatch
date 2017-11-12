package com.revature.ui;

import java.util.HashMap;

import com.revature.users.User;

public class AdminTool {
	
	/**
	 * Displays information of users, such as usernames, approval status, ban status, and access level
	 * 
	 * @param users HashMap of users to loop through
	 */
	public static void showAllUser(HashMap<String, User> users) {
		
		System.out.println("User : Approval Status : Ban Status : Access Level");
		for(String u: users.keySet()) {
			System.out.println(u + " : " + users.get(u).isApproved() + " : " + users.get(u).isBanned() + " : " + users.get(u).getAccess_level());
		}
	}
	
	/**
	 * Menu used by administrators to approve or disprove a user
	 * 
	 * @param users HashMap of users for quick look-ups
	 */
	public static void approveUser(HashMap<String, User> users) {
		System.out.println("Approval Screen");
		String input = "";
		User u;
		System.out.print("Enter user name to be approved: ");
		input = UserInterface.readInput();
		u = users.get(input);
		
		if(u == null) {
			System.out.println("User not found.");
			return;
		}
		
		if(u.getAccess_level() == 2) {
			System.out.println("You cannot approve another admin");
			return;
		}
		
		System.out.print("To approve, enter a; To disprove, enter d --> ");
		input = UserInterface.readInput();
		switch(input) {
			case "a":
				u.setApproved(true);
				UserInterface.startLogging("Admin has approved user " + u.getName());
				break;
			case "d":
				u.setApproved(false);
				UserInterface.startLogging("Admin has disproved user " + u.getName());
				break;
			default:
				System.out.println("Incorrect input.");
		}
	}
	
	/**
	 * Menu used by administrators to ban or unban a user
	 * 
	 * @param users HashMap of users for quick look-ups
	 */
	public static void banUser(HashMap<String, User> users) {
		System.out.println("Ban Screen");
		String input = "";
		User u;
		
		System.out.print("Enter user name: ");
		input = UserInterface.readInput();
		u = users.get(input);
		if(u == null) {
			System.out.println("User not found.");
			return;
		}
		if(u.getAccess_level() == 2) {
			System.out.println("You cannot ban another admin");
			return;
		}
		
		System.out.print("To ban, enter b; To unban, enter u --> ");
		input = UserInterface.readInput();
		switch(input) {
			case "b":
				u.setBanned(true);
				UserInterface.startLogging("Admin has banned user " + u.getName());
				break;
			case "u":
				u.setBanned(false);
				UserInterface.startLogging("Admin has unbanned user " + u.getName());
				break;
			default:
				System.out.println("Incorrect Input.");
		}
	}
	
	/**
	 * Menu for administrator used to promote another user to administrator status
	 * 
	 * @param users HashMap of users for quick look-ups
	 */
	public static void promoteUser(HashMap<String, User> users) {
		System.out.println("Promote Screen");
		String input = "";
		User u;
		
		System.out.print("Enter user name: ");
		input = UserInterface.readInput();
		u = users.get(input);
		
		if(u == null) {
			System.out.println("User not found.");
			return;
		}
		
		if(u.getAccess_level() == 2) {
			System.out.println("You cannot promote another admin");
			return;
		}
		
		u.setAccess_level(2);
		System.out.println(u.getName() + " has been promoted to admin");
		UserInterface.startLogging("Admin has promoted user " + u.getName() + " to administrator");
	}
}
