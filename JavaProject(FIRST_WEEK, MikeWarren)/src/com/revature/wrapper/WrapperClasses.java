package com.revature.wrapper;

public class WrapperClasses {
	public static void main(String[] args) {
		/*
		 * A wrapper class is the Object representation of a primitive data type that wraps around the data type. 
		 * All wrapper classes are simply the full name of a primitive value following CamelCase.
		 */
		Integer i = 5; // autoboxing by instantiation
		
		int j = i;	// auto-unboxing by instantiation
		System.out.println(i);
		System.out.println(2 * i);
		
		
		
		Double d = new Double(4.4);
		/*
		 * Integer
		 * Double
		 * Float 
		 * Short
		 * Long
		 * Byte
		 * Character
		 * Boolean
		 */
		
		/*
		 * Autoboxing an auto-unboxing
		 *	• Autoboxing is simply the implicit conversion of a primitive data type to its wrapper class
		 *	• Auto-unboxing is the implicit conversion of a wrapper class to its primitive data type.
		 */
		
		WrapperClasses wc = new WrapperClasses();
		wc.doMath(5.5, 3.3);
	}
	
	public void doMath(Double x, Double y) {
		System.out.println(x/y);
	}
}
