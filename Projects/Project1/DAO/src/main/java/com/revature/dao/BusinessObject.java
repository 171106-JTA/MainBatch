package com.revature.dao;

import java.util.List;

/**
 * Interface used to manage data 
 * @author Antony Lulciuc
 * @param <T> Java representation of Database Table
 */
public interface BusinessObject<T> {
	/**
	 * Acquires all records of represented type from database
	 * @return all records of represented type
	 */
	public List<T> loadAll();
	
	/**
	 * Loads items which matches argument (null values ignored)
	 * @param item - what to check records against
	 * @return list of records which met the criteria specified by argument 
	 */
	public List<T> load(T item);
	
	/**
	 * Adds set of items to database
	 * @param items - what to save in database
	 * @return number of records created 
	 */
	public int insert(List<T> items);
	
	/**
	 * Adds item to database
	 * @param item - what to save in database
	 * @return 1 if item added to database else 0
	 */
	public int insert(T item);
	
	/**
	 * Attempts to update existing record
	 * @param item - what to update
	 * @param value - new values for record
	 * @return number of records updated 
	 */
	public int update(T item, T value);
	
	/**
	 * Deletes set of records from database
	 * @param items - list of items to delete
	 * @return number of records removed from database
	 */
	public int delete(List<T> items);
	
	/**
	 * Deletes item from database
	 * @param item - what to delete
	 * @return number of records removed from database
	 */
	public int delete(T item);
}
