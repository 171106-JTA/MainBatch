package com.revature.ui;

import java.util.HashMap;
import java.util.Scanner;

import com.revature.users.User;

public class UserInterface {
	
	public UserInterface() {
		
	}
	
	private static String readInput() {
		String result;
		
		Scanner scan = new Scanner(System.in);
		result = scan.nextLine();
		scan.close();
		
		return result;
	}
	public static int splashScreen() {
		
		System.out.println("Welcome to the Bank of the Apes!");
		System.out.println("Where Humans Fear to Tread!");
		System.out.println("Please Select one of the following:");
		System.out.println("1. Login for existing user");
		System.out.println("2. Create new user account");
		
		System.out.println("Please Enter Number: ");
		
		return Integer.parseInt(UserInterface.readInput());
	}
	
	public static int newUserScreen(HashMap<String, User> users) {
		
		
		
		return 0;
	}
	
	public static int oldUserScreen(HashMap<String, User> users) {
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
				return 0;
			}
			else {
				return -1;
			}
		}
		else {
			return -1;
		}
	}
	
	public static int adminScreen() {
		return 0;
	}
}
