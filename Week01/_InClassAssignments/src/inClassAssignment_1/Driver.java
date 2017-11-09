package inClassAssignment_1;

import java.util.ArrayList;
import java.util.List;

public class Driver {
	public static void main(String[] args) {
		
		//Create a list of universes with appropriate test values
		List <Universe> vList = new ArrayList<>();
		vList.add(new Universe("Alpha", 5, (Double)5.5));
		vList.add(new Universe("Beta", 7, (Double)1.1));
		vList.add(new Universe("Gamma", 4, (Double)4.4));
		vList.add(new Universe("Delta", 0, (Double)1000000.0));
		vList.add(new Universe("Epsilon", 876, (Double)0.0));
		
		
		//Print the original ordering the list
		System.out.println("Printing original order");
		for(Universe universe : vList) {
			System.out.println(universe.getUniverseName() 
					+ " : Dim - " + universe.getNumDimensions() 
					+ " : Gal - " + universe.getNumGalaxies());
		}
		System.out.println('\n');
		
		//////////////////////////////////////////////
		//Part 1: Comparable
		//////////////////////////////////////////////
		//Sort the list according to the natural ordering
		//Natural ordering: sort the Dimensions in descending order
		System.out.println("Sort Naturally (using comparable) by number of Dimensions (ascending order)");
		vList.sort(null);
		for(Universe universe : vList) {
			System.out.println(universe.getUniverseName() 
					+ " : Dim - " + universe.getNumDimensions() 
					+ " : Gal - " + universe.getNumGalaxies());
		}
		System.out.println('\n');
		
		//////////////////////////////////////////////
		//Part 2: Comparator
		//////////////////////////////////////////////
		//Comparator example
		//Sort the list using the comparator
		//Comparator ordering: sort the galaxies in descending order
		System.out.println("Sort using comparator by number of Galaxies (descending order)");
		vList.sort(new UniverseGalaxyComparator()); 
		for(Universe universe : vList) {
			System.out.println(universe.getUniverseName() 
					+ " : Dim - " + universe.getNumDimensions() 
					+ " : Gal - " + universe.getNumGalaxies());
		}
		System.out.println('\n');
		
		//////////////////////////////////////////////
		//Part 3: Reverse String Sorting
		//////////////////////////////////////////////
		//Test the StringReverseComparator Class
		List <String> sList = new ArrayList<>();
		sList.add("BBBBB");
		sList.add("CCCCC");
		sList.add("AAAAA");
		sList.add("ZZZZZ");
		sList.add("XXXXX");
		sList.add("YYYYY");
		
		System.out.println("Use the string comparator to sort strings in reverse order");
		System.out.println("Original Ordering: " + sList);
		sList.sort(null);
		System.out.println("Natural Ordering: " + sList);
		sList.sort(new StringReverseComparator()); 
		System.out.println("Reverse Ordering: " + sList);
	}
}
