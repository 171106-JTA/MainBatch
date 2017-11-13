package com.revature.junit;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArithmeticTest {
	/*
	 * Unit testing is the most micro level of testing. It encompasses testing your application at its smallest 
	 * components. Testing methods individually, testing interfaces, etc...
	 */
	
	Arithmetic arithmetic;
	int x = 30, y = 10;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("BEFORE CLASS");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("AFTER CLASS");
	}

	@BeforeEach
	void setUp() throws Exception {
		System.out.println("BEFORE");
		arithmetic = new Arithmetic();
	}

	@AfterEach
	void tearDown() throws Exception {
		System.out.println("AFTER");
		
	}
	/*
	 * The assert library is used to determine the state of a test.
	 * With it you can either fail or pass tests.
	 * 	• assertEquals() : determine if actual result equals expected result
	 * 	• assertTrue()   : pass if expected boolean is true
	 * 	• assertFalse()
	 * In a 3-parameter assert, the first parameter serves as a custom fail message.  
	 */

	@Test
	void testDivision() {
		Assert.assertEquals(3, arithmetic.division(30,10));
	}

	/*
	 * @Ignore is used to skip tests entirely.
	 * NOTE: A Java annotation is a tag used for metadata that can help provide useful rules and guidelines for an
	 * underlying method
	 */
	@Test
	void testAddition() { 
		Assert.assertEquals(40, arithmetic.addition(30,10));
	}
	@Test
	void testSubtraction() { 
		Assert.assertEquals(20, arithmetic.subtraction(30,10));
	}
	@Test
	void testMultiplication() { 
		Assert.assertEquals(300, arithmetic.multiplication(30,10));
	}
	
	/*Doesn't work in JUnit5
	 * @Test(timeout=3000)
	void testException()
	{
		throw new ArithmeticException();
	}*/
}
