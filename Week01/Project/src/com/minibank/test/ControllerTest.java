package com.minibank.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.minibank.controller.Controller;
import com.minibank.users.User;
import com.minibank.users.UsersDataBase;
import com.minibank.accounts.Bank;

	

public class ControllerTest {
	static String acct1_1;
	static String acct1_2;
	static String acct2_1;
	static String acct2_2;
	static String acct3_1;
	static String acct3_2;
	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		User user1 = new User("John", "Doe", "user1", "password1"); 
		User user2 = new User("John", "Smith", "user2", "password2"); 
		User user3 = new User("Jane", "Doe", "user3", "password3");

		UsersDataBase udb = UsersDataBase.getUsersDataBase();
		udb.addUser(user1);
		udb.addUser(user2);
		udb.addUser(user3);
		
		Bank bank = Bank.getBank();
		acct1_1 = bank.openAcct("user1"); 
		acct1_2 = bank.openAcct("user1");
		user1.addAcct(acct1_1);
		user1.addAcct(acct1_2);
		
		acct2_1 = bank.openAcct("user2");
		acct2_2 = bank.openAcct("user2");
		user2.addAcct(acct2_1);
		user2.addAcct(acct2_2);
		
		acct3_1 = bank.openAcct("user3"); 
		acct3_2 = bank.openAcct("user3");
		user3.addAcct(acct3_1);
		user3.addAcct(acct3_2);
		
		bank.activateAcct(acct1_1); 
		bank.activateAcct(acct1_2); 
		bank.activateAcct(acct2_1);
		bank.activateAcct(acct2_2);
		bank.activateAcct(acct3_1);
		bank.activateAcct(acct3_2); 
		
		udb.activateUser("user1");
		udb.activateUser("user2");
		udb.activateUser("user3");
				
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		UsersDataBase udb = UsersDataBase.getUsersDataBase();
		udb.removeUser("user1");
		udb.removeUser("user2");
		udb.removeUser("user3");		

