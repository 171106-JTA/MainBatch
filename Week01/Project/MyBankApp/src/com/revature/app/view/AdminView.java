package com.revature.app.view;

import com.revature.app.Menu;
import com.revature.app.MyBank;
import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.account.AccountType;
import com.revature.businessobject.user.Checkpoint;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Request;
import com.revature.core.Resultset;

public class AdminView implements View {
	private Resultset pendingUsers;
	private Resultset pendingAccounts;
	
	
	@Override
	public void run() {
		String input = "";
		
		Menu.printUser(MyBank.data);
		Menu.printViewMenu();
		
		while (!input.toLowerCase().equals("logout")) {
			printWorkLoad();
			
		}		
	}
	
	
	///
	//	PRIVATE METHODS 
	///
	
	private void printWorkLoad() {
		Menu.println(MyBank.data.get(User.USERNAME) + " you have:");
		printPendingUsers();
		printPendingAccounts();
		Menu.println(" remaining to be processed." );
	}
	
	private void printPendingUsers() {
		FieldParams params = new FieldParams();
		
		// Set params to pull pending user records
		params.put(User.CHECKPOINT, Integer.toString(Checkpoint.PENDING.ordinal()));
		
		// Get pending users
		pendingUsers = MyBank.send(new Request(MyBank.data, "USER", "GETUSER", params, null));
		
		// Print message
		Menu.println("\t-> " + pendingUsers.size() + " pending users.");
	}
	
	private void printPendingAccounts() {
		FieldParams params = new FieldParams();
		
		// Set params to pull pending user records
		params.put(Account.TYPE, Integer.toString(AccountType.PENDING.ordinal()));
		
		// Get pending users
		pendingAccounts = MyBank.send(new Request(MyBank.data, "USER", "GETACCOUNT", params, null));
		
		// Print message
		Menu.println("\t-> " + pendingAccounts.size() + " pending accounts.");
	}

	
	
}
