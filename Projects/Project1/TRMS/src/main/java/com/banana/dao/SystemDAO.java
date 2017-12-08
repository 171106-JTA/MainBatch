package com.banana.dao;

import java.util.List;

import com.banana.bean.Employee;
import com.banana.bean.ReimburseRequest;

public interface SystemDAO {
	public Employee getEmployeeByUsername(String username);
	public List<ReimburseRequest> getEmployeeRequests(int empId);
	public List<ReimburseRequest> getAllRequests();
}
