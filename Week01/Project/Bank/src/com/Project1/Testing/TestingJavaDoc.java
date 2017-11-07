package com.Project1.Testing;

import java.util.Scanner;

/** Class Description of TestingJavaDoc */
public class TestingJavaDoc {

	/** 
	 * Contains a public string 
	 */
	public String publicString; 
	
	/** 
	 * Contains a protected string 
	 */
	protected String protectedString; 
	
	/**
	 * Contains a package-private string
	 */
	String defaultString;  //Default or package-private
	
	/**
	 * Contains a private string
	 */
	private String privateString;
	
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
	
	/**
	 * Getter for privateString member
	 * @return string contained in privateString
	 */
	public String getPrivateString() {
		return privateString;
	}

	/**
	 * Setter for privateString member
	 * @param privateString			String to save into privateString member
	 */
	public void setPrivateString(String privateString) {
		this.privateString = privateString;
	}
	
	
	
	

}
