package main.java.com.revature.p1;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Grantley Morrison
 *
 */

public class BankTest {
	/*
	 * Things to Test
	 * 1. User
	 * 1. Withdraw
	 * 2. Deposit
	 * 3. Balance
	 * 4. Withdraw Revoke
	 * 2. Admin
	 * 5. Approve
	 * 6. Revoke
	 * 7. Promote
	 */
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	User	u	= new User("TestUser", "testuser", "testpass");
	
	Admin	a	= new Admin("TestAdmin", "testadmin", "testpass");
	
	@Before
	public void setUp() throws Exception {
		a.approve(a);
		a.approve(u);
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testApprove() {
		assertEquals("Admin Approval is broken.", true, u.isApproved());
	}
	
	@Test
	public void testBal() {
		assertEquals("User Balance is broken.", 0.0, u.getBal(), 0);
	}
	
	@Test
	public void testDeposit() {
		u.deposit(100);
		assertEquals("User Deposit is broken.", 100.0, u.getBal(), 0);
		u.withdraw(100);
	}
	
	@Test
	public void testPromote() {
		Admin v = a.promote(u);
		assertEquals("Admin Promotion is broken.", true, v.isAdmin());
	}
	
	@Test
	public void testRevoke() {
		a.revoke(u);
		assertEquals("Admin Approval is broken.", false, u.isApproved());
	}
	
	@Test
	public void testWithdraw() {
		u.deposit(100);
		u.withdraw(100);
		assertEquals("User Withdraw is broken.", 0.0, u.getBal(), 0);
	}
	
	@Test
	public void testWithdrawRevoke() {
		u.withdraw(100);
		assertEquals("Withdraw Revoke is broken.", false, u.isApproved());
	}
}
