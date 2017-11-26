package com.revature.dao.factory;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.revature.businessobject.BusinessObject;

/**
 * 
 * @author Antony Lulciuc
 *
 */
public final class BusinessObjectFactory {
	private static BusinessObjectFactory factory = new BusinessObjectFactory();
	private Logger logger = Logger.getLogger(BusinessObjectFactory.class);
	
	private BusinessObjectFactory() {
		// do nothing
	}
	
	public static BusinessObjectFactory getFactory() {
		return factory;
	}
	
	public List<BusinessObject> getBusinessObject(ResultSet res, Class<? extends BusinessObject> type) {
		return null;
	}
	
	/**
	 * Acquires all non null values contained in BusinessObject Bean
	 * @param businessObject - what to access
	 * @return map of all non-null values contained in instance 
	 */
	public Map<String, Map<String, Object>> getParams(BusinessObject businessObject) {
		Map<String, Map<String, Object>> params = new HashMap<>();
		Class<? extends BusinessObject> clazz = businessObject.getClass();
		Map<String, Object> fields;
		Object data;
		String name;
		
		// Go through all fields and extract all non-null values 
		for (Field field : clazz.getDeclaredFields()) {
			// set access property
			field.setAccessible(true);
			data = null;
			
			try {
				// get data
				data = field.get(businessObject);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				logger.warn("failed to access instance variables, message=" + e);
			}
			
			// if valid value 
			if (data != null) {
				name = data.getClass().getSimpleName();
				
				// if Exists then add to map of that type
				if (params.containsKey(name)) {
					fields = params.get(name);
					fields.put(field.getName(), data);
				} else {
					// create new list to store value
					fields = new HashMap<>();
					fields.put(field.getName(), data);
					
					// insert new list 
					params.put(name, fields);
				}
			}
			
			// reset to false
			field.setAccessible(false);
		}
		
		return params;
	}
}
