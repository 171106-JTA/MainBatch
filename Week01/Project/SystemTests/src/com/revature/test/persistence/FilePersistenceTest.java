package com.revature.test.persistence;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.businessobject.info.account.Account;
import com.revature.businessobject.info.account.Checking;
import com.revature.businessobject.info.account.Credit;
import com.revature.businessobject.info.user.UserInfo;
import com.revature.businessobject.user.Admin;
import com.revature.businessobject.user.Customer;
import com.revature.businessobject.user.User;
import com.revature.core.FieldParams;
import com.revature.core.Resultset;
import com.revature.persistence.file.FileDataManager;
import com.revature.persistence.file.FilePersistence;

public class FilePersistenceTest {
	static FilePersistence manager;
	static Resultset resultset;
	static FieldParams conditions;
	static FieldParams values;
	static User admin;
	static User customer;
	static UserInfo adminInfo;
	static Checking customerCheckingAcct;
	static Credit customerCreditAcct;
	static long adminId;
	static long customerId;
	static String adminUsername;
	static String adminPassword;
	static String customerUsername;
	static String customerPassword;
	static long checkingId;
	static long creditId;
	@BeforeClass
	public static void setupBeforeClass() {
		manager = FileDataManager.getManager();
		adminId = 1423412;
		adminUsername = "myAdmin";
		adminPassword = "adogmsjdfh";
		
		customerId = 42135132;
		customerUsername = "billy";
		customerPassword = "password";
		checkingId = 518973822;
	}
	
	@Before
	public void setUp() throws Exception {
		resultset = new Resultset();
		conditions = new FieldParams();
		values = new FieldParams();
		admin = new Admin(adminId, adminUsername, adminPassword);
		customer = new Customer(customerId, customerUsername, customerPassword);
		adminInfo = new UserInfo(adminId, "abc@xyz.com", "My Street and stuff", "1234567890");
		customerCheckingAcct = new Checking(customerId, checkingId, 100.0f);
		customerCreditAcct = new Credit(customerId, creditId, 345.0f, 10.5f, 1500.0f);
		FilePersistence.setDirectory(System.getProperty("user.dir") + "\\data\\");
	}

	@After
	public void tearDown() throws Exception {
		FilePersistence.deleteData();
	}

	@Test
	public void shouldStoreDataInCurrentWorkingDirectory() {
		assertEquals("Should store data in working directory", System.getProperty("user.dir") + "\\data\\", FilePersistence.getDirectory());
	}
	
	@Test
	public void shouldStoreDataInDifferentDirectory() {
		FilePersistence.setDirectory("C:\\");
		assertEquals("Should store data in different directory", "C:\\", FilePersistence.getDirectory());
	}
	
	///
	// Data management Class Tests
	//
	//	USER RECORDS
	///
	
	/**
	 * Creates admin account using BusinessObject
	 */
	@Test
	public void shouldCreateNewAdminUserUsingBusinessObject() {
		assertEquals("Should add admin with", 1, manager.insert(admin));
	}
	
	/**
	 * Creates admin account using FieldParams
	 */
	@Test
	public void shouldCreateNewAdminUserUsingFieldParams() {
		// Set data
		values.put("id", Long.toString(adminId));
		values.put("role", Integer.toString(admin.getRole().ordinal()));
		
		// Perform test
		assertEquals("Should add admin with", 1, manager.insert("User", values));
	}
	
	/**
	 * Should be able to load user from memory 
	 */
	@Test
	public void shouldBeAbleToGetExistingUser() {
		// Set condition to pull from manager
		conditions.put("id", Long.toString(adminId));
		manager.insert(admin);
		
		// Perform test
		assertNotNull("Should get handle to list of business objects", resultset = manager.select("User", conditions));
		assertEquals("Should have one record in resultset", 1, resultset.size());
		assertTrue("Record should be newly created admin", admin.equals(resultset.get(0)));
	}
	
	/**
	 * Attempts to update an existing record using instance of BusinessObject
	 */
	@Test
	public void shouldUpdateUserByBusinessObject() {
		User demote = new Customer(adminId, adminUsername, adminPassword);
		
		// Set data
		conditions.put("id", Long.toString(adminId));
		manager.insert(admin);
		
		// Update account (demote to customer)
		assertEquals("Should demote admin account to customer", 1, manager.update(demote));
		assertNotNull("Should be able to get updated record", resultset = manager.select("User", conditions));
		assertEquals("Should have one record in resultset", 1, resultset.size());
		assertTrue("Record in system should reflect changes", demote.equals(resultset.get(0)));
	}

