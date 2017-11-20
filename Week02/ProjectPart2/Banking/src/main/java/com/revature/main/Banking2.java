/**
 * Class containing the project main
 * 
 * @Instructor Ryan Lessley
 * @author Mahamadou
 * @Version 1.0
 * @Date 11/13/2017
 */

package com.revature.main;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.revature.dao.MyAccountDao;
import com.revature.dao.MyCustomerDao;
import com.revature.dao.MyLoanDao;
import com.revature.dao.MyPersonDao;
import com.revature.dao.MyTransactionDao;
import com.revature.dao.PersonDao;
import com.revature.objects.Bank_account;
import com.revature.objects.Bank_customer;
import com.revature.objects.Loan;
import com.revature.objects.Person;
import com.revature.objects.Transaction;
import com.revature.generalmethods.*;

public class Banking2 {
	
	//1. Load the customers list into an ArrayList
	static ArrayList<Bank_customer> baseCustomers = new MyCustomerDao().listOfAllCustomer();
	//2. Load the accounts list into an ArrayList
	static ArrayList<Bank_account> baseAccounts = new MyAccountDao().listOfAllAccounts();
	//2. Load the transactions list into an ArrayList
	static ArrayList<Transaction> baseTransactions = new MyTransactionDao().listAllTransactions();
	//2. Load the transactions list into an ArrayList
	static ArrayList<Loan> baseLoans = new MyLoanDao().listOfPendingLoans();
	
	public static int nbOfCustomers;
	public static int nbOfTransactions;
	
	private static Scanner reader = new Scanner(System.in); //Reading from System.in

	public static void main(String[] args) {
		
		//To be use for auto increment id 
		nbOfCustomers = baseCustomers.size();
		nbOfTransactions = baseTransactions.size();
		
		//3. Prompt the user to identify himself or create an account
		int menuChoice = -1;
		Bank_customer connectedCustomer = null;	
		ArrayList<Bank_account> connectedAccounts = new ArrayList<>();
		String response = null;
		boolean newCustomerResult = false;
		double amount = 0.0, balance = 0.0;		//To be used for the transactions
		int customerID = -1;					//To be used in customer valiation and promotion
		boolean newActivation = true;
		int loanChoice = -1;					//To be used for loan approval process
		String loanNumber = null;
		String ssn, fname, lname, username, password, passwordconfirm;
		
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
					
					menuChoice = 1;											//In case we recover from a failed login
					reader.nextLine(); //reset the reader
					//Call the login function and load the customer
					System.out.println("----------------------------------\nLOGIN \n----------------------------------\n" +
					"User name: \n");
					String userName = reader.nextLine();
					System.out.println("Password:\n");
					password = reader.nextLine();
					
					connectedCustomer = new MyCustomerDao().login(userName, password);
					Person p1 = (new MyPersonDao()).findById(connectedCustomer.getPersonID());
					 System.out.println(p1.getFName());
					break;
				case 2:															//SignUp
					/*New customer creation*/
					leave = false;
					//do {
						menuChoice = 2;											//In case we recover from a failed login

						//reader.nextLine();						//Reset the reader
						//String name = null, address, email, phone, dob, ssn, passwordConfirm, userName, password;
						System.out.println("NEW CUSTOMER\n----------------------------------\n ");
						
						System.out.println("First name: ");
						fname = reader.nextLine();
						System.out.println("Last name: ");
						lname = reader.nextLine();
						System.out.println("User name: ");
						username = reader.nextLine();
						
						do{//Password confirmation
							System.out.println("Password: ");
							password = reader.nextLine();
						
							/*2nd Control for the password*/
							System.out.println("Confirm password ");
							passwordconfirm = reader.nextLine();
						}
						while (!password.equals(passwordconfirm));
						
						//Updates the customers list
						PersonDao pers_dao = new MyPersonDao();
						Person person = new Person(2, fname, lname, "11730 PLAZA AMERICA", "HENDON", "VA", "75220", "USA",
								"mahammadou@rev.com", "4445862000", "000000000", (new GeneralFunctions<>()).reverseFormatDate("01/25/1985"));				
						pers_dao.newPerson(person);

						new MyCustomerDao().newCustomer(new Bank_customer((new GeneralFunctions<>()).reverseFormatDate("11/20/2017"), username, password, 1, 2, 0));
					//}
					//while (newCustomerResult == false && leave == false);
					break;
					
				default:
					System.out.println("Invalid entry!\n");
					menuChoice = -1;
					break;
				}
			}
		}
		while (menuChoice == -1);
		
/*		
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
	*/	
	}
	

}
