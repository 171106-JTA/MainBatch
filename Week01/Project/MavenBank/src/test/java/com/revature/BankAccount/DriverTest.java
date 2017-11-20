package com.revature.BankAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

import com.revature.dao.UserDaoImplement;

public class DriverTest {
	Driver dr = new Driver();
	
//	String filename = "database.txt";
//	String filename = "basic_database_1.txt";
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
//	@Test 
//	public void testReadDatabase() {
////		try {
////			String filename = "src/test/resorces/basic_database_1.txt";
////			System.out.println("filename: " + filename);
////			dr.readDatabase(filename);
////			System.out.println("The Database: " + dr.getDb());
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
//		
//		HashMap<String,User> sampleDb = sampleDatabase1();
//		dr.setDb(sampleDb);
//		
//		
//		String user1 = "evanwest"; 
//		User user = dr.getDb().get(user1);
//		System.out.println("Hello___USER!!!" + user);
//		
////		assertNotNull(user);
////		HashMap<String, User> local_db = sampleDatabase1();
//		
////		System.out.println("Local DB");
////		for (String key : local_db.keySet()) {
////			System.out.println(local_db.get(key));
////		}
////		System.out.println("Driver's DB");
////		for (String key : dr.getDb().keySet()) {
////			System.out.println(dr.getDb().get(key));
////		}
////		boolean value = local_db.equals(dr.getDb());
////		System.out.println("value: " + value);
////		assertTrue(value);
//	}

	@Test
	public void testLoginLogic() {		
		dr.initializeDao();
		
		String username_1 = "Not_a_user";
		String password_1 = "Not_a_password";
		boolean result_1 = dr.loginLogic(username_1, password_1);
//		System.out.println("Result1: " + result_1);
		assertFalse(result_1);
		
		//Note, users A, B, C, and D 
		//are currently seeded into the database by
		//default. 
		
		//Note: even though A.status = 0, still expect login=true. 
		//for error message purposes
		String username_2 = "A";
		String password_2 = "A";
		boolean result_2 = dr.loginLogic(username_2, password_2);
//		System.out.println("Result2: " + result_2);
		assertTrue(result_2);
		
		String username_3 = "B";
		String password_3 = "B";
		boolean result_3 = dr.loginLogic(username_3, password_3);
//		System.out.println("Result3: " + result_3);
		assertTrue(result_3);
		
		String username_4 = "C";
		String password_4 = "C";
		boolean result_4 = dr.loginLogic(username_4, password_4);
//		System.out.println("Result4: " + result_4);
		assertTrue(result_4);
		
		//Note: even though A.status = 0, still expect login=true,
		//for error message purposes
		String username_5 = "D";
		String password_5 = "D";
		boolean result_5 = dr.loginLogic(username_5, password_5);
//		System.out.println("Result5: " + result_5);
		assertTrue(result_5);
	}
	
	@Test
	public void testAddNewClientToDatabase() {
		dr.initializeDao();
		
//		String firstName = "Y";
//		String lastName = "Y";
//		String middleInitial = "Y";
//		String ssn = "Y";
//		String password = "Y";
//		int permissions = 0;
//		int status = 0;
//		int accountAmount = 0;
//		User newUser = new User(firstName, lastName, middleInitial, ssn, password, permissions, status,
//				accountAmount);
		
		//Commented out because there is no 'remove_user' function to reset the insert
//		//First call, expect TRUE
//		boolean result1 = dr.addNewClientToDatabase(newUser);	
//		assertTrue(result1);
		
		String firstName = "A";
		String lastName = "A";
		String middleInitial = "A";
		String username = "A";
		String password = "A";
		int permissions = 0;
		int status = 0;
		int accountAmount = 0;
		User newUser = new User(firstName, lastName, middleInitial, username, password, permissions, status,
				accountAmount);
		
		boolean result2 = dr.addNewClientToDatabase(newUser);
		assertFalse(result2);
	}
	
	@Test
	public void testCheckAndChangeStatusAndPermissions() {
		//Stub
	}

	@Test
	public void testSetAmount() {
		//Stub
	}
}
