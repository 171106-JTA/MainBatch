package com.revature.dao;

import static com.revature.dao.util.ConnectionUtil.close;
import static com.revature.dao.util.ConnectionUtil.getConnection;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
			// Get handle to statement 
			statement = conn.createStatement();
			
			// Perform query
			res = statement.executeQuery(sql);
			
			// Transform database records into Java Objects
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
		String sql = "SELECT * FROM " + map.get(item.getClass().getSimpleName());
		List<String> params = prepareParameters(item);
		List<BusinessObject> records = null;
		PreparedStatement statement = null;
		ResultSet res = null;

		// If Where clause needed 
		if (params.size() > 0) 
			sql += " WHERE " + String.join(" AND ", params);
		
		logger.debug("preforming query " + sql + " ...");

		// Attempt to load records from database
		try (Connection conn = getConnection()) {
			// Get handle to statement 
			statement = conn.prepareStatement(sql);
			
			// Sanitize/Parameterize statement
			assignClauseValues(statement, item, 1);
			
			// Perform query
			res = statement.executeQuery();
			
			// Transform database records into Java Objects
			records = factory.getBusinessObject(res, item.getClass());
		} catch (SQLException | ClassCastException e) {
			logger.debug("query failed " + sql + ", message=" + e);
			e.printStackTrace();
		} finally {
			close(statement);
			close(res);
		}

		return records;
	}
	
	/**
	 * Adds set of items to database
	 * @param items - what to save in database
	 * @return number of records created 
	 */
	public static int insert(List<BusinessObject> items) {
		int total = 0;

		for (BusinessObject item : items) 
			total += insert(item);
	
		return total;
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
		int total = 0;

		for (BusinessObject item : items) 
			total += delete(item);
	
		return total;
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
	
	///
	//	PRIVATE METHODS 
	///
	
	/**
	 * Converts BusinessObject fields into parameterized list
	 * @param item - what to convert
	 * @return parameterized list representing object fields (non-null values only)
	 */
	private static List<String> prepareParameters(BusinessObject item) {
		Map<String, Map<String, Object>> params = factory.getParams(item);
		List<String> parameterization = new LinkedList<>();
		Map<String, Object> fields;
		
		
		// Assign fields to clause
		for (Map.Entry<String, Map<String, Object>> node : params.entrySet()) {
			// Get fields 
			fields = node.getValue();

			// assign field names
			for (Map.Entry<String, Object> data : fields.entrySet()) 
				parameterization.add(data.getKey().toLowerCase() + "=?");
		}
		
		return parameterization;
	}
	
	/**
	 * Assigns values to parameterized statement
	 * @param statement - what to apply values to
	 * @param item - values to apply
	 * @param pos - which column do we start on
	 * @throws SQLException, ClassCastException
	 */
	private static void assignClauseValues(PreparedStatement statement, BusinessObject item, int pos) throws SQLException, ClassCastException {
		Map<String, Map<String, Object>> params = factory.getParams(item);
		Map<String, Object> fields;
		String clazz;
		
		// Assign fields to clause
		for (Map.Entry<String, Map<String, Object>> node : params.entrySet()) {
			// Get fields 
			fields = node.getValue();
			
			// get type
			clazz = node.getKey().toLowerCase();
			
			// assign field names
			for (Map.Entry<String, Object> data : fields.entrySet()) {
				switch (clazz) {
					case "string":
						statement.setString(pos, (String)data.getValue());
						break;
					case "integer":
						statement.setInt(pos, (Integer)data.getValue());
						break;
					case "long":
						statement.setLong(pos, (Long)data.getValue());
						break;
					case "boolean":
						statement.setBoolean(pos, (Boolean)data.getValue());
						break;
					case "float":
						statement.setFloat(pos, (Float)data.getValue());
						break;
					case "double":
						statement.setDouble(pos, (Double)data.getValue());
						break;
					case "short":
						statement.setShort(pos, (Short)data.getValue());
						break;
					case "blob":
						statement.setBlob(pos, (Blob)data.getValue());
						break;
					case "clob":
						statement.setClob(pos, (Clob)data.getValue());
						break;
					case "date":
						statement.setDate(pos, (Date)data.getValue());
						break;
				}
				
				// Move to next parameter
				++pos;
			}
		}
	}	
}
