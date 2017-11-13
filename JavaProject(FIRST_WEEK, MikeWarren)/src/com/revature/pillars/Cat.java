package com.revature.pillars;

abstract class Cat extends Animal {
	/*
	 * The only modifiers you can use for a class are public, abstract, and final, also just the default modifier.
	 */
	
	public String word = "Cat";
	
	@Override
	public String whatAmI() {
		return "I am a cat";
	}
	
	@Override
	public String species() {
		return "Cat";
	}
}
