/**
 * Class containing the project main
 * 
 * @Instructor Ryan Lessley
 * @author Mahamadou
 * @Version 1.0
 * @Date 11/13/2017
 * 
 */
package com.revature.display;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.revature.implementations.Banking;
import com.revature.implementations.MyLogger;
import com.revature.objects.Account;
import com.revature.objects.Customer;
import com.revature.objects.Transaction;

public class MyDisplays <T>{
	/*This class has most of the methods used by the application*/
	private static Scanner reader = new Scanner(System.in); //Reading from System.in
	private static ObjectOutputStream myOutStream;

	BufferedReader myBufferReader;
	BufferedWriter myBufferWriter;
	
	/*Format the date according to the chosen format*/
	public String formatDate(Date date, String format) {
		SimpleDateFormat outFormat = new SimpleDateFormat(format);		
		return outFormat.format(date);
	}
	
	/*This method reverses formatDate to retrieve the Date format*/
	public Date reverseFormatDate(String date) {
		SimpleDateFormat simpleDate = new SimpleDateFormat("MM/dd/yyyy");	
		Date val = new Date();
		try {
			val = simpleDate.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		return val;
	}
				
	/*Serialization method for a given object into a specified file name*/
	public void serialize(ArrayList<T> tobeSerialized, String fileName) {		
		try {
			myOutStream = new ObjectOutputStream(new FileOutputStream(fileName));
			myOutStream.writeObject(tobeSerialized);
			} 
		catch (IOException e) {
			e.printStackTrace();
		}	
	}	
	
	/*Helper method returning a collection of objects obtained
	 * after deserializing a given file.*/
	public ArrayList<T> readObject(String fileName) {
		ObjectInputStream myInStream;	
		ArrayList<T> myObjectList = new ArrayList<>();
		try {
			myInStream = new ObjectInputStream(new FileInputStream(fileName));
			myObjectList = (ArrayList<T>) myInStream.readObject();
			//myTObject = (T)myInStream.readObject();
			} 
		catch (FileNotFoundException e) {
			e.printStackTrace();} 
		catch (IOException e) {
			e.printStackTrace();} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return myObjectList;
	}	

	/*Returns a customer object with the info of the current customer connected*/
	public Customer login(String userName, String password, ArrayList<Customer> baseCustomers) {
	/*This function uses the username and password provided,
	 *traverses the customers arrayList until it finds a match
	 *or returns null.*/
		for (Customer customer : baseCustomers) {
			if (customer.getUserName().equals(userName))
				if (customer.getPassword().equals(password)) {									//Username found and password matches
					if (customer.isActive() == false) {											//Customer not active
						System.out.println("This account still need to be activated by an Admin!");
						new MyLogger().logTransactions(new Date() + " - " + "Unvalidated customer access attempt - " + customer.getUserName());
						return null;
					}
					return customer;															//Username found and customer active
				}
				else {																			//Username found but password doesn't match
					System.out.println("Sorry, Wrong credentials!\n Password doen't match");
					return null;
			}
		}
		System.out.println("Sorry, Wrong credentials!\nCouldn't find this customer");			//Customer not found
		return (Customer)null;						
	}
	
	/*Creates a new customer and returns indicates the outcome*/
	public boolean newCustomer(ArrayList<Customer> baseCustomers) {
		/*Creates and serializes a customer*/
		
		//reader.nextLine();						//Reset the reader
		String name = null, address, email, phone, dob, ssn, passwordConfirm, userName, password;
		System.out.println("NEW CUSTOMER\n----------------------------------\n " +
		"Please provide your Social: ");
		ssn = reader.nextLine();
		
		/*1st control to avoid duplicate accounts based on the social security number*/
		for(Customer cust : baseCustomers) {
			if (ssn.equals(cust.getSsn().toString())) {
				System.out.println("This social can't be used to create a new user!");
				return false;
			}
		}
		
		//Customer social not found, then the operation can resume.
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
		userName = reader.nextLine();
		
		do{//Password confirmation
			System.out.println("Password: ");
			password = reader.nextLine();
		
			/*2nd Control for the password*/
			System.out.println("Confirm password ");
			passwordConfirm = reader.nextLine();
		}
		while (!password.equals(passwordConfirm));
		
		//Updates the customers list			
		baseCustomers.add(new Customer( ++(new Banking().nbOfCustomers), name, address, email, phone, ssn, new MyDisplays<>().reverseFormatDate(dob), 
				new Date(), userName, password, 1, false));
		
		//Serializes and reload the file
		new MyDisplays<Customer>().serialize(baseCustomers, "customers.ser");		
		return true;
	}

	/*Shows the options available to the customer according to his type(Admin or no)
	and request a choice in the presented list of options.*/
	public int displayUserMenu(int roleID) {
	/*Method to display a menu using the user role permissions*/		
		int val = 0;
		
		switch (roleID) {
		case 0: 
			System.out.println(
					"1 Activate account\n"
					+ "2 Withdrawal\n"
					+ "3 Deposit\n"
					+ "4 View transactions\n"
					+ "5 Promote Customer\n"
					+ "6 Loans approval\n\n"
					+ "Choice?:");
			val = reader.nextInt();
			break;			
		case 1: 
			System.out.println(
					"2 Withdrawal\n" + 
					"3 Deposit\n" +
					"4 View transactions\n\n" +
					"Choice?:");
			val =  reader.nextInt();
			break;			
		default: 
			val = 0;
			break;
		}
		
		return val;
	}

	/*Returns the new balance after the customer withdraw money from his account*/
	public double deposit(String accountNber, double amount, ArrayList<Transaction> baseTransactions, ArrayList<Account> baseAccounts) {
		//Updates the deposits list		
		double balance = 0.0;
		baseTransactions.add(new Transaction( ++(new Banking().nbOfTransactions), new Date(), "deposit", (amount), accountNber)); 
		//Serializes and reload the file
		for(Account account : baseAccounts) {
			if(accountNber.equals(account.getAccountNber())) {
				account.setBalance(balance = (account.getBalance() + amount));
			}
		}
		new MyDisplays<Transaction>().serialize(baseTransactions, "transactions.ser");	
		new MyDisplays<Account>().serialize(baseAccounts, "accounts.ser");	
		return balance;

	
	
	}
	
	/*Returns the new balance after the customer withdraw money from his account*/
	public double withdraw(String accountNber, double amount, ArrayList<Transaction> baseTransactions, ArrayList<Account> baseAccounts) {
		//Updates the withdrawals list			
		double balance = 0.0;
		baseTransactions.add(new Transaction( ++(new Banking().nbOfTransactions), new Date(), "Withdrawal", (-1 * amount), accountNber)); 
		//Serializes and reload the file
		for(Account account : baseAccounts) {
			if(accountNber.equals(account.getAccountNber())) {
				account.setBalance(balance = (account.getBalance() - amount));
			}
		}
		new MyDisplays<Transaction>().serialize(baseTransactions, "transactions.ser");	
		new MyDisplays<Account>().serialize(baseAccounts, "accounts.ser");	
		return balance;
		
	}

	/*Validates a customer in the waiting queue
	 * When this process occurs, it creates the customer's account*/
	public void activateAccounts(Customer customer) {
		customer.activate();
		System.out.println("Accont activated!");
	}
	
	/*Promote a customer from a simple to an Admin account*/
	public void promoteUser(Customer customer) {
		customer.promote();
	}
	
	public void listTransactions(ArrayList<Account> accounts, ArrayList<Transaction> transactions) {
		for(Account account : accounts) {
			for(Transaction trans : transactions) {
				if (account.getAccountNber().equals(trans.getAccountNber()))
					System.out.println(trans.toString());
			}
		}
	
	}
	
	public void displayUser(int customer) {
		
	}

}