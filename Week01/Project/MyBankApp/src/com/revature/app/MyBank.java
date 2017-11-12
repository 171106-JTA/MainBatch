package com.revature.app;
import com.revature.app.view.AdminView;
import com.revature.app.view.CustomerView;
import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.user.UserInfo;
import com.revature.businessobject.user.Checkpoint;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;
import com.revature.server.Server;

public class MyBank {
	private static Server server = new Server();
	public static FieldParams data;
	
	public static void main(String[] args) {
		String input = "";

		// Start Server
		server.start();
		
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
	
	public static void start() {
		switch (Checkpoint.values()[Integer.parseInt(data.get(User.CHECKPOINT))]) {
			case ADMIN:
				new AdminView().run();
				break;
			case CUSTOMER:
				new CustomerView().run();
				break;
			default:
				Menu.println("Unknown user role");
		}
	}
	
	public static void login() {
		signin(Menu.getInput("Username:>"), Menu.getInput("Password:>"));
	}
	
	public static void createAccount() {
		FieldParams params = new FieldParams();
		Resultset res;
		String username;
		String password;
		String email;
		String address;
		String phonenumber;
		
		Menu.printCreateUserMenu();
		username = Menu.getInput("Usename: ");
		password = Menu.getInput("Password: ");
		email = Menu.getInput("Email: ");
		address = Menu.getInput("Address: ");
		phonenumber = Menu.getInput("Phonenumber: ");
		
		// Sign in as guest
		signin("guest", "guest");
		
		if (data != null) {
			// Set new user params
			params.put(User.SESSIONID, data.get(User.SESSIONID));
			params.put(User.USERNAME, username);
			params.put(User.PASSWORD, password);
			
			Menu.print("\n\tAttempting to create account...");
			
			// Request to create new account
			if ((res = send(new Request(data, "USER", "CREATEUSER", null, params))).getRecordsModified() == 0) {
				synchronized(MyBank.class) {
					Menu.println("fail!\n");
					Menu.println("error:>" + res.getException().getMessage() + "\n");	
				}
			} else {
				Menu.println("done\n\tAttempting to create user info...");
				
				// Close guest account
				server.kill(Integer.parseInt(data.get(User.SESSIONID)));
				
				// login in account
				signin(username, password);
				
				// Set userinfo data
				params.clear();
				params.put(UserInfo.USERID, data.get(User.ID));
				params.put(User.SESSIONID, data.get(User.SESSIONID));
				params.put(UserInfo.EMAIL, email);
				params.put(UserInfo.ADDRESS, address);
				params.put(UserInfo.PHONENUMBER, phonenumber);
				
				// Did we fail to create user information?
				if ((res = send(new Request(data, "USER", "CREATEUSERINFO", null, params))).getRecordsModified() == 0) {
					Menu.println("fail!");
					Menu.println("\nerror:>" + res.getException().getMessage() + "\n");
				}
				else {
					Menu.println("done");
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

	public static Resultset send(Request request) {
		Resultset res = null;
		
		try {
			MyBank.server.pushRequest(request);
			
			while ((res = MyBank.server.getResponse(request)) == null) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO log
				}
			}
			
		} catch (RequestException e) {
			res = new Resultset(e);
		}
		
		return res;
	}
	
	public static Resultset getUserInfo(FieldParams data) {
		FieldParams params = new FieldParams();

		// Set query params
		params.put(UserInfo.USERID, data.get(User.ID));
		
		return MyBank.send(new Request(data, "USER", "GETUSERINFO", params, null));
	}
	
	
	public static Resultset getAccount(FieldParams data) {
		FieldParams params = new FieldParams();

		// Set query params
		params.put(Account.USERID, data.get(User.ID));
		
		// If account number specified 
		if (data.get(Account.NUMBER) != null)
			params.put(Account.NUMBER, data.get(Account.NUMBER));
		
		return MyBank.send(new Request(data, "USER", "GETACCOUNT", params, null));
	}
	
	///
	//	PRIVATE METHODS 
	///
	
	private static void signin(String username, String password) {
		try {	
			data = server.login(username, password);
		} catch (Exception e) {
			Menu.println("error:>" + e.getMessage());
			data = null;
		}
	}
	
}
