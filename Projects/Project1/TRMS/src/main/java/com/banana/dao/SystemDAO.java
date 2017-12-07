package com.banana.dao;

import java.util.List;

import com.banana.bean.Employee;
import com.banana.bean.ReimburseRequest;

public interface SystemDAO {
	public Employee getEmployeeByUsername(String username);
	public boolean submitRequest(ReimburseRequest request);
	public List<ReimburseRequest> getEmployeeRequests(int empId);
	public List<ReimburseRequest> getAllRequests();
	public double getPercentage(int eventId);
}
