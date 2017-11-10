package com.revature.ui;

public class Splash extends UserInterface {

	public static int Screen() {
		System.out.println("Welcome to the Bank of the Apes!");
		System.out.println("Where Humans Fear to Tread!");
		System.out.println("Please Select one of the following:");
		System.out.println("1. Login for existing user");
		System.out.println("2. Create new user account");
		System.out.println("3. Administrator Login");
		
		System.out.print("Please Enter Number: ");
		
		return Integer.parseInt(UserInterface.readInput());
	}
	
}
