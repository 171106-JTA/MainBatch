package com.revature.collections;

import java.util.ArrayList;
import java.util.List;

import com.revature.collections.Bike;
import com.revature.collections.BikeComparator;

public class BikeComparatorComparableTest {

	public static void main(String[] args) {
		// create some bikes
		List<Bike> someBikes = new ArrayList<>();
		someBikes.add(new Bike("Giant", "Roam", 8000, 21));
		someBikes.add(new Bike("Giant", "Bobbert", 100, 10));
		someBikes.add(new Bike("Schwinn", "Speedster", 0, 21));
		someBikes.add(new Bike("Mongoose", "Legend", 15000, 40));
		System.out.println("Comparing bikes by natural order...");
		someBikes.sort(null);
		for (Bike b : someBikes)
		{
			System.out.println(b);
		}
		System.out.println("\nNow let's 'reverse' that order...");
		someBikes.sort(new BikeComparator(true));
		for (Bike b : someBikes)
		{
			System.out.println(b);
		}
		System.out.println("\nNow let's sort it in custom way....");
		someBikes.sort(new BikeComparator(false));
		for (Bike b : someBikes)
		{
			System.out.println(b);
		}
	}

}
