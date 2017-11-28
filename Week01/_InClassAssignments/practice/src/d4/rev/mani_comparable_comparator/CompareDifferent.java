package d4.rev.mani_comparable_comparator;

import java.util.Comparator;

public class CompareDifferent implements Comparator<Watch> {
	//sorts by color
	@Override
	public int compare(Watch w1, Watch w2) {
//		return w1.getCost().compareTo(w2.getCost());
		if (w1.getCost() > w2.getCost()) return 1;
		else if (w1.getCost() < w2.getCost()) return -1;
		else return 0;
				
	}
}
