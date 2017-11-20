package com.revature.main;

import java.util.Date;

import com.revature.dao.AccountDao;
import com.revature.dao.CustomerDao;
import com.revature.dao.LoanDao;
import com.revature.dao.MyAccountDao;
import com.revature.dao.MyCustomerDao;
import com.revature.dao.MyLoanDao;
import com.revature.dao.MyPersonDao;
import com.revature.dao.MyTransactionDao;
import com.revature.dao.PersonDao;
import com.revature.dao.TransactionDao;
import com.revature.generalmethods.GeneralFunctions;
import com.revature.objects.Bank_account;
import com.revature.objects.Bank_customer;
import com.revature.objects.Loan;
import com.revature.objects.Person;
import com.revature.objects.Transaction;

public class Banking {
	
	
	
	
	public static void main(String[] args) throws Exception {
/*		//Person-----------------------------------------------------------
		//Add Person in the database
		PersonDao pers_dao = new MyPersonDao();
		Person person = new Person("MAHAMADOU", "TRAORE", "11730 PLAZA AMERICA", "HENDON", "VA", "75220", "USA",
				"mahammadou@rev.com", "4445862000", "000000000", new GeneralFunctions<Date>().reverseFormatDate("01/25/1985"));				
		pers_dao.newPerson(person);
		
		//Find a Person by his id from the database
		System.out.println((new MyPersonDao()).findById(2));
		
		//Customer----------------------------------------------------------
		//Add Customer in the database
		CustomerDao cust_dao = new MyCustomerDao();		
		Bank_customer cust = new Bank_customer(new java.sql.Date(0), 
				"User1", "password", 1, 1, 0);
		cust_dao.newCustomer(cust);
		
		//Find a Customer by his id from the database
		System.out.println((new MyCustomerDao()).findById(1));
		
		//Activate a customer
		System.out.println("Before customer activation\n" + 
				(new MyCustomerDao()).findById(2));
		(new MyCustomerDao()).validateCustomer(2);
		System.out.println("After customer activation\n" +
				(new MyCustomerDao()).findById(2));

		//Promote a customer
		System.out.println("Before customer promotion\n" +
				(new MyCustomerDao()).findById(2));
		(new MyCustomerDao()).promoteCustomer(2);
		System.out.println("After customer promotion\n" +
		(new MyCustomerDao()).findById(2));
		
		//Account------------------------------------------------------------
		//Create a new account
				
		AccountDao account_dao = new MyAccountDao();		
		Bank_account account = new Bank_account("0000000001", new java.sql.Date(0), 
				1, 1, 1, 0.0);
		account_dao.newAccount(account);
		
		//Update the balance of an account(transactions)
		System.out.println("Before balance transaction\n" +
				(new MyAccountDao()).findById("0000000001"));
		(new MyAccountDao()).updateBalance("0000000001", 438.0);
		System.out.println("After balance transaction\n" +
				(new MyAccountDao()).findById("0000000001"));
		
		//Transaction--------------------------------------------------------
		//Add transaction
		TransactionDao transact_dao = new MyTransactionDao();		
		Transaction transact = new Transaction(new java.sql.Date(0), 
				"Test", 20.0, "0000000001");
		transact_dao.newTransaction(transact);
		
		//Find a transaction by transactionid
		System.out.println((new MyTransactionDao()).findTransaction(3));

		//Loan--------------------------------------------------------
		//Add loan
		LoanDao loan_dao = new MyLoanDao();		
		Loan loan = new Loan(1 , 15200.0, 60, 2.5, 0, 0, "0000000001");
		loan_dao.newLoan(loan);
		
		//Find a loan by id
		System.out.println((new MyLoanDao()).findById(1));
		
		//Display the pending loans
		System.out.println(((new MyLoanDao()).listOfPendingLoans()).get(0));
*/
		
		
		
	
	
	
	}


}
