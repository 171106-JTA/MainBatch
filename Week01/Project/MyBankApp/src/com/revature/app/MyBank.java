package com.revature.app;
import java.util.Scanner;

import com.revature.app.view.*;
import com.revature.businessobject.user.UserRole;
import com.revature.core.FieldParams;
import com.revature.server.Server;

public class MyBank {
	public static Server server = new Server();
	public static FieldParams data;
	
	public static void main(String[] args) {
		String input = "";

		printMenu();
		
		while (!input.toLowerCase().equals("exit")) {
			input = getInput("enter:>");
			
			switch (input.toLowerCase()) {
				case "new":
					break;
				case "login":
					login();
			}
			
			if (data != null) {
				start();
				
				// reset data 
				data = null;
			}
		}
	}
	
	public static void start() {
		switch (UserRole.values()[Integer.parseInt(data.get("role"))]) {
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
		String username = getInput("Username:>");
		String password = getInput("Password:>");
		
		try {	
			data = server.login(username, password);
		} catch (Exception e) {
			System.out.println("error:>" + e.getMessage());
			data = null;
		}
	}
	
	public static String getInput(String prefix) {
		Scanner stream = new Scanner(System.in);
		
		// Print content before input
		System.out.print(prefix);
		
		// get input
		return stream.nextLine();
	}
	
	public static void printMenu() {
		System.out.println("===============================================================");
		System.out.println("====================== WELCOME TO MYBANK ======================");
		System.out.println("===============================================================");
		System.out.println("= Options, type:                                              =");
		System.out.println("=                                                             =");
		System.out.println("=   - new - to create new account                             =");
		System.out.println("=                                                             =");
		System.out.println("=   - login - signing for existing users                      =");
		System.out.println("=                                                             =");
		System.out.println("=   - exit - to quit the application                          =");
		System.out.println("=                                                             =");
		System.out.println("===============================================================");
	}
	
}
