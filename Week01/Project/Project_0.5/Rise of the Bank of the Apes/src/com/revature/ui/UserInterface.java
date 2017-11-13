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
	protected static int readIntInput() {
		int i = -1;
		
		scan = new Scanner(System.in);
		
		//Try-catch for user input of letters
		try {
			i = Integer.parseInt(scan.nextLine());
		}catch (NumberFormatException ne){
			
		}
		return i;
	}
	
	/**
	 * Read user input for currency inputs. A double is used as the storage datatype.
	 * 
	 * @return -1.0 for invalid inputs (i.e. letters), otherwise it is the user input
	 * it also returns -1.0 when too many digits after the decimal place is added
	 */
	protected static double readDouble() {
		double d = -1.0;
		
		scan = new Scanner(System.in);
		String val = null;
		String afterDecimal = null;
		
		//Try-catch for user input of letters
		try {
			val = scan.nextLine();
			d = Double.valueOf(val);
		}catch(NumberFormatException ne) {
			
		}
		
		//try-catch for inputs with decimals
		try {
			afterDecimal = val.split("\\.")[1];
			if(afterDecimal.length() > 2) {
				return -1;
			}
		}catch(IndexOutOfBoundsException e) {
			//may throw an index out of bound exception when user inputs value with decimal pts
		}
		return d;
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
		
		//Asks for username
		System.out.print("Username: ");
		user = UserInterface.readInput();
		//Checks for username in "database"
		if(users.containsKey(user)) {
			//Asks for password
			System.out.print("Password: ");
			password = UserInterface.readInput();
			u = users.get(user);
			savedPassword = u.getPassword();
			//Checks for password correctness
			if(password.equals(savedPassword)) {
				//Checks user for approval/locked status
				if(!u.isBanned() && u.isApproved()) {
					log = Logging.getLogging();
					return u;
				}
				else {
					System.out.println("Contact Administrators. ");
					//Approval status issue
					if(!u.isApproved() && !u.isBanned()) {
						System.out.println("Account pending approval.");
					}
					//Locked status issue
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
	
	/**
	 * Begins the logging process
	 * 
	 * @param message String to be logged
	 */
	protected static void startLogging(String message) {
		//Needed to accommodate Junit
		if(log == null) {
			log = Logging.getLogging();
		}
		log.start(message);
	}
}