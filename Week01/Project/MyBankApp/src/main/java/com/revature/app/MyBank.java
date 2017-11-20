package com.revature.app;
import com.revature.app.view.AdminView;
import com.revature.app.view.CustomerView;
import com.revature.businessobject.info.CodeList;
import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.user.UserInfo;
import com.revature.businessobject.user.Checkpoint;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;
import com.revature.route.Routes;
import com.revature.server.Server;

/**
 * Front-end part of application
 * @author Antony Lulciuc
 */
public class MyBank {
	private static Server server = new Server();
	public static FieldParams data;
	
	/**
	 * Main application
	 * @param args - ignored
	 */
	public static void main(String[] args) {
		String input = "";

		// Start Server
		new Thread(server).start();
		
		Menu.printMenu();
		
		// Run application until user exits 
		while (!input.toLowerCase().equals("exit")) {
			input = Menu.getInput("enter:>");
			
			// Handle possible inputs by user 
			switch (input.toLowerCase()) {
				case "new":
					createAccount();
					break;
				case "login":
					login();
					break;
				case "exit":
					break;
				default:
					Menu.println(input + " is unknown input!");
			}
			
			// Data is non-null value on successful login
			if (data != null) {
				// Start application based on user checkpoints
				start();
				
				// Terminate session when done 
				server.kill(Integer.parseInt(data.get(User.SESSIONID)));
				
				// reset data 
				data = null;
			}
		}
		
		Menu.println("Goodbye!!!");
		
		// Tell server to stop when app complete
		server.kill();
	}
	
	/**
	 * Starts application view for user
	 */
	public static void start() {
		switch (data.get(User.CHECKPOINT)) {
			case Checkpoint.ADMIN:
				new AdminView().run();
				break;
			case Checkpoint.CUSTOMER:
			case Checkpoint.PENDING:
				new CustomerView().run();
				break;
			default:
				Menu.println("=== Unknown user account type ===");
		}
	}
	
	/**
	 * Attempts to login and create session for user
	 */
	public static void login() {
		signin(Menu.getInput("Username:>"), Menu.getInput("Password:>"));
	}
	
	/**
	 * Attempts to create new User account
	 */
	public static void createAccount() {
		FieldParams params = new FieldParams();
		FieldParams info = new FieldParams();
		String username;
		String password;
		Resultset res;
		
		Menu.printCreateUserMenu();
		
		// Sign in as guest
		signin("guest", "guest");
		
		if (data != null) {
			// Set new user params
			params.put(User.SESSIONID, data.get(User.SESSIONID));
			params.put(User.USERNAME, username = Menu.getInput("Usename: "));
			params.put(User.PASSWORD, password = Menu.getInput("Password: "));
			info.put(User.SESSIONID, data.get(User.SESSIONID));
			info.put(UserInfo.FIRSTNAME, Menu.getInput("First name: "));
			info.put(UserInfo.LASTNAME, Menu.getInput("Last name: "));
			info.put(UserInfo.SSN, Menu.getInput("SSN: "));
			info.put(UserInfo.EMAIL, Menu.getInput("Email: "));
			info.put(UserInfo.PHONENUMBER, Menu.getInput("Phonenumber: "));
			info.put(UserInfo.ADDRESS1, Menu.getInput("Address 1: "));
			info.put(UserInfo.ADDRESS2, Menu.getInput("Address 2: "));
			info.put(UserInfo.STATE, Menu.getInput("State: "));
			info.put(UserInfo.CITY, Menu.getInput("City: "));
			info.put(UserInfo.POSTALCODE, Menu.getInput("Zip: "));
			
			Menu.print("\n\tAttempting to create account...");
			
			// Request to create new account
			if ((res = send(new Request(data, Routes.USER, "CREATEUSER", null, params))).getRecordsModified() == 0) {
				Menu.println("fail!\n");
				Menu.println("\t\terror:>" + res.getException().getMessage() + "\n");	
			} else {
				Menu.print("done\n");
				
				// Close guest account
				server.kill(Integer.parseInt(data.get(User.SESSIONID)));
				
				// login in account
				signin(username, password);
				info.put(User.SESSIONID, data.get(User.SESSIONID));
				info.put(UserInfo.USERID, data.get(User.ID));
				
				Menu.print("\n\tAttempting to create user info...");
				// Request to create new account
				if ((res = send(new Request(data, Routes.USER, "CREATEUSERINFO", null, info))).getRecordsModified() == 0) {
					Menu.println("fail!\n");
					Menu.println("\t\terror:>" + res.getException().getMessage() + "\n");	
					send(new Request(data, Routes.USER, "DELETEUSER", params, null));
				} else {
					Menu.print("done\n");
					Menu.println("Account now waiting for approval!");
					Menu.println("Thank you!");			
				}	
			}
			
			// Close session
			server.kill(Integer.parseInt(data.get(User.SESSIONID)));
			data = null;
		} else {
			Menu.println("Failed to create account!");
		}
	}
	
	///
	//	COMM Methods 
	///

	/**
	 * Sends request to server
	 * @param request - what client wants to process
	 * @return result of request
	 */
	public static Resultset send(Request request) {
		Resultset res = null;
		
		try {
			MyBank.server.pushRequest(request);
			
			while ((res = MyBank.server.getResponse(request)) == null) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO log
				}
			}
			
		} catch (RequestException e) {
			res = new Resultset(e);
		}
		
		return res;
	}
	
	/**
	 * Acquires user info 
	 * @param data - login info
	 * @return User Info (contact data)
	 */
	public static Resultset getUserInfo(FieldParams data) {
		FieldParams params = new FieldParams();

		// Set query params
		params.put(UserInfo.USERID, data.get(User.ID));
		
		return MyBank.send(new Request(data, Routes.USER, "GETUSERINFO", params, null));
	}
	
	/**
	 * Acquires user account data 
	 * @param data - login info
	 * @return Accounts tied to user
	 */
	public static Resultset getAccount(FieldParams data) {
		FieldParams params = new FieldParams();

		// Set query params
		params.put(Account.USERID, data.get(User.ID));
		
		// If account number specified 
		if (data.get(Account.NUMBER) != null)
			params.put(Account.NUMBER, data.get(Account.NUMBER));
		
		return MyBank.send(new Request(data, Routes.USER, "GETACCOUNT", params, null));
	}
	
	///
	//	PRIVATE METHODS 
	///
	
	/**
	 * Performs actual signin to account 
	 * @param username - associated with user
	 * @param password - needed to access account 
	 */
	private static void signin(String username, String password) {
		try {	
			data = server.login(username, password);
		} catch (Exception e) {
			Menu.println("error:>" + e.getMessage());
			data = null;
		}
	}
	
}
