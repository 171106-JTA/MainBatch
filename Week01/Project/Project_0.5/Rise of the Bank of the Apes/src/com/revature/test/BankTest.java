package com.revature.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.apebank.BankOfTheApes;
import com.revature.data.Logging;
import com.revature.data.ProcessData;
import com.revature.ui.ApplyLoan;
import com.revature.ui.Deposit;
import com.revature.ui.UserInterface;
import com.revature.ui.Withdraw;
import com.revature.users.User;

public class BankTest {
	BankOfTheApes bota;
	Logging l;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bota = BankOfTheApes.getBank();
		l = Logging.getLogging();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetBank() {
		//Tests whether the singleton is working
		assertNotNull(BankOfTheApes.getBank());
	}
	
	@Test
	public void testGetLogging() {
		//Tests whether the singleton is working
		assertNotNull(Logging.getLogging());
	}
	
	@Test
	public void testSerialization() {
		//Serialization
		String fileName = "test.ser";
		User a = new User("a", "a");
		HashMap<String, User> hm = new HashMap<>();
		hm.put("a", new User("a", "a"));
		ProcessData.serialize(hm, fileName);
		//Unserialization
		assert(ProcessData.unserialize(fileName) instanceof HashMap);
		assertNotNull(ProcessData.unserialize(fileName));
		assertEquals(ProcessData.unserialize(fileName).get("a").getName(), a.getName());
	}
	
	@Test
	public void testLoginIncorrect() {
		User a = new User("a", "a");
		HashMap<String, User> hm = new HashMap<>();
		hm.put("a", new User("a", "a"));
		
		System.out.println("===Incorrect Input===\n");
		/*
		 * Test result of incorrect inputs;
		 * Null should be returned
		 */
		assertEquals(UserInterface.loginScreen(hm), null);
	}
	
	@Test
	public void testLoginCorrect() {
		User a = new User("a", "a");
		a.setApproved(true);
		HashMap<String, User> hm = new HashMap<>();
		hm.put("a", a);
		
		System.out.println("\n===Correct Input===\n");
		/*
		 * Test result of correct inputs;
		 * User a should be returned
		 */
		assertEquals(UserInterface.loginScreen(hm), a);
	}
	
	@Test
	public void testDeposit() {
		User a = new User("a", "a");
		Deposit.Screen(a);
		/*
		 * User input = 5
		 * account balance = 0
		 * result should be 5
		 */
		assertEquals(5.0, a.getBalance(), 0D);
	}
	
	@Test
	public void testWithdraw() {
		//Test withdrawing money
		User a = new User("a", "a");
		a.setBalance(50);
		Withdraw.Screen(a);
		/*
		 * User input = 23
		 * account balance = 50
		 * result should be 27
		 */
		assertEquals(27.0, a.getBalance(), 0D);
	}
	
	@Test
	public void testRepayLoan() {
		//Test repaying loans
		User a = new User("a", "a");
		a.setBalance(50);
		a.getLoan().setAmount(40);
		a.getLoan().setApproval(true);
		ApplyLoan.Screen(a);
		/*
		 * Loan amount is 40
		 * User has 50 in account
		 * The account should have 10 after repayment
		 */
		assertEquals(10.0, a.getBalance(), 0D);
	}
	
	@Test
	public void testApplyLoan() {
		User a = new User("a", "a");
		ApplyLoan.Screen(a);
		/*
		 * user input for loan amount is 5
		 */
		assertEquals(5.0, a.getLoan().getAmount(), 0D);
	}
}
