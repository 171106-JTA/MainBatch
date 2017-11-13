package d3.revature.junit;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class ArithmeticTest {
	/*
	 * Unit testing is the most micro level of testing. It encompasses testing your application
	 * at its smallest components. Testing methods individually, testing interfaces, etc.
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
		System.out.println("BEFORE");
		arithmetic = new Arithmetic();
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("AFTER");
	}

	/*
	 * The assert library is used to determine the state of a test.
	 * With it you can either fail or pass tests.
	 * -assertEqauls ->Determine if actual result equals expected result.
	 * -assertTrue -> Pass if expected boolean is true
	 * -assertFalse
	 */
	
	/*
	 * @Ignore is used to skip a test entirely. (More specifically the first method below it)
	 * Note: A Java annotation is a tag used for metadata that can help
	 * provide useful rules and guidelines for an underlying method
	 */
	
	@Ignore
	@Test
	public void testAddition() {
		assertEquals(40, arithmetic.addition(x, y));
	}
	
	@Test
	public void testSubtraction() {
		assertEquals(20, arithmetic.subtraction(x,y));
	}
	
	/*
	 * In a 3 parameter assert, the first parameter serves as a custom 
	 * fail message.
	 */
	@Test
	public void testMultiplication() {
		assertEquals("You don't need to know what happened...", 3000, arithmetic.multiplication(x, y));
	}
	
	@Test
	public void testDivision() {
		assertEquals(3, arithmetic.division(x, y));
	}
	
	//For Junit4, Junit5 uses "assertThrows" method.
	@Test(expected=ArithmeticException.class)
	public void testException() {
		throw new ArithmeticException();
	}
	
	/*
	 * Test will fail if it takes longer than 3 seconds. (JUnit 4)
	 * In Junit5 -> this is now an assert method: assertTimeout()
	 */
	@Test(timeout=3000)
	public void testTimeout(){
		throw new ArithmeticException();
	}

}
