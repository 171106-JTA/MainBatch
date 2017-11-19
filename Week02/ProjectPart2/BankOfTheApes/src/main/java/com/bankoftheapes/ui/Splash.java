package com.bankoftheapes.ui;

public class Splash extends UserInterface {

	/**
	 * The first screen the user sees. Allows user to create new account, log into an old
	 * account, or log in as an administrator
	 * 
	 * @return user option input
	 */
	public static int Screen() {
		System.out.println("Welcome to the Bank of the Apes!");
		System.out.println("Where Humans Fear to Tread!\n");
		System.out.println("Please Select one of the following:");
		System.out.println("1. Login for existing user");
		System.out.println("2. Create new user account");
		System.out.println("3. Management Login");
		
		System.out.print("Please Enter Number: ");
		
		return UserInterface.readIntInput();
	}
	
}
