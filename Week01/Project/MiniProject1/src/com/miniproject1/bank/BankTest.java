package com.miniproject1.bank;


import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BankTest {
	
	Bank bank;
	User tester;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bank = new Bank();
		tester = new User("testing", "testingPW");
		tester.setActivated(true);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBalance() {
		tester.setBalance(2000);
		assertEquals(2000, tester.getBalance(),0.001);
	}
	
	@Test
	public void testDeposit() {
		tester.setBalance(2000);
		tester.deposit(200);
		assertEquals(2200, tester.getBalance(),0.001);
	}
	@Test
	public void testWithdraw() {
		tester.setBalance(2000);
		tester.withdraw(200);
		assertEquals(1800, tester.getBalance(),0.001);
	}

	@Test
	public void testCreate() {
		
		User newUser = new User("Bruce", "NotBatman");
		
		assertEquals("Bruce", newUser.getName());
		assertEquals("NotBatman", newUser.getPassword());
		assertEquals(0, newUser.getBalance(),0.001);
		assertEquals(false, newUser.isActivated());
		assertEquals(false, newUser.isLocked());
		assertEquals(false, newUser.isAdmin());
		
	}

}