	/**
	 * Attempts to update an existing record using FieldParams instance 
	 */
	@Test
	public void shouldUpdateUserByFieldParams() {
		User demote = new Customer(adminId, adminUsername, adminPassword);
		// Set data
		conditions.put("id", Long.toString(adminId));
		values.put("role", Integer.toString(demote.getRole().ordinal()));
		manager.insert(admin);
		
		// Update Account
		assertEquals("Should demote admin account to customer", 1, manager.update("User", conditions, values));
		assertNotNull("Should be able to get updated record", resultset = manager.select("User", conditions));
		assertEquals("Should have one record in resultset", 1, resultset.size());
		assertTrue("Record in system should reflect changes", demote.equals(resultset.get(0)));
	}
	
	/**
	 * Should delete all User records from system
	 */
	@Test
	public void shouldRemoveAllUserRecords() {
		// Set data
		manager.insert(admin);
		manager.insert(customer);
		
		// Perform test
		assertEquals("Should all users from system (2 users)", 2, manager.delete("User", null));
		assertEquals("Should have 0 users in system", 0, manager.select("User", null).size());
	}
	
	/**
	 * Should be able to remove existing user from system with BusinessObject
	 */
	@Test
	public void shouldRemoveUserByBusinessObject() {
		// Set data
		manager.insert(admin);
		
		// Perform test
		assertEquals("Should delete admin account", 1, manager.delete(admin));
	}
	
	/**
	 * Should be able to remove existing user from system with FieldParams
	 */
	@Test
	public void shouldRemoveUserByFieldParams() {
		// Set data
		conditions.put("id", Long.toString(adminId));
		manager.insert(admin);
		
		// Perform test
		assertEquals("Should delete admin account", 1, manager.delete("User", conditions));
	}
	
	///
	//	USERINFO TESTS
	///
	
	@Test 
	public void shouldCreateNewUserInfoWithBusinessObject() {
		assertEquals("Should create UserInfo record", 1, manager.insert(adminInfo));
	}
	
	@Test 
	public void shouldCreateNewUserInfoWithFieldParams() {
		FieldParams params = new FieldParams();
		
		// Set data
		params.put("userid", Long.toString(adminId));
		params.put("email", "abc@xyz.com");
		params.put("address", "My Street and Stuff");
		params.put("pnonenumber", "1234567890");
		
		// Test
		assertEquals("Should create UserInfo record", 1, manager.insert("userinfo", params));
	}
	
	@Test
	public void shouldBeAbleToGetExistingUserInfo() {
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set data 
		cnds.put("userid", Long.toString(adminId));
		manager.insert(adminInfo);
		
		// Perform test
		assertNotNull("Should get resultset for userinfo query", resultset = manager.select("userinfo", cnds));
		assertTrue("Should exist single record from query", resultset.size() == 1);
		assertEquals(adminInfo, resultset.get(0));
	}
	
	@Test
	public void shouldBeAbleToUpdateExistingUserInfoRecordWithBusinessObject() {
		UserInfo data = new UserInfo(adminId, "new_email@xyz.com", "new place dr. something", "0987654321");
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set data
		cnds.put("userid", Long.toString(adminId));
		manager.insert(adminInfo);
		
		// Perform test
		assertEquals("Should update a single userinfo record", 1, manager.update(data));
		assertNotNull("Should have resultset from query", resultset = manager.select("userinfo", cnds));
		assertTrue("Should exist single record from query", resultset.size() == 1);
		assertFalse(adminInfo.equals(resultset.get(0)));
		assertEquals(data, resultset.get(0));
	}
	
	
	@Test
	public void shouldBeAbleToUpdateExistingUserInfoRecordWithFieldParams() {
		UserInfo data = new UserInfo(adminId, "new_email@xyz.com", "new place dr. something", "0987654321");
		FieldParams params = new FieldParams();
		FieldParams cnds = new FieldParams();
		
		// Set data
		cnds.put("userid", Long.toString(adminId));
		params.put("email", "new_email@xyz.com");
		params.put("address", "new place dr. something");
		params.put("phonenumber", "0987654321");
		manager.insert(adminInfo);
		
		
		// Perform test
		assertEquals("Should update a single userinfo record", 1, manager.update("userinfo", cnds, params));
		assertNotNull("Should have resultset from query", resultset = manager.select("userinfo", cnds));
		assertTrue("Should exist single record from query", resultset.size() == 1);
		assertFalse(adminInfo.equals(resultset.get(0)));
		assertEquals(data, resultset.get(0));
	}
	
	@Test 
	public void shouldRemoveUserInfoWithBusinessObject() {
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set data 
		cnds.put("userid", Long.toString(adminId));
		manager.insert(adminInfo);
		
		assertEquals("Should remove admin userinfo record", 1, manager.delete(adminInfo));
		assertNotNull("Should have resultset from query", resultset = manager.select("userinfo", cnds));
		assertTrue("Should have 0 records", resultset.size() == 0);
	}
	
