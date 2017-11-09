package com.revature.assignment;

import java.util.Comparator;

public class PersonComparatorDescending implements Comparator<Person>{

	@Override
	public int compare(Person o1, Person o2) {
		// TODO Auto-generated method stub
		return o1.compareTo(o2) * -1;
	}

}
