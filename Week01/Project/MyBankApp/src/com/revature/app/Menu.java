package com.revature.app;

import java.util.Scanner;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.account.Type;
import com.revature.businessobject.info.account.Checking;
import com.revature.businessobject.info.account.Credit;
import com.revature.businessobject.info.user.UserInfo;
import com.revature.businessobject.user.Checkpoint;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Resultset;

/**
 * Assortment of content which can be written to the console
 * @author Antony Lulciuc
 *
 */
public class Menu {
	private static Scanner in = new Scanner(System.in);
	
	/**
	 * Acquire input from user
	 * @param prefix - note before input
	 * @return user input
	 */
	public static String getInput(String prefix) {
		// Print content before input
		System.out.print(prefix);
		
		// get input
		return in.nextLine().trim();
	}

	///
	//	WRITES TO CONSOLE
	///
	
	/**
	 * Prints message to console
	 * @param message
	 */
	public static synchronized void print(String message) {
		System.out.print(message);
	}
	
	/**
	 * Prints message to console and newline
	 * @param message
	 */
	public static synchronized void println(String message) {
		System.out.println(message);
	}
	
	/**
	 * Prints message main menu
	 */
	public static void printMenu() {
		println("===============================================================");
		println("====================== WELCOME TO MYBANK ======================");
		println("===============================================================");
		println("= Options, type:                                              =");
		println("=                                                             =");
		println("=   - new - to create new account                             =");
		println("=                                                             =");
		println("=   - login - signing for existing users                      =");
		println("=                                                             =");
		println("=   - exit - to quit the application                          =");
		println("=                                                             =");
		println("===============================================================");
	}
	
	/**
	 * Prints create user menu
	 */
	public static void printCreateUserMenu() {
		println("=====================   Welcome New User  =====================");
		println("=                                                             =");
		println("=                      Please Fill in the                     =");
		println("=                   required information below                =");
		println("=                            please.                          =");
		println("=                                                             =");
		println("===============================================================");
	}
	
	/**
	 * Prints active user menu logout header notification
	 */
	public static void printViewMenu() {
		println("===============================================================");
		println("= To leave application, type \'logout\'                        =");
		println("===============================================================");
	}
	
	/**
	 * Prints all user data 
	 * @param data - login info
	 */
	public static void printUser(FieldParams data) {
		println("======================= User Information =====================");
		printUserData(data);
		printUserInfoData(data);
		printAccountData(data);
		println("===============================================================");
	}
	
	/**
	 * Prints basic user data
	 * @param data - login info
	 */
	public static void printUserData(FieldParams data) {
		String username = data.get(User.USERNAME);
		println("\tUser name: " + username);
		println("\tPassword: **************");
		print("\tStatus: ");
		
		switch (data.get(User.CHECKPOINT)) {
			case Checkpoint.ADMIN:
			case Checkpoint.CUSTOMER:
				println("ACTIVE");
				break;
			case Checkpoint.PENDING:
				println("PENDING");
				break;
			default:
				println("UNKNOWN");
		}
		
	}
	
	/**
	 * Prints user info (contact data)
	 * @param data - login info
	 */
	public static void printUserInfoData(FieldParams data) {
		Resultset res = MyBank.getUserInfo(data);
		UserInfo info;
		
		if (res.size() == 0) {
			println("\t\tError: User has not supplied any personal");
			println("\t\t       information (Account Corrupted)!");
		}
		else {
			info = (UserInfo) res.get(0);
			println("\tEmail: " + info.getEmail());
			println("\tAddress: " + info.getAddress());
			println("\tPhonenumber: " + info.getPhonenumber());
		}
	}
	
	/**
	 * Prints user accounts 
	 * @param data - login data
	 */
	public static void printAccountData(FieldParams data) {
		Resultset res = MyBank.getAccount(data);
		
		if (res.size() == 0)
			println("\tNo accounts found.");
		else {
			for (BusinessObject item : res) {
				Account acct = (Account)item;
				
				switch (acct.getType()) {
					case Type.CHECKING:
						printCheckingAccount((Checking)acct);
						break;
					case Type.CREDIT:
						printCreditAccount((Credit)acct);
						break;
				}
				
			}
		}
	}
	
	/**
	 * Prints checking account
	 * @param checking - checking account instance 
	 */
	public static void printCheckingAccount(Checking checking) {
		println("===============================================================");
		println("\tAccount Type: Checking");
		println("\tAccount status: " + checking.getStatus().toUpperCase());
		println("\tAccount Number: " + checking.getNumber());
		println("\tTotal: $" + checking.getTotal());
		println("===============================================================");
	}
	
	/**
	 * Prints credit account
	 * @param credit - credit account instance 
	 */
	public static void printCreditAccount(Credit credit) {
		println("===============================================================");
		println("\tAccount Type: Credit");
		println("\tAccount status: " + credit.getStatus().toUpperCase());
		println("\tAccount Number: " + credit.getNumber());
		println("\tMonthly Interest: " + credit.getInterest() + "%");
		println("\tCredit Limit: $" + credit.getCreditLimit());
		println("\tTotal: $" + credit.getTotal());
		println("===============================================================");
	}
}
