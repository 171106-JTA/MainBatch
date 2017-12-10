package com.revature.bankAppMain;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.revature.beans.Account;
import com.revature.beans.Administrator;
import com.revature.beans.Customer;
import com.revature.beans.User;
import com.revature.dao.AccountDaoImpl;
import com.revature.dao.AdminDaoImpl;
import com.revature.dao.CustomerDaoImpl;

/**
 * @author Matthew
 * This class runs the BankApp.  It creates instances of the BankConsole and
 * the Database. This class controls the flow of data between these classes:
 * 		Bank Console: displays data to user, and accepts user inputs
 * 		Database: holds all User and Account data. Also serializes and deserializes
 * 				  this data to allow for data to persist if the BankApp is closed
 */
public class BankDriver {
	
	private static final Logger log = Logger.getLogger("GLOBAL");

	BankConsole tui;
	Database db;
	AccountDaoImpl acctDao;
	AdminDaoImpl adminDao;
	CustomerDaoImpl custDao;
	
	User currentUser;
	Customer currentUserCustomer;
	Administrator currentUserAdmin;
	Customer newCustomerRequest;
	
	
	
	public BankDriver() {
		tui = new BankConsole();
		db = new Database();
		
		acctDao = new AccountDaoImpl();
		adminDao = new AdminDaoImpl();
		custDao = new CustomerDaoImpl();
		
	}
	
	/**
	 * This method tells the TUI to:
	 * 		1. display the start menu
	 * 		2. return the User's selection
	 * This method then passes the User's selection to executeChoice
	 */
	public void start(){
		log.info("BankDriver: In Start Menu");
		tui.printStartMenu();
		int choice = tui.readIntWithPrompt("Please make a selection: \n");
		executeChoice(choice, "atStartMenu");
	}
	
	/**
	 * This method takes the User's selection (or choice), and passes control
	 * to the appropriate method so that the User's selection can be executed
	 * @param choice
	 * This is the int entered by the User to indicated their desired selection
	 * @param menuLocation
	 * This is a String that indicates which menu the User is currently at
	 */
	public void executeChoice(int choice, String menuLocation) {
		switch (menuLocation){
		case ("atStartMenu"):
			switch (choice){
			case (1):
				createNewCustomer();
				break;
			case (2):
				login();
				break;
			case (0):
				System.out.println("Goodbye");
				break;
			default:
				System.out.print("Invalid option - returning to Start Menu\n" +
								 "=======================================\n");
				start();
				break;
			}
			break;
		
		case ("atCustomerDashboard"):
			switch (choice){
			case (2): 
				withdraw();
				break;
			case (1):
				deposit();
				break;
			case (0):
				start(); //logout -> return to start menu
				break;
			default:
				System.out.print("Invalid option\n\n");
				executeChoice(tui.printDashboard(currentUserCustomer), "atCustomerDashboard");
				break;
			}
			break;
		
		case ("atAdminDashboard"):
			switch (choice){
			case (1):
				blockUnblock("block");
				break;
			case (2):
				blockUnblock("unblock");
				break;
			case (3):
				promote();
				break;
			case (0):
				start(); //logout -> return to start menu
				break;
			default:
				System.out.print("Invalid option\n\n");
				executeChoice(tui.printDashboard(currentUserAdmin), "atAdminDashboard");
				break;
			}
			break;
		default :
		}	
	}


	/**
	 * This method instructs the TUI to print the New User Page. The TUI returns
	 * an array of Strings that has all data needed to create a new Customer.
	 * This method uses these Strings to create a new Customer. It also creates a
	 * new Account with a balance of $0.00, and associates this Account with the
	 * new Customer.
	 */
	private void createNewCustomer(){
		String[] idInfo = tui.printNewUserPage();
		String firstName = idInfo[0];
		String lastName = idInfo[1];
		String ssn = idInfo[2];
		String username = idInfo[3];
		String password = idInfo[4];
		
		if (!db.uniqueUsername(username)){
			System.out.println("Username was not unique...\nReturning to Start Menu...\n");
			start();
		} else{
			newCustomerRequest = new Customer(firstName, lastName, ssn, username, password);
			log.info("BankDriver: New Customer " + newCustomerRequest.getUsername() + " created.");
			Account acct1 = db.createAccount(0.0);
			System.out.println(acct1);
////////////////////			
			acctDao.createAcct(acct1);
			log.info("BankDriver: New Account " + acct1.getAcctNumber() + " created.");
			newCustomerRequest.setAccount(acct1);
			custDao.createCust(newCustomerRequest);
			log.info("BankDriver: " + newCustomerRequest.getUsername() + " opened Account " + acct1.getAcctNumber() + ".");
			db.customerList.add(newCustomerRequest);
			db.accountList.add(acct1);

			System.out.println("---------------------------\n " +
								"Request Submitted. You will NOT be able to login until \n" +
								"an Admin unblocks your account...\n" +
								"--------------------------- \n" +
								"Returning to Start Menu...\n" +
								"--------------------------- \n");
			start(); //return to the welcome page
		}
	}

	
	
