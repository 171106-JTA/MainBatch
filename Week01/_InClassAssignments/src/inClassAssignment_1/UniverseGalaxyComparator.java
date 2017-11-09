package inClassAssignment_1;

import java.util.Comparator;

public class UniverseGalaxyComparator implements Comparator<Universe>{
	////////////////////////////////
	//Comparator
	////////////////////////////////
	/**
	* Comparator ordering for Universe class:
	* Sort by number of Galaxies in descending order
	*/
	@Override
	public int compare(Universe u1, Universe u2) {
		return -1*(u1.getNumGalaxies().compareTo(u2.getNumGalaxies()));
	}
}
