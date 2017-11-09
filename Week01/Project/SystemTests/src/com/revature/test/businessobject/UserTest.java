package com.revature.test.businessobject;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.BeforeClass;

import com.revature.businessobject.user.*;

public class UserTest {
	static long id;
			
	@BeforeClass
	public static void setupBeforeClass() {
		id = 123456;
	}
	
	/**
	 * Ensures user instance can have role of Administrator 
	 */
	@Test
	public void shouldCreateUserWithAdminRole() {
		User user = new User(id, UserRole.ADMIN);
		assertEquals("Should get handle to list of business objects", user.getRole(), UserRole.ADMIN);
	}
	
	/**
	 * Ensures user instance can have role of Customer 
	 */
	@Test
	public void shouldCreateUserWithCustomerRole() {
		User user = new User(id, UserRole.CUSTOMER);
		assertEquals("User role should be customer", user.getRole(), UserRole.CUSTOMER);
	}
	
	/**
	 * Ensures user id can be set 
	 */
	@Test
	public void shouldGetCorrectUserId() {
		User user = new User(id, UserRole.ADMIN);
		assertEquals("User id should be " + id, user.getId(), id);
	}
	
	/**
	 * Ensures customer instance has role of CUSTOMER 
	 */
	@Test
	public void customerInstanceShouldHaveCustomerRole() {
		Customer customer = new Customer(id);
		assertEquals("Customer instance should have customer role", customer.getRole(), UserRole.CUSTOMER);
	}
	
	/**
	 * Ensures administrator instance has role of ADMIN 
	 */
	@Test
	public void adminInstanceShouldHaveAdminRole() {
		Admin admin = new Admin(id);
		assertEquals("Administrator instance should have admin. role", admin.getRole(), UserRole.ADMIN);
	}
}
