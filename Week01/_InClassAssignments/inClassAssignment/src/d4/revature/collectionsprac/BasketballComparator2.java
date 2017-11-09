package d4.revature.collectionsprac;

import java.util.Comparator;

public class BasketballComparator2 implements  Comparator<BasketballPlayer>{

	@Override
	public int compare(BasketballPlayer b1, BasketballPlayer b2) {
		return  b2.getPlayer().compareTo(b1.getPlayer());	
	}
	

}
