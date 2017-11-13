package com.revature.collections;

import java.util.Comparator;

public class PojoComparatorReverse implements Comparator<Pojo>{

	@Override
	public int compare(Pojo o1, Pojo o2) {
		return -(o1.getName().compareTo(o2.getName()));
	}

}
