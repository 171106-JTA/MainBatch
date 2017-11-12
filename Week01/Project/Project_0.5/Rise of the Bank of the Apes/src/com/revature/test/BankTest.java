package com.revature.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.apebank.BankOfTheApes;
import com.revature.data.ProcessData;

public class BankTest {
	BankOfTheApes bota;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bota = BankOfTheApes.getBank();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUnserialize() {
		assert(ProcessData.unserialize() instanceof HashMap);
		assertEquals(ProcessData.unserialize().get("a!joe").getName(), bota.getUsers().get("a!joe").getName());
	}
}
