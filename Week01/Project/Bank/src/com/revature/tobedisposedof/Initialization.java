package com.revature.tobedisposedof;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Date;

import com.revature.display.MyDisplays;
import com.revature.objects.*;

public class Initialization {
	
	BufferedReader myBufferReader;
	BufferedWriter myBufferWriter;


	public static void main(String[] args) {

		@SuppressWarnings("unused")
		ArrayList<Customer> customerList = new ArrayList<>();
		//ArrayList<Loan> loanList = new ArrayList<>();
		@SuppressWarnings("unused")
		ArrayList<Account> accountList = new ArrayList<>();
		@SuppressWarnings("unused")
		ArrayList<Transaction> transactList = new ArrayList<>();
		
		
		customerList.add(new Customer("Mahamadou TRAORE", "Reston VA", "mt@ht", 
		"5133588855", "000000000", new MyDisplays<>().reverseFormatDate("01/01/2017"), 
		new MyDisplays<>().reverseFormatDate("01/01/2017"), "UserTM", "password", 1, true));
		new MyDisplays<Customer>().serialize(customerList, "customers.ser");

		accountList.add(new Account("ACCTTM", new MyDisplays<>().reverseFormatDate("01/25/2017"), 1, true, 1));	
		new MyDisplays<Account>().serialize(accountList, "accounts.ser");

		transactList.add(new Transaction(new Date(), "Withdrawal", -500.0, "ACCTTM"));
		new MyDisplays<Transaction>().serialize(transactList, "transactions.ser");

	
		System.out.println(new MyDisplays<Customer>().readObject("customers.ser"));

		System.out.println(new MyDisplays<Account>().readObject("accounts.ser"));
		
		System.out.println((new MyDisplays<Transaction>().readObject("transactions.ser")));
		

		
		
		
		
		
		
		
		
		
/*		customerList.add(new Customer("Mahamadou TRAORE", "Reston VA", "mt@ht", 
		"5133588855", "000000000", new MyDisplays<>().reverseFormatDate("01/01/2017"), 
		new MyDisplays<>().reverseFormatDate("01/01/2017"), "UserTM", "password", 1, true));

		customerList.add(new Customer(2, "Maham TRAORE", "DC", "mt@ht", 
		"5133588855", "000000000", new MyDisplays<>().reverseFormatDate("01/01/2017"), 
		"cust2", new MyDisplays<>().reverseFormatDate("01/01/2017"), "UserTM1", "password", 1, true));

		customerList.add(new Customer(2, "Maimouna TRAORE", "Cincinnati OH", "mt@ht", 
		"5133588855", "000000000", new MyDisplays<>().reverseFormatDate("01/01/2017"), 
		"cust3", new MyDisplays<>().reverseFormatDate("01/01/2016"), "mai", "password", 1, true));
*/		

		//loanList.add( new Loan("ACT001", new MyDisplays<>().reverseFormatDate("01/25/2017"), 1, true, 1, 12000.0, 60, 0.12, true, false));
		
		//new MyDisplays<Loan>().serialize(loanList, "loans.ser");
		//new MyDisplays<Account>().serialize(accountList, "accounts.ser");
		
		//System.out.println((new MyDisplays<Account>().readObject("loans.ser")));

	}}
	

