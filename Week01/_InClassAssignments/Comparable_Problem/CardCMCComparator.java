package d4.revature.collections;

import java.util.Comparator;
import java.util.IllegalFormatException;

public class CardCMCComparator implements Comparator<Card> {

	@Override
	public int compare(Card c1, Card c2) {
		//get preceding generic cost
		Integer cost1 = 0; 
		Integer cost2 = 0; 
		try {
			cost1 = Integer.parseInt(c1.getCmc().substring(0,1));
			
		} catch(NumberFormatException e){
			cost1++; 
		} finally {
			cost1 += c1.getCmc().length() - 1;
		}
		
		try {
			cost2 = Integer.parseInt(c2.getCmc().substring(0,1));
			
		} catch(NumberFormatException e){
			cost2++; 
		} finally {
			cost2 += c2.getCmc().length() - 1;
		}
		
		return cost1.compareTo(cost2); 


	}



}
