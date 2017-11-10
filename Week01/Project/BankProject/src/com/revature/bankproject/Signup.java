package com.revature.bankproject;

import java.io.FileOutputStream; 
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Signup {
	
	/*
	 * Serialization method is called when object needs to be stored in a file for later access
	 */
	
	public void signup(User u) {
		
		try {
	         FileOutputStream file = new FileOutputStream("C:\\Users\\Sherdil Khawaja\\Documents\\workspace\\BankProject\\src" + u.getName() + ".ser");
	         ObjectOutputStream out = new ObjectOutputStream(file);
	         
	         out.writeObject(u);
	         out.close();
	         file.close();
	         
	         System.out.printf("You're now registered.");
	         
	      } catch (IOException i) {
	    	  i.printStackTrace();
	      }
	}

}
