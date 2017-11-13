package com.revature.BankAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

public class DriverTest {
	Driver dr = new Driver();
	
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
//
//	@Before
//	public void setUp() throws Exception {
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}

//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}
	@Test 
	public void testReadDatabase() {
		
		
		try {
			String filename = "src/test/resorces/basic_database_1.txt";
//			System.out.println("filename: " + filename);
			dr.readDatabase(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertNotNull(dr.getDb());
//		HashMap<String, User> local_db = sampleDatabase1();
		
//		System.out.println("Local DB");
//		for (String key : local_db.keySet()) {
//			System.out.println(local_db.get(key));
//		}
//		System.out.println("Driver's DB");
//		for (String key : dr.getDb().keySet()) {
//			System.out.println(dr.getDb().get(key));
//		}
//		boolean value = local_db.equals(dr.getDb());
//		System.out.println("value: " + value);
//		assertTrue(value);
	}

	@Test
	public void testLoginLogic() {
		try {
			dr.readDatabase("basic_database_1.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String ssn_1 = "Not an SSN";
		String password_1 = "Not a password";
		boolean result_1 = dr.loginLogic(ssn_1, password_1);
		System.out.println("Result1: " + result_1);
		assertFalse(result_1);
		
		String ssn_2 = "A";
		String password_2 = "A";
		boolean result_2 = dr.loginLogic(ssn_2, password_2);
		System.out.println("Result2: " + result_2);
		assertTrue(result_2);
		
		//Note: even thought B.status = 0, still expect login=true. 
		//      Will need to fix the login in controlLogin(), eventually
		String ssn_3 = "B";
		String password_3 = "B";
		boolean result_3 = dr.loginLogic(ssn_3, password_3);
		System.out.println("Result3: " + result_3);
		assertTrue(result_3);
		
		String ssn_4 = "Z";
		String password_4 = "Z";
		boolean result_4 = dr.loginLogic(ssn_4, password_4);
		System.out.println("Result4: " + result_4);
		assertTrue(result_4);
	}
	
	@Test
	public void testAddNewClientToDatabase() {
		try {
			dr.readDatabase("basic_database_1.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String firstName = "D";
		String lastName = "D";
		String middleInitial = "D";
		String ssn = "D";
		String password = "D";
		int permissions = 0;
		int status = 0;
		int accountAmount = 0;
		User newUser = new User(firstName, lastName, middleInitial, ssn, password, permissions, status,
				accountAmount);
		
		dr.addNewClientToDatabase(newUser);		
		User insertedUser = dr.getDb().get(newUser.getSsn());
		
		//Test that the user was actually inserted into the database
		assertTrue(newUser.equals(insertedUser));
	}
	
	@Test
	public void testGetAndUnlockAccount() {
		try {
			dr.readDatabase("basic_database_1.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Not a user in the database
		String user_1 = "Not A User"; 
		boolean result_1 = dr.getAndUnlockAccount(user_1);
		assertFalse(result_1);
		
		//A user that needs to be unlocked
		String user_2 = "C";
		boolean result_2 = dr.getAndUnlockAccount(user_2);
		assertTrue(result_2);
		
		int newStatus_2 = dr.getDb().get(user_2).getStatus();
		assertEquals(newStatus_2, 1);
		
		//A user that does not need to be unlocked
		String user_3 = "Z";
		boolean result_3 = dr.getAndUnlockAccount(user_3);
		assertFalse(result_3);		
	}
	
	//Setup local database
//	public HashMap<String,User> sampleDatabase1() {
//		String firstName = "A";
//		String lastName = "A";
//		String middleInitial = "A";
//		String ssn = "A";
//		String password = "A";
//		int permissions = 0;
//		int status = 0;
//		int accountAmount = 0;
//		User default_user_1 = new User(firstName, lastName, middleInitial, ssn, password, permissions, status,
//				accountAmount);
//		
//		firstName = "Z";
//		lastName = "Z";
//		middleInitial = "Z";
//		ssn = "Z";
//		password = "Z";
//		permissions = 0;
//		status = 0;
//		accountAmount = 0;
//		User default_user_2 = new User(firstName, lastName, middleInitial, ssn, password, permissions, status,
//				accountAmount);
//
//		firstName = "B";
//		lastName = "B";
//		middleInitial = "B";
//		ssn = "B";
//		password = "B";
//		permissions = 0;
//		status = 0;
//		accountAmount = 0;
//		User default_user_3 = new User(firstName, lastName, middleInitial, ssn, password, permissions, status,
//				accountAmount);
//		
//		HashMap<String, User> db = new HashMap<String, User>();
//		db.put(default_user_1.getSsn(), default_user_1);
//		db.put(default_user_2.getSsn(), default_user_2);
//		db.put(default_user_3.getSsn(), default_user_3);
//		
//		return db;
//	}

}