	@Test 
	public void shouldRemoveUserInfoWithFieldParams() {
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set data 
		cnds.put("userid", Long.toString(adminId));
		manager.insert(adminInfo);
		
		assertEquals("Should remove admin userinfo record", 1, manager.delete("userinfo", cnds));
		assertNotNull("Should have resultset from query", resultset = manager.select("userinfo", cnds));
		assertTrue("Should have 0 records", resultset.size() == 0);
	}
	
	@Test
	public void shouldRemoveAllUserInfoRecords() {
		Resultset resultset;
		
		// Set data
		manager.insert(adminInfo);
		manager.insert(new UserInfo(customerId, "me@people.com", "cardboard box", ""));
		
		// Perform test
		assertEquals("Should exist a total of 2 userinfo records", 2, manager.select("userinfo", null).size());
		assertEquals("Should remove all userinfo records", 2, manager.delete("userinfo", null));
		assertNotNull("Should have resultset from query", resultset = manager.select("userinfo", null));
		assertTrue("Should have 0 records", resultset.size() == 0);
	}
	
	///
	//	ACCOUNT TESTS
	//	
	//	CHECKING ACCOUNTS
	///
	

	@Test
	public void shouldCreateNewCheckingAccountWithBusinessObject() {
		assertEquals("Should create new checking account", 1, manager.insert(customerCheckingAcct));
	}
	
	@Test
	public void shouldCreateNewCheckingAccountWithFieldParams() {
		FieldParams params = new FieldParams();
		
		// Set data;
		params.put("userid", Long.toString(customerId));
		params.put("number", Long.toString(checkingId));
		params.put("total", Float.toString(100.0f));
		params.put("type", Integer.toString(customerCheckingAcct.getType().ordinal()));
		
		// Perform test
		assertEquals("Should add new account with fieldparams", 1, manager.insert("account", params));
	}
	
	@Test
	public void shouldBeAbleToGetExistingCheckingAccount() {
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set data
		cnds.put("userid", Long.toString(customerId));
		manager.insert(customerCheckingAcct);
		
		// Perform test
		assertNotNull("Should have resultset from query", resultset = manager.select("account", cnds));
		assertTrue("Should exist single record from query", resultset.size() == 1);
		assertEquals(customerCheckingAcct, resultset.get(0));
	}
	
	@Test
	public void shouldBeAbleToUpdateExistingCheckingAccountRecordWithBusinessObject() {
		Account data = new Checking(customerId, checkingId, 20.0f);
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set Data 
		cnds.put("userid", Long.toString(customerId));
		manager.insert(customerCheckingAcct);
		
		// Perform test
		assertEquals("Should update a single account record", 1, manager.update(data));
		assertNotNull("Should have resultset from query", resultset = manager.select("account", cnds));
		assertTrue("Should exist single record from query", resultset.size() == 1);
		assertFalse(customerCheckingAcct.equals(resultset.get(0)));
		assertEquals(data, resultset.get(0));
	}
	
	@Test
	public void shouldBeAbleToUpdateExistingCheckingAccountWithFieldParams() {
		Account data = new Checking(customerId, checkingId, 1.0f);
		FieldParams cnds = new FieldParams();
		FieldParams params = new FieldParams();
		Resultset resultset;
		
		// Set Data 
		cnds.put("userid", Long.toString(customerId));
		params.put("total", Float.toString(1.0f));
		manager.insert(customerCheckingAcct);
		
		// Perform test
		assertEquals("Should update a single account record", 1, manager.update("account", cnds, params));
		assertNotNull("Should have resultset from query", resultset = manager.select("account", cnds));
		assertTrue("Should exist single record from query", resultset.size() == 1);
		assertFalse(customerCheckingAcct.equals(resultset.get(0)));
		assertEquals(data, resultset.get(0));
	}
	
	@Test 
	public void shouldRemoveCheckingAccountWithBusinessObject() {
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set data 
		cnds.put("userid", Long.toString(customerId));
		manager.insert(customerCheckingAcct);
		
		assertEquals("Should remove admin checking acct record", 1, manager.delete(customerCheckingAcct));
		assertNotNull("Should have resultset from query", resultset = manager.select("account", cnds));
		assertTrue("Should have 0 records", resultset.size() == 0);
	}
	
	@Test 
	public void shouldRemoveCheckingAccountWithFieldParams() {
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set data 
		cnds.put("userid", Long.toString(customerId));
		manager.insert(customerCheckingAcct);
		
		assertEquals("Should remove checking record", 1, manager.delete("account", cnds));
		assertNotNull("Should have resultset from query", resultset = manager.select("account", cnds));
		assertTrue("Should have 0 records", resultset.size() == 0);
	}
	
