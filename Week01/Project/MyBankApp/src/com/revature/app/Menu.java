package com.revature.app;

import java.util.Scanner;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.account.AccountType;
import com.revature.businessobject.info.account.Checking;
import com.revature.businessobject.info.account.Credit;
import com.revature.businessobject.info.user.UserInfo;
import com.revature.businessobject.user.Checkpoint;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Resultset;

public class Menu {
	private static Scanner in = new Scanner(System.in);
	
	public static String getInput(String prefix) {
		// Print content before input
		System.out.print(prefix);
		
		// get input
		return in.nextLine().trim();
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
	
	public static void printCreateUserMenu() {
		println("=====================   Welcome New User  =====================");
		println("=                                                             =");
		println("=                      Please Fill in the                     =");
		println("=                   required information below                =");
		println("=                            please.                          =");
		println("=                                                             =");
		println("===============================================================");
	}
	
	public static void printViewMenu() {
		println("===============================================================");
		println("= To leave application, type \'logout\'                        =");
		println("===============================================================");
	}
	
	public static void printUser(FieldParams data) {
		println("======================= User Information =====================");
		printUserData(data);
		printUserInfoData(data);
		printAccountData(data);
		println("===============================================================");
	}
	
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
	
	public static void printAccountData(FieldParams data) {
		Resultset res = MyBank.getAccount(data);
		
		if (res.size() == 0)
			println("\tNo accounts found.");
		else {
			for (BusinessObject item : res) {
				Account acct = (Account)item;
				
				switch (acct.getType()) {
					case AccountType.CHECKING:
						printCheckingAccount((Checking)acct);
						break;
					case AccountType.CREDIT:
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
		println("===============================================================");
		println("\tAccount Type: Checking");
		println("\tAccount status: " + checking.getStatus().toUpperCase());
		println("\tAccount Number: " + checking.getNumber());
		println("\tTotal: $" + checking.getTotal());
		println("===============================================================");
	}
	
	private static void printCreditAccount(Credit credit) {
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
