package inClassAssignment_1;

import java.util.Comparator;

public class StringReverseComparator implements Comparator<String>{
	@Override
	public int compare(String s1, String s2) {
		//Return the opposite of the existing comparTo() function
		return -1*(s1.compareTo(s2));
	}
}
