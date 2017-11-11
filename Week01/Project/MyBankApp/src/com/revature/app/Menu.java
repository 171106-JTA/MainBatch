package com.revature.app;

import java.util.Scanner;

import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Request;
import com.revature.core.Resultset;
import com.revature.core.exception.RequestException;

public class Menu {
	private static Scanner in = new Scanner(System.in);
	
	public static String getInput(String prefix) {
		// Print content before input
		System.out.print(prefix);
		
		// get input
		return in.nextLine();
	}
	
	///
	//	WRITES TO CONSOLE
	///
	
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
	
	public static void printUser(FieldParams data) {
		System.out.println("======================= User Information =====================");
		printUserData(data);
		printUserInfoData(data);
		printAccountData(data);
		System.out.println("===============================================================");
	}
	
	public static void printUserData(FieldParams data) {
		String username = data.get(User.USERNAME);
		System.out.println("\tUser name: " + username);
		System.out.println("\tPassword: **************");
	}
	
	public static void printUserInfoData(FieldParams data) {
		Request request = new Request(data, "USER", "GETUSERINFO", null, null);
		FieldParams params = new FieldParams();
		Resultset res;
		
		// Set query params
		params.put(User.ID, data.get(User.ID));
		request.setQuery(params);
		
		try {
			MyBank.server.pushRequest(request);
			
			while ((res = MyBank.server.getResponse(request)) == null) {
				try {
				Thread.sleep(50);
				} catch (InterruptedException e) {
					
				}
			}
			
		} catch (RequestException e) {
			// TODO log
		}
		
		System.out.println("");
		
	}
	
	public static void printAccountData(FieldParams data) {
		System.out.println("");
		
	}
	
}