	/**
	 * This method instructs the TUI to print Login Page. The TUI returns
	 * 	an array of Strings that has the User's username and password.
	 * This method then verifies these are valid username and password.
	 * If Verification fails, then this method returns the User to the 
	 * 	Start Menu
	 * If Verification succeeds, then this method passes control the User
	 * Dashboard - the Admin dashboard for Administrators, and the Customer
	 * 	dashboad for Customers.
	 */	
	private void login(){
		String[] loginInfo = tui.printLoginPage();
		String username = loginInfo[0];
		String password = loginInfo[1];
		Customer custLoggingIn;
		Administrator adminLoggingIn;
		boolean isAdmin = false;
		boolean successfulLogin = false;
		
		for (int i = 0; i < db.customerList.size(); i++){
			if(db.customerList.get(i).getUsername().equals(username)){
				custLoggingIn = db.customerList.get(i);
				isAdmin  = false;
				if(custLoggingIn.isBlocked()){
					log.info("BankDriver: Block User attempted to login");
					System.out.println("\n Account BLOCKED" + 
										"\nPlease request access from an Administrator");
				} else if(custLoggingIn.getPassword().equals(password)){
					successfulLogin = true;
					log.info("BankDriver: Customer logged in");
					System.out.println("\n\nSucessful Login!\n\n");
					currentUserCustomer = custLoggingIn;
				}
			}
		}
		if (!successfulLogin){
			for (int i = 0; i < db.adminList.size(); i++){
				if(db.adminList.get(i).getUsername().equals(username)){
					adminLoggingIn = db.adminList.get(i);
					isAdmin  = true;
					if(adminLoggingIn.getPassword().equals(password)){
						successfulLogin = true;
						log.info("BankDriver: Admin " + currentUserAdmin + " logged in");
						System.out.println("Successful Login\n\n");
						currentUserAdmin = adminLoggingIn;
					}
				}
			}
		}
		
		if(!successfulLogin){
			System.out.println("Login Failed... Returning to Start Menu\n" +
							   "=======================================" +
								"\n");
			start();
		} else{
			if (isAdmin){
				executeChoice(tui.printDashboard(currentUserAdmin), "atAdminDashboard");
			} else {
				executeChoice(tui.printDashboard(currentUserCustomer), "atCustomerDashboard");
			}
		}
	}
	
	
	/**
	 * This method instructs the TUI to prompt the user to enter the amount of
	 *   money they would like to withdraw.
	 * This method is implemented to prevent the user from ever withdrawing more 
	 *   money then they current have in their balance.
	 * This method then the Database to update the records to reflect the changes
	 */
	public void withdraw(){
		double amt = tui.readDoubleWithPrompt("\nHow much would you like to withdraw?\n");
		double currentBalance = currentUserCustomer.getAccount().getBalance();
		if(currentUserCustomer.getAccount().withdraw(amt).equals("acctBalanceZero")){
			currentUserCustomer.getAccount().setBalance(currentBalance);
			System.out.println("\nYou are attempting to withdraw from an account that has a zero Balance.\n");
			log.info("BankDriver: " + currentUserCustomer.getUsername() + " tried to withdraw from a Zero Balance Account");
			executeChoice(tui.printDashboard(currentUserCustomer), "atCustomerDashboard");
		} else if(currentUserCustomer.getAccount().withdraw(amt).equals("amt2High")){
			currentUserCustomer.getAccount().setBalance(currentBalance);
			System.out.println("\nYou are attempting to withdraw an amount greater than your balance...\n");
			log.info("BankDriver: " + currentUserCustomer.getUsername() + " attempted to withdraw more than their account balance.");
			executeChoice(tui.printDashboard(currentUserCustomer), "atCustomerDashboard");
		} else if(currentUserCustomer.getAccount().withdraw(amt).equals("negAmt")){
			currentUserCustomer.getAccount().setBalance(currentBalance);
			System.out.println("\nYou are attempting to withdraw a negative amount.\n");
			log.info("BankDriver: " + currentUserCustomer.getUsername() + " attempted to withdraw a negative amount.");
			executeChoice(tui.printDashboard(currentUserCustomer), "atCustomerDashboard");
		} else{
			currentUserCustomer.getAccount().setBalance(currentBalance);
			acctDao.updateAcctBalance(currentUserCustomer.getAccount().getAcctNumber(), currentBalance-amt);
			currentUserCustomer.getAccount().withdraw(amt);
			System.out.println("\n You successfully withdrew $"+amt+"\nReturning to your Dashboard\n");
			log.info("BankDriver: " + currentUserCustomer.getUsername() + " withdrew $" + amt +
					" and now has a balance of $" + currentUserCustomer.getAccount().getBalance());
			executeChoice(tui.printDashboard(currentUserCustomer), "atCustomerDashboard");
		}
	}
	
	
	/**
	 * This method instructs the TUI to prompt the user to enter the amount of
	 *   money they would like to deposit.
	 * This method then the Database to update the records to reflect the changes
	 */	
	public void deposit(){
		double amt = tui.readDoubleWithPrompt("\nHow much would you like to deposit?\n");
		double currentBalance = currentUserCustomer.getAccount().getBalance();
		if(currentUserCustomer.getAccount().deposit(amt).equals("negAmt")){
			currentUserCustomer.getAccount().setBalance(currentBalance);
			System.out.println("\nYou are attempting to withdraw a negative amount.\n");
			log.info("BankDriver: " + currentUserCustomer.getUsername() + " attempted to withdraw a negative amount.");
			executeChoice(tui.printDashboard(currentUserCustomer), "atCustomerDashboard");
		} else{
			currentUserCustomer.getAccount().setBalance(currentBalance);
			acctDao.updateAcctBalance(currentUserCustomer.getAccount().getAcctNumber(), currentBalance+amt);
			currentUserCustomer.getAccount().deposit(amt);
			log.info("BankDriver: Customer " + currentUserCustomer.getUsername() + " deposited $" + amt +
					" and now has a balance of $" + currentUserCustomer.getAccount().getBalance());
			System.out.println("\n You successfully deposited $"+amt+"\nReturning to your Dashboard\n");
			executeChoice(tui.printDashboard(currentUserCustomer), "atCustomerDashboard");
		}		
	}
	
	
	/**
	 * This method is called if an Administrator selects "Block or Unblock a
	 * 	a Customer" at the Admin Dashboard
	 * It instructs the TUI to prompt the Admin User to select a Customer to 
	 *  Block or Unblock
	 * @param blockOrUnblock
	 * This is a String that indicates whether the selected Customer should
	 *  be Blocked or Unblocked
	 */
	public void blockUnblock(String blockOrUnblock){
		System.out.println("\n\nPlease select Customer to " + blockOrUnblock);
		int index = tui.printCustList(db.customerList);
		Customer cust = db.customerList.get(index);
		
		switch (blockOrUnblock){
		case ("block"):
			if (cust.isBlocked()){
				log.info("BankDriver: Admin " + currentUserAdmin + " attempted to block a blocked Customer");
				System.out.println("\nThis Customer is already blocked... \n" +
									"Returning to Dashboard\n" +
									"=======================================\n");
			} else {
				custDao.blockCust(cust);
				cust.setBlocked(true);
				db.customerList.set(index, cust);
				log.info("BankDriver: Admin " + currentUserAdmin + " blocked Customer " + cust.getUsername());
				System.out.println("\nThis Customer is now Blocked. \n" +
						"Returning to Dashboard\n" +
						"=======================================\n");
			}
			executeChoice(tui.printDashboard(currentUserAdmin), "atAdminDashboard");
			break;
		case ("unblock"):
			if (!cust.isBlocked()){
				log.info("BankDriver: Admin " + currentUserAdmin + " attempted to unblock an unblocked Customer");
				System.out.println("\nThis Customer is already unblocked... \n" +
									"Returning to Dashboard\n" +
									"=======================================\n");
			} else {
				custDao.unblockCust(cust);
				cust.setBlocked(false);
				db.customerList.set(index, cust);
				log.info("BankDriver: Admin " + currentUserAdmin + " unblocked Customer " + cust.getUsername());
				System.out.println("\nThis Customer is now Unblocked. \n" +
						"Returning to Dashboard\n" +
						"=======================================\n");
			}
			executeChoice(tui.printDashboard(currentUserAdmin), "atAdminDashboard");
			break;
		}
	}
	
	/*
	 * This method is called if an Administrator selects "Promote Customer to Admin"
	 * 	at the Admin Dashboard
	 * It instructs the TUI to prompt the Admin User to select a Customer to promote
	 */
	public void promote(){
		System.out.println("\n\nPlease select Customer to promote to Administrator");
		int index = tui.printCustList(db.customerList);
		Customer cust = db.customerList.get(index);
		
		//System.out.println("In BankDriver " + cust.getAccount().getBalance());
		int result = tui.printPromotePage(cust);	
		if(result == 1){
			Administrator admin = new Administrator(cust.getUsername(), cust.getPassword());
			adminDao.createAdmin(admin);
			db.adminList.add(admin);
			custDao.deleteCust(db.customerList.get(index));
			db.customerList.remove(index);
			log.info("Admin: " + currentUserAdmin + " promoted Customer " + cust + "to admin.");
		}
		executeChoice(tui.printDashboard(currentUserAdmin), "atAdminDashboard");
	}

}