	@Test
	public void shouldRemoveAllCheckingAccountRecords() {
		Resultset resultset;
		
		// Set data
		manager.insert(customerCheckingAcct);
		manager.insert(new Checking(41181, 512423, 1.0f));
		
		// Perform test
		assertEquals("Should exist a total of 2 checking records", 2, manager.select("account", null).size());
		assertEquals("Should remove all checking records", 2, manager.delete("account", null));
		assertNotNull("Should have resultset from query", resultset = manager.select("account", null));
		assertTrue("Should have 0 records", resultset.size() == 0);
	}
	
	///
	//	CREDIT ACCOUNT TESTS 
	///

	@Test
	public void shouldCreateNewCreditAccountWithBusinessObject() {
		assertEquals("Should create new credit account", 1, manager.insert(customerCreditAcct));
	}
	
	@Test
	public void shouldCreateNewCreditAccountWithFieldParams() {
		FieldParams params = new FieldParams();
		
		// Set data;
		params.put("userid", Long.toString(customerId));
		params.put("number", Long.toString(checkingId));
		params.put("total", Float.toString(100.0f));
		params.put("type", Integer.toString(customerCheckingAcct.getType().ordinal()));
		params.put("interest", Float.toString(10.05f));
		params.put("creditlimit", Float.toString(1500.0f));
		
		// Perform test
		assertEquals("Should add new account with fieldparams", 1, manager.insert("account", params));
	}
	
	@Test
	public void shouldBeAbleToGetExistingCreditAccount() {
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set data
		cnds.put("userid", Long.toString(customerId));
		manager.insert(customerCreditAcct);
		
		// Perform test
		assertNotNull("Should have resultset from query", resultset = manager.select("account", cnds));
		assertTrue("Should exist single record from query", resultset.size() == 1);
		assertEquals(customerCreditAcct, resultset.get(0));
	}
	
	@Test
	public void shouldBeAbleToUpdateExistingCreditAccountRecordWithBusinessObject() {
		Account data = new Credit(customerId, creditId, 20.0f, 100.0f, 1500.0f);
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set Data 
		cnds.put("userid", Long.toString(customerId));
		manager.insert(customerCreditAcct);
		
		// Perform test
		assertEquals("Should update a single account record", 1, manager.update(data));
		assertNotNull("Should have resultset from query", resultset = manager.select("account", cnds));
		assertTrue("Should exist single record from query", resultset.size() == 1);
		assertFalse(customerCreditAcct.equals(resultset.get(0)));
		assertEquals(data, resultset.get(0));
	}
	
	@Test
	public void shouldBeAbleToUpdateExistingCreditAccountWithFieldParams() {
		Account data = new Credit(customerId, creditId, 1.0f, customerCreditAcct.getInterest(), customerCreditAcct.getCreditLimit());
		FieldParams cnds = new FieldParams();
		FieldParams params = new FieldParams();
		Resultset resultset;
		
		// Set Data 
		cnds.put("userid", Long.toString(customerId));
		params.put("total", Float.toString(1.0f));
		manager.insert(customerCreditAcct);
		
		// Perform test
		assertEquals("Should update a single account record", 1, manager.update("account", cnds, params));
		assertNotNull("Should have resultset from query", resultset = manager.select("account", cnds));
		assertTrue("Should exist single record from query", resultset.size() == 1);
		assertFalse(customerCreditAcct.equals(resultset.get(0)));
		assertEquals(data, resultset.get(0));
	}
	
	@Test 
	public void shouldRemoveCreditAccountWithBusinessObject() {
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set data 
		cnds.put("userid", Long.toString(customerId));
		manager.insert(customerCreditAcct);
		
		assertEquals("Should remove credit acct record", 1, manager.delete(customerCreditAcct));
		assertNotNull("Should have resultset from query", resultset = manager.select("account", cnds));
		assertTrue("Should have 0 records", resultset.size() == 0);
	}
	
	@Test 
	public void shouldRemoveCreditAccountWithFieldParams() {
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set data 
		cnds.put("userid", Long.toString(customerId));
		manager.insert(customerCreditAcct);
		
		assertEquals("Should remove credit record", 1, manager.delete("account", cnds));
		assertNotNull("Should have resultset from query", resultset = manager.select("account", cnds));
		assertTrue("Should have 0 records", resultset.size() == 0);
	}
	
	@Test
	public void shouldRemoveAllCreditAccountRecords() {
		Resultset resultset;
		
		// Set data
		manager.insert(customerCreditAcct);
		manager.insert(new Credit(41181, 512423, 1.0f, 10.0f, 1500.0f));
		
		// Perform test
		assertEquals("Should exist a total of 2 credit acct records", 2, manager.select("account", null).size());
		assertEquals("Should remove all account records", 2, manager.delete("account", null));
		assertNotNull("Should have resultset from query", resultset = manager.select("account", null));
		assertTrue("Should have 0 records", resultset.size() == 0);
	}
	
}
