package com.revature.ui;

import java.util.HashMap;

import com.revature.users.User;

public class Admin extends UserInterface{
	
	public static int Screen(HashMap<String, User> users) {
		User u = UserInterface.loginScreen(users);
		
		if(u == null) {
			return 0;
		}
		if(u.getAccess_level() != 2) {
			System.out.println("Wrong Menu");
			return 0;
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
					UserInterface.closeScanner();
					break;
				default:
					System.out.println("Invalid input. Please try again");
			}
		}
		return 0;
	}
}
