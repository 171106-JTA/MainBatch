package d3.revature.junit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class ArithmeticTest {
	/*
	 *Unit testing is the most micro level of testing it encompasses testing your
	 * app as its smallest components testing methods individually testing interfaces etc.
	 */
	
	Arithmetic arithmetic;
	int x = 30;
	int y = 10;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		System.out.println("BEFORE CLASS");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("AFTER CLASS");
	}

	@Before
	public void setUp() throws Exception {
		arithmetic = new Arithmetic();
		System.out.println("BEFORE");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("AFTER");
	}


	/*
	 * The Assert library is used to determine the state of a test 
	 * With it you can either fail or pass tests
	 * 	-assertEquals -> Determine if actual result equals expected result.
	 * 	-assertTrue -> Pass if expected boolean is true
	 * 	-assertFalse -> Pass if exptected boolean is false
	 * 
	 * */
	@Ignore
	@Test
	public void testAddition() {
		assertEquals(40, arithmetic.addition(x, y));

	}
	@Test
	public void testSubtraction() {
		assertEquals(20, arithmetic.subtration(x, y));

	}
	@Test
	public void testMultiplication() {
		assertEquals("U dont need to know", 300, arithmetic.multiplication(x, y));

	}
	@Test
	public void testDivision() {
		assertEquals(300, arithmetic.division(x, y));

	}
	

}
