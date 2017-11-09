package d4.revature.comparablevscomparator;

import java.util.Comparator;

public class StringComparator implements Comparator<String>{
/*
 * String comparator that sorts strings from Z to A
 * */
	@Override
	public int compare(String o1, String o2) {
		return -1 *(o1.compareTo(o2));
	}

}
