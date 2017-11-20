package com.revature.bankAppMain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.revature.beans.Account;
import com.revature.beans.Administrator;
import com.revature.beans.Customer;
import com.revature.dao.AccountDaoImpl;
import com.revature.dao.AdminDaoImpl;
import com.revature.dao.CustomerDaoImpl;

/**
 * @author Matthew
 * This class holds all of the data that needs to persist. It holds all
 * relevant User and Account information. It's implementation allows for
 * all of this information to be serialized and deserialized as needed - 
 * (this is what will enable persistance). 
 */
public class Database {
	
	private static final Logger log = Logger.getLogger("GLOBAL");
	
	public ArrayList<Customer> customerList;
	public ArrayList<Administrator> adminList;
	public ArrayList<Account> accountList;
	
	public AccountDaoImpl acctDao;
	public AdminDaoImpl adminDao;
	public CustomerDaoImpl custDao;	
	
	public Database(){
		customerList = new ArrayList<Customer>();
		adminList = new ArrayList<Administrator>();
		accountList = new ArrayList<Account>();
		
		acctDao = new AccountDaoImpl();
		adminDao = new AdminDaoImpl();
		custDao = new CustomerDaoImpl();
		
		this.accountList = acctDao.getAcctList();
		this.adminList = adminDao.getAdminList();
		this.customerList = custDao.getCustList();
		log.info("Database: Customer, Admin, and Account Lists successfully loaded");


		// prints all customers		
		for( int i = 0; i < customerList.size(); i++){
			System.out.println(customerList.get(i).toString());
		}
		
		// prints all administrators		
		for( int i = 0; i < adminList.size(); i++){
			System.out.println(adminList.get(i).toString());
		}

		// prints all accounts		
		for( int i = 0; i < accountList.size(); i++){
			System.out.println(accountList.get(i).toString());
		}
		
	}
	
	/**
	 * This method ensures that every Account created has a unique Account Number
	 * @param amt
	 * This is a double. It is the dollar amount that the Account should be created
	 *  with
	 * @return
	 * This method returns the instance of Account that it was created
	 */
	public Account createAccount(double amt){
		int maxAcctNo = 0;
		for(Account acct: accountList){
			if(maxAcctNo<acct.getAcctNumber()){
				maxAcctNo = acct.getAcctNumber();
			}
		}
		Account acct = new Account(maxAcctNo, amt);
		return acct;
	}
	
	/**
	 * This method verifies that userName is not already used by another customer
	 * in the Customer List
	 * @param userName
	 * String containing a potential username
	 * @return
	 * Returns true if username is unique
	 */
	public boolean uniqueUsername(String userName){
		boolean result = true;
		for(int i = 0; i < customerList.size(); i++){
			if (customerList.get(i).getUsername().equals(userName)){
				result = false;
			}
		}
		return result;
	}
	
}