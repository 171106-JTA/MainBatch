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
	 * Administrator only approval screen. 
	 * Admin finds the loanee through console input and approves/rejects their loan
	 * Shows the admin the loan amount first
	 * 
	 * @param users Used to quickly find the loanee for approval
	 */
	public static void approveLoan(HashMap<String, User> users) {
		String input = "";
		User u;
		System.out.print("Enter user name of loanee: ");
		input = UserInterface.readInput();
		u = users.get(input);
		
		if(u == null) {
			System.out.println("User not found.\n");
			return;
		}
		
		if(u.getLoan().getAmount() == 0) {
			System.out.println("User has no loans to approve.");
			return;
		}
		
		System.out.println("Loan of " + u.getLoan().getAmount());
		System.out.print("To approve, enter a; To reject, r --> ");
		input = UserInterface.readInput();
		switch(input) {
			//When approved, loan amount is added to account
			case "a":
				u.getLoan().setApproval(true);
				u.setBalance(u.getLoan().getAmount());
				UserInterface.startLogging("Loan of " + u.getLoan().getAmount()+ " for " + u.getName() + " has been approved");
				break;
			//When rejected, loan amount returns to 0
			case "r":
				UserInterface.startLogging("Loan of " + u.getLoan().getAmount()+ " for " + u.getName() + " has been rejected");
				u.setLoan(null);
				break;
			default:
				System.out.println("Incorrect Input.");
		}
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
				qu.updateApproval(u);
				//Log admin approval
				UserInterface.startLogging("Management has approved user " + u.getName());
				break;
			case "d":
				u.setApproved(0);
				qu.updateApproval(u);
				//Log admin disapproval
				UserInterface.startLogging("Management has disapproved user " + u.getName());
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
		
		System.out.print("Enter username: ");
		input = UserInterface.readInput();
		u = users.get(input);
		if(u == null) {
			System.out.println("User not found.");
			return;
		}
		
		//Prevent administrators from banning each other
		if(u.getAccess_level() == 2) {
			System.out.println("You cannot ban another administrator.");
			return;
		}
		
		System.out.print("To ban, enter b; To unban, enter u --> ");
		input = UserInterface.readInput();
		switch(input) {
			case "b":
				u.setBanned(true);
				//Log admin ban
				UserInterface.startLogging("Admin has banned user " + u.getName());
				break;
			case "u":
				u.setBanned(false);
				//Log admin unban
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
	public static void promoteUser(HashMap<String, User> users, int access_level) {
		System.out.println("Promote Screen");
		String input = "";
		int intInput = 0;
		User u;
		
		System.out.print("Enter username: ");
		input = UserInterface.readInput();
		u = users.get(input);
		
		if(u == null) {
			System.out.println("User not found.");
			return;
		}
		
		//Prevents administrators from pro/demoting each other
		if(u.getAccess_level() == 2) {
			System.out.println("You cannot change access level of another administrator.");
			return;
		}
		
		//Give administrators total freedom in promoting and demoting others
		if(access_level == 2) {
			System.out.print("Please enter promotion/demotion level: ");
			intInput = UserInterface.readIntInput();
			if(intInput < 0) {
				System.out.println("Invalid input. Please try again.");
				return;
			}
			
			if(intInput == 0) {
				u.setAccess_level(0);
				System.out.println(u.getName() + " will become a regular user.");
				//Log demotion
				UserInterface.startLogging("Admin has demoted user " + u.getName() + " to user.");
			}
			else if(intInput == 1) {
				u.setAccess_level(1);
				//Restore account to go health
				u.setApproved(true);
				u.setBanned(false);
				System.out.println(u.getName() + " will become a Moderator.");
				//Log promotion
				UserInterface.startLogging("Admin has promoted user " + u.getName() + " to moderator.");
			}
			else {
				u.setAccess_level(2);
				//Restore account to go health
				u.setApproved(true);
				u.setBanned(false);
				System.out.println(u.getName() + " has been promoted to administrator.");
				//Log promotion
				UserInterface.startLogging("Admin has promoted user " + u.getName() + " to administrator.");
			}
			return;
		}
		
		//Moderators can only promote and the max promotion is moderator
		u.setAccess_level(1);
		//Restore account to go health
		u.setApproved(true);
		u.setBanned(false);
		System.out.println(u.getName() + " will become a Moderator.");
		//Log promotion
		UserInterface.startLogging("Moderator has promoted user " + u.getName() + " to moderator.");
	}
	
}
