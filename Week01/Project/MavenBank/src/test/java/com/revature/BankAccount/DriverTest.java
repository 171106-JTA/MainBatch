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
	
//	String filename = "database.txt";
	String filename = "basic_database_1.txt";
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
//		try {
//			String filename = "src/test/resorces/basic_database_1.txt";
//			System.out.println("filename: " + filename);
//			dr.readDatabase(filename);
//			System.out.println("The Database: " + dr.getDb());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		HashMap<String,User> sampleDb = sampleDatabase1();
		dr.setDb(sampleDb);
		
		
		String user1 = "evanwest"; 
		User user = dr.getDb().get(user1);
		System.out.println("Hello___USER!!!" + user);
		
//		assertNotNull(user);
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
			dr.readDatabase(filename);
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
		
		//Note: even though B.status = 0, still expect login=true. 
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
			dr.readDatabase(filename);
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
			dr.readDatabase(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Not a user in the database
		String user_1 = "Not A User"; 
		boolean result_1 = dr.getAndUnlockAccount(user_1);
		assertFalse(result_1);
		
		//A user whose account is not approved
		String user_2 = "B";
		boolean result_2 = dr.getAndLockAccount(user_2);
		assertFalse(result_2);
		
		//A user that is already unlocked
		String user_3 = "Z";
		boolean result_3 = dr.getAndUnlockAccount(user_3);
		assertFalse(result_3);	
		
		//A user that needs to be unlocked
		String user_4 = "C";
		boolean result_4 = dr.getAndUnlockAccount(user_4);
		assertTrue(result_4);
		//Check that the status was actually changed
		int newStatus_4 = dr.getDb().get(user_4).getStatus();
		assertEquals(newStatus_4, 1);
	}
	
	@Test
	public void testGetAndLockAccount() {
		try {
			dr.readDatabase(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Not a user in the database
		String user_1 = "Not A User"; 
		boolean result_1 = dr.getAndLockAccount(user_1);
		assertFalse(result_1);
		
		//A user whose account is not approved
		String user_2 = "B";
		boolean result_2 = dr.getAndLockAccount(user_2);
		assertFalse(result_2);
		
		//A user that is already locked
		String user_3 = "C";
		boolean result_3 = dr.getAndLockAccount(user_3);
		assertFalse(result_3);	
		
		//A user that can be locked
		String user_4 = "Z";
		boolean result_4 = dr.getAndLockAccount(user_4);
		assertTrue(result_4);	
		//Check that the status was actually changed
		int newStatus_4 = dr.getDb().get(user_4).getStatus();
		assertEquals(newStatus_4, 2);
	}
	
	@Test
	public void testGetAndApproveAccount() {
		try {
			dr.readDatabase(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Not a user in the database
		String user_1 = "Not A User"; 
		boolean result_1 = dr.getAndApproveAccount(user_1);
		assertFalse(result_1);
		
		//A user whose account active (i.e. already approved)
		String user_2 = "Z";
		boolean result_2 = dr.getAndApproveAccount(user_2);
		assertFalse(result_2);
		
		//A user that is locked (i.e. aproved, but locked)
		String user_3 = "C";
		boolean result_3 = dr.getAndApproveAccount(user_3);
		assertFalse(result_3);	
		
		//A user that can be approved
		String user_4 = "B";
		boolean result_4 = dr.getAndApproveAccount(user_4);
		assertTrue(result_4);	
		//Check that the status was actually changed
		int newStatus_4 = dr.getDb().get(user_4).getStatus();
		assertEquals(newStatus_4, 1);
	}
	
	@Test
	public void testGetAndPromoteClient() {
		try {
			dr.readDatabase(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Not a user in the database
		String user_1 = "Not A User"; 
		boolean result_1 = dr.getAndApproveAccount(user_1);
		assertFalse(result_1);
		
		//An active client account
		String user_2 = "Z";
		boolean result_2 = dr.getAndApproveAccount(user_2);
		assertFalse(result_2);
		
		//A locked client account
		String user_3 = "C";
		boolean result_3 = dr.getAndApproveAccount(user_3);
		assertFalse(result_3);
		
		//A client account awaiting approval
		String user_4 = "B";
		boolean result_4 = dr.getAndApproveAccount(user_4);
		assertTrue(result_4);	
		//Check that the status was actually changed
		int newStatus_4 = dr.getDb().get(user_4).getStatus();
		assertEquals(newStatus_4, 1);
		
		//An Admin Account
		String user_5 = "A";
		boolean result_5 = dr.getAndApproveAccount(user_5);
		assertFalse(result_5);	
		
	}
	
	@Test
	public void testSetAmount() {
		try {
			dr.readDatabase(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		User myUser = dr.getDb().get("Z");
		System.out.println("My User: " + myUser);
		dr.setCurrentUser(myUser);
		System.out.println(dr.getCurrentUser().getSsn());
		
		double amount = 50.0;
		dr.setAmount(amount);
		double stored_amount = dr.getDb().get("Z").getAccountAmount();
		assertTrue(stored_amount == amount);
	}
	
	//Setup local database
	public HashMap<String,User> sampleDatabase1() {
		String firstName = "A";
		String lastName = "A";
		String middleInitial = "A";
		String ssn = "A";
		String password = "A";
		int permissions = 1;
		int status = 1;
		int accountAmount = 0;
		User default_user_A = new User(firstName, lastName, middleInitial, ssn, password, permissions, status,
				accountAmount);

		firstName = "Z";
		lastName = "Z";
		middleInitial = "Z";
		ssn = "Z";
		password = "Z";
		permissions = 0;
		status = 1;
		accountAmount = 0;
		User default_user_Z = new User(firstName, lastName, middleInitial, ssn, password, permissions, status,
				accountAmount);

		firstName = "B";
		lastName = "B";
		middleInitial = "B";
		ssn = "B";
		password = "B";
		permissions = 0;
		status = 0;
		accountAmount = 0;
		User default_user_B = new User(firstName, lastName, middleInitial, ssn, password, permissions, status,
				accountAmount);
		
		String stuff = "C"; 
		firstName = stuff;
		lastName = stuff;
		middleInitial = stuff;
		ssn = stuff;
		password = stuff;
		permissions = 0;
		status = 2;
		accountAmount = 0;
		User default_user_C = new User(firstName, lastName, middleInitial, ssn, password, permissions, status,
				accountAmount);
		
		stuff = "D"; 
		firstName = stuff;
		lastName = stuff;
		middleInitial = stuff;
		ssn = stuff;
		password = stuff;
		permissions = 1;
		status = 0;
		accountAmount = 0;
		User default_user_D = new User(firstName, lastName, middleInitial, ssn, password, permissions, status,
				accountAmount);
		
		stuff = "E"; 
		firstName = stuff;
		lastName = stuff;
		middleInitial = stuff;
		ssn = stuff;
		password = stuff;
		permissions = 1;
		status = 2;
		accountAmount = 0;
		User default_user_E = new User(firstName, lastName, middleInitial, ssn, password, permissions, status,
				accountAmount);
		
		HashMap<String, User> db = new HashMap<String,User>();
		db.put(default_user_A.getSsn(), default_user_A);
		db.put(default_user_Z.getSsn(), default_user_Z);
		db.put(default_user_B.getSsn(), default_user_B);
		db.put(default_user_C.getSsn(), default_user_C);
		db.put(default_user_D.getSsn(), default_user_D);
		db.put(default_user_E.getSsn(), default_user_E);
		
		return db;
	}

}
