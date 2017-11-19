package com.revature.persistence.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.core.FieldParams;
import com.revature.persistence.database.util.ConnectionUtil;
public abstract class DatabaseInsertor extends DatabaseQuery {

	@Override
	public int insert(BusinessObject businessObject) {
		String name = businessObject.getClass().getSimpleName();
		return insert(name, fieldParamsFactory.getFieldParams(businessObject));
	}

	@Override
	public int insert(String name, FieldParams values) {
		String sql = "INSERT INTO " + validateTableName(name);
		List<String> params = new ArrayList<>();
		List<String> data = new ArrayList<>();
		PreparedStatement statement = null;
		int total = 0;
		
		// Prepare transaction 
		for (String key : values.keySet()) {
			params.add(key);
			data.add(key + "=?");
		}
		
		sql += "(" + String.join(",", params) + ")" + "VALUES(" + String.join(",", data) + ")";
		
		try (Connection conn = ConnectionUtil.getConnection();) {
			statement = conn.prepareStatement(sql);
			clauseBuilder.build(name, statement, values);
			total = statement.executeUpdate();
		} catch (SQLException e) {
			
		} finally {
			ConnectionUtil.close(statement);
		}
	
		return total;
	}
}
