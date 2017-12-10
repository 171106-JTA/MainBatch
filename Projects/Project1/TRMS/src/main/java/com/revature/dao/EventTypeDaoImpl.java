package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.revature.util.ConnectionUtil;

public class EventTypeDaoImpl {


	ConnectionUtil cUtil;
	
	public EventTypeDaoImpl() {
		cUtil = ConnectionUtil.getInstance();
	}

	
	public Map<Integer, Double> getReimbursementAmounts() throws SQLException{
		
		String sql = "select event_type_id, event_type_reimburse_per "
				+ "from event_type";
		
		try(Connection conn = cUtil.getConnection()) {
			PreparedStatement p = conn.prepareStatement(sql);
			ResultSet rs = p.executeQuery();
			
			Map<Integer, Double> reimburseMap = new HashMap<Integer, Double>();
			
			while(rs.next()) {
				reimburseMap.put(rs.getInt(1), rs.getDouble(2) / 100.0);
			}
			return reimburseMap;
		}
	}
	
}
