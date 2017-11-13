/**
 * Class containing the project main
 * 
 * @Instructor Ryan Lessley
 * @author Mahamadou
 * @Version 1.0
 * @Date 11/13/2017
 * 
 */
package com.revature.implementations;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.revature.display.MyDisplays;
import com.revature.objects.*;

public class Banking {
	
	private static Scanner reader = new Scanner(System.in); //Reading from System.in

	//1. Load the customers list into an ArrayList
	ArrayList<Customer> baseCustomers = new MyDisplays<Customer>().readObject("customers.ser");
	//2. Load the accounts list into an ArrayList
	ArrayList<Account> baseAccounts = new MyDisplays<Account>().readObject("accounts.ser");
	//2. Load the transactions list into an ArrayList
	ArrayList<Transaction> baseTransactions = new MyDisplays<Transaction>().readObject("transactions.ser");

	public static void main(String[] args) {
		
		//3. Prompt the user to identify himself or create an account
		int menuChoice = 0;
		Customer connectedCustomer = null;		
		String response = null, wantToQuit = "N";
		String acctNumber;
		double transactAmount;
		
		do {
			//Loop until the user types in a valid response
			System.out.println("New user (Y/N)?");
			response = reader.nextLine().toUpperCase();

			switch (response) {
			case "Y":
				//Customer responded Yes, so he can create a customer account
				if (new Banking().newCustomer())
					System.out.println("New customer created!");
				else
					response = null;
				break;
			case "N":
				/*Since the customer is a returning user, he won't have to create 
				 *an account but log in by providing a username and password.
				 *if success, the customer data are loaded into a session variable.
				 */
				if ((connectedCustomer = new Banking().login()) == null) {
					System.out.println("Want to quit?");
					wantToQuit = ((reader.nextLine().toUpperCase() == "Y") ? "Y" : "N");}
				break;
			default:
				response = null;
				break;
		}	}
		while(response == null && wantToQuit == "N");		
		/*4.Display the customer's accounts information.
		 * 	Call the displayUserMenu to display the menu
		 */
		if (connectedCustomer != null) {
			connectedCustomer.toString();
			for(Account acct : new Banking().baseAccounts) {
				if (acct.getCustomerID() == connectedCustomer.getId()) {
					System.out.println(acct.toString());
					for(Transaction transact : new Banking().baseTransactions)
						if (transact.getAccountNber().equals(acct.getAccountNber())){
							System.out.println(transact.toString());
				}	} 	}
				
			menuChoice = new Banking().displayUserMenu(connectedCustomer.getRoleID());
			System.out.println("You chose " + menuChoice);
			do {
				switch(menuChoice) {
				case 1:
					//Activate Accounts
					if (connectedCustomer.getRoleID() == 1)
						System.out.println("You don't have this permission!");
					else {
						System.out.println("Account number to activate: ");
						String account = reader.nextLine();
						for(Account acct : new Banking().baseAccounts) {
							if (acct.getAccountNber().equals(account))
								new Banking().activateAccounts(acct);
					}	}
					break;
				case 2:
					//Withdraw
					System.out.println("Account number: ");
					acctNumber = reader.nextLine();
					System.out.println("Transaction amount: ");
					transactAmount = reader.nextDouble();
					
					new Banking().withdraw(acctNumber, transactAmount);
					break;
				case 3:
					//Deposit
					System.out.println("Account number: ");
					acctNumber = reader.nextLine();
					System.out.println("Transaction amount: ");
					transactAmount = reader.nextDouble();
					
					new Banking().deposit(acctNumber, transactAmount);
					break;
				case 4:
					//Promote
					if (connectedCustomer.getRoleID() == 1)
						System.out.println("You don't have this permission!");
					else {
					System.out.println("Account to promote: ");
					String account = reader.nextLine();
					for(Account acct : new Banking().baseAccounts) {
						if (acct.getAccountNber().equals(account))
							new Banking().promoteUser(acct);}
					}
					break;
				default:
					menuChoice = 0;
					break;
				}
			}
			while (menuChoice == 0);
		}
				
		//Close the scanner
		if (reader != null)
			reader.close();
	}
	
	public int displayUserMenu(int roleID) {
	/*Method to display a menu using the user role permissions*/		
		int val = 0;
		
		switch (roleID) {
		case 0: 
			System.out.println("\nYou are an Admin \n "
					+ "1 Accounts Management\n "
					+ "2 Withdrawal\n "
					+ "3 Deposit\n "
					+ "4 Quit\n "
					+ "Choice?:");
			val = reader.nextInt();
			break;			
		case 1: 
			System.out.println("\nWelcome valued customer \n "
					+ "2 Withdrawal\n "
					+ "3 Deposit\n "
					+ "4 Quit\n "
					+ "Choice?:");
			val =  reader.nextInt();
			break;			
		default: 
			val = 0;
			break;
		}
		
		return val;
	}
	
	public Customer login() {
		/*
		 * Customer login process
		 * */
		
		String userName = null, password = null, response = "Y";
		Customer connectedCustomer = null;
		
		do {	
			System.out.println("Username: ");
			userName = reader.nextLine();
			
			System.out.println("Password: ");
			password = reader.nextLine();
			
			for(Customer cust : baseCustomers) {
				if (userName.equals(cust.getUserName().toString()) && password.equals(cust.getPassword().toString())) {
					System.out.println("Welcome " + cust.getName());
					connectedCustomer = cust;												//store the user data into connectedUser
					break;
				}
				else {
					System.out.println("Wrong credentials, try again?");
					if((reader.next()).toUpperCase().equals(new String("Y")))
						response = "Y";
					else
						response = "N";
					break;
				}					
			}
		}
		
		while(connectedCustomer == null && response == "Y");

		return connectedCustomer;
	}

	public boolean newCustomer() {
		/*Creates and serializes a customer*/
		String name = null, address, email, phone, dob, ssn, passwordConfirm, userName, password;
		System.out.println("Please provide your Social: ");
		ssn = reader.nextLine().toUpperCase();
		/*
		 * 1st control to avoid duplicate accounts based on the social security number*/
		for(Customer cust : baseCustomers) {
			if (ssn.equals(cust.getSsn().toString())) {
				System.out.println("This social can't be used to create a new user!");
				return false;
			}
		}
		//Customer social not found, then the operation can resume.
		//name, address, email, phone, ssn, customerID, userName, password;
		System.out.println("First and Last name: ");
		name = reader.nextLine();
		System.out.println("Address on 1 line: ");
		address = reader.nextLine();
		System.out.println("Email address: ");
		email = reader.nextLine();
		System.out.println("Phone number: ");
		phone = reader.nextLine();
		System.out.println("DOB: ");
		dob = reader.nextLine();
		System.out.println("User name: ");
		userName = reader.nextLine().toUpperCase();
		
		do{//Password confirmation
			System.out.println("Password: ");
			password = reader.nextLine();
		
			/*2nd Control for the password*/
			System.out.println("Confirm password ");
			passwordConfirm = reader.nextLine();
		}
		while (!password.equals(passwordConfirm));
		
		//Updates the customers list			
		baseCustomers.add(new Customer(name, address, email, phone, ssn, new MyDisplays<>().reverseFormatDate(dob), 
				new Date(), userName, password, 1, true));
		//Serializes and reload the file
		new MyDisplays<Customer>().serialize(baseCustomers, "customers.ser");		
		return true;
	}
	
	public void deposit(String accountNber, double amount) {
		//Updates the deposits list			
		baseTransactions.add(new Transaction(new Date(), "Deposit", amount, accountNber)); 
		//Serializes and reload the file
		new MyDisplays<Transaction>().serialize(baseTransactions, "transaction.ser");		
	}
	
	public void withdraw(String accountNber, double amount) {
		//Updates the deposits list			
		baseTransactions.add(new Transaction(new Date(), "Deposit", (-1 * amount), accountNber)); 
		//Serializes and reload the file
		new MyDisplays<Transaction>().serialize(baseTransactions, "transaction.ser");			
	}

	public void activateAccounts(Account account) {
		account.activate();
		System.out.println("Accont activated!");
	}
	
	public void promoteUser(Account account) {
		account.promote();
	}
	
}
