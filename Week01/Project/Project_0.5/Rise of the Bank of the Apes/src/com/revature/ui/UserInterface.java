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
	
	public static int accountScreen(User user) {
		System.out.println("Welcome Back!");
		System.out.println("You Have " + user.getBalance() + " Bananas");
		System.out.println("1. Withdraw");
		System.out.println("2. Deposit");
		
		return 0;
	}
	
	/**
	 * 
	 * @param user
	 */
	public static void depositScreen(User user) {
		
	}
	public static void withdrawScreen(User user) {
		
	}
	
	public static int adminScreen() {
		return 0;
	}
}
