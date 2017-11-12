package com.revature.app.view;

import java.util.Iterator;

import com.revature.app.Menu;
import com.revature.app.MyBank;
import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.account.AccountStatus;
import com.revature.businessobject.user.Checkpoint;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.factory.FieldParamsFactory;

public class AdminView implements View {
	private Resultset allUsers;
	private Resultset allAccounts;
	private Resultset pendingUsers;
	private Resultset pendingAccounts;
	private String name = MyBank.data.get(User.USERNAME);
	private FieldParamsFactory factory = FieldParamsFactory.getFactory();
	
	@Override
	public void run() {
		String input = "";
		
		Menu.printUser(MyBank.data);
		Menu.printViewMenu();
		
		while (!input.toLowerCase().equals("logout")) {
			printUserCount();
			printAccountCount();
			printWorkLoad();
			printOptions();
			input = Menu.getInput(name + ":>");
			
			switch (input) {
				case "0":
					manageUser(pendingUsers);
					break;
				case "1":
					manageAccount(pendingAccounts);
					break;
				case "2":
					manageUser(allUsers);
					break;
				case "3":
					manageAccount(allAccounts);
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
	
	private void manageAccount(Resultset list) {
		if (list.size() > 0) {
			FieldParams params;
			String input;
			int index; 
			
			Menu.println("Select one of the following accounts: ");
			printList(list);
			input = Menu.getInput(name + ":>");
			
			try {
				index = Integer.parseInt(input);
				
				if (index > -1 && index < list.size()) {
					// Set params to view account information
					params = factory.getFieldParams(list.get(index));
					params.put(User.SESSIONID, MyBank.data.get(User.SESSIONID));
					Menu.printAccountData(params);
					printUserActionOptions();
					input = Menu.getInput(name + ":>");
					
					switch (input) {
						case "0": 
							updateUserCheckpoint((User)list.get(index), Checkpoint.CUSTOMER);
							break;
						case "1":
							updateUserCheckpoint((User)list.get(index), Checkpoint.ADMIN);
							break;
						case "2":
							updateUserCheckpoint((User)list.get(index), Checkpoint.BLOCKED);
							break;
						case "3":
							deleteUser((User)list.get(index));
							break;
						case "4":
							break;
						default:
							Menu.println("Invalid selection!");		
					}
					
				}else {
					Menu.println("Invalid selection!");
				}
			} catch (Exception e) {
			}	
		} else {
			Menu.println("You do not have any users");;
		}
	}
	
	private void manageUser(Resultset list) {
		if (list.size() > 0) {
			FieldParams params;
			String input;
			int index; 
			
			Menu.println("Select one of the following users: ");
			printList(list);
			input = Menu.getInput(name + ":>");
			
			try {
				index = Integer.parseInt(input);
				
				if (index > -1 && index < list.size()) {
					// Set params to view user information
					params = factory.getFieldParams(list.get(index));
					params.put(User.SESSIONID, MyBank.data.get(User.SESSIONID));
					Menu.printUser(params);
					printUserActionOptions();
					input = Menu.getInput(name + ":>");
					
					// Process selection 
					switch (input) {
						case "0": 
							updateUserCheckpoint((User)list.get(index), Checkpoint.CUSTOMER);
							break;
						case "1":
							updateUserCheckpoint((User)list.get(index), Checkpoint.ADMIN);
							break;
						case "2":
							updateUserCheckpoint((User)list.get(index), Checkpoint.BLOCKED);
							break;
						case "3":
							deleteUser((User)list.get(index));
							break;
						case "4":
							break;
						default:
							Menu.println("Invalid selection!");		
					}
				}else {
					Menu.println("Invalid selection!");
				}
			} catch (Exception e) {
			}	
		} else {
			Menu.println("You do not have any users");;
		}
	}
	

	///
	//	USER MANIPULATION CONTROLS 
	///
	
	private void deleteUser(User user) {
		Resultset res;
		
		// Send request
		res = MyBank.send(new Request(MyBank.data, "USER", "DELETEUSER", factory.getFieldParams(user), null));
		
		if (res.size() == 1)
			Menu.println("Account deleted");
		else {
			Menu.print(res.getException().getMessage());
		}
	}

	
	private void updateUserCheckpoint(User user, Checkpoint checkpoint) {
		FieldParams trans;
		Resultset res;
		
		// Set request params
		trans = factory.getFieldParams(user);
		trans.put(User.CHECKPOINT, Integer.toString(checkpoint.ordinal()));
	
		// Send request
		res = MyBank.send(new Request(MyBank.data, "USER", "SETUSER", factory.getFieldParams(user), trans));
		
		if (res.size() == 1)
			Menu.println("Account updated");
		else {
			Menu.print(res.getException().getMessage());
		}
	}
	
	
	private void updateAccount(Account account) {
		
	}
	
	///
	//	PRINT METHODS 
	///
	
	private void printUserActionOptions() {
		Menu.println("What would you like to do?");
		Menu.println("\t- 0 - SET AS CUSTOMER");
		Menu.println("\t- 1 - SET AS ADMIN");
		Menu.println("\t- 2 - SET AS BLOCKED");
		Menu.println("\t- 3 - DELETE");
		Menu.println("\t- 4 - CANCEL");
	}
	
	private void printAccountOptions() {
		Menu.println("What would you like to do?");
		Menu.println("\t- 0 - SET AS ACTIVE");
		Menu.println("\t- 1 - SET AS BLOCKED");
		Menu.println("\t- 2 - MANAGE");
		Menu.println("\t- 3 - DELETE");
		Menu.println("\t- 4 - CANCEL");
	}
	
	private void printList(Resultset list) {
		Iterator<BusinessObject> it = list.iterator();
		
		for (int i = 0; it.hasNext(); i++) {
			Menu.println("\t- " + i + " - " + it.next());
		}
	}
	
	private void printOptions() {
		Menu.println("What would like to due: ");
		Menu.println("\t- 0 - manage pending users");
		Menu.println("\t- 1 - manage pending accounts");
		Menu.println("\t- 2 - manage existing users");
		Menu.println("\t- 3 - manage existing accounts");
	}
	
	private void printWorkLoad() {
		Menu.println(MyBank.data.get(User.USERNAME) + " you have:");
		printPendingUserCount();
		printPendingAccountCount();
		Menu.println(" remaining to be processed." );
	}
	
	private void printPendingUserCount() {
		FieldParams params = new FieldParams();
		
		// Set params to pull pending user records
		params.put(User.CHECKPOINT, Integer.toString(Checkpoint.PENDING.ordinal()));
		
		// Get pending users
		pendingUsers = MyBank.send(new Request(MyBank.data, "USER", "GETUSER", params, null));
		
		// Print message
		Menu.println("\t-> " + pendingUsers.size() + " pending users.");
	}
	
	private void printPendingAccountCount() {
		FieldParams params = new FieldParams();
		
		// Set params to pull pending user records
		params.put(Account.STATUS, Integer.toString(AccountStatus.PENDING.ordinal()));
		
		// Get pending users
		pendingAccounts = MyBank.send(new Request(MyBank.data, "USER", "GETACCOUNT", params, null));
		
		// Print message
		Menu.println("\t-> " + pendingAccounts.size() + " pending accounts.");
	}

	private void printUserCount() {
		FieldParams params = new FieldParams();
	
		params.put(User.CHECKPOINT, Integer.toString(Checkpoint.ADMIN.ordinal()));
		allUsers = MyBank.send(new Request(MyBank.data, "USER", "GETUSER", params, null));
		params.put(User.CHECKPOINT, Integer.toString(Checkpoint.CUSTOMER.ordinal()));
		allUsers.addAll(MyBank.send(new Request(MyBank.data, "USER", "GETUSER", params, null)));
		params.put(User.CHECKPOINT, Integer.toString(Checkpoint.BLOCKED.ordinal()));
		allUsers.addAll(MyBank.send(new Request(MyBank.data, "USER", "GETUSER", params, null)));
		Menu.println("We have " + allUsers.size() + " users!");
	}
	
	private void printAccountCount() {
		FieldParams params = new FieldParams();

		params.put(Account.STATUS, Integer.toString(AccountStatus.ACTIVE.ordinal()));
		allAccounts = MyBank.send(new Request(MyBank.data, "USER", "GETACCOUNT", params, null));
		Menu.println("We have " + allAccounts.size() + " accounts!");;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
