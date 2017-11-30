package com.revature.app.view;

import java.util.Iterator;

import com.revature.app.Menu;
import com.revature.app.MyBank;
import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.Info;
import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.account.Status;
import com.revature.businessobject.info.user.UserInfo;
import com.revature.businessobject.user.Checkpoint;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.factory.FieldParamsFactory;
import com.revature.route.Routes;

/**
 * Displays application used by admins for managing accounts 
 * @author Antony Lulciuc
 */
public class AdminView implements View {
	private FieldParamsFactory factory = FieldParamsFactory.getFactory();
	private String name = MyBank.data.get(User.USERNAME);
	private Resultset allUsers;
	private Resultset allAccounts;
	private Resultset pendingUsers;
	private Resultset pendingAccounts;
	
	/**
	 * Main view process for admins 
	 */
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
	
	/**
	 * Allows admins to manage user accounts
	 * @param list - accounts available 
	 */
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
	
	/**
	 * Allows admins to manage accounts 
	 * @param list - accounts available
	 */
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
					params.put(User.ID, params.get(Info.USERID));
					Menu.printAccountData(params);
					printAccountActionOptions();
					input = Menu.getInput(name + ":>");
					
					switch (input) {
						case "0": 
							updateAccountStatus((Account)list.get(index), Status.ACTIVE);
							break;
						case "1":
							updateAccountStatus((Account)list.get(index), Status.BLOCKED);
							break;
						case "2":
							deleteAccount((Account)list.get(index));
							break;
						case "3":
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
			Menu.println("You do not have any accounts");
		}
	}

	///
	//	USER MANIPULATION CONTROLS 
	///
	
	/**
	 * Updates user checkpoint
	 * @param user - who to update
	 * @param checkpoint new user checkpoint
	 */
	private void updateUserCheckpoint(User user, String checkpoint) {
		FieldParams trans = new FieldParams();
		FieldParams query = new FieldParams();
		Request request;
		Resultset res;
		
		// Set request params
		trans.put(UserInfo.STATUSID, checkpoint);
		query.put(UserInfo.USERID, Long.toString(user.getId()));
		// Create request
		request = new Request(MyBank.data, Routes.USER, "SETUSERINFO", query, trans);
		
		Menu.print("\tAttempting to update user checkpoint...");
		
		if ((res = MyBank.send(request)).getRecordsModified() == 0) {
			Menu.println("fail");
			if (res.getException() != null)
				Menu.println("\t\terror:>" + res.getException().getMessage() + "\n");
		} else {
			Menu.println("done\n");
		}
	}
	
	/**
	 * Removes userfrom system
	 * @param user - who to delete
	 */
	private void deleteUser(User user) {
		Request request;
		Resultset res;
		
		// Create request
		request = new Request(MyBank.data, Routes.USER, "DELETEUSER", factory.getFieldParams(user), null);
		
		Menu.print("\tAttempting to delete user...");
		
		if ((res = MyBank.send(request)).getRecordsModified() == 0) {
			Menu.println("fail");
			if (res.getException() != null)
				Menu.println("\t\terror:>" + res.getException().getMessage() + "\n");
		} else {
			Menu.println("done\n");
		}
	}
	
	/**
	 * Updates account status 
	 * @param account - account to update
	 * @param status - new status
	 * @see Status
	 */
	private void updateAccountStatus(Account account, String status) {
		FieldParams params = new FieldParams();
		FieldParams transact = new FieldParams();
		Request request;
		Resultset res;
		
		// Set params
		params.put(Info.USERID, Long.toString(account.getUserId()));
		params.put(Account.NUMBER, account.getNumber());
		transact.put(Account.STATUSID, status);
		
		// Create request
		request = new Request(MyBank.data, Routes.USER, "SETACCOUNTSTATUS", params, transact);

		Menu.print("\tAttempting to update account status...");
		
		if ((res = MyBank.send(request)).getRecordsModified() == 0) {
			Menu.println("fail");
			if (res.getException() != null)
				Menu.println("\t\terror:>" + res.getException().getMessage() + "\n");
		} else {
			Menu.println("done\n");
		}
	}
	
	/**
	 * Removes account from system
	 * @param account - account to remove 
	 */
	private void deleteAccount(Account account) {
		FieldParams params = new FieldParams();
		Request request;
		Resultset res;
		
		// Set params
		params.put(Info.USERID, Long.toString(account.getUserId()));
		params.put(Account.NUMBER, account.getNumber());
		
		// Create request
		request = new Request(MyBank.data, Routes.USER, "DELETEACCOUNT", params, null);

		Menu.print("\tAttempting to delete account...");
		
		if ((res = MyBank.send(request)).getRecordsModified() == 0) {
			Menu.println("fail");
			if (res.getException() != null)
				Menu.println("\t\terror:>" + res.getException().getMessage() + "\n");
		} else {
			Menu.println("done\n");
		}
	}
	
	///
	//	PRINT METHODS 
	///
	
	/**
	 * Prints main actions to set user checkpoints 
	 */
	private void printUserActionOptions() {
		Menu.println("What would you like to do?");
		Menu.println("\t- 0 - SET AS CUSTOMER");
		Menu.println("\t- 1 - SET AS ADMIN");
		Menu.println("\t- 2 - SET AS BLOCKED");
		Menu.println("\t- 3 - DELETE");
		Menu.println("\t- 4 - CANCEL");
	}
	
	/**
	 * Prints main actions to set account statuses 
	 */
	private void printAccountActionOptions() {
		Menu.println("What would you like to do?");
		Menu.println("\t- 0 - SET AS ACTIVE");
		Menu.println("\t- 1 - SET AS BLOCKED");
		Menu.println("\t- 2 - DELETE");
		Menu.println("\t- 3 - CANCEL");
	}
	
	/**
	 * Prints list
	 * @param list - list to print 
	 */
	private void printList(Resultset list) {
		Iterator<BusinessObject> it = list.iterator();
		
		for (int i = 0; it.hasNext(); i++) {
			Menu.println("\t- " + i + " - " + it.next());
		}
	}
	
	/**
	 * Prints main admin options available to them
	 */
	private void printOptions() {
		Menu.println("What would like to do: ");
		Menu.println("\t- 0 - manage pending users");
		Menu.println("\t- 1 - manage pending accounts");
		Menu.println("\t- 2 - manage existing users");
		Menu.println("\t- 3 - manage existing accounts");
	}
	
	/**
	 * Prints number pending users/accounts 
	 */
	private void printWorkLoad() {
		Menu.println(MyBank.data.get(User.USERNAME) + " you have:");
		printPendingUserCount();
		printPendingAccountCount();
		Menu.println(" remaining to be processed." );
	}
	
	/**
	 * Prints number of pending users 
	 */
	private void printPendingUserCount() {
		FieldParams params = new FieldParams();
		
		// Set params to pull pending user records
		params.put(User.CHECKPOINT, Checkpoint.PENDING);
		
		// Get pending users
		pendingUsers = MyBank.send(new Request(MyBank.data, Routes.USER, "GETUSER", params, null));
		
		// Print message
		Menu.println("\t-> " + pendingUsers.size() + " pending users.");
	}
	
	/**
	 * prints number of pending accounts 
	 */
	private void printPendingAccountCount() {
		FieldParams params = new FieldParams();
		
		// Set params to pull pending user records
		params.put(Account.STATUSID, Status.PENDING);
		
		// Get pending users
		pendingAccounts = MyBank.send(new Request(MyBank.data, Routes.USER, "GETACCOUNT", params, null));
		
		// Print message
		Menu.println("\t-> " + pendingAccounts.size() + " pending accounts.");
	}

	/**
	 * Prints total number of users in system
	 * Also caches users returned to be used in application
	 */
	private void printUserCount() {
		FieldParams params = new FieldParams();
	
		params.put(User.CHECKPOINT, Checkpoint.ADMIN);
		allUsers = MyBank.send(new Request(MyBank.data, Routes.USER, "GETUSER", params, null));
		params.put(User.CHECKPOINT, Checkpoint.CUSTOMER);
		allUsers.addAll(MyBank.send(new Request(MyBank.data, Routes.USER, "GETUSER", params, null)));
		params.put(User.CHECKPOINT, Checkpoint.BLOCKED);
		allUsers.addAll(MyBank.send(new Request(MyBank.data, Routes.USER, "GETUSER", params, null)));
		Menu.println("We have " + allUsers.size() + " users!");
	}
	
	/**
	 * Prints total number of accounts in system 
	 * Also caches accounts returned to be used in application
	 */
	private void printAccountCount() {
		FieldParams params = new FieldParams();

		params.put(Account.STATUSID, Status.ACTIVE);
		allAccounts = MyBank.send(new Request(MyBank.data, Routes.USER, "GETACCOUNT", params, null));
		params.put(Account.STATUSID, Status.BLOCKED);
		allAccounts.addAll(MyBank.send(new Request(MyBank.data, Routes.USER, "GETACCOUNT", params, null)));
		Menu.println("We have " + allAccounts.size() + " accounts!");;
	}
}