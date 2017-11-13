package com.revature.collections;

import java.util.Comparator;

public class PojoComparatorKids implements Comparator<Pojo>{

	@Override
	public int compare(Pojo o1, Pojo o2) {
		if(o1.getNumOfKids() < o2.getNumOfKids()) {
			return -1;
		}else if(o1.getNumOfKids() > o2.getNumOfKids()) {
			return 1;
		}
		return 0;
	}

}
