package d4.revature.comparablevscomparator;

import java.util.Comparator;

public class ShoesComparator implements Comparator<Shoe>{
	/*
	 * Comparator class to sort shoes by price*/

	@Override
	public int compare(Shoe o1, Shoe o2) {
		//Sorts the shoes by price
		if (o1.getPrice() < o2.getPrice())
				return -1;
		else if (o1.getPrice() < o2.getPrice())
			return 1;
		return 0;
	}

}
