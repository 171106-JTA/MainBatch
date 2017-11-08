package com.revature.persistence;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import com.revature.businessobject.BusinessObject;

/**
 * Defines Data and Business Logic Layer communication protocol 
 * @author Antony Lulciuc
 */
public interface Persistenceable {
	/**
	 * Acquires BusinessObject which meets the conditions supplied by the second parameter
	 * @param name data structure we wish to perform query on
	 * @param cnds values data must have in-order to be returned 
	 * @return list of all records which pass query conditions 
	 */
	List<BusinessObject> select(String name, List<SimpleEntry<String,String>> cnds);
	
	/**
	 * Updates existing record
	 * @param businessObject must have unique identifier to update record
	 * @return if less than or equal to then 0 failed else 1 and was successful
	 */
	int update(BusinessObject businessObject);
	
	/**
	 * Updates existing records
	 * @param name data structure to update
	 * @param cnds values data must have to perform update
	 * @param values fields to update
	 * @return if less than or equal to 0 then failed, else how many records were updated 
	 */
	int update(String name, List<SimpleEntry<String,String>> cnds, List<SimpleEntry<String,String>> values);
	
	/**
	 * Creates new record from business object
	 * @param businessObject new record
	 * @return if less than or equal to then 0 failed else 1 and was successful
	 */
	int insert(BusinessObject businessObject);
	
	/**
	 * Creates new record from values supplied
	 * @param name data structure representing record
	 * @param values assigned to record
	 * @return if less than or equal to then 0 failed else 1 and was successful
	 */
	int insert(String name, List<SimpleEntry<String,String>> values);
	
	/**
	 * Removes record from system
	 * @param businessObject instance representation of record to be removed
	 * @return if less than or equal to then 0 failed else 1 and was successful
	 */
	int delete(BusinessObject businessObject);
	
	/**
	 * Removes records from system
	 * @param name data structure to remove
	 * @param cnds conditions record must have to be deleted
	 * @return if less than or equal to 0 then failed, else how many records were removed 
	 */
	int delete(String name, List<SimpleEntry<String,String>> cnds);
}
