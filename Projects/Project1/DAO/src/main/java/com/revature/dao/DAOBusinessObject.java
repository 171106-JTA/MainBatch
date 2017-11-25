package com.revature.dao;

import java.util.List;

import com.revature.businessobject.BusinessObject;

/**
 * Communication medium between database and server
 * @author Antony Lulciuc
 * @param <T> Java representation of Database Table
 */
public final class DAOBusinessObject {
	/**
	 * Acquires all records of represented type from database
	 * @return all records of represented type
	 */
	public static List<BusinessObject> loadAll() {
		// stub
		return null;
	}
	
	/**
	 * Loads items which matches argument (null values ignored)
	 * @param item - what to check records against
	 * @return list of records which met the criteria specified by argument 
	 */
	public List<BusinessObject> load(BusinessObject item) {
		// stub
		return null;
	}
	
	/**
	 * Adds set of items to database
	 * @param items - what to save in database
	 * @return number of records created 
	 */
	public int insert(List<BusinessObject> items) {
		// stub
		return 0;
	}
	
	/**
	 * Adds item to database
	 * @param item - what to save in database
	 * @return 1 if item added to database else 0
	 */
	public int insert(BusinessObject item) {
		// stub
		return 0;
	}
	
	/**
	 * Attempts to update existing record
	 * @param item - what to update
	 * @param value - new values for record
	 * @return number of records updated 
	 */
	public int update(BusinessObject item, BusinessObject value) {
		// stub
		return 0;
	}
	
	/**
	 * Deletes set of records from database
	 * @param items - list of items to delete
	 * @return number of records removed from database
	 */
	public int delete(List<BusinessObject> items) {
		// stub
		return 0;
	}
	
	/**
	 * Deletes item from database
	 * @param item - what to delete
	 * @return number of records removed from database
	 */
	public int delete(BusinessObject item) {
		// stub
		return 0;
	}
}
