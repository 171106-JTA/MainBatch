package inClassAssignment_1;

import java.util.ArrayList;
import java.util.List;

public class Driver {
	public static void main(String[] args) {
		List <Universe> vList = new ArrayList<>();
		
		vList.add(new Universe("Alpha", 5, (Double)5.5));
		vList.add(new Universe("Beta", 7, (Double)1.1));
		vList.add(new Universe("Gamma", 4, (Double)4.4));
		
		
		System.out.println(vList);
		
		System.out.println("Printing original order");
		for(Universe universe : vList) {
			System.out.println(universe.getUniverseName() 
					+ " : Dim - " + universe.getNumDimensions() 
					+ " : Gal - " + universe.getNumGalaxies());
		}
		System.out.println('\n');
		
		System.out.println("Sort Naturally (using comparable) by number of Dimensions");
		vList.sort(null);
		for(Universe universe : vList) {
			System.out.println(universe.getUniverseName() 
					+ " : Dim - " + universe.getNumDimensions() 
					+ " : Gal - " + universe.getNumGalaxies());
		}
		System.out.println('\n');
		
		System.out.println("Sort using comparator by number of Galaxies");
		vList.sort(new UniverseGalaxyComparator()); 
		for(Universe universe : vList) {
			System.out.println(universe.getUniverseName() 
					+ " : Dim - " + universe.getNumDimensions() 
					+ " : Gal - " + universe.getNumGalaxies());
		}
		System.out.println('\n');
		
		
		//testing the StringReverseComparator
		List <String> sList = new ArrayList<>();
		
		
		sList.add("BBBBB");
		sList.add("CCCCC");
		sList.add("AAAAA");
		
		System.out.println(sList);
		sList.sort(new StringReverseComparator()); 
		System.out.println(sList);
	}
}
