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
		String password;
		String savedPassword;
		
		System.out.println("Username: ");
		user = UserInterface.readInput();
		if(users.containsKey(user)) {
			System.out.println("Password");
			password = UserInterface.readInput();
			savedPassword = users.get(user).getPassword();
			if(password.equals(savedPassword)) {
				UserInterface.accountScreen(users.get(user));
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
	
	public static int adminScreen() {
		return 0;
	}
}
