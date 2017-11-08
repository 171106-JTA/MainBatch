package com.revature.test.businessobject;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.revature.businessobject.user.Admin;
import com.revature.businessobject.user.Customer;
import com.revature.businessobject.user.User;
import com.revature.businessobject.user.UserRole;

class UserTest extends Assert {
	
	/**
	 * Ensures user instance can have role of Administrator 
	 */
	@Test
	void shouldCreateUserWithAdminRole() {
		User user = new User(1314232, UserRole.ADMIN);
		assertTrue("User role should be admin", user.getRole() == UserRole.ADMIN);
	}
	
	/**
	 * Ensures user instance can have role of Customer 
	 */
	@Test
	void shouldCreateUserWithCustomerRole() {
		User user = new User(1314232, UserRole.CUSTOMER);
		assertTrue("User role should be customer", user.getRole() == UserRole.CUSTOMER);
	}
	
	/**
	 * Ensures user id can be set 
	 */
	@Test
	void shouldGetCorrectUserId() {
		User user = new User(12322342, UserRole.ADMIN);
		assertTrue("User id should be 12322342", user.getId() == 12322342);
	}
	
	/**
	 * Ensures customer instance has role of CUSTOMER 
	 */
	@Test
	void customerInstanceShouldHaveCustomerRole() {
		Customer customer = new Customer(123456);
		assertTrue("Customer instance should have customer role", customer.getRole() == UserRole.CUSTOMER);
	}
	
	/**
	 * Ensures administrator instance has role of ADMIN 
	 */
	@Test
	void adminInstanceShouldHaveAdminRole() {
		Admin admin = new Admin(123456);
		assertTrue("Administrator instance should have admin. role", admin.getRole() == UserRole.ADMIN);
	}
}
