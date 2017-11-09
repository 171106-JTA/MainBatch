package com.revature.assignment;

import java.util.Comparator;

public class StringComparatorDescending implements Comparator<String>{

	@Override
	public int compare(String o1, String o2) {
		return o1.compareTo(o2) * -1;
	}

}
