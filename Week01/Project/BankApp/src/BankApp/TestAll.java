package BankApp;

import static org.junit.Assert.*;

import java.io.IOException;


//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
import org.junit.Test;

public class TestAll {
	
	BankDriver bd = new BankDriver();
	BankConsole bc = new BankConsole();
	Database db = new Database();
	
	Account acct1 = new Account(1, 0.0);
	Account acct2 = new Account(2, 500.0);
	Account acct3 = new Account(3, 300.0);
	
	@Test
	public void testSerialization() throws IOException{
		db.customerList.add(new Customer("Jen", "Eric", "345-67-8912", "jeric", "password"));
		db.accountList.add(db.accountList.get(db.accountList.size()-1));
		db.adminList.add(new Administrator("adminX", "xxxx"));

		int custIndex = db.customerList.size()-1;
		int acctIndex = db.accountList.size()-1;
		int adminIndex = db.adminList.size()-1;
		
		Customer testCust = db.customerList.get(custIndex);
		Account testAcct = db.accountList.get(acctIndex);
		Administrator testAdmin = db.adminList.get(adminIndex);
		
		db.serializeRecords("customerList");
		db.serializeRecords("accountList");
		db.serializeRecords("adminList");
		
		db = null;
		Database db2 = new Database();
		
		db2.deserializeRecords("customerList");
		db2.deserializeRecords("accountList");
		db2.deserializeRecords("adminList");
		
		assertTrue(db2.customerList.get(custIndex).equals(testCust));
		assertTrue(db2.accountList.get(acctIndex).equals(testAcct));
		assertTrue(db2.adminList.get(adminIndex).equals(testAdmin));
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
