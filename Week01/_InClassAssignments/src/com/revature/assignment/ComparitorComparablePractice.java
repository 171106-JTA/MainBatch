package com.revature.assignment;

import java.util.ArrayList;
import java.util.List;

public class ComparitorComparablePractice {
	public static void main(String[] args) {
		List<Person> people = new ArrayList<>();
		List<String> stuff = new ArrayList<>();
		
		// Init Lists
		people.add(new Person("Billy", "Path Dr.", 23));
		people.add(new Person("Joe", "Brand Dr.", 54));
		people.add(new Person("Moe", "Town Dr.", 34));
		people.add(new Person("Andrew", "County Dr.", 33));
		people.add(new Person("Andrew", "County Dr.", 32));
		
		for (int i = (int)'A'; i <= (int)'Z'; i++) 
			stuff.add("" +(char)i);
		
		
		
		// Print
		System.out.println("=== People ===");
		System.out.println(people);
		
		System.out.println("=== String Stuff ===");
		System.out.println(stuff);
		
		// Sort
		people.sort(null);
		
		// Print
		System.out.println("=== Natural Sort People ===");
		System.out.println(people);
		
		// Sort
		people.sort(new PersonComparatorDescending());
		stuff.sort(new StringComparatorDescending());
		
		// Print
		System.out.println("=== Descending Sort People ===");
		System.out.println(people);
		
		System.out.println("=== Descending Sort String Stuff ===");
		System.out.println(stuff);
		
	}
}
