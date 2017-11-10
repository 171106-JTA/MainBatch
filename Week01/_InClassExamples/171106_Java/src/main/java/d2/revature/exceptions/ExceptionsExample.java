package d2.revature.exceptions;

import java.io.FileNotFoundException;

public class ExceptionsExample {

	public static void main(String[] args) {
		/*
		 * Exceptions are any event where something unexpected occurs.
		 * -When an application behaves in a manner it should not.
		 * 
		 * This is different from Error, which is it's own class.
		 * -Any situation where the program cannot reasonably recover.\
		 * -Think: OutOfMemoryError (Heap), StackOverflowError (Stack)
		 */
		
		ExceptionsExample ee = new ExceptionsExample();
		ee.attemptException();
		
		try{
			ee.method1();
		}catch(Exception e){
			System.out.println("Caught");
		}
		

	}
	public void attemptException(){
		int i[] = {1,2,3,4,5}; //a list of 5 elements, meaning the highest index for our array is 4
		
		//A try block is where you put 'risky' code
		try{
			System.out.println(i[5]);
		
		//A catch block runs code in the event the specified exception occurs.
		//Whatever functionality inside the try block that is remaining, is skipped.
		}catch(ArithmeticException e){
			System.err.println("Woops, found an exception...");
			e.printStackTrace();
		}catch(RuntimeException e){
			System.err.println("Woops, found an exception...");
			e.printStackTrace();
		//The finally block will always execute in a try catch block, regardless if an exception
		// occurred or not. The only way a finally block will not run is when there is the command:
		//system.exit(0)
		//or an unrecoverable error occurs.
			
		}finally{
			System.out.println("FINALLY");
		}
	}
	
	public void method1() throws FileNotFoundException{
/*		try{*/
			method2();
/*		}
		catch(Exception e){
			System.out.println("CAUGHT");
		}*/
	}
	public void method2() {//throws FileNotFoundException{
		try{
			method3();
			method4();
		}catch(FileNotFoundException | MyCustomException e){
			System.out.println(e.getMessage());

		}
	}
	public void method3() throws FileNotFoundException{
		throw new FileNotFoundException(); //"throw" purposely triggers and exception
	}
	
	public void method4() throws FileNotFoundException, MyCustomException{
		throw new MyCustomException();
	}
	/*
	 * Two types of exceptions:
	 * -Unchecked Exceptions
	 * --An exception that does not require you to have it handled at compile time.
	 * --Anything under "RuntimeException" is considered unchecked.
	 * -Checked Exceptions
	 * --Requires you to have coded a try/catch block to handle the exception at compile time.
	 */
}
