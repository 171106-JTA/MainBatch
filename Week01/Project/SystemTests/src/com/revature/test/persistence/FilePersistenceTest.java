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
import com.revature.core.BusinessClass;
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
		values.put(User.ID, Long.toString(adminId));
		values.put(User.CHECKPOINT, Integer.toString(admin.getCheckpoint().ordinal()));
		
		// Perform test
		assertEquals("Should add admin with", 1, manager.insert(BusinessClass.USER, values));
	}
	
	/**
	 * Should be able to load user from memory 
	 */
	@Test
	public void shouldBeAbleToGetExistingUser() {
		// Set condition to pull from manager
		conditions.put(User.ID, Long.toString(adminId));
		manager.insert(admin);
		
		// Perform test
		assertNotNull("Should get handle to list of business objects", resultset = manager.select(BusinessClass.USER, conditions));
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
		conditions.put(User.ID, Long.toString(adminId));
		manager.insert(admin);
		
		// Update account (demote to customer)
		assertEquals("Should demote admin account to customer", 1, manager.update(demote));
		assertNotNull("Should be able to get updated record", resultset = manager.select(BusinessClass.USER, conditions));
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
		conditions.put(User.ID, Long.toString(adminId));
		values.put(User.CHECKPOINT, Integer.toString(demote.getCheckpoint().ordinal()));
		manager.insert(admin);
		
		// Update Account
		assertEquals("Should demote admin account to customer", 1, manager.update(BusinessClass.USER, conditions, values));
		assertNotNull("Should be able to get updated record", resultset = manager.select(BusinessClass.USER, conditions));
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
		assertEquals("Should all users from system (2 users)", 2, manager.delete(BusinessClass.USER, null));
		assertEquals("Should have 0 users in system", 0, manager.select(BusinessClass.USER, null).size());
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
		conditions.put(User.ID, Long.toString(adminId));
		manager.insert(admin);
		
		// Perform test
		assertEquals("Should delete admin account", 1, manager.delete(BusinessClass.USER, conditions));
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
		params.put(UserInfo.USERID, Long.toString(adminId));
		params.put(UserInfo.EMAIL, "abc@xyz.com");
		params.put(UserInfo.ADDRESS, "My Street and Stuff");
		params.put(UserInfo.PHONENUMBER, "1234567890");
		
		// Test
		assertEquals("Should create UserInfo record", 1, manager.insert(BusinessClass.USERINFO, params));
	}
	
	@Test
	public void shouldBeAbleToGetExistingUserInfo() {
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set data 
		cnds.put(UserInfo.USERID, Long.toString(adminId));
		manager.insert(adminInfo);
		
		// Perform test
		assertNotNull("Should get resultset for userinfo query", resultset = manager.select(BusinessClass.USERINFO, cnds));
		assertTrue("Should exist single record from query", resultset.size() == 1);
		assertEquals(adminInfo, resultset.get(0));
	}
	
	@Test
	public void shouldBeAbleToUpdateExistingUserInfoRecordWithBusinessObject() {
		UserInfo data = new UserInfo(adminId, "new_email@xyz.com", "new place dr. something", "0987654321");
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set data
		cnds.put(UserInfo.USERID, Long.toString(adminId));
		manager.insert(adminInfo);
		
		// Perform test
		assertEquals("Should update a single userinfo record", 1, manager.update(data));
		assertNotNull("Should have resultset from query", resultset = manager.select(BusinessClass.USERINFO, cnds));
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
		cnds.put(UserInfo.USERID, Long.toString(adminId));
		params.put(UserInfo.EMAIL, "new_email@xyz.com");
		params.put(UserInfo.ADDRESS, "new place dr. something");
		params.put(UserInfo.PHONENUMBER, "0987654321");
		manager.insert(adminInfo);
		
		
		// Perform test
		assertEquals("Should update a single userinfo record", 1, manager.update(BusinessClass.USERINFO, cnds, params));
		assertNotNull("Should have resultset from query", resultset = manager.select(BusinessClass.USERINFO, cnds));
		assertTrue("Should exist single record from query", resultset.size() == 1);
		assertFalse(adminInfo.equals(resultset.get(0)));
		assertEquals(data, resultset.get(0));
	}
	
	@Test 
	public void shouldRemoveUserInfoWithBusinessObject() {
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set data 
		cnds.put(UserInfo.USERID, Long.toString(adminId));
		manager.insert(adminInfo);
		
		assertEquals("Should remove admin userinfo record", 1, manager.delete(adminInfo));
		assertNotNull("Should have resultset from query", resultset = manager.select(BusinessClass.USERINFO, cnds));
		assertTrue("Should have 0 records", resultset.size() == 0);
	}
	
	@Test 
	public void shouldRemoveUserInfoWithFieldParams() {
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set data 
		cnds.put(UserInfo.USERID, Long.toString(adminId));
		manager.insert(adminInfo);
		
		assertEquals("Should remove admin userinfo record", 1, manager.delete(BusinessClass.USERINFO, cnds));
		assertNotNull("Should have resultset from query", resultset = manager.select(BusinessClass.USERINFO, cnds));
		assertTrue("Should have 0 records", resultset.size() == 0);
	}
	
	@Test
	public void shouldRemoveAllUserInfoRecords() {
		Resultset resultset;
		
		// Set data
		manager.insert(adminInfo);
		manager.insert(new UserInfo(customerId, "me@people.com", "cardboard box", ""));
		
		// Perform test
		assertEquals("Should exist a total of 2 userinfo records", 2, manager.select(BusinessClass.USERINFO, null).size());
		assertEquals("Should remove all userinfo records", 2, manager.delete(BusinessClass.USERINFO, null));
		assertNotNull("Should have resultset from query", resultset = manager.select(BusinessClass.USERINFO, null));
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
		params.put(Checking.USERID, Long.toString(customerId));
		params.put(Checking.NUMBER, Long.toString(checkingId));
		params.put(Checking.TOTAL, Float.toString(100.0f));
		params.put(Checking.TYPE, Integer.toString(customerCheckingAcct.getType().ordinal()));
		
		// Perform test
		assertEquals("Should add new account with fieldparams", 1, manager.insert(BusinessClass.ACCOUNT, params));
	}
	
	@Test
	public void shouldBeAbleToGetExistingCheckingAccount() {
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set data
		cnds.put(Checking.USERID, Long.toString(customerId));
		manager.insert(customerCheckingAcct);
		
		// Perform test
		assertNotNull("Should have resultset from query", resultset = manager.select(BusinessClass.ACCOUNT, cnds));
		assertTrue("Should exist single record from query", resultset.size() == 1);
		assertEquals(customerCheckingAcct, resultset.get(0));
	}
	
	@Test
	public void shouldBeAbleToUpdateExistingCheckingAccountRecordWithBusinessObject() {
		Account data = new Checking(customerId, checkingId, 20.0f);
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set Data 
		cnds.put(Checking.USERID, Long.toString(customerId));
		manager.insert(customerCheckingAcct);
		
		// Perform test
		assertEquals("Should update a single account record", 1, manager.update(data));
		assertNotNull("Should have resultset from query", resultset = manager.select(BusinessClass.ACCOUNT, cnds));
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
		cnds.put(Checking.USERID, Long.toString(customerId));
		params.put(Checking.TOTAL, Float.toString(1.0f));
		manager.insert(customerCheckingAcct);
		
		// Perform test
		assertEquals("Should update a single account record", 1, manager.update(BusinessClass.ACCOUNT, cnds, params));
		assertNotNull("Should have resultset from query", resultset = manager.select(BusinessClass.ACCOUNT, cnds));
		assertTrue("Should exist single record from query", resultset.size() == 1);
		assertFalse(customerCheckingAcct.equals(resultset.get(0)));
		assertEquals(data, resultset.get(0));
	}
	
	@Test 
	public void shouldRemoveCheckingAccountWithBusinessObject() {
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set data 
		cnds.put(Checking.USERID, Long.toString(customerId));
		manager.insert(customerCheckingAcct);
		
		assertEquals("Should remove admin checking acct record", 1, manager.delete(customerCheckingAcct));
		assertNotNull("Should have resultset from query", resultset = manager.select(BusinessClass.ACCOUNT, cnds));
		assertTrue("Should have 0 records", resultset.size() == 0);
	}
	
	@Test 
	public void shouldRemoveCheckingAccountWithFieldParams() {
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set data 
		cnds.put(Checking.USERID, Long.toString(customerId));
		manager.insert(customerCheckingAcct);
		
		assertEquals("Should remove checking record", 1, manager.delete(BusinessClass.ACCOUNT, cnds));
		assertNotNull("Should have resultset from query", resultset = manager.select(BusinessClass.ACCOUNT, cnds));
		assertTrue("Should have 0 records", resultset.size() == 0);
	}
	
	@Test
	public void shouldRemoveAllCheckingAccountRecords() {
		Resultset resultset;
		
		// Set data
		manager.insert(customerCheckingAcct);
		manager.insert(new Checking(41181, 512423, 1.0f));
		
		// Perform test
		assertEquals("Should exist a total of 2 checking records", 2, manager.select(BusinessClass.ACCOUNT, null).size());
		assertEquals("Should remove all checking records", 2, manager.delete(BusinessClass.ACCOUNT, null));
		assertNotNull("Should have resultset from query", resultset = manager.select(BusinessClass.ACCOUNT, null));
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
		params.put(Credit.USERID, Long.toString(customerId));
		params.put(Credit.NUMBER, Long.toString(checkingId));
		params.put(Credit.TOTAL, Float.toString(100.0f));
		params.put(Credit.TYPE, Integer.toString(customerCheckingAcct.getType().ordinal()));
		params.put(Credit.INTEREST, Float.toString(10.05f));
		params.put(Credit.CREDITLIMIT, Float.toString(1500.0f));
		
		// Perform test
		assertEquals("Should add new account with fieldparams", 1, manager.insert(BusinessClass.ACCOUNT, params));
	}
	
	@Test
	public void shouldBeAbleToGetExistingCreditAccount() {
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set data
		cnds.put(Credit.USERID, Long.toString(customerId));
		manager.insert(customerCreditAcct);
		
		// Perform test
		assertNotNull("Should have resultset from query", resultset = manager.select(BusinessClass.ACCOUNT, cnds));
		assertTrue("Should exist single record from query", resultset.size() == 1);
		assertEquals(customerCreditAcct, resultset.get(0));
	}
	
	@Test
	public void shouldBeAbleToUpdateExistingCreditAccountRecordWithBusinessObject() {
		Account data = new Credit(customerId, creditId, 20.0f, 100.0f, 1500.0f);
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set Data 
		cnds.put(Credit.USERID, Long.toString(customerId));
		manager.insert(customerCreditAcct);
		
		// Perform test
		assertEquals("Should update a single account record", 1, manager.update(data));
		assertNotNull("Should have resultset from query", resultset = manager.select(BusinessClass.ACCOUNT, cnds));
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
		cnds.put(Credit.USERID, Long.toString(customerId));
		params.put(Credit.TOTAL, Float.toString(1.0f));
		manager.insert(customerCreditAcct);
		
		// Perform test
		assertEquals("Should update a single account record", 1, manager.update(BusinessClass.ACCOUNT, cnds, params));
		assertNotNull("Should have resultset from query", resultset = manager.select(BusinessClass.ACCOUNT, cnds));
		assertTrue("Should exist single record from query", resultset.size() == 1);
		assertFalse(customerCreditAcct.equals(resultset.get(0)));
		assertEquals(data, resultset.get(0));
	}
	
	@Test 
	public void shouldRemoveCreditAccountWithBusinessObject() {
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set data 
		cnds.put(Credit.USERID, Long.toString(customerId));
		manager.insert(customerCreditAcct);
		
		assertEquals("Should remove credit acct record", 1, manager.delete(customerCreditAcct));
		assertNotNull("Should have resultset from query", resultset = manager.select(BusinessClass.ACCOUNT, cnds));
		assertTrue("Should have 0 records", resultset.size() == 0);
	}
	
	@Test 
	public void shouldRemoveCreditAccountWithFieldParams() {
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set data 
		cnds.put(Credit.USERID, Long.toString(customerId));
		manager.insert(customerCreditAcct);
		
		assertEquals("Should remove credit record", 1, manager.delete(BusinessClass.ACCOUNT, cnds));
		assertNotNull("Should have resultset from query", resultset = manager.select(BusinessClass.ACCOUNT, cnds));
		assertTrue("Should have 0 records", resultset.size() == 0);
	}
	
	@Test
	public void shouldRemoveAllCreditAccountRecords() {
		Resultset resultset;
		
		// Set data
		manager.insert(customerCreditAcct);
		manager.insert(new Credit(41181, 512423, 1.0f, 10.0f, 1500.0f));
		
		// Perform test
		assertEquals("Should exist a total of 2 credit acct records", 2, manager.select(BusinessClass.ACCOUNT, null).size());
		assertEquals("Should remove all account records", 2, manager.delete(BusinessClass.ACCOUNT, null));
		assertNotNull("Should have resultset from query", resultset = manager.select(BusinessClass.ACCOUNT, null));
		assertTrue("Should have 0 records", resultset.size() == 0);
	}
	
}
