package d4.revature.classexample;

import java.util.Comparator;

public class ReverseSodaComparator implements Comparator<Soda>{

	@Override
	public int compare(Soda soda1, Soda soda2) {
		
		return -1*soda1.getName().compareTo(soda2.getName());
	}

}
