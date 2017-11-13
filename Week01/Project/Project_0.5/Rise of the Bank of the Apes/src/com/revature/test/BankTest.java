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
import com.revature.data.ProcessData;
import com.revature.ui.UserInterface;
import com.revature.users.User;

public class BankTest {
	BankOfTheApes bota;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bota = BankOfTheApes.getBank();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSerialization() {
		String fileName = "test.ser";
		User a = new User("a", "a");
		HashMap<String, User> hm = new HashMap<>();
		hm.put("a", new User("a", "a"));
		ProcessData.serialize(hm, fileName);
		
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
}
