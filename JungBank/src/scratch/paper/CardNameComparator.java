package scratch.paper;

import java.util.Comparator;

public class CardNameComparator implements Comparator<Card> {
	
	@Override 
	public int compare(Card b1, Card b2) {
		return b1.getName().compareTo(b2.getName());
	}

}
