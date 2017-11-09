package inClassAssignment_1;

import java.util.Comparator;

public class UniverseGalaxyComparator implements Comparator<Universe>{
	@Override
	public int compare(Universe u1, Universe u2) {
		return u1.getNumGalaxies().compareTo(u2.getNumGalaxies());
	}
}
