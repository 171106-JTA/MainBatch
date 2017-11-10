package com.revature.ui;

import java.util.HashMap;
import java.util.Scanner;

import com.revature.users.User;

public class UserInterface {
	private static Scanner scan;
	
	public UserInterface() {
		
	}
	//TODO Fix exception when inputting letter in parseInt
	protected static String readInput() {
		String result;
		
		scan = new Scanner(System.in);
		result = scan.nextLine();
		
		return result;
	}
	
	protected static void closeScanner() {
		scan.close();
	}	
	
	public static User loginScreen(HashMap<String, User> users) {
		String user;
		User u;
		String password;
		String savedPassword;
		
		System.out.println("Username: ");
		user = UserInterface.readInput();
		if(users.containsKey(user)) {
			System.out.println("Password");
			password = UserInterface.readInput();
			u = users.get(user);
			savedPassword = u.getPassword();
			if(password.equals(savedPassword)) {
				if(!u.isBanned() && u.isApproved()) {
					return u;
				}
				else {
					System.out.println("Contact admin");
					return null;
				}
			}
		}
		System.out.println("Sorry. Incorrect Username or password.");
		return null;
	}
	
	/**
	 * 
	 * @param user
	 */
	public static void depositScreen(User user) {
		System.out.println("Depositing");
		System.out.println("Please enter amount to be deposited: ");
		//TODO Fix the datatype
		double amount = Integer.parseInt(UserInterface.readInput());
		double prevAmount = user.getBalance();
		double currAmount = prevAmount + amount;
		user.setBalance(currAmount);
		
		return;
	}
	public static void withdrawScreen(User user) {
		System.out.println("Withdrawing");
		while(true) {
			System.out.println("Please enter withdrawl amount: ");
			double amount = Integer.parseInt(UserInterface.readInput());
			double prevAmount = user.getBalance();
			double currAmount = prevAmount - amount;
			if(currAmount > 0) {
				user.setBalance(currAmount);
				System.out.println("$" + currAmount + " has been withdrawn.");
				break;
			}
			System.out.println("Invalid input. Please try again.");
		
		}
		return;
	}
	
	public static void showAllUserScreen(HashMap<String, User> users) {
		
		System.out.println("User : Approval Status: Ban Status");
		for(String u: users.keySet()) {
			System.out.println(u + " : " + users.get(u).isApproved() + " : " + users.get(u).isBanned());
		}
	}
	
	public static void approveUserScreen(HashMap<String, User> users) {
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
	
	public static void banUserScreen(HashMap<String, User> users) {
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
