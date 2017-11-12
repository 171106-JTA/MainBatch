package com.revature.core.factory;

import java.util.Comparator;

import com.revature.core.BusinessClass;
import com.revature.core.comparator.*;

/**
 * FieldParamsBusinessObjectComparatorFactory (FPBOComparatorFactory)
 * @author Antony Lulciuc
 */
public final class FPBOComparatorFactory {
	private static FPBOComparatorFactory factory;
	
	private FPBOComparatorFactory() {
		// do nothing
	}
	
	public static FPBOComparatorFactory getFactory() {
		return factory == null ? factory = new FPBOComparatorFactory() : factory;
	}
	
	/**
	 * 
	 * @param name business object single class name
	 * @return
	 */
	public Comparator<Object> getComparator(String name) {
		switch (name.toLowerCase()) {
			case BusinessClass.USER:
			case BusinessClass.ADMIN:
			case BusinessClass.CUSTOMER:
				return new FieldParamsUserComparator();
			case BusinessClass.ACCOUNT:
			case BusinessClass.CHECKING:
			case BusinessClass.CREDIT:
				return new FieldParamsAccountComparator();
			case BusinessClass.USERINFO:
				return new FieldParamsUserInfoComparator();
			default:
				return null;
		}
	}
	
}
