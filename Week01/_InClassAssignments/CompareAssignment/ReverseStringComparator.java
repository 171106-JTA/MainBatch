package d4.revature.collections;

import java.util.Comparator;

public class ReverseStringComparator implements Comparator<String>{
	
	@Override
	public int compare(String o1, String o2) {
		// TODO Auto-generated method stub
		return o1.compareTo(o2) * -1;
	}
}
