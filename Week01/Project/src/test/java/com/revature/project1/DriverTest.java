package com.revature.project1;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import com.revature.main.User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

/**
 * Tests account can be made with desired id/password
 */
public class DriverTest {
	
	
	@Test
	public void testIsAvailable() {
		String t = "Bobbert";
		assertTrue("Username available, returned empty acct", (User.returnExisting(t).getUserID().equals(" ")));
	}

	@Test
	public void testStandardMet() {
		String t = "Bobbert";
		assertTrue("Username at least 5 characters", t.length() >= 5);
	}
	
}
