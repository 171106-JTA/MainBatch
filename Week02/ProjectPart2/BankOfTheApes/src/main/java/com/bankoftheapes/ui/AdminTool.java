package com.bankoftheapes.ui;

import java.util.HashMap;

import com.bankoftheapes.dao.QueryUtil;
import com.bankoftheapes.user.User;

public class AdminTool {
	
	/**
	 * Displays information of users, such as usernames, approval status, ban status, and access level
	 * 
	 * @param users HashMap of users to loop through
	 */
	public static void showAllUser(QueryUtil qu) {
		
		System.out.println("\nUser : Approval Status : Ban Status : Access Level");
		qu.showAllUsers();
	}
	
	/**
	 * Menu used by administrators to approve or disapprove a user
	 * 
	 * @param users HashMap of users for quick look-ups
	 */
	public static void approveUser(QueryUtil qu) {
		System.out.println("Approval Screen");
		String input = "";
		User u;
		System.out.print("Enter user name to be approved: ");
		input = UserInterface.readInput();
		u = qu.getUserInfo(input);
		
		if(u == null) {
			System.out.println("User not found.");
			return;
		}
		
		//Prevent administrators from approving each other
		if(u.getAccess_level().equals("ADM")) {
			System.out.println("You cannot approve an administrator.");
			return;
		}
		
		System.out.print("To approve, enter a; To disapprove, enter d --> ");
		input = UserInterface.readInput();
		switch(input) {
			case "a":
				u.setApproved(1);
				//Log admin approval
				UserInterface.startLogging("Management has approved user " + u.getName());
				break;
			case "d":
				u.setApproved(0);
				//Log admin disapproval
				UserInterface.startLogging("Management has disapproved user " + u.getName());
				break;
			default:
				System.out.println("Incorrect input.");
		}
		qu.updateApproval(u);
	}
	
	/**
	 * Menu used by administrators to ban or unban a user
	 * 
	 * @param users HashMap of users for quick look-ups
	 */
	public static void banUser(QueryUtil qu) {
		System.out.println("Ban Screen");
		String input = "";
		User u = null;
		
		System.out.print("Enter username: ");
		input = UserInterface.readInput();
		u = qu.getUserInfo(input);
		if(u == null) {
			System.out.println("User not found.");
			return;
		}
		
		//Prevent administrators from banning each other
		if(u.getAccess_level().equals("ADM")) {
			System.out.println("You cannot ban another administrator.");
			return;
		}
		
		System.out.print("To ban, enter b; To unban, enter u --> ");
		input = UserInterface.readInput();
		switch(input) {
			case "b":
				u.setBanned(1);
				//Log admin ban
				UserInterface.startLogging("Admin has banned user " + u.getName());
				break;
			case "u":
				u.setBanned(0);
				//Log admin unban
				UserInterface.startLogging("Admin has unbanned user " + u.getName());
				break;
			default:
				System.out.println("Incorrect Input.");
		}
		qu.updateApproval(u);
	}
	
	/**
	 * Menu for administrator used to promote another user to administrator status
	 * 
	 * @param users HashMap of users for quick look-ups
	 */
	public static void promoteUser(String access_level, QueryUtil qu) {
		System.out.println("Promote Screen");
		String input = "";
		int intInput = 0;
		User u;
		
		System.out.print("Enter username: ");
		input = UserInterface.readInput();
		u = qu.getUserInfo(input);
		
		if(u == null) {
			System.out.println("User not found.");
			return;
		}
		
		//Prevents administrators from pro/demoting each other
		if(u.getAccess_level().equals("ADM")) {
			System.out.println("You cannot change access level of another administrator.");
			return;
		}
		
		//Give administrators total freedom in promoting and demoting others
		if(access_level.equals("ADM")) {
			System.out.print("Please enter promotion/demotion level: ");
			intInput = UserInterface.readIntInput();
			if(intInput < 0) {
				System.out.println("Invalid input. Please try again.");
				return;
			}
			
			if(intInput == 0) {
				u.setAccess_level("REG");
				System.out.println(u.getName() + " will become a regular user.");
				//Log demotion
				UserInterface.startLogging("Admin has demoted user " + u.getName() + " to user.");
			}
			else if(intInput == 1) {
				u.setAccess_level("MOD");
				//Restore account to good health
				u.setApproved(1);
				u.setBanned(0);
				System.out.println(u.getName() + " will become a Moderator.");
				//Log promotion
				UserInterface.startLogging("Admin has promoted user " + u.getName() + " to moderator.");
			}
			else {
				u.setAccess_level("ADM");
				//Restore account to good health
				u.setApproved(1);
				u.setBanned(0);
				System.out.println(u.getName() + " has been promoted to administrator.");
				//Log promotion
				UserInterface.startLogging("Admin has promoted user " + u.getName() + " to administrator.");
			}
			qu.updateAccessStatus(u);
			return;
		}
		
		//Moderators can only promote and the max promotion is moderator
		u.setAccess_level("MOD");
		//Restore account to good health
		u.setApproved(1);
		u.setBanned(0);
		System.out.println(u.getName() + " will become a Moderator.");
		//Log promotion
		UserInterface.startLogging("Moderator has promoted user " + u.getName() + " to moderator.");
		qu.updateAccessStatus(u);
	}
	
}
