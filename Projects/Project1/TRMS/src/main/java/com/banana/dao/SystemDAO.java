package com.banana.dao;

import com.banana.bean.Employee;
import com.banana.bean.ReimburseRequest;

public interface SystemDAO {
	public Employee getEmployeeByUsername(String username);
	public int submitRequest(ReimburseRequest request);
}
