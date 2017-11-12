package com.revature.app.view;

import java.util.Iterator;

import com.revature.app.Menu;
import com.revature.app.MyBank;
import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.Info;
import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.account.Checking;
import com.revature.businessobject.info.account.Status;
import com.revature.businessobject.info.account.Transaction;
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
	private Resultset accounts;
	
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
				loadUserAccounts();
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
				Menu.println("\t\terror:>" + res.getException().getMessage() + "\n");
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
				Menu.println("\t\terror:>" + res.getException().getMessage() + "\n");
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
				Menu.println("\t\terror:>" + res.getException().getMessage() + "\n");
		} else {
			Menu.println("done\n");
		}
	}
	
	private void createCheckingAccount() {
		Request request;
		Resultset res;
		
		// Create request
		request = new Request(MyBank.data, "USER", "CREATECHECKINGACCOUNT", null, null);
		
		Menu.print("\tAttempting to create a checking account...");
		
		if ((res = MyBank.send(request)).getRecordsModified() == 0) {
			Menu.println("fail");
			if (res.getException() != null)
				Menu.println("\t\terror:>" + res.getException().getMessage() + "\n");
		} else {
			Menu.println("done\n");
		}
	}
	
	private void manageAccount() {
		if (accounts.size() > 0) {
			FieldParams params;
			String input;
			int index;
			
			// Display account info 
			Menu.println("Select account please:");
			printAccounts();
			input = Menu.getInput(name + ":>");
			
			try {
				index = Integer.parseInt(input);
				
				if (index > -1 && index < accounts.size()) {
					// Set params to view user information
					params = factory.getFieldParams(accounts.get(index));
					params.put(User.SESSIONID, MyBank.data.get(User.SESSIONID));
					Menu.printCheckingAccount((Checking)accounts.get(index));
					printAccountOptions();
					input = Menu.getInput(name + ":>");
					
					// Process selection 
					switch (input) {
						case "0": 
							addFunds((Account)accounts.get(index));
							break;
						case "1":
							removeFunds((Account)accounts.get(index));
							break;
						case "2":
							deleteAccount((Account)accounts.get(index));
							break;
						default:
							Menu.println("Invalid selection!");		
					}
				}else {
					Menu.println("Invalid selection!");
				}
			} catch (Exception e) {
			}	
		}else {
			Menu.println("You do not have any accounts");;
		}
	}
	
	private void addFunds(Account account) {
		if (!account.getStatus().equals(Status.ACTIVE))
			Menu.println("Account needs to be activated first!");
		else { 
			FieldParams query = factory.getFieldParams(account);
			FieldParams transact = new FieldParams();
			String amount = Menu.getInput("Please enter total amount you wish to add:>");
			Request request;
			Resultset res;
			
			// set amount we wish to add to account
			transact.put(Transaction.AMOUNT, amount);
			
			// Create request
			request = new Request(MyBank.data, "BANKING", "CHECKINGADDFUNDS", query, transact);
			
			Menu.print("\tAttempting to add funds to account...");
			
			if ((res = MyBank.send(request)).getRecordsModified() == 0) {
				Menu.println("fail");
				if (res.getException() != null)
					Menu.println("\t\terror:>" + res.getException().getMessage() + "\n");
			} else {
				Menu.println("done\n");
			}
		}
	}
	
	private void removeFunds(Account account) {
		if (!account.getStatus().equals(Status.ACTIVE))
			Menu.println("Account needs to be activated first!");
		else { 
			FieldParams query = factory.getFieldParams(account);
			FieldParams transact = new FieldParams();
			String amount = Menu.getInput("Please enter total amount you wish to remove:>");
			Request request;
			Resultset res;
			
			// set amount we wish to add to account
			transact.put(Transaction.AMOUNT, amount);
			
			// Create request
			request = new Request(MyBank.data, "BANKING", "CHECKINGREMOVEFUNDS", query, transact);
			
			Menu.print("\tAttempting to remove funds from account...");
			
			if ((res = MyBank.send(request)).getRecordsModified() == 0) {
				Menu.println("fail");
				if (res.getException() != null)
					Menu.println("\t\terror:>" + res.getException().getMessage() + "\n");
			} else {
				Menu.println("done\n");
			}
		}
	}
	
	private void deleteAccount(Account account) {
		FieldParams query = factory.getFieldParams(account);
		Request request;
		Resultset res;
		
		// Create request
		request = new Request(MyBank.data, "USER", "DELETEACCOUNT", query, null);
					
		Menu.print("\tAttempting to delete account...");
					
		if ((res = MyBank.send(request)).getRecordsModified() == 0) {
			Menu.println("fail");
			if (res.getException() != null)
				Menu.println("\t\terror:>" + res.getException().getMessage() + "\n");
			} else {
				Menu.println("done\n");
		}
	}
	
	
	
	private void loadUserAccounts() {
		FieldParams params = new FieldParams();
		Request request;
		
		// set query params
		params.put(Info.USERID, MyBank.data.get(User.ID));
		
		// create request
		request = new Request(MyBank.data, "USER", "GETACCOUNT", params, null);
		accounts = MyBank.send(request);
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
		Menu.println("\t- 5 - view self");
	}
	
	private void printAccountOptions() {
		Menu.println("What would like to do: ");
		Menu.println("\t- 0 - ADD FUNDS");
		Menu.println("\t- 1 - REMOVE FUNDS");
		Menu.println("\t- 1 - DELETE ACCOUNT");
		Menu.println("\t- 2 - CANCEL");
	}
	
	private void printAccounts() {
		Iterator<BusinessObject> it = accounts.iterator();
		
		for (int i = 0; it.hasNext(); i++) {
			Menu.println("\t- " + i + " - " + it.next());
		}
	}
	
}
