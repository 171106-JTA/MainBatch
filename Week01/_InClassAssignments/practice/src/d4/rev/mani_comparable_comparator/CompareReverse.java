package d4.rev.mani_comparable_comparator;

import java.util.Comparator;

public class CompareReverse implements Comparator<Watch> {
	//sorts z-a (reverse natural)
	@Override
	public int compare(Watch w1, Watch w2) {
		return w1.getBrand().compareTo(w2.getBrand())*-1;
	}
}
