package com.revature.processor.handler.helper;

import java.util.Comparator;
import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.core.FieldParams;
import com.revature.core.Resultset;
import com.revature.core.factory.FPBOComparatorFactory;
import com.revature.core.factory.FieldParamsFactory;
import com.revature.server.Server;

/**
 * Operations used to aid Processor Handlers 
 * @author Antony Lulciuc
 */
public final class GenericHelper {
	public static FPBOComparatorFactory fieldParamsBusnissObjectComparatorFactory = FPBOComparatorFactory.getFactory();
	public static FieldParamsFactory fieldParamsFactory = FieldParamsFactory.getFactory();
	
	/**
	 * Searches all records for desired table
	 * @param table name of table in question
	 * @param fields what values to check against 
	 * @return null if no data exist for table else record with greatest value for specified conditions 
	 */
	public static BusinessObject getLargest(String table, List<String> fields){
		Comparator<Object> comparator = fieldParamsBusnissObjectComparatorFactory.getComparator(table);
		Resultset res = Server.database.select(table, null);
		BusinessObject largest = null;
		FieldParams largestParams;
		
		// If we have any data to compare
		if (res.size() > 0) {
			largest = res.get(0);
			largestParams = fieldParamsFactory.getFieldParams(largest);
			largestParams = joinFieldParams(largestParams, fields);
			
			
			for (BusinessObject item : res) {
				// If left < than right set new largest value
				if (comparator.compare(largestParams, item) < 0) {
					largest = item;
					largestParams = fieldParamsFactory.getFieldParams(largest);
					largestParams = joinFieldParams(largestParams, fields);
				}
			}
		}
			
		return largest;
	}
	
	/**
	 * Joins fieldparams and keeps Left param values 
	 * @param left 
	 * @param right
	 * @return Joined params with left param values 
	 */
	public static FieldParams joinFieldParams(FieldParams left, List<String> right) {
		FieldParams joined = new FieldParams();
		
		for (String key : left.keySet()) {
			if (right.contains(key))
				joined.put(key, left.get(key));
		}
		
		return joined;
	}
	
	
	
	
	
	
	
}
