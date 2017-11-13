package com.revature.pillars;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializationExample {
	/*
	 * Variable scopes:
	 *	• static scope: accessible from anywhere, any class
	 *	• instance scope: variables outside of methods, but within the class
	 *		accessible from anywhere within the class, below the declaration. 
	 *	• method scope: accessible from only within the method. 
	 *		Variable deallocated after method execution
	 *	• loop scope: variable only used within the loop it was created in. includes if and switch statement declarations
	 */
	public static ObjectOutputStream oos;
	
	public static void main(String[] args) {
		Employee emp = new Employee("Bobbert Bobby Bobson", 19, 123123123);
		System.out.println(emp);
		
		try {
			oos = new ObjectOutputStream(new FileOutputStream("employee.ser"));
			oos.writeObject(emp);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

}
