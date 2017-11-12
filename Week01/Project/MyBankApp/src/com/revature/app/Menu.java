package com.revature.app;

import java.util.Scanner;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.account.Checking;
import com.revature.businessobject.info.account.Credit;
import com.revature.businessobject.info.user.UserInfo;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Resultset;

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
	
	public static synchronized void print(String message) {
		System.out.print(message);
	}
	
	
	public static synchronized void println(String message) {
		System.out.println(message);
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
	
	public static void printCreateUserMenu() {
		System.out.println("=====================   Welcome New User  =====================");
		System.out.println("=                                                             =");
		System.out.println("=                      Please Fill in the                     =");
		System.out.println("=                   required information below                =");
		System.out.println("=                            please.                          =");
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
		Resultset res = MyBank.getUserInfo(data);
		UserInfo info;
		
		if (res.size() == 0) {
			System.out.println("\t\tError: User has not supplied any personal");
			System.out.println("\t\t       information (Account Corrupted)!");
		}
		else {
			info = (UserInfo) res.get(0);
			System.out.println("\tEmail: " + info.getEmail());
			System.out.println("\tAddress: " + info.getAddress());
			System.out.println("\tPhonenumber: " + info.getPhonenumber());
		}
	}
	
	public static void printAccountData(FieldParams data) {
		Resultset res = MyBank.getAccount(data);
		
		if (res.size() == 0)
			System.out.println("\tNo accounts found.");
		else {
			for (BusinessObject item : res) {
				Account acct = (Account)item;
				
				switch (acct.getType()) {
					case CHECKING:
						printCheckingAccount((Checking)acct);
						break;
					case CREDIT:
						printCreditAccount((Credit)acct);
						break;
				}
				
			}
		}
	}
	
	/// 
	//	PRIVATE METHODS 
	///
	
	private static void printCheckingAccount(Checking checking) {
		System.out.println("\tAccount Type: Checking");
		System.out.println("\tAccount Number: " + checking.getNumber());
		System.out.println("\tTotal: $" + checking.getTotal());
	}
	
	private static void printCreditAccount(Credit credit) {
		System.out.println("\tAccount Type: Credit");
		System.out.println("\tAccount Number: " + credit.getNumber());
		System.out.println("\tMonthly Interest: " + credit.getInterest() + "%");
		System.out.println("\tCredit Limit: $" + credit.getCreditLimit());
		System.out.println("\tTotal: $" + credit.getTotal());
	}
}
