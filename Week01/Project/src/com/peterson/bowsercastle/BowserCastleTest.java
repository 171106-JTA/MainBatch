package com.peterson.bowsercastle;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BowserCastleTest {
	
	private BowserCastle bc;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bc = new BowserCastle();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		bc.enterCastle();
	}

}