package com.revature.test.businessobject;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.BeforeClass;

import com.revature.businessobject.user.*;

public class UserTest {
	static long id;
	static String username;
	static String password;
	
	@BeforeClass
	public static void setupBeforeClass() {
		id = 123456;
		username = "billy";
		password = "password";
	}
	
	@Test
	public void shouldBeEqualToInstanceWithSameValues() {
		User user1 = new Admin(id, username, password);
		User user2 = new Admin(id, username, password);
		
		// Perform tests
		assertEquals(user1, user2);
	}
	
	/**
	 * Ensures user instance can have role of Administrator 
	 */
	@Test
	public void shouldCreateUserWithAdminRole() {
		User user = new User(id, username, password, UserRole.ADMIN);
		assertEquals("Should get handle to list of business objects", UserRole.ADMIN, user.getRole());
	}
	
	/**
	 * Ensures user instance can have role of Customer 
	 */
	@Test
	public void shouldCreateUserWithCustomerRole() {
		User user = new User(id, username, password, UserRole.CUSTOMER);
		assertEquals("User role should be customer", UserRole.CUSTOMER, user.getRole());
	}
	
	/**
	 * Ensures user id can be set 
	 */
	@Test
	public void shouldGetCorrectUserId() {
		User user = new User(id, username, password, UserRole.ADMIN);
		assertEquals("User id should be " + id, user.getId(), id);
	}
	
	/**
	 * Ensures customer instance has role of CUSTOMER 
	 */
	@Test
	public void customerInstanceShouldHaveCustomerRole() {
		Customer customer = new Customer(id, username, password);
		assertEquals("Customer instance should have customer role", UserRole.CUSTOMER, customer.getRole());
	}
	
	/**
	 * Ensures administrator instance has role of ADMIN 
	 */
	@Test
	public void adminInstanceShouldHaveAdminRole() {
		Admin admin = new Admin(id, username, password);
		assertEquals("Administrator instance should have admin. role", UserRole.ADMIN, admin.getRole());
	}
}
