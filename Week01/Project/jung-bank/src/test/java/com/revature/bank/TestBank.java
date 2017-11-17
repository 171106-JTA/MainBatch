package com.revature.bank;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestBank {
	Bank testerBank = new Bank();
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	 
	
	// test if message is correct
	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}
	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}
	@Test
	public void testOutQuit() {
		// calling quit makes bankOn false always
		testerBank.quit();
		assertEquals(false, Bank.bankOn);
		assertEquals("Thank you for banking with us, Good bye.", outContent.toString());
	}
}
