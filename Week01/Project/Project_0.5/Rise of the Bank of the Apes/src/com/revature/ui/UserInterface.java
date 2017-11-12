package com.revature.ui;

import java.util.HashMap;
import java.util.Scanner;

import com.revature.apebank.BankOfTheApes;
import com.revature.data.Logging;
import com.revature.data.ProcessData;
import com.revature.users.User;

public class UserInterface {
	private static Scanner scan;
	private static Logging log;
	
	public UserInterface() {
		
	}
	
	/**
	 * For reading string inputs. 
	 * 
	 * @return Inputed user string
	 */
	protected static String readInput() {
		String result;
		
		scan = new Scanner(System.in);
		result = scan.nextLine();
		
		return result;
	}
	
	/**
	 * For reading integer inputs. Checks to make sure that options requiring integers only
	 * receive integer inputs
	 * 
	 * @return the user's input; if the input is not an integer, -1 is returned
	 */
	protected static int readNumberInput() {
		int i = -1;
		
		scan = new Scanner(System.in);
		
		try {
			i = Integer.parseInt(scan.nextLine());
		}catch (NumberFormatException ne){
			
		}
		return i;
	}
	
	/**
	 * Closes the scanner and serializes the user HashMap. Used when the user logs out
	 * 
	 * @param users HashMap to be serialized
	 */
	protected static void cleanUp(HashMap<String, User> users) {
		scan.close();
		ProcessData.serialize(users, BankOfTheApes.fileName);
	}	
	
	/**
	 * Login screen for users. It checks for matching usernames and passwords
	 * before opening an account. It also checks the status of the account before opening.
	 * If the account is banned or not approved, the user cannot access their account.
	 * 
	 * @param users HashMap of users to check for correct usernames and passwords
	 * 
	 * @return User object to be loaded into other screens
	 */
	public static User loginScreen(HashMap<String, User> users) {
		String user;
		User u;
		String password;
		String savedPassword;
		
		System.out.print("Username: ");
		user = UserInterface.readInput();
		if(users.containsKey(user)) {
			System.out.print("Password: ");
			password = UserInterface.readInput();
			u = users.get(user);
			savedPassword = u.getPassword();
			if(password.equals(savedPassword)) {
				if(!u.isBanned() && u.isApproved()) {
					log = Logging.getLogging();
					return u;
				}
				else {
					System.out.println("Contact Administrators. ");
					if(!u.isApproved() && !u.isBanned()) {
						System.out.println("Account pending approval.");
					}
					else if(u.isBanned()) {
						System.out.println("Account has been banned.");
					}
					return null;
				}
			}
		}
		System.out.println("Sorry. Incorrect login credentials.");
		return null;
	}
	
	protected static void startLogging(String message) {
		log.startLogging(message);
	}
}
