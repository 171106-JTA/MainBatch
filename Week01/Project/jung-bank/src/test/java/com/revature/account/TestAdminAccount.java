package com.revature.account;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestAdminAccount {
	AdminAccount testAdmin = new AdminAccount();
	UserAccount testUser = new UserAccount();

	@Test
	public void TestLockingUser() {

		testUser.setLocked(false);
		assertEquals(true, testAdmin.lockUser(testUser).isLocked());
	}

	@Test
	public void TestUnlockingUser() {
		testUser.setLocked(true);
		assertEquals(false, testAdmin.unlockUser(testUser).isLocked());
	}

	@Test
	public void TestPromotingUser() {
		
		assertEquals(testAdmin.getClass(), testAdmin.promoteUser(testUser).getClass());
	}
}
