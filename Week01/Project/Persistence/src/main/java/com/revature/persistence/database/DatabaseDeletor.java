package com.revature.persistence.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.core.FieldParams;
import com.revature.persistence.database.util.ConnectionUtil;

public abstract class DatabaseDeletor extends DatabaseInsertor {

	public int delete(BusinessObject businessObject) {
		String name = businessObject.getClass().getSimpleName();
		return delete(name, fieldParamsFactory.getFieldParams(businessObject));
	}

	public int delete(String name, FieldParams cnds) {
		String sql = "DELETE FROM " + validateTableName(name);
		List<String> params = new ArrayList<>();
		PreparedStatement statement = null;
		int total = 0;
		
		try (Connection conn = ConnectionUtil.getConnection();) {
			for (String key : cnds.keySet())  {
				if (!key.equals(BusinessObject.SESSIONID))
					params.add(key + "=?");
			}
			if (params.size() > 0) 
				sql += " WHERE " + String.join(" AND ", params);
			
			
			statement = conn.prepareStatement(sql);
			clauseBuilder.build(name, statement, cnds);
			total = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.warn("failed to delete records " + cnds + " message=" + e.getMessage());
		} finally {
			ConnectionUtil.close(statement);
		}
		
		return total;
	}

}
