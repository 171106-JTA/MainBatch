package com.revature.ui;

import java.util.HashMap;
import java.util.Scanner;

import com.revature.users.User;

public class UserInterface {
	public static Scanner scan;
	
	public UserInterface() {
		
	}
	
	private static String readInput() {
		String result;
		
		scan = new Scanner(System.in);
		result = scan.nextLine();
		
		return result;
	}
	
	private static void closeScanner() {
		scan.close();
	}
	public static int splashScreen() {
		
		System.out.println("Welcome to the Bank of the Apes!");
		System.out.println("Where Humans Fear to Tread!");
		System.out.println("Please Select one of the following:");
		System.out.println("1. Login for existing user");
		System.out.println("2. Create new user account");
		System.out.println("3. Administrator Login");
		
		System.out.print("Please Enter Number: ");
		
		return Integer.parseInt(UserInterface.readInput());
	}
	
	public static int newUserScreen(HashMap<String, User> users) {
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
	
	public static void oldUserScreen(HashMap<String, User> users) {
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
					UserInterface.accountScreen(users.get(user));
				}
				else {
					System.out.println("Unable to access account. Contact admin");
				}
			}
		}
	}
	
	public static void accountScreen(User user) {
		System.out.println("Welcome Back!");
		int option = 0;
		while(option != 3) {
			System.out.println("You Have " + user.getBalance() + " Bananas");
			System.out.println("1. Withdraw");
			System.out.println("2. Deposit");
			System.out.println("3. Log Out");
			System.out.println("Enter Option: ");
			option = Integer.parseInt(UserInterface.readInput());
			
			
			if(option == 1) {
				UserInterface.withdrawScreen(user);
			}
			else if(option == 2) {
				UserInterface.depositScreen(user);
			}
			else if(option > 3) {
				System.out.println("Invalid option. Please try again.");
			}
		}	
		System.out.println("Thank you. Have a nice day! Hail Ceasar!");
		UserInterface.closeScanner();
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
	
	public static int adminScreen(HashMap<String, User> users) {
		String user;
		User u = null;
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
				if(u.getAccess_level() != 2) {
					System.out.println("Sorry. Not Found. Wrong Menu.");
					return 0;
				}
			}
			
		}
		
		System.out.println("Welcome Admin " + u.getName());
		int option = 0;
		while(option != 5) {
			System.out.println("1. Show all users");
			System.out.println("2. Approve user");
			System.out.println("3. Ban user");
			System.out.println("4. Promote user");
			System.out.println("5. Log out");
			System.out.println("Enter option: ");
			option = Integer.parseInt(UserInterface.readInput());
			
			switch(option) {
				case 1:
					UserInterface.showAllUserScreen(users);
					break;
				case 2:
					UserInterface.approveUserScreen(users);
					break;
				case 3:
					UserInterface.banUserScreen(users);
					break;
				case 4:
					UserInterface.promoteUser();
					break;
				case 5:
					break;
				default:
					System.out.println("Invalid input. Please try again");
			}
		}
		return 0;
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
