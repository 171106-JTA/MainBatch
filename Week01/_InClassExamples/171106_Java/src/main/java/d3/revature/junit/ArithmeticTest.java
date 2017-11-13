package d3.revature.junit;

<<<<<<< HEAD
import static org.junit.Assert.*;
=======
import static org.junit.Assert.assertEquals;
>>>>>>> 908c4a08b3cf9c5cb65e60015f7c54cf564145ce

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class ArithmeticTest {
	/*
<<<<<<< HEAD
	 *Unit testing is the most micro level of testing it encompasses testing your
	 * app as its smallest components testing methods individually testing interfaces etc.
	 */
	
=======
	 * Unit testing is the most micro level of testing. It encompasses testing your application
	 * at its smallest components. Testing methods individually, testing interfaces, etc.
	 */
>>>>>>> 908c4a08b3cf9c5cb65e60015f7c54cf564145ce
	Arithmetic arithmetic;
	int x = 30;
	int y = 10;
	
<<<<<<< HEAD

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
=======
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
>>>>>>> 908c4a08b3cf9c5cb65e60015f7c54cf564145ce
		System.out.println("BEFORE CLASS");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("AFTER CLASS");
	}

	@Before
	public void setUp() throws Exception {
<<<<<<< HEAD
		arithmetic = new Arithmetic();
		System.out.println("BEFORE");
=======
		System.out.println("BEFORE");
		arithmetic = new Arithmetic();
>>>>>>> 908c4a08b3cf9c5cb65e60015f7c54cf564145ce
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("AFTER");
	}

<<<<<<< HEAD

	/*
	 * The Assert library is used to determine the state of a test 
	 * With it you can either fail or pass tests
	 * 	-assertEquals -> Determine if actual result equals expected result.
	 * 	-assertTrue -> Pass if expected boolean is true
	 * 	-assertFalse -> Pass if exptected boolean is false
	 * 
	 * */
=======
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
	
>>>>>>> 908c4a08b3cf9c5cb65e60015f7c54cf564145ce
	@Ignore
	@Test
	public void testAddition() {
		assertEquals(40, arithmetic.addition(x, y));
<<<<<<< HEAD

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
	
=======
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
>>>>>>> 908c4a08b3cf9c5cb65e60015f7c54cf564145ce

}
