package com.revature.collections;

import java.util.Comparator;

public class BikeComparator implements Comparator<Bike>{

	private boolean doStringReverseCompare;
	public BikeComparator(boolean b)
	{
		this.doStringReverseCompare = b;
	}
	@Override
	public int compare(Bike o1, Bike o2) {
		return ((doStringReverseCompare) ?
				// let's compare brands in reverse order
				-o1.getBrand().compareTo(o2.getBrand())  
				// let's compare by mileage, in ascending order!
				: -Integer.compare(o1.getMileage(), o2.getMileage()));
	}

}
