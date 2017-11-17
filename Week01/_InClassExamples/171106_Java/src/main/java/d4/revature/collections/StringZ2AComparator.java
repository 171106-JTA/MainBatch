package d4.revature.collections;

import java.util.Comparator;

public class StringZ2AComparator implements Comparator<String>{

	@Override
	public int compare(String o1, String o2) {
		return (o1.compareTo(o2)*-1);
	}

}
