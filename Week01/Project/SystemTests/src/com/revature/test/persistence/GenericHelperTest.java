package com.revature.test.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.user.Admin;
import com.revature.businessobject.user.Customer;
import com.revature.businessobject.user.User;
import com.revature.core.BusinessClass;
import com.revature.core.Resultset;
import com.revature.persistence.file.FileDataManager;
import com.revature.persistence.file.FilePersistence;
import com.revature.processor.handler.helper.GenericHelper;

public class GenericHelperTest {
	static FilePersistence manager;
	static Resultset resultset;
	static List<String> fields;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		manager = FileDataManager.getManager();
	}

	@Before
	public void setUp() throws Exception {
		fields = new ArrayList<>();
		manager.insert(new Admin(1, "d", "abcd"));
		manager.insert(new Customer(2, "c", "b"));
		manager.insert(new Admin(3, "b", "z"));
		manager.insert(new Customer(4, "a", "abcd"));
	}

	@After
	public void tearDown() throws Exception {
		FilePersistence.deleteData();
	}

	@Test
	public void shouldGetUserWithLargestID() {
		User shouldBe = new Customer(4, "a", "abcd");
		BusinessObject user;
		
		// Set fields to check 
		fields.add(User.ID);
		
		// Perform tests
		assertNotNull("Should find user with Field ID", user = GenericHelper.getLargest(BusinessClass.USER, fields));
		assertTrue("User with greatest id should be " + shouldBe, shouldBe.equals(user));
	}
	
	@Test
	public void shouldGetUserWithLargestUserName() {
		User shouldBe = new Admin(1, "d", "abcd");
		BusinessObject user;
		
		// Set fields to check 
		fields.add(User.USERNAME);
		
		// Perform tests
		assertNotNull("Should find user with Field Username", user = GenericHelper.getLargest(BusinessClass.USER, fields));
		assertTrue("User with greatest username should be " + shouldBe, shouldBe.equals(user));
	}
	
	@Test
	public void shouldGetUserWithLargestPassword() {
		User shouldBe = new Admin(3, "b", "z");
		BusinessObject user;
		
		// Set fields to check 
		fields.add(User.PASSWORD);
		
		// Perform tests
		assertNotNull("Should find user with Field PASSWORD", user = GenericHelper.getLargest(BusinessClass.USER, fields));
		assertTrue("User with greatest password should be " + shouldBe, shouldBe.equals(user));
	}
	
}
