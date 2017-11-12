package com.revature.app.view;

import com.revature.app.Menu;
import com.revature.app.MyBank;
import com.revature.businessobject.user.User;

public class CustomerView implements View {
	private String name = MyBank.data.get(User.USERNAME);
	
	@Override
	public void run() {
		String input = "";
		
		Menu.printUser(MyBank.data);
		Menu.printViewMenu();
		
		while (!input.toLowerCase().equals("logout")) {
			printOptions();
			input = Menu.getInput(name + ":>");
			
			switch (input) {
				case "0":
					updateUserName();
					break;
				case "1":
					updatePassword();
					break;
				case "2":
					updateContactInfo();
					break;
				case "3":
					createCheckingAccount();
					break;
				case "4":
					manageAccount();
					break;
				case "5":
					deleteAccount();
					break;
				case "logout":
					break;
				default:
					Menu.println(input + " is an unknown selection!");;
			}
		}
	}
	
	///
	//	PRIVATE METHODS 
	///
	
	private void updateUserName() {
		// stub
	}
	
	private void updatePassword() {
		// stub
	}
	
	private void updateContactInfo() 
	// stub
	}
	
	private void createCheckingAccount() {
		// stub
	}
	
	private void manageAccount() {
		// stub
	}
	
	private void deleteAccount() {
		// stub
	}
	
	///
	//	PRINT METHODS 
	///
	
	private void printOptions() {
		Menu.println("What would like to do: ");
		Menu.println("\t- 0 - update user name ");
		Menu.println("\t- 1 - update password");
		Menu.println("\t- 2 - update contact info");
		Menu.println("\t- 3 - create checking account");
		Menu.println("\t- 4 - manage account");
		Menu.println("\t- 5 - delete account");
	}
}
