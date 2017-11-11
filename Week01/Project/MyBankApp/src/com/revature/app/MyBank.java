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
					break;
				case "login":
					login();
					break;
				case "exit":
					break;
				default:
					System.out.println(input + " is unknown input!");
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
		
		System.out.println("Goodbye!!!");
		
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
				System.out.println("Unknown user role");
		}
	}
	
	///
	//	COMM Methods 
	///
	
	
	public static void login() {
		String username = Menu.getInput("Username:>");
		String password = Menu.getInput("Password:>");
		
		try {	
			data = server.login(username, password);
		} catch (Exception e) {
			System.out.println("error:>" + e.getMessage());
			data = null;
		}
	}

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
			// TODO log
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
}