		Bank bank = Bank.getBank();
		bank.closeAcct(acct1_1);
		bank.closeAcct(acct1_2);
		bank.closeAcct(acct2_1);
		bank.closeAcct(acct2_2);
		bank.closeAcct(acct3_1);
		bank.closeAcct(acct3_2);
		
	}

	@Before
	public void setUp() throws Exception {
		Controller.initialize();
		Bank bank = Bank.getBank();
		UsersDataBase udb = UsersDataBase.getUsersDataBase();
		
		udb.activateUser("user1");
		udb.activateUser("user2");
		udb.activateUser("user3");
		
		bank.activateAcct(acct1_1); 
		bank.activateAcct(acct1_2); 
		bank.activateAcct(acct2_1);
		bank.activateAcct(acct2_2);
		bank.activateAcct(acct3_1);
		bank.activateAcct(acct3_2); 

	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void userExistsTest() {
		assertEquals(true, Controller.userExists("user1")); 
		assertEquals(true, Controller.userExists("user2")); 
		assertEquals(false, Controller.userExists("user 5")); 	
	}
	
	@Test
	public void checkCredentialsTest() {
		assertEquals(1, Controller.checkCredentials("user1", "password1"));
		assertEquals(1, Controller.checkCredentials("user2", "password2"));
		assertEquals(-1, Controller.checkCredentials("user1", "password2"));
		assertEquals(0, Controller.checkCredentials("Willford", "password1")); 
		
	}
	
	@Test
	public void checkAdminTest() {
		assertEquals(1, Controller.checkAdmin("admin"));
		assertEquals(-1,Controller.checkAdmin("user1"));
		assertEquals(0, Controller.checkAdmin("Wilford"));
	}
	
	@Test
	public void verifyOwnershipTest() {
		assertEquals(1, Controller.verifyOwnership("user1", acct1_1)); 
		assertEquals(1, Controller.verifyOwnership("user2", acct2_2));
		assertEquals(0, Controller.verifyOwnership("user1", "000")); 
		assertEquals(-1, Controller.verifyOwnership("Wilford", acct1_2)); 
		assertEquals(0, Controller.verifyOwnership("111", "aoiejfoi")); 
	}
	
	@Test
	public void depositFunds() {
		Bank bank = Bank.getBank();
		assertEquals(1, Controller.depositFunds(acct1_1, 100.00));		
		assertEquals(1, Controller.depositFunds(acct1_2, 200.00)); 
		assertEquals(1, Controller.depositFunds(acct3_2, 300.00)); 
		assertEquals(0, Controller.depositFunds("Wilford", 100.00)); 
		bank.lockAcct(acct2_1); 
		assertEquals(-2, Controller.depositFunds(acct2_1, 100.00));
		
		double rslt = 1.0;
		if(100.00 != (rslt = bank.balanceInquiry(acct1_1)))
			fail("Incorrect balance after deposit: " + rslt); 
		if(200.00 != (rslt = bank.balanceInquiry(acct1_2)))
			fail("Incorrect balance after deposit: " + rslt); 
		if(300.00 != (rslt = bank.balanceInquiry(acct3_2)))
			fail("Incorrect balance after deposit: " + rslt); 
			
	}
	
	
	@Test
	public void withdrawFundsTest() {
		Bank bank = Bank.getBank();
		assertEquals(1, Controller.withdrawFunds(acct1_1, 10.00));
		double rslt = 0.0; 
		if(90.00 != (rslt = bank.balanceInquiry(acct1_1)))
				fail("Incorrect withdraw after deposit: " + rslt); 
		
		assertEquals(0, Controller.withdrawFunds("000", 10.00));
		if(90.00 != (rslt = bank.balanceInquiry(acct1_1)))
			fail("Incorrect withdraw after deposit: " + rslt); 
		
		bank.lockAcct(acct1_1);
		assertEquals(-2, Controller.withdrawFunds(acct1_1, 10.00));
		if(90.00 != (rslt = bank.balanceInquiry(acct1_1)))
			fail("Incorrect withdraw after deposit: " + rslt); 
		
		String acct4 = bank.openAcct("Wilfordson"); 
		assertEquals(-1, Controller.withdrawFunds(acct4, 10.00));
		if(0.00 != (rslt = bank.balanceInquiry(acct4)))
			fail("Incorrect withdraw after deposit: " + rslt); 
		bank.closeAcct(acct4); 
						
	}
	
	@Test
	public void getAcctStatusTest() {
		Bank bank = Bank.getBank();
		assertEquals(1, Controller.getAcctStatus(acct1_1)); 
		assertEquals(1, Controller.getAcctStatus(acct1_2)); 
		
		String acct4 = bank.openAcct("Wilford"); 
		assertEquals(0, Controller.getAcctStatus(acct4)); 
		
		bank.lockAcct(acct1_2);
		assertEquals(-1, Controller.getAcctStatus(acct1_2)); 
		assertEquals(-2, Controller.getAcctStatus("000")); 
	}
	
	@Test
	public void getUserStatusTest() {
		UsersDataBase udb = UsersDataBase.getUsersDataBase();
		udb.activateUser("user1"); 
		assertEquals(1, Controller.getUserStatus("user1")); 
		assertEquals(1, Controller.getUserStatus("user2"));
		
		udb.addUser(new User("Wilson", "Wilfordson","Wilford","passwordw"));
		assertEquals(0, Controller.getUserStatus("Wilford")); 
		
		udb.lockUser("Wilford");
		assertEquals(-1, Controller.getUserStatus("Wilford")); 
		
		udb.removeUser("Wilford"); 
	}
	
	@Test
	public void balanceInquiryTest() {

		Bank bank = Bank.getBank();
		bank.deposit(acct2_1, 1000.00); 
		double rslt = 0.0;
		if(1000.00 != (rslt = Controller.balanceInquiry(acct2_1)))
			fail("Incorrect balance, expected 1000.00, got " + rslt); 
		
		if(-1.0 != (rslt = Controller.balanceInquiry("000")))
			fail("Incorrect balance, expected 1000.00, got " + rslt); 
		
	}
	
	@Test
	public void creatUserTest() {
		UsersDataBase udb = UsersDataBase.getUsersDataBase();
		Controller.createUser("Wilson", "Wilfordson", "Wilford", "passwordw");
		assertEquals("Wilson", udb.getUser("Wilford").getFirstName());
		assertEquals("Wilfordson", udb.getUser("Wilford").getLastName());
		assertEquals("Wilford", udb.getUser("Wilford").getUserID());
		assertEquals("passwordw", udb.getUser("Wilford").getPassword());
		assertEquals(0, udb.getUser("Wilford").getStatus());
		
		udb.removeUser("Wilford");
		
		User wilf = new User("Wilson", "Wilfordson", "Wilford", "passwordw");  
		Controller.createUser(wilf);
		assertEquals("Wilson", udb.getUser("Wilford").getFirstName());
		assertEquals("Wilfordson", udb.getUser("Wilford").getLastName());
		assertEquals("Wilford", udb.getUser("Wilford").getUserID());
		assertEquals("passwordw", udb.getUser("Wilford").getPassword());
		assertEquals(0, udb.getUser("Wilford").getStatus());
		
		udb.removeUser("Wilford");
		
	}
	
	@Test
	public void createBankAcctTest() {
		Bank bank = Bank.getBank(); 
		
		String acct4 = Controller.createBankAcct("user1");
		assertEquals("user1", bank.getAcctOwnerID(acct4)); 
		if(bank.balanceInquiry(acct4) != 0.0) 
			fail("Incorrect balance; expected 0.0."); 
		
		
	}
	
	

}
