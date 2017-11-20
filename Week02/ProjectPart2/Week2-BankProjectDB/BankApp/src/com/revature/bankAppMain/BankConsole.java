package com.revature.bankAppMain;

import java.util.*;

import com.revature.beans.Administrator;
import com.revature.beans.Customer;

/**
 * @author Matthew
 * This class is the Text-based User Interface (TUI) it is responsible
 * for displaying messages to the console and returning the user's 
 * input from the console. *
 */
public class BankConsole {
	
	public Scanner in;
	
	public BankConsole(){
		in = new Scanner(System.in);
	}
	
	/**
	 * Prints the Start Menu.
	 * This method will be called when the BankApp is started.
	 * This method will be called when the User has logged out
	 */
	public void printStartMenu(){
		System.out.println("\n--------------------------- ");
		System.out.println(    "Welcome to The Banking App!");
		System.out.println("\nMenu:");
		System.out.println("1 - New Users");
		System.out.println("2 - Login");
		System.out.println("0 - Quit");
	}
	
	/**
	 * This method is called if the User selects the "New Users" option at
	 * the Start Menu.
	 * This method prompts the user to enter all required Strings for creating
	 * a new User
	 * @return
	 * This method returns an Array of Strings the following user info:
	 * 		First Name
	 * 		Last Name
	 * 		Social Security Number (ssn)
	 * 		Desired Username
	 * 		Desired Password
	 */
	public String[] printNewUserPage(){
		System.out.println("---------------------------");
		System.out.println("Creating New User!");
		String firstName = readStringWithPrompt("\nPlease enter your first name:\n");
		String lastName = readStringWithPrompt("\nPlease enter your last name:\n");
		String ssn = readStringWithPrompt("\nPlease enter your SSN:\n");
		String username = readStringWithPrompt("\nPlease enter your desired Username:\n");
		String password = readStringWithPrompt("\nPlease enter your desired Password:\n");
		String[] result = {firstName, lastName, ssn, username, password};
		return result;
	}
	
	/**
	 * This method is called if the User selects the "Login" option at the Start Menu
	 * This method prompts the user to enter a Username and Password
	 * a new User
	 * @return
	 * This method returns an Array of Strings the following user info:
	 * 		Username
	 * 		Password 
	 */
	public String[] printLoginPage(){
		System.out.println("--------------------------- \n" + "Login Page!");
		String username = readStringWithPrompt("\nPlease enter your Username:\n");
		String password = readStringWithPrompt("\nPlease enter your Password:\n");
		String[] result = {username, password};
		return result;
	}	
	
	/**
	 * This method is called upon successful login by a Customer User
	 * @param cust
	 * This is the Customer who is currently logging in.
	 * @return
	 * This method prompts the user to select a menu option and returns an int
	 * that holds their selection
	 */
	public int printDashboard(Customer cust){
		int result = 0;
		System.out.println("\n--------------------------- ");
		System.out.println("Welcome " + cust.getFirstName() + " " + cust.getLastName());
		System.out.println("Account Balance: $" + cust.getAccount().getBalance());
		System.out.println("2 - Withdraw");
		System.out.println("1 - Deposit");
		System.out.println("0 - Logout");
		result = readIntWithPrompt("Make a Selection:");
		return result;
	}

	/**
	 * This method is called upon successful login by an Administrator User
	 * @param admin
	 * This is the Administrator who is currently logging in.
	 * @return
	 * This method prompts the user to select a menu option and returns an int
	 * that holds their selection
	 */	
	public int printDashboard(Administrator admin){
		int result = 0;
		System.out.println("\n--------------------------- ");
		System.out.println("Welcome Administrator " + admin.getUsername() + "!");
		System.out.println("1 - Block a Customer");
		System.out.println("2 - Unblock a Customer");
		System.out.println("3 - Promote Customer to Admin");
		System.out.println("0 - Logout");
		result = readIntWithPrompt("Make a Selection:");
		return result;
	}
	
	/**
	 * This method is called by an Administrator in order to promote a Customer User
	 * to an Administrator User
	 * @param cust
	 * This is the Customer selected by the Current (Administrator)User - this Customer
	 * will be promoted to an admin, but their Customer Account will be lost.
	 * @return
	 * This method prompts the user to select a menu option and returns an int
	 * that holds their selection 
	 */
	public int printPromotePage(Customer cust){
		int result = 0;
		System.out.println("\n !!! WARNING !!! \n");
		System.out.println("Admin accounts are not allowed to hold Account Balances\n");
		System.out.println(cust.getFirstName() + " " + cust.getLastName());
		System.out.println("Account has a balance of: $" + cust.getAccount().getBalance());
		System.out.println("\nProceed?\n");
		System.out.println("1 - Yes");
		System.out.println("0 - No");
		result = readIntWithPrompt("Make a Selection:");
		return result;
	}
	
	/**
	 * This method prints a list of all Customer Users to the console.
	 * @param customerList
	 * This is a list of all Customer Users
	 * @return
	 * This method prompts the user to select a menu option and returns an int
	 * that holds their selection 
	 */
	public int printCustList(ArrayList<Customer> customerList){
		int result = 0;
		int index = 0;
		for (Customer cust: customerList){
			System.out.println(index + " - " + cust.toString());
			index++;
		}
		result = readIntWithPrompt("Enter number of Customer: ");
		return result;
	}
	
	/**
	 * This method displays a prompt to the console and returns the User's 
	 * input.
	 * @param prompt
	 * This is the String that will display to the User 
	 * (e.g. "Please Enter an Option from the Menu above: ")
	 * @return
	 * This is the int that the User enters in response to the prompt
	 */
	public int readIntWithPrompt( String prompt){
		System.out.print(prompt);
		System.out.flush();
		while( !in.hasNextInt() ){
			in.nextLine();
			System.out.println("Invalid number.");
			System.out.println(prompt);
			System.out.flush();
		}
		int input = in.nextInt();
		in.nextLine();
		return input;
	}
	
	/**
	 * This method displays a prompt to the console and returns the User's 
	 * input.
	 * @param prompt
	 * This is the String that will display to the User 
	 * (e.g. "Please Enter Amount to Deposit: ")
	 * @return
	 * This is the double that the User enters in response to the prompt
	 */	
	public double readDoubleWithPrompt( String prompt){
		System.out.print(prompt);
		System.out.flush();
		while( !in.hasNextDouble()){
			in.nextLine();
			System.out.println("Invalid number.");
			System.out.println(prompt);
			System.out.flush();
		}
		double input = in.nextDouble();
		in.nextLine();
		return input;
	}
	
	/**
	 * This method displays a prompt to the console and returns the User's 
	 * input.
	 * @param prompt
	 * This is the String that will display to the User 
	 * (e.g. "Please Enter Username: ")
	 * @return
	 * This is the double that the User enters in response to the prompt
	 */		
	public String readStringWithPrompt( String prompt){
		System.out.print(prompt);
		System.out.flush();
		String input = in.next();
		in.nextLine();
		return input;
	}
	
	
}
