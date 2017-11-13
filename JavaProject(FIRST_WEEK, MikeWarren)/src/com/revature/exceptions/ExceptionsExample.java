package com.revature.exceptions;

import java.io.FileNotFoundException;

public class ExceptionsExample {

	public static void main(String[] args) throws FileNotFoundException { 
		/*
		 * Exceptions are any event where something unexpected occurs.
		 * 	• When an application behaves in a manner it should not.
		 * 
		 * This is different from Error, which is its own class.
		 * 	• Any situation where the program cannot reasonably recover.
		 *	• Think OutOfMemoryError (heap), StackOverflowError (stack)
		 * 	
		 */
		ExceptionsExample ee = new ExceptionsExample();
		ee.attemptException();
		try 
		{
			ee.method1();
		} catch (FileNotFoundException ex)
		{
			
		}
	}
	
	public void attemptException() { 
		int[] i = { 1,2,3,4,5,6 };	// a list of 5 elements meaning the highest index for our array is 4
		
		// a try block is where you put 'risky' code
		try {
			System.out.println(i[5]);
			
		}
		// a catch block runs code in the event the specified exception occurs.
		// whatever functionality insie the try block that is remaining is skipped.
		catch(ArrayIndexOutOfBoundsException ex) {
			System.err.println("Whoops, found exception");
			ex.printStackTrace();
		}
		catch (RuntimeException e)
		{
		}
		 /* The finally block will always execute in a try catch block, regardless if an exception occurred or not. 
		  * The only way a finally block will not run is when there is :
		  * • the command System.exit(0) or 
		  * • an unrecoverable error occurs
		  */		
		finally
		{
			System.out.println("FINALLY");
		}
		
	}

	public void method1() throws FileNotFoundException
	{
		method2();
	}
	public void method2()
	{
		try 
		{
//			method3();
			method4();
		}
		catch (FileNotFoundException | MyCustomException e)
		{
			System.out.println(e.getMessage());
		}
	}
	public void method3() throws FileNotFoundException
	{
		throw new FileNotFoundException(); // on purpose
	}
	
	public void method4() throws FileNotFoundException,MyCustomException
	{
		throw new MyCustomException();
	}
	/*
	 * Two types of exceptions: 
	 * 	• unchecked Exceptions
	 * 		- doesn't require you to handle it at compile time
	 * 		- anything under "RuntimeException" is considered unchecked. 
	 * 	• checked Exception
	 * 		- requires you to have coded a try/catch block to handle the Exception at compile time
	 */
}
