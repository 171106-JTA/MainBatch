package com.bankoftheapes.ui;

import java.util.HashMap;

import com.bankoftheapes.user.User;

public class Moderator {
	/**
	 * Displays Moderator screen. Allows moderators to approve and promote people. They are able to see all users.
	 * 
	 * @param users HashMap of users used to gather access, approval, and banned statuses of users
	 * @param u user object needed to check 
	 */
	public static void Screen(HashMap<String, User> users, User u) {
		
		if(u == null) {
			return;
		}
		//Checks to see if the user has the right access_level;
		//They get kicked out if the user is not authorized
		if(u.getAccess_level() != 1) {
			System.out.println("Wrong Menu");
			return;
		}
		
		System.out.println("Welcome Mod " + u.getName());
		int option = 0;
		while(option != 4) {
			System.out.println("1. Show all users");
			System.out.println("2. Approve user");
			System.out.println("3. Promote user");
			System.out.println("4. Log out");
			System.out.print("Enter option: ");
			option = UserInterface.readIntInput();
			
			switch(option) {
				case 1:
					AdminTool.showAllUser(users);
					break;
				case 2:
					AdminTool.approveUser(users);
					break;
				case 3:
					AdminTool.promoteUser(users, u.getAccess_level());
					break;
				case 4:
					System.out.println("Thank you for the hard work!");
					UserInterface.cleanUp(users);
					break;
				default:
					System.out.println("Invalid input. Please try again");
			}
		}
	}
}
