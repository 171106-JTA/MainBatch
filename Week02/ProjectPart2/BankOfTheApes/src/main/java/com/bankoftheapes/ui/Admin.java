package com.bankoftheapes.ui;

import com.bankoftheapes.dao.QueryUtil;
import com.bankoftheapes.user.User;

public class Admin extends UserInterface{
	
	/**
	 * Displays operations screen for administrators. Actions include: banning users, 
	 * approving users, promoting users to admin, and showing all users
	 * 
	 * @param users HashMap of users used to gather access, approval, and banned statuses of users
	 * @param u user object needed to check access level and name
	 */
	public static void Screen(User u, QueryUtil qu) {
		
		if(u == null) {
			return;
		}
		//Checks to see if the user has the right access_level;
		//They get kicked out if the user is not authorized
		if(!u.getAccess_level().equals("ADM")) {
			System.out.println("\nWrong Menu");
			return;
		}
		
		System.out.println("\nWelcome Admin " + u.getName());
		int option = 0;
		while(option != 6) {
			System.out.println("1. Show all users");
			System.out.println("2. Approve user");
			System.out.println("3. Ban user");
			System.out.println("4. Promote user");
			System.out.println("5. Approve Loan");
			System.out.println("6. Log out");
			System.out.print("Enter option: ");
			option = UserInterface.readIntInput();
			
			switch(option) {
				case 1:
					AdminTool.showAllUser(qu);
					break;
				case 2:
					AdminTool.approveUser(qu);
					break;
				case 3:
					AdminTool.banUser(qu);
					break;
				case 4:
					AdminTool.promoteUser(u.getAccess_level(), qu);
					break;
				case 5:
				case 6:
					System.out.println("Thank you for the hard work!");
					UserInterface.cleanUp();
					break;
				default:
					System.out.println("Invalid input. Please try again");
			}
		}
	}
}
