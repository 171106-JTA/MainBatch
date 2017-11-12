package com.revature.app.view;

import com.revature.app.Menu;
import com.revature.app.MyBank;
import com.revature.businessobject.info.user.UserInfo;
import com.revature.businessobject.user.Checkpoint;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.factory.FieldParamsFactory;

public class CustomerView implements View {
	private FieldParamsFactory factory = FieldParamsFactory.getFactory();
	private String name = MyBank.data.get(User.USERNAME);
	
	@Override
	public void run() {
		String input = "";
		
		// Show basic account information
		Menu.printUser(MyBank.data);
		
		if (!MyBank.data.get(User.CHECKPOINT).equals(Checkpoint.CUSTOMER))
			Menu.println("Account is waiting for approval, goodbye");
		else {
			// Print active user menu
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
					case "6":
						Menu.printUser(MyBank.data);
						break;
					case "logout":
						break;
					default:
						Menu.println(input + " is an unknown selection!");;
				}
			}
		}
	}
	
	///
	//	PRIVATE METHODS 
	///
	
	private void updateUserName() {
		FieldParams transact = new FieldParams(); 
		String username = Menu.getInput("New Username:>");
		Request request;
		Resultset res;
		
		// Set data to update
		transact.put(User.USERNAME, username);
		
		// Create request
		request = new Request(MyBank.data, "USER", "SETUSER", MyBank.data, transact);
		
		Menu.print("\tAttempting to change username...");
		
		if ((res = MyBank.send(request)).getRecordsModified() == 0) {
			Menu.println("fail");
			if (res.getException() != null)
				Menu.println("\t\terror:>" + res.getException().getMessage());
		} else {
			Menu.println("done\n");
			this.name = username;
			MyBank.data.put(User.USERNAME, username);
		}
	}
	
	private void updatePassword() {
		FieldParams transact = new FieldParams(); 
		String password = Menu.getInput("New Password:>");
		Request request;
		Resultset res;
		
		// Set data to update
		transact.put(User.PASSWORD, password);
		
		// Create request
		request = new Request(MyBank.data, "USER", "SETUSER", MyBank.data, transact);
		
		Menu.print("\tAttempting to change password...");
		
		if ((res = MyBank.send(request)).getRecordsModified() == 0) {
			Menu.println("fail");
			if (res.getException() != null)
				Menu.println("\t\terror:>" + res.getException().getMessage());
		} else {
			Menu.println("done\n");
			MyBank.data.put(User.PASSWORD, password);
		}
	}
	
	private void updateContactInfo() {
		FieldParams transact = new FieldParams();
		String email = Menu.getInput("New Email:>");
		String address = Menu.getInput("New Address:>");
		String phonenumber = Menu.getInput("New Phonenumber:>");
		Request request;
		Resultset res;
		
		// Prepare request
		transact.put(UserInfo.EMAIL, email);
		transact.put(UserInfo.ADDRESS, address);
		transact.put(UserInfo.PHONENUMBER, phonenumber);
		
		// Create request
		request = new Request(MyBank.data, "USER", "SETUSERINFO", MyBank.data, transact);
		
		Menu.print("\tAttempting to change contact info...");
		
		if ((res = MyBank.send(request)).getRecordsModified() == 0) {
			Menu.println("fail");
			if (res.getException() != null)
				Menu.println("\t\terror:>" + res.getException().getMessage());
		} else {
			Menu.println("done\n");
		}
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
		Menu.println("\t- 6 - view self");
	}
}
