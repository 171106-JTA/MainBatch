package com.revature.project1;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

/**
 * Tests User object flags are properly set.
 */
public class AdminTest {
	
	User t1 = new User("testUser1","testing",100,false,0);
	User t2 = new User("testUser2","testing",100,true,1);
	User t3 = new User("testUser3","testing",100,false,1);
	
	
	@Test
	public void testApproveAccount() {
		t1.setStatusFlag(1);
		assertTrue("User successfully approved", t1.getStatusFlag()==1);
	}
	@Test
	public void testBlockAccount() {
		t3.setStatusFlag(2);
		assertTrue("User successfully blocked", t3.getStatusFlag()==2);
	}
	@Test
	public void testMakeAdmin() {
		t1.setAdmin(true);
		assertTrue("User successfully granted admin status", t1.getAdmin() == true);
	}
	@Test
	public void testRevokeAdmin() {
		t2.setAdmin(false);
		assertTrue("User successfully granted admin status", t2.getAdmin() == false);
	}
	
	

}
