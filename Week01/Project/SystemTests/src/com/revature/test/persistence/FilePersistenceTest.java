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
import com.revature.core.FieldParams;
import com.revature.core.Resultset;
import com.revature.persistence.Persistenceable;
import com.revature.persistence.file.FilePersistence;

public class FilePersistenceTest {
	static Persistenceable manager;
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
	 * Creates and attempts to get user from file
	 */
	@Test
	public void shouldGetUser() {
		FieldParams cnds = new FieldParams();
		Resultset resultset;
		
		// Set condition to pull from manager
		cnds.put("id", Long.toString(adminId));
		
		// Perform test
		assertEquals("Should add admin with", manager.insert(admin), 1);
		assertNotNull("Should get handle to list of business objects", resultset = manager.select("User", cnds));
		assertEquals("Should have one record in resultset", resultset.size(), 1);
		assertTrue("Record should be newly created admin", admin.equals(resultset.get(0)));
	}
	
	@Test
	public void shouldUpdateUserByBusinessObject() {
		
		
	}

	@Test
	public void shouldUpdateUserByParams() {
		fail("Not implemented");
	}
	
	@Test
	public void shouldInsertUserByBusinessObject() {
		fail("Not implemented");
	}
	
	@Test
	public void shouldInsertUserByParams() {
		fail("Not implemented");
	}
	
	@Test
	public void shouldRemoveUserByBusinessObject() {
		fail("Not implemented");
	}
	
	@Test
	public void shouldRemoveUserByParams() {
		fail("Not implemented");
	}
}
