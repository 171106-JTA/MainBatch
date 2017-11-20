/**
 * Class containing the project main
 * 
 * @Instructor Ryan Lessley
 * @author Mahamadou
 * @Version 1.0
 * @Date 11/13/2017
 */
package com.revature.implementations;

import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.revature.display.MyDisplays;
import com.revature.objects.*;

public class Banking {
	
	//1. Load the customers list into an ArrayList
	static ArrayList<Customer> baseCustomers = new MyDisplays<Customer>().readObject("customers.ser");
	//2. Load the accounts list into an ArrayList
	static ArrayList<Account> baseAccounts = new MyDisplays<Account>().readObject("accounts.ser");
	//2. Load the transactions list into an ArrayList
	static ArrayList<Transaction> baseTransactions = new MyDisplays<Transaction>().readObject("transactions.ser");
	//2. Load the transactions list into an ArrayList
	static ArrayList<Loan> baseLoans = new MyDisplays<Loan>().readObject("loans.ser");
	
	public static int nbOfCustomers;
	public static int nbOfTransactions;
	
	private static Scanner reader = new Scanner(System.in); //Reading from System.in

	public static void main(String[] args) {
		
		//To be use for auto increment id 
		nbOfCustomers = baseCustomers.size();
		nbOfTransactions = baseTransactions.size();
		
		//3. Prompt the user to identify himself or create an account
		int menuChoice = -1;
		Customer connectedCustomer = null;	
		ArrayList<Account> connectedAccounts = new ArrayList<>();
		String response = null;
		boolean newCustomerResult = false;
		double amount = 0.0, balance = 0.0;		//To be used for the transactions
		int customerID = -1;					//To be used in customer valiation and promotion
		boolean newActivation = true;
		int loanChoice = -1;					//To be used for loan approval process
		String loanNumber = null;
		
		/* wantToQuit = "N", account = null;
		String acctNumber;
		double transactAmount;
*/		
		
		//Welcome Message
		System.out.println("Please start by making a choice \n"
						+ "----------------------------------\n");
		
		//Loop until the user types in a valid response
		System.out.println(
							"1. Log In\n2. Sign Up\n");
		do {
			try {
				menuChoice = reader.nextInt();
			}
			catch(InputMismatchException e) {
				menuChoice = -1;
				break;
			}
			finally {
				switch(menuChoice) {
				case 1:															//Login
					/*User login */
					boolean leave = false;
					do {
						menuChoice = 1;											//In case we recover from a failed login
					reader.nextLine(); //reset the reader
					//Call the login function and load the customer
					System.out.println("----------------------------------\nLOGIN \n----------------------------------\n" +
					"User name: \n");
					String userName = reader.nextLine();
					System.out.println("Password:\n");
					String password = reader.nextLine();
					
					connectedCustomer = new MyDisplays().login(userName, password, baseCustomers);
					
					if (connectedCustomer == null) {
						System.out.println("Try again?");
						response = reader.nextLine().trim();
						
						leave = ((response.toUpperCase().equals("YES") || response.toUpperCase().equals("Y"))? false : true);
						menuChoice = -1;
					}
					}
					while (connectedCustomer == null && leave == false);
					break;
				case 2:															//SignUp
					/*New customer creation*/
					leave = false;
					do {
						menuChoice = 2;											//In case we recover from a failed login

						newCustomerResult = new MyDisplays().newCustomer(baseCustomers);
						if (newCustomerResult == false) {
						reader.nextLine();
						System.out.println("Try again?");
						response = reader.nextLine().trim();
						
						leave = ((response.toUpperCase().equals("YES") || response.toUpperCase().equals("Y"))? false : true);
						menuChoice = -1;
					}
						else
							System.out.println("New customer created! \n plase wait for your account to be review and approved.");
					}
					while (newCustomerResult == false && leave == false);
					break;
					
				default:
					System.out.println("Invalid entry!\n");
					menuChoice = -1;
					break;
				}
			}
		}
		while (menuChoice == -1);
		
		if (connectedCustomer != null && connectedCustomer.isActive()) {
			//Display Welcome message
			System.out.println("Welcome " + connectedCustomer.getName() + "\n");
			
			//Retrieve account info to be stored in currentAccounts
			for (Account account : baseAccounts) {
				if (account.getCustomerID() == connectedCustomer.getId())
					connectedAccounts.add(account);
				}
			
			//Display accounts balance
			for(Account account : connectedAccounts)
				System.out.println("----------------------------------\nAccount #: " + account.getAccountNber() + " - Balance: $" + account.getBalance() + "\n----------------------------------\n");
			
			//TODO--Call menu fct
			int action = new MyDisplays().displayUserMenu(connectedCustomer.getRoleID());
			boolean foundCustomer = false;		//Used to control the input while activating an account
			
			switch(action) {
			case 1:
				if (connectedCustomer.getRoleID() != 0) {
					System.out.println("Permission refused!");
					break;
				}
				newActivation = true;
				do {
				customerID = -1;
				ArrayList<Customer> toBeValidated = new ArrayList<>();
				//Display the accounts that need activation
				for (Customer customer : baseCustomers) {
					if (customer.isActive() == false) {
						System.out.println(customer.getId() + "\t" + customer.getName()  + "\t" + customer.getUserName() + "\t" + customer.getSince());
						//Save the customerID for control
						toBeValidated.add(customer);
					}
				}
				if(toBeValidated.size() == 0) {
					System.out.println("No account waiting for validation found!");
					newActivation = false;
				}
				else {
					System.out.println("Enter the customer ID to activate");
					customerID = reader.nextInt();
					foundCustomer = false;
					for(Customer customer : toBeValidated) {
						if (customer.getId() == customerID) {
							new MyDisplays().activateAccounts(customer);
							//Creates a new account for the customer
							System.out.println("Creating a new account for the customer...");
							baseAccounts.add(new Account("ACCT" + customer.getId(), new Date(), 1, true, customer.getId()));	
							foundCustomer = true;
	
							//Updates the customers list			
							new MyDisplays<Account>().serialize(baseAccounts, "accounts.ser");		
							for (Customer customer1 : baseCustomers) {
								if(customer1.getId() == customerID)
									customer1.activate();
							}
							//baseCustomers.add(customer);
							
							//Serializes and reload the file
							new MyDisplays<Customer>().serialize(baseCustomers, "customers.ser");		
							new MyDisplays<Account>().serialize(baseAccounts, "accounts.ser");		
	
						}					
					}
					if (foundCustomer == false)
						System.out.println("This customer ID was not found!");
					
					System.out.println("Another activation?");
					newActivation = reader.nextBoolean();
				}
				}
				while (newActivation == true);
				
				break;
			case 2:		//Withdrawal
				System.out.println("Amount to be withdrawn: ");
				amount = reader.nextDouble();
				balance = new MyDisplays().withdraw(connectedAccounts.get(0).getAccountNber(), amount, baseTransactions, baseAccounts);
				System.out.println("Transaction completed!\nNew balance: $" + balance);
				break;
			case 3:		//Deposit
				System.out.println("Amount to be deposit: ");
				amount = reader.nextDouble();
				balance = new MyDisplays().deposit(connectedAccounts.get(0).getAccountNber(), amount, baseTransactions, baseAccounts);
				System.out.println("Transaction completed!\nNew balance: $" + balance);
				break;
			case 4:		//View Transactions
				new MyDisplays().listTransactions(connectedAccounts, baseTransactions);
				break;
			case 5:		//Promote customer
				if (connectedCustomer.getRoleID() != 0) {
					System.out.println("Permission refused!");
					break;
				}

				newActivation = true;
				do {

				System.out.println("Enter the customer ID to activate");
				customerID = reader.nextInt();
				foundCustomer = false;
					for(Customer customer : baseCustomers) {
						if (customer.getId() == customerID) {
							new MyDisplays().promoteUser(customer);
							foundCustomer = true;
	
							//Updates the customers list			
							new MyDisplays<Account>().serialize(baseAccounts, "accounts.ser");		
					
							//Serializes and reload the file
							new MyDisplays<Customer>().serialize(baseCustomers, "customers.ser");		
						
						}					
					}
					if (foundCustomer == false)
						System.out.println("This customer ID was not found!");
					
					System.out.println("Another activation?");
					newActivation = reader.nextBoolean();
				}
				while (newActivation == true);
				
				System.out.println("Promote customer...");
				break;
			case 6:		//Loans
				if (connectedCustomer.getRoleID() != 0) {
					System.out.println("Permission refused!");
					break;
				}

				newActivation = true;
				do {
				customerID = -1;
				ArrayList<Loan> toBeApprove = new ArrayList<>();
				//Display the loans that need approval
				for (Loan loan : baseLoans) {
					if (((Loan)loan).isApproved() == false) {
						System.out.println(loan.getAccountNber() + "\t" + loan.getCustomerID()  + "\t" + loan.getCreationDate());
						//Save the customerID for control
						toBeApprove.add((Loan)loan);
					}
				}
				if(toBeApprove.size() == 0) {
					System.out.println("No loan waiting for approval found!");
					newActivation = false;
					break;
				}
				
				System.out.println("Enter the loan number review");
				loanNumber = reader.nextLine();
				foundCustomer = false;
				for (Loan loan : baseLoans) {
					if(loanNumber.equals(loan.getAccountNber())) {
						for(Customer customer : baseCustomers) {
							if (customer.getId() == loan.getCustomerID()) {
							new MyDisplays().displayUser(customerID);
							System.out.println("0 Deny\n1 Approve");
							loanChoice = reader.nextInt();
							switch(loanChoice) {
							case 0:		//Denial
								System.out.println("Denied!");
								break;
							case 1:
								System.out.println("Approved!");
							default:
								break;
							}			
						
							foundCustomer = true;
	
							//Updates the customers list			
							new MyDisplays<Loan>().serialize(baseLoans, "loans.ser");		
							
							System.out.println("Another approval?");
							newActivation = reader.nextBoolean();
							}
						}
					}
				}
				
				}
				while(newActivation == true);

				break;
				
			default:
				System.out.println("Wrong entry...");
				break;
			}
		}
			//System.out.println(connectedCustomer.toString());
		
	}
	

}
