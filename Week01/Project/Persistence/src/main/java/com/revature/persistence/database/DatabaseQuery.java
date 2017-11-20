package com.revature.persistence.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.core.FieldParams;
import com.revature.core.Resultset;
import com.revature.persistence.database.util.ConnectionUtil;

public abstract class DatabaseQuery extends DatabasePersistence {

	public Resultset select(String name, FieldParams cnds) {
		List<String> clause = new ArrayList<String>();
		PreparedStatement statement = null;
		String sql = "SELECT * FROM " + validateTableName(name);
		ResultSet sqlRes = null;
		Resultset res = null;
		
		if (cnds == null)
			cnds = new FieldParams();
		
		try (Connection conn = ConnectionUtil.getConnection();) {
			for (String key : cnds.keySet()) {
				if (!key.equals(BusinessObject.SESSIONID))
					clause.add(key + "=?");
			}
			
			// Append where 
			if (clause.size() > 0)
				sql += " WHERE " + String.join(" AND ", clause);
			
			// Set select 
			statement = conn.prepareStatement(sql);
			
			// Assemble where clause 
			clauseBuilder.build(name, statement, cnds);
			
			// execute query
			sqlRes = statement.executeQuery();
			
			// Convert to BusinessObject Resultset instance 
			res = businessObjectFactory.getResultset(name, sqlRes);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("failed to make query " + cnds);
		} finally {
			ConnectionUtil.close(sqlRes);
			ConnectionUtil.close(statement);
		}
		
		return res;
	}
}
