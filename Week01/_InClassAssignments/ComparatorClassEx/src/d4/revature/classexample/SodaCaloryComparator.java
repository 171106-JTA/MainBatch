package d4.revature.classexample;

import java.util.Comparator;

public class SodaCaloryComparator implements Comparator<Soda> {

	@Override
	public int compare(Soda soda1, Soda soda2) {
		if(soda1.getCalories()<soda2.getCalories())
			return -1;
		else if(soda1.getCalories()<soda2.getCalories())
			return 1;
		else
			return 0;
	}
	
}
