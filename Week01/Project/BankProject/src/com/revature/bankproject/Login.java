package com.revature.bankproject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Login {

	Scanner scan = new Scanner(System.in);
	static ObjectInputStream ois;
	
	
	public void login(String u, String p, User user) throws IOException {
		
		/*
		 * Deserialize object, if it exist users log in to the bank account system
		 */
			try {
				ois = new ObjectInputStream(new FileInputStream("C:\\Users\\Sherdil Khawaja\\Documents\\workspace\\BankProject\\src" + u + ".ser"));
				user = (User)ois.readObject();
				
				FileOutputStream file = new FileOutputStream("C:\\Users\\Sherdil Khawaja\\Documents\\workspace\\BankProject\\src" + u + ".ser");
		        ObjectOutputStream out = new ObjectOutputStream(file);
				
				/*
				 * If the user's role is an administrator
				 */
				if (user.getRole().equals("Admin")) {
					System.out.println("Hello Admin");
					
					
					
					
					
					
				} else {
					
					/*
					 * Check if the user has been verified, if not program is exited as they wait for admin to verify them. 
					 */
					
					/*if(user.isVerify() == false) {
						System.out.println("Please wait");
					}*/

					
					/*
					 * User is asked whether he/she wants to withdraw/deposit money to/from account
					 */
				if (user.getName().equals(u) && user.getPassword().equals(p)) {

					System.out.print("Welcome " + u + "!\nYour Balance: " + user.getAccount().getBalance());
					System.out.print("\nWould you like to (1)Deposit or (2)Withdraw money or (0)Exit");
					int c = scan.nextInt();
					
					if (c == 1) {
						System.out.println("Please enter the amount you want to deposit");
						double amount = scan.nextDouble();
						user.getAccount().deposit(amount);

				        out.writeObject(user);
				        out.close();
				        file.close();

						System.out.println("You now have: " + user.getAccount().getBalance());
		
					}
					
					if (c == 2) {
						System.out.println("Please enter the amount you want to withdraw");
						double amount = scan.nextDouble();
						user.getAccount().withdraw(amount);
						
						out.writeObject(user);
				        out.close();
				        file.close();
						
						System.out.println("You have: " + user.getAccount().balance);

					}
					
				}
				
				/*
				 * If user name and password are incorrect
				 */
				else if(user.getName().equals(u) || user.getPassword().equals(p)) {
					System.out.println("Incorrect username/password try again");
				}
				}
				
			} catch (IOException | ClassNotFoundException e ){
				System.out.println("Oops");
			
			} finally{
				if  (ois != null) {
					ois.close();
				}
			}
			
	}
}
