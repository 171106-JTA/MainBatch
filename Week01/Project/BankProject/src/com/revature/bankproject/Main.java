package com.revature.bankproject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class Main {
	
	static ObjectInputStream ois;
	static User user;
	static Signup s = new Signup();
	static Login ln = new Login();
	
	
	public static void main (String[] args) throws IOException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome! \nPlease (1)Login, (2)Register or (0) to exit");
		int l = scan.nextInt();
		boolean exit = false;
		
		do {
			  switch (l) {
			  /*
			   * If user wants to login
			   */
              case 1:
            	  System.out.println("Please enter username: ");
            	  String u = scan.next();
            	  System.out.println("Please enter password: ");
            	  String p = scan.next();
            	  
            	  ln.login(u, p, user);
					
            	  exit = true;
            	  break;
				
              /*
               * If user wants to register for a bank account
               */
              case 2:		
					System.out.println("Please enter new username: ");
					String uNew = scan.next();
					System.out.println("Please enter new password: ");
					String pNew = scan.next();
					System.out.println("Please enter new balance: $");
					double b = scan.nextDouble();
					Account a = new Account(b);
					
					User newUser = new User (uNew, pNew, "User", a, false);
					//User newUser = new User (uNew, pNew, "Admin", a, false);

					System.out.println(newUser.toString());
					
					/*
					 * Calls the serialize method in class Serialization, to store user data in files
					 */
					s.signup(newUser);
					exit = true;
					break;
					
              
              /*
               * If user wants to exit the program
               */
              case 0:
            	  exit = true;
            	  break;
					
			  }
			  
		
		} while (!exit);
			System.out.println("\nGoodbye");
			scan.close();
	}
}

