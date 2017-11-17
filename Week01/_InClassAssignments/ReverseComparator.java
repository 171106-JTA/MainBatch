package scratch.paper;

import java.util.Comparator;

public class ReverseComparator implements Comparator<Card> {

	@Override 
	public int compare(Card b1, Card b2) {
		return -1*(b1.getName().compareTo(b2.getName()));
	}
}
