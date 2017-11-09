package d4.revature.collectionsprac;

import java.util.Comparator;

public class BasketballComparator implements  Comparator<BasketballPlayer> {

	@Override
	public int compare(BasketballPlayer b1, BasketballPlayer b2) {
		return b1.getPlayer().compareTo(b2.getPlayer());	
	}
	

	

}
