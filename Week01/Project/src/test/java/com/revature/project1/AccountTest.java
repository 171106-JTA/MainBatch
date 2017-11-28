package com.revature.project1;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import com.revature.main.User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
/**
 * Tests User balance allows transfer/is prperly updated. 
 */
public class AccountTest {
	User t1 = new User("testUser1","testing",100,false,1);
	User t2 = new User("testUser2","testing",100,false,1);
	double d = 28;
	
	@Test
	public void testEnoughFunds() {
		assertTrue("User has enough funds:", d < t1.getBalance() && t1.getBalance() > d);
	} 
	
	@Test
	public void testTransfer() {
		double balanceUpdated1 = t1.getBalance()-d; 
		double balanceUpdated2 = t2.getBalance()+d;
		assertTrue("Money successfully transfered from t1", (t1.getBalance() == balanceUpdated1));
		assertTrue("Money successfully transfered to t2", (t2.getBalance() == balanceUpdated2));
		
	}

}
