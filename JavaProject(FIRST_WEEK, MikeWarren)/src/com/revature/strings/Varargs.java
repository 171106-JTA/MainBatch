package com.revature.strings;

public class Varargs {

	public static void main(String[] args) {
		Varargs v = new Varargs();
		v.addition(2,3,4);
	}

	public int addition(int... x)
	{
		/* 
		 * The method above uses varargs as a parameter. 
		 * This allows for dynamic amount of input that is stored as an array upon processing.
		 * 
		 * We can use enhanced for loop to iterate through the array and add all numbers together.
		 */
		int result = 0;
		for (int i : x) { 
			System.out.println(result + " + " + i + " = " + (result += i));
		}
		
		
		return result; 
	}
}
