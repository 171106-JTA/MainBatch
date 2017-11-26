package com.revature.dao;

import static com.revature.dao.util.ConnectionUtil.close;
import static com.revature.dao.util.ConnectionUtil.getConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.businessobject.BusinessObject;
import com.revature.dao.factory.BusinessObjectFactory;

/**
 * Communication medium between database and server
 * @author Antony Lulciuc
 */
public final class DAOBusinessObject {
	private static BusinessObjectFactory factory = BusinessObjectFactory.getFactory();
	private static BODBMap map = BODBMap.getMap();
	private static Logger logger = Logger.getLogger(DAOBusinessObject.class);
	
	/**
	 * Acquires all records of represented type from database
	 * @return all records of represented type
	 */
	public static List<BusinessObject> loadAll(Class<? extends BusinessObject> type) {
		String sql = "SELECT * FROM " +  map.get(type.getSimpleName());
		List<BusinessObject> records = null;
		Statement statement = null;
		ResultSet res = null;
		
		logger.debug("preforming query " + sql + " ...");
		
		// Attempt to load all records from database
		try (Connection conn = getConnection()) {
			statement = conn.createStatement();
			res = statement.executeQuery(sql);
			records = factory.getBusinessObject(res, type);
		} catch (SQLException e) {
			logger.debug("query failed " + sql + ", message=" + e);
			e.printStackTrace();
		} finally {
			close(statement);
			close(res);
		}
		
		return records;
	}
	
	/**
	 * Loads items which matches argument (null values ignored)
	 * @param item - what to check records against
	 * @return list of records which met the criteria specified by argument 
	 */
	public static List<BusinessObject> load(BusinessObject item) {
		// stub
		return null;
	}
	
	/**
	 * Adds set of items to database
	 * @param items - what to save in database
	 * @return number of records created 
	 */
	public static int insert(List<BusinessObject> items) {
		// stub
		return 0;
	}
	
	/**
	 * Adds item to database
	 * @param item - what to save in database
	 * @return 1 if item added to database else 0
	 */
	public static int insert(BusinessObject item) {
		// stub
		return 0;
	}
	
	/**
	 * Attempts to update existing record
	 * @param item - what to update
	 * @param value - new values for record
	 * @return number of records updated 
	 */
	public static int update(BusinessObject item, BusinessObject value) {
		// stub
		return 0;
	}
	
	/**
	 * Deletes set of records from database
	 * @param items - list of items to delete
	 * @return number of records removed from database
	 */
	public static int delete(List<BusinessObject> items) {
		// stub
		return 0;
	}
	
	/**
	 * Deletes item from database
	 * @param item - what to delete
	 * @return number of records removed from database
	 */
	public static int delete(BusinessObject item) {
		// stub
		return 0;
	}
	
}
