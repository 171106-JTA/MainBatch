package com.Project1.Testing;

import java.util.Scanner;

public class TestingConsole {

	public static void main(String[] args) {			
		//Get and print user input
		//Following tutorial: 
		//http://www.programmingsimplified.com/java/source-code/java-program-take-input-from-user
		//Accessed 11/7/2017
		
		Scanner in = new Scanner(System.in); 
		System.out.println("Enter a string: ");
		String s = in.nextLine();
		System.out.println(s);
	}

}
