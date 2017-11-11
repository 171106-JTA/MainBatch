package com.revature.app;
import java.util.Scanner;

import com.revature.app.view.AdminView;
import com.revature.app.view.CustomerView;
import com.revature.businessobject.user.Checkpoint;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.server.Server;

public class MyBank {
	public static Server server = new Server();
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
	
}
