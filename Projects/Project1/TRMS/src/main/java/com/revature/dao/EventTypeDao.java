package com.revature.dao;

import java.sql.SQLException;
import java.util.Map;

public interface EventTypeDao {

	public Map<Integer, Double> getReimbursementAmounts() throws SQLException;
}
