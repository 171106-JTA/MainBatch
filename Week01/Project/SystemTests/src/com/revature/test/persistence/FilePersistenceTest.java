package com.revature.test.persistence;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.businessobject.user.Admin;
import com.revature.businessobject.user.Customer;
import com.revature.businessobject.user.User;
import com.revature.businessobject.user.UserRole;
import com.revature.core.FieldParams;
import com.revature.core.Resultset;
import com.revature.persistence.Persistenceable;
import com.revature.persistence.file.FilePersistence;

public class FilePersistenceTest {
	static FilePersistence manager;
	static Resultset resultset;
	static FieldParams conditions;
	static FieldParams values;
	static User admin;
	static User customer;
	static long adminId;
	static long customerId;
	static String adminUsername;
	static String adminPassword;
	static String customerUsername;
	static String customerPassword;
	
	@BeforeClass
	public static void setupBeforeClass() {
		manager = FilePersistence.getManager();
		adminId = 1423412;
		adminUsername = "myAdmin";
		adminPassword = "adogmsjdfh";
		
		customerId = 42135132;
		customerUsername = "billy";
		customerPassword = "password";
	}
	
	@Before
	public void setUp() throws Exception {
		resultset = new Resultset();
		conditions = new FieldParams();
		values = new FieldParams();
		admin = new Admin(adminId, adminUsername, adminPassword);
		customer = new Customer(customerId, customerUsername, customerPassword);
		FilePersistence.setDirectory(System.getProperty("user.dir") + "\\data\\");
	}

	@After
	public void tearDown() throws Exception {
		FilePersistence.deleteData();
	}

	@Test
	public void shouldStoreDataInCurrentWorkingDirectory() {
		assertEquals("Should store data in working directory", System.getProperty("user.dir"), FilePersistence.getDirectory());
	}
	
	@Test
	public void shouldStoreDataInDifferentDirectory() {
		FilePersistence.setDirectory("C:\\");
		assertEquals("Should store data in different directory", "C:\\", FilePersistence.getDirectory());
	}
	
	///
	// Data management Class Tests 
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
		values.put("role", admin.getRole().toString());
		
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
		values.put("role", demote.getRole().toString());
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
	public void shouldRemoveAllUsersFromSystem() {
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
}
