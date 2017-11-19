package com.revature.project1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.log.LogUtil;

public class Driver {
	/**
	 * Defines static variables used throughout program,
	 * e.g. input scanner, User object array list
	 */
	public static List<User> userList 	= new ArrayList<User>(); //static? w/in main?
	public static Scanner scanner 		= new Scanner(System.in);
	static final int UNAPPROVED 			= 0;
	static final int APPROVED 			= 1;
	static final int BLOCKED 			= 2;
	static final int DELETE	 			= 3;
	static Cereal cereal = new Cereal();

	/**
	 * Starts program by loading populated arraylist if it exists and prompting
	 * login or account creation. Directs users to basic functionality upon
	 * successful login; prevents them from acessing their account if unapproved by
	 * admin or blocked; prevents duplicate usernames.
	 * 
	 * @param args
	 * @throws Exception
	 *             generated when .ser file doesn't exist.
	 */  
	public static void main(String[] args) throws Exception { 			
		
		boolean running = true;
		User OG = new User("mnlwsn", "master", 1000, true, 1); 
		userList.add(OG);									
		
		
		try {
			userList = cereal.deserialize("accounts.ser"); 
		} catch (Exception e) {				   			  
			LogUtil.log.warn(".ser file doesn't exist", e);
		}
	
		System.out.println("Welcome to the National Bank of Second Chances; this is your third~!");
		while (running) {
			System.out.println("\n> To login, type your username: \n> To make an account, type 'create': \n> To exit the program, type 'quit':");
			String input = scanner.next(); 
	
			switch(input) {
			default:
				User u = User.returnExisting(input); 
					
				int attempts = 2; 
				boolean loginSuccess = true;
				boolean active = (u.getStatusFlag() == 1);
				
				if (active) {										//while account is active, allow login attempt
					System.out.println("\nPlease input password:"); 	//3 attempts 
					
					while (!scanner.next().equals(u.getPswrd())) {	
						System.out.println("\nIncorrect password; you have " + attempts + ((--attempts != 0) ? " attempts remaining." : " attempt remaining."));
						if(!(attempts == -1)) System.out.println("Please try again:");
						if (attempts == -1) {
							System.out.println("Too many wrong attempts, account is now locked. Please try again later.");	
							u.setStatusFlag(BLOCKED);
							loginSuccess = false;
							continue;
						}
					}
					
					if (loginSuccess) {
						Account.displayBalance(u); 		
						continue;									//logout returns here
					}
				} else {
					System.out.println("\nAccount is not yet active or does not exist. Please wait for admin activation.");  
					continue;
				}
				
			//––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
			
			case "create":
				User newUser = new User();
				System.out.println("\nPlease choose a username:");
				input = scanner.next();
				while (!isAvailable(input)) {
					System.out.println("\nUsername is already taken; please choose another:");
					input = scanner.next();
				}
				newUser.setUserID(input);
				System.out.println("\nPlease input a password to finish account creation:");
				input = scanner.next();
				while (!standardMet(input)) {
					System.out.println("\nPasswords must be at least 5 characters long. Try again:");
					input = scanner.next();
				}
				newUser.setPswrd(input);
				userList.add(newUser);
				System.out.println("\nThanks for creating a new account. Your account is under review and will be approved shortly. Attempt logging in then."); 
				continue;
			
			//––––––––––––––––––––––––––––––––––––––––––––––––––––––––––––
				
			case "quit":
				break;
			}
			
			running = false;
		}
		
		quit();
	}
	

	/**
	 * Preserves userlist and closes resource to prevent memory leakage.
	 */
	private static void quit() {
		cereal.serialize(userList);	
		scanner.close(); 				
	}

	/**
	 * Checks userid availability/validity
	 * @param input from console
	 * @return true if available
	 */
	private static boolean isAvailable(String input) {
		while (!standardMet(input)) {
			System.out.println("\nUsername must be at least 5 characters.");
			input = scanner.next();	//check for spaces?
		}
		if (User.returnExisting(input).getUserID().equals(" ")) return true; 	//if return existing returns empty id, means user dne previously
		else return false;													//may allow users w same 5 letters but diff cases
	}		
	
	/**
	 * Checks password validity
	 * @param input from console
	 * @return true if input >= 5 characters 
	 */
	private static boolean standardMet(String input) {
		if (input.length() < 5) return false;
		return true;
	}
}
