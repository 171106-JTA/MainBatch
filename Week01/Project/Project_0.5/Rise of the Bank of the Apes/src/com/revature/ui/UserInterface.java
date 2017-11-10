package com.revature.ui;

import java.util.HashMap;
import java.util.Scanner;

import com.revature.data.ProcessData;
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
	
	protected static int readNumberInput() {
		int i = -1;
		
		scan = new Scanner(System.in);
		
		try {
			i = Integer.parseInt(scan.nextLine());
		}catch (NumberFormatException ne){
			System.out.println("Input invalid.");
		}
		return i;
	}
	
	protected static void cleanUp(HashMap<String, User> users) {
		scan.close();
		ProcessData.serialize(users);
	}	
	
	public static User loginScreen(HashMap<String, User> users) {
		String user;
		User u;
		String password;
		String savedPassword;
		
		System.out.print("Username: ");
		user = UserInterface.readInput();
		if(users.containsKey(user)) {
			System.out.print("Password: ");
			password = UserInterface.readInput();
			u = users.get(user);
			savedPassword = u.getPassword();
			if(password.equals(savedPassword)) {
				if(!u.isBanned() && u.isApproved()) {
					return u;
				}
				else {
					System.out.println("Contact Administrators");
					return null;
				}
			}
		}
		System.out.println("Sorry. Incorrect Username or password.");
		return null;
	}
	
}
