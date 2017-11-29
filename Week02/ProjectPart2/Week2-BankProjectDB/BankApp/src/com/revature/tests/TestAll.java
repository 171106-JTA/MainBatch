package com.revature.tests;

import static org.junit.Assert.*;

import java.io.IOException;







import java.util.ArrayList;

//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.bankAppMain.BankConsole;
import com.revature.bankAppMain.BankDriver;
import com.revature.bankAppMain.Database;
import com.revature.beans.Account;
import com.revature.beans.Administrator;
import com.revature.beans.Customer;
import com.revature.dao.AccountDaoImpl;
import com.revature.dao.AdminDaoImpl;
import com.revature.dao.CustomerDaoImpl;

public class TestAll {
	
	BankDriver bd = new BankDriver();
	BankConsole bc = new BankConsole();
	Database db = new Database();
	
	AccountDaoImpl acctDao = new AccountDaoImpl();
	AdminDaoImpl adminDao = new AdminDaoImpl();
	CustomerDaoImpl custDao = new CustomerDaoImpl();
	
	Account acct1 = new Account(1, 0.0);
	Account acct2 = new Account(2, 500.0);
	Account acct3 = new Account(3, 300.0);
	
	@Test
	public void testPersistence() throws IOException{
		Account acct1 = db.acctDao.selectAcctById(1000000);

		db = null;
		Database db2 = new Database();
		
		Account acct2 = db2.acctDao.selectAcctById(1000000);
		
		assertTrue(acct1.equals(acct2));
	}
	
	@Test
	public void testDeposit() {
		Account testAcct = new Account(11111, 100.0);	
		String s = testAcct.deposit(-100.00);
		assertTrue(s.equals("negAmt"));

		testAcct = new Account(11111, 100.0);	
		s = testAcct.deposit(50.00);
		assertTrue(s.equals(""));
		
		assertEquals(150.0, testAcct.getBalance(), 0.001);
	}
	
	@Test
	public void testWithdraw() {
		Account testAcct = new Account(11111, 0.0);	
		String s = testAcct.withdraw(100.00);
		assertTrue(s.equals("acctBalanceZero"));
		
		testAcct = new Account(11111, 100.0);	
		s = testAcct.withdraw(101.00);
		assertTrue(s.equals("amt2High"));
		
		testAcct = new Account(11111, 100.0);	
		s = testAcct.withdraw(-101.00);
		assertTrue(s.equals("negAmt"));
		
		testAcct = new Account(11111, 100.0);	
		s = testAcct.withdraw(50.00);
		assertTrue(s.equals(""));
		
		assertEquals(50.0, testAcct.getBalance(), 0.001);
	}
	
	

}
