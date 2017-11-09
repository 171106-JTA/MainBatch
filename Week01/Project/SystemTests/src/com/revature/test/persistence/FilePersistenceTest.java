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
	static Persistenceable manager;
	static Resultset resultset;
	static FieldParams conditions;
	static FieldParams values;
	static User admin;
	static User customer;
	static long adminId;
	static long customerId;
	
	@BeforeClass
	public static void setupBeforeClass() {
		adminId = 1423412;
		customerId = 42135132;
	}
	
	@Before
	public void setUp() throws Exception {
		manager = new FilePersistence();
		resultset = new Resultset();
		conditions = new FieldParams();
		values = new FieldParams();
		admin = new Admin(adminId);
		customer = new Customer(customerId);
	}

	@After
	public void tearDown() throws Exception {
		manager.delete("User", null);
	}

	@Test
	public void shouldStoreDataInCurrentWorkingDirectory() {
		FilePersistence fp = new FilePersistence();
		assertEquals("Should store data in working directory", fp.getDirectory(), System.getProperty("user.dir"));
	}
	
	@Test
	public void shouldStoreDataInDifferentDirectory() {
		FilePersistence fp = new FilePersistence("C:\\");
		assertEquals("Should store data in different directory", fp.getDirectory(), "C:\\");
	}
	
	///
	// Data management Class Tests 
	///
	
	/**
	 * Creates admin account using BusinessObject
	 */
	@Test
	public void shouldCreateNewAdminUserUsingBusinessObject() {
		assertEquals("Should add admin with", manager.insert(admin), 1);
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
		assertEquals("Should add admin with", manager.insert("User", values), 1);
	}
	
	@Test
	public void shouldBeAbleToGetExistingUser() {
		// Set condition to pull from manager
		conditions.put("id", Long.toString(adminId));
		manager.insert(admin);
		
		// Perform test
		assertNotNull("Should get handle to list of business objects", resultset = manager.select("User", conditions));
		assertEquals("Should have one record in resultset", resultset.size(), 1);
		assertTrue("Record should be newly created admin", admin.equals(resultset.get(0)));
	}
	
	/**
	 * Attempts to update an existing record using instance of BusinessObject
	 */
	@Test
	public void shouldUpdateUserByBusinessObject() {
		User demote = new Customer(adminId);
		
		// Set data
		conditions.put("id", Long.toString(adminId));
		manager.insert(admin);
		
		// Update account (demote to customer)
		assertEquals("Should demote admin account to customer", manager.update(demote), 1);
		assertNotNull("Should be able to get updated record", resultset = manager.select("User", conditions));
		assertEquals("Should have one record in resultset", resultset.size(), 1);
		assertTrue("Record in system should reflect changes", demote.equals(resultset.get(0)));
	}

	/**
	 * Attempts to update an existing record using FieldParams instance 
	 */
	@Test
	public void shouldUpdateUserByFieldParams() {
		User demote = new Customer(adminId);
		// Set data
		conditions.put("id", Long.toString(adminId));
		values.put("role", demote.getRole().toString());
		manager.insert(admin);
		
		// Update Account
		assertEquals("Should demote admin account to customer", manager.update("User", conditions, values), 1);
		assertNotNull("Should be able to get updated record", resultset = manager.select("User", conditions));
		assertEquals("Should have one record in resultset", resultset.size(), 1);
		assertTrue("Record in system should reflect changes", demote.equals(resultset.get(0)));
	}
	
	@Test
	public void shouldRemoveUserByBusinessObject() {
		// Set data
		manager.insert(admin);
		
		// Perform test
		assertEquals("Should delete admin account", manager.delete(admin), 1);
	}
	
	@Test
	public void shouldRemoveUserByParams() {
		// Set data
		conditions.put("id", Long.toString(adminId));
		manager.insert(admin);
		
		// Perform test
		assertEquals("Should delete admin account", manager.delete("User", conditions), 1);
	}
}
