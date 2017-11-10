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
			System.out.print("Enter option: ");
			option = UserInterface.readNumberInput();
			
			switch(option) {
				case 1:
					AdminTool.showAllUser(users);
					break;
				case 2:
					AdminTool.approveUser(users);
					break;
				case 3:
					AdminTool.banUser(users);
					break;
				case 4:
					AdminTool.promoteUser(users);
					break;
				case 5:
					System.out.println("Thank you for the hard work!");
					UserInterface.cleanUp(users);
					break;
				default:
					System.out.println("Invalid input. Please try again");
			}
		}
		return 0;
	}
}
