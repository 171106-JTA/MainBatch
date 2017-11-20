package com.emeraldbank.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.emeraldbank.dao.EmeraldBankDao;
import com.emeraldbank.dao.EmeraldBankDaoImpl;

public class DaoTest {

	EmeraldBankDao dao; 
	
	@Before
	public void setUpBeforeClass() throws Exception {
		dao = new EmeraldBankDaoImpl(); 
	}
	
	@Test
	public void getUserByIdTest() {
		
		assertTrue( dao.getUserByLoginId("sjgillet", false).getPassword().equals("passwords") ); 
		
	}
	
	@Test
	public void getAcctByAcctNumTest() {
		
		assertTrue( dao.getAcctByAcctNum("1234567").getOwnerID().equals("sjgillet") ); 
	}
	
	@Test
	public void getAcctBalanceTest() {
		System.out.println(dao.getAcctBalance("8749702")); 
	}
	
	
	@Test
	public void depositIntoAcctTest() {
		System.out.println("Previous Balance: " + String.format("%.2f",dao.getAcctBalance("1234567")) + 
				"\tNewBalance: " + String.format("%.2f", dao.depositIntoAcct("1234567", 20.00))); 
	}
	
	@Test
	public void withdrawFromAcctTest() {
		System.out.println("Previous Balance: " + String.format("%.2f",dao.getAcctBalance("1234567")) + 
				"\tNewBalance: " + String.format("%.2f", dao.withdrawFromAcct("1234567", 10.25))); 
		
	}
	

}
