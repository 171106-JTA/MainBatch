package com.revature.pillars;

/*
 * Interfaces are like abstract classes, except there are a few key differences.
 * 	• Interfaces are a contract: whatever concrete class implements them, 
 * 		must provide implementation for the method signatures.
 * 	• Interfaces, as of Java 8+, can now have concrete classes. This is done through one of two ways:
 * 		- use a static modifier
 * 		- use a new modifier, default
 * 	• Compared to abstract classes, differences include : 
 * 		- can implement any number of interfaces
 * 		- can only extend 1 abstract class at a time
 * 		- abstract classes can have concrete methods by default, whereas interfaces require keyword for it.
 * 		- all members in an interface, default to public static, and final
 */
public interface Animalable {
	int i = 0;	// implicitly public, static, final.
	public void walk();
	public void eat();
	public void makeNoise();
	
	public default void randomMethod() { 
		System.out.println("Implemented");
	}
	
}
