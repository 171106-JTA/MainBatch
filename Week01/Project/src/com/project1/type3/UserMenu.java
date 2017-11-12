package com.project1.type3;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class UserMenu {
	
	final static Logger logger = Logger.getLogger("UserMenu.class");
	
	/**
	 * This method will generate a menu for an account logging in as an basic user
	 * It only has the options to make deposits, withdrawals, loan requests and payments.
	 * I designed this menu to be for a customers needs. 
	 * @param user
	 */
	public void userAccount(User user){
		System.out.println("Welcome " + user.getUsername());
		boolean go = true;
		String s;
		Scanner scan = new Scanner(System.in);
		logger.trace("USER: " + user.getUsername() + " HAS LOGGED IN");
		while(go){
			System.out.println("Deposit: " + user.getDeposit());
			System.out.println("Amount Due: " + user.getDebt());
			if(user.isLoanPending())System.out.println("Loan request is pending approval");
			if(user.isAdmin())System.out.println("THIS USER CAN LOGIN AS AN ADMINSTRATOR");
			System.out.println("--------");
			System.out.println("0: Logout");
			System.out.println("1: Make a deposit");
			System.out.println("2: Make a withdrawal");
			System.out.println("3: Apply for a loan");
			System.out.println("4: Make loan payment");
			s = scan.nextLine();
			
			switch(s){
			case "0":
				go = false;
				logger.info("USER: " + user.getUsername() + " LOGGED OUT");
				break;
				
			case "1":
				user.makeDeposit();
				break;
				
			case "2":
				user.makeWithdrawal();
				break;
				
			case "3":
				user.takeLoan();
				break;
				
			case "4":
				user.makePayment();
				break;
				
			}
		}
		
	}
	
	/**
	 * This method will generate the menu for an account signing in as an admin. As you
	 * can see they have different effects on the user accounts. This menu allows the admin 
	 * to approve/deny loans and approve/lock/unlock/promote accounts. To determine if the account
	 * signing in has admin access will be determined when calling this method in the main method in 
	 * the MainMenu class.   
	 * @param users
	 * @param name
	 */
	public void adminAccount(ArrayList<User> users, String name){
		System.out.println("Welcome " + name);
		boolean go = true;
		String s;
		Scanner scan = new Scanner(System.in);
		logger.trace("ADMIN: " + name + " HAS LOGGED IN");
		while(go){
			
			System.out.println("--------");
			System.out.println("0: Logout");
			System.out.println("1: Approve UserAccount");
			System.out.println("2: Approve Loan");
			System.out.println("3: Deny Loan");
			System.out.println("4: Lock User Account");
			System.out.println("5: Unlock User Account");
			System.out.println("6: Promote Account");
			s = scan.nextLine();
			
			switch(s){
			case "0":
				go = false;
				logger.info("ADMIN: " + name + " LOGGED OUT");
				break;
				
			case "1":
				//user.makeDeposit();
				showPendingAccounts(users);
				approveAccount(users, name);
				break;
				
			case "2":
				//user.makeWithdrawal();
				showLoansPending(users);
				approveLoan(users, name);
				break;
				
			case "3":
				//user.takeLoan();
				showLoansPending(users);
				denyLoan(users, name);
				break;
				
			case "4":
				showUnlockedAccounts(users);
				lockAccount(users, name);
				break;
				
			case "5":
				showLockedAccounts(users);
				unlockAccount(users, name);
				break;
				
			case "6":
				showAdminAccounts(users);
				promoteAccount(users);
				logger.warn("ADMIN: " + name + " WAS SEEKING TO PROMOTE AN ACCOUNT");
				break;
			}
		}
	}
	
	/**
	 * display all accounts awaiting approval for login
	 * @param users
	 */
	public void showPendingAccounts(ArrayList<User> users){
		int count = 1;
		System.out.println("Accounts Awaiting Account Approval:");
		System.out.println("===================================");
		for(User user : users){
			if(!user.isApproved()){
				System.out.println(count + ") " + user.getUsername());
				count++;
			}
		}
		System.out.println("===================================");
	}
	
	/**
	 * display all lockedaccounts
	 * @param users
	 */
	public void showLockedAccounts(ArrayList<User> users){
		int count = 1;
		System.out.println("Locked Accounts:");
		System.out.println("================");
		for(User user : users){
			if(user.isLocked() && user.isApproved()){
				System.out.println(count + ") " + user.getUsername());
				count++;
			}
		}
		System.out.println("===================================");
	}
	
	/**
	 * display all unlocked accounts
	 * @param users
	 */
	public void showUnlockedAccounts(ArrayList<User> users){
		int count = 1;
		System.out.println("Unlocked Accounts:");
		System.out.println("==================");
		for(User user : users){
			if(user.isApproved() && !user.isLocked()){
				System.out.println(count + ") " + user.getUsername());
				count++;
			}
		}
		System.out.println("===================================");
	}
	
	/**
	 * display all accounts awaiting approval for loan
	 * @param users
	 */
	public void showLoansPending(ArrayList<User> users){
		int count = 1;
		System.out.println("Accounts Awaiting Loan Approval:");
		System.out.println("================================");
		for(User user : users){
			if(user.isApproved() && user.isLoanPending()){
				System.out.println(count + ") " + user.getUsername() + " for $" + user.getLoan());
				count++;
			}
		}
		System.out.println("===================================");
	}
	
	/**
	 * display all accounts displays their status(admin or seeking approval)
	 * this method will be used for admin to choose which account to promote for admin status
	 * @param users
	 */
	public void showAdminAccounts(ArrayList<User> users){
		int count = 1;
		System.out.println("Showing All Accounts:");
		System.out.println("=====================");
		for(User user : users){
			if(user.isAdmin()){
				System.out.println(count + ") " + user.getUsername() + " is an Admin");
				count++;
			}
			
			else if(!user.isApproved()){
				System.out.println(count + ") " + user.getUsername() + " is awaiting account approval");
				count++;
			}
			
			else{
				System.out.println(count + ") " + user.getUsername());
				count++;
			}
		}
		System.out.println("===================================");
	}
	
	/**
	 * Allows admin to search for user and approve them for login
	 * @param users
	 * @param name
	 */
	public void approveAccount(ArrayList<User> users, String name){
		String username, s;
		Scanner scan  = new Scanner(System.in);
		System.out.println("0: Exit");
		System.out.println("1: Approve One Account");
		System.out.println("2: Approve All Accounts");
		s = scan.nextLine();
		
		switch(s){
		case "0":
			break;
			
		case "1":
			System.out.println("Which account would you like to approve?");
			username = scan.nextLine();
			for(User user : users)
				if(user.getUsername().equals(username) && !username.equals(name)){
					user.makeApproved();
					logger.debug("ADMIN: " + name + " APPROVED USER: " + username);
				}
			break;
			
		case "2":
			for(User user : users)
				if(!user.isApproved()){
					user.makeApproved();
					logger.debug("ADMIN: " + name + " APPROVED ALL PENDING USERS");
				}
			break;
		}
		
		
	}
	
	/**
	 * Admin can search for user to lock account. Note that admin
	 * cannot lock himself out
	 * @param users
	 * @param name
	 */
	public void lockAccount(ArrayList<User> users, String name){
		String username;
		Scanner scan  = new Scanner(System.in);
		
		
		System.out.println("Which account do you want to lock?");
		username = scan.nextLine();
		for(User user : users)
			if(user.getUsername().equals(username) && !username.equals(name)){//admin cannot lock himself out
				user.lockAccount();
				logger.warn("ADMIN: " + name + " LOCKED USER: " + username);
			}
		
	}
	
	/**
	 * Admin can search for user to unlock account. 
	 * @param users
	 * @param name
	 */
	public void unlockAccount(ArrayList<User> users, String name){
		String username;
		Scanner scan  = new Scanner(System.in);
		
		System.out.println("Which account do you want to unlock?");
		username = scan.nextLine();
		for(User user : users)
			if(user.getUsername().equals(username) && !username.equals(name)){
				user.unlockAccount();
				logger.warn("ADMIN: " + name + " UNLOCKED USER: " + username);
			}
			
	}
	
	/**
	 * Admin can search for user to grant loan. Note that admin
	 * cannot approve himself for loan, He will need another admin to 
	 * approve it for him
	 * @param users
	 * @param name
	 */
	public void approveLoan(ArrayList<User> users, String name){
		String username;
		Scanner scan  = new Scanner(System.in);
				
		System.out.println("Which account's loan request do you want to approve?");
		username = scan.nextLine();
		for(User user : users)
			if(user.getUsername().equals(username) && !username.equals(name)){//admin cannot approve himself for loan
				user.loanApproved();
				logger.warn("ADMIN: " + name + " APPROVED USER: " + username + " FOR LOAN REQUEST");
			}
		
	}
	
	/**
	 * Admin can search for user to deny a loan. Note that admin
	 * cannot deny himself a loan, will need another admin to deny for him
	 * @param users
	 * @param name
	 */
	public void denyLoan(ArrayList<User> users, String name){
		String username;
		Scanner scan  = new Scanner(System.in);
		
		System.out.println("Which account's loan request do you want to deny?");
		username = scan.nextLine();
		for(User user : users)
			if(user.getUsername().equals(username) && !username.equals(name)){
				user.loanDenied();
				logger.debug("ADMIN: " + name + " DENIED USER: " + username + " FOR LOAN REQUEST");
			}
		
	}
	
	/**
	 * Admin searches for account to grant admin access.
	 * @param users
	 */
	public void promoteAccount(ArrayList<User> users){
		String username;
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Which account would you like to promote?");
		username = scan.nextLine();
		for(User user : users)
			if(user.getUsername().equals(username))// && !username.equals(name))
				user.makeAdmin();
	}

}
