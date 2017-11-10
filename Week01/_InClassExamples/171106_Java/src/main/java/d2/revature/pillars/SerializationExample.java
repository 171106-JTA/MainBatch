package d2.revature.pillars;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializationExample {
	/*
	 * Variable Scopes:
	 * Static scope - accessible from anywhere, any class
	 * Instance scope - Variables outside of methods, but within the class
	 * 				  - Accessible from anywhere within the class, below the declaration.
	 * Method scope - Accessible from only within the method. Variable deallocated after object is deallocated.
	 * 				- Variable deallocated after method execution.
	 * Loop scope - Variable only used within the loop it was created in.
	 */
	
	//Instance Scope
	public static ObjectOutputStream oos;

	public static void main(String[] args) {
		//Method scope
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
