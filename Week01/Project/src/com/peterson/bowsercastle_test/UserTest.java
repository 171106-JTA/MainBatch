package com.peterson.bowsercastle_test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.peterson.bowsercastle_model.Role;
import com.peterson.bowsercastle_model.User;

public class UserTest {

	private User user1;
	private String name = "Alex";
	private int pw = "password123".hashCode();
	private Role role = Role.UNVERIFIED;
	
	@Before
	public void setUp() throws Exception {
		user1 = new User(name, pw, role);
	}

	@Test
	public void testGetters() {
		assertEquals(name, user1.getName());
		assertEquals(pw, user1.getHashedPassword());
		assertEquals(role, user1.getRole());
	}
	
	@Test
	public void testSetters() {
		int coins = 50;
		int level = 19999;
		Role role = Role.ADMIN;
		
		user1.setCoins(coins);
		user1.setRole(role);
		user1.levelUp();
		
		assertEquals(coins, user1.getCoins());
		assertEquals(level + 10, user1.getLevel());
		assertEquals(role, user1.getRole());
	}
	
	/**
	 * Test that two users are equal or not.
	 */
	@Test
	public void testEquals() {
		User user2 = new User(name, pw, role);
		User user3 = new User(name + "af", (pw + "sdf").hashCode(), Role.LOCKED);
		
		assertEquals("Should be equal", user1, user2);
		assertNotEquals("Should not be equal", user1, user3);
	}
}