package com.revature.dao.factory;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.revature.businessobject.BusinessObject;

/**
 * Used to convert ResultSet to Java Objects and extract Object field data for queries 
 * @author Antony Lulciuc
 */
public final class BusinessObjectFactory {
	private static BusinessObjectFactory factory = new BusinessObjectFactory();
	private Logger logger = Logger.getLogger(BusinessObjectFactory.class);
	
	/**
	 * Singleton
	 */
	private BusinessObjectFactory() {
		// do nothing
	}
	
	/**
	 * @return instance of BusinessObject Factory
	 */
	public static BusinessObjectFactory getFactory() {
		return factory;
	}
	
	/**
	 * Converts ResultSet into specified Java Object which extends [implements] BusinessObject
	 * @param res - result set from query
	 * @param type - Class definition
	 * @return List of BusinessObjects represented 
	 */
	public List<BusinessObject> getBusinessObject(ResultSet res, Class<? extends BusinessObject> type) {
		List<BusinessObject> list = new LinkedList<>();
		BusinessObject object;
		Class<? extends BusinessObject> clazz;
		Object data;
		String name;
		
		logger.debug("converting resultset to Java objects");
		
		// Go through result set and 
		try {
			while (res.next()) {
				try {
					// create new instance to represent record
					object = type.newInstance();
					clazz = object.getClass();
					
					// Assign data to fields 
					for (Field field : clazz.getDeclaredFields()) {
						data = res.getObject(name = field.getName().toLowerCase());
						
						// Assign value to object
						if (data != null) {
							// set accessibility
							field.setAccessible(true);

							// Assign value
							switch (field.getType().getSimpleName().toLowerCase()) {
								case "string":
									field.set(object, res.getString(name));
									break;
								case "integer":
									field.set(object, res.getInt(name));
									break;
								case "long":
									field.set(object, res.getLong(name));
									break;
								case "short":
									field.set(object, res.getShort(name));
									break;
								case "float":
									field.set(object, res.getFloat(name));
									break;
								case "double":
									field.set(object, res.getDouble(name));
									break;
								case "boolean":
									field.set(object, res.getBoolean(name));
									break;
									
									// TODO - Add Blob & Clob
							}
							
							// reset
							field.setAccessible(false);
						}
					}
					
					// add record to list
					list.add(object);
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
					logger.warn("failed instantiate Java object, message=" + e);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warn("failed to convert resultset to Java objects, message=" + e);
		}
		
		return list;
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
