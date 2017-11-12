package com.revature.processor.handler.helper;

import java.util.Comparator;

import com.revature.businessobject.BusinessObject;
import com.revature.core.FieldParams;
import com.revature.core.Resultset;
import com.revature.core.factory.FPBOComparatorFactory;
import com.revature.core.factory.FieldParamsFactory;
import com.revature.server.Server;

public final class GenericHelper {
	private static FPBOComparatorFactory fieldParamsBusnissObjectComparatorFactory = FPBOComparatorFactory.getFactory();
	private static FieldParamsFactory fieldParamsFactory = FieldParamsFactory.getFactory();
	
	public static BusinessObject getLargest(String table, FieldParams cnds, FieldParams values){
		Comparator<Object> comparator = fieldParamsBusnissObjectComparatorFactory.getComparator(table);
		Resultset res = Server.database.select(table, cnds);
		BusinessObject largest = null;
		FieldParams largestParams;
		
		// If we have any data to compare
		if (res.size() > 0) {
			largest = res.get(0);
			largestParams = fieldParamsFactory.getFieldParams(largest);
			
			for (BusinessObject item : res) {
				// If left < than right set new largest value
				if (comparator.compare(largestParams, item) < 0) {
					largest = item;
					largestParams = fieldParamsFactory.getFieldParams(largest);
				}
			}
		}
			
		return largest;
	}
	
}
