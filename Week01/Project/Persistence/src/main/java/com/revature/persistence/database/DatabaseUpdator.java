package com.revature.persistence.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.core.FieldParams;
import com.revature.persistence.database.util.ConnectionUtil;

public class DatabaseUpdator extends DatabaseDeletor {

	public int update(BusinessObject businessObject) {
		String name = businessObject.getClass().getSimpleName();
		return update(name, fieldParamsFactory.getFieldParams(businessObject), fieldParamsFactory.getFieldParams(businessObject));
	}

	public int update(String name, FieldParams cnds, FieldParams values) {
		String sql = "UPDATE " + validateTableName(name);
		String set = " SET ";
		String where = " WHERE ";
		List<String> setParams = new ArrayList<>();
		List<String> whereParams = new ArrayList<>();
		PreparedStatement statement = null;
		int total = 0;
		
		try (Connection conn = ConnectionUtil.getConnection();) {
			for (String key : cnds.keySet()) {
				if (!key.equals(BusinessObject.SESSIONID))
					whereParams.add(key + "=?");
			}
			
			for (String key : values.keySet()) {
				if (!key.equals(BusinessObject.SESSIONID))
					setParams.add(key + "=?");
			}
			
			// Build sql statement
			sql += set + String.join(",", setParams) + where + String.join(" AND ", whereParams);
			statement = conn.prepareStatement(sql);
			clauseBuilder.build(name, statement, values, cnds);
			total = statement.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
			logger.warn("failed to update record " + cnds + values + " message=" + e.getMessage());
		} finally {
			ConnectionUtil.close(statement);
		}
		
		return total;
	}

}
