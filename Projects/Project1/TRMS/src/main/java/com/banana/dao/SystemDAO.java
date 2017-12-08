package com.banana.dao;

import java.util.List;

import com.banana.bean.Employee;
import com.banana.bean.ReimburseRequest;

public interface SystemDAO {
	public Employee getEmployeeByUsername(String username);
	public Employee getEmployeeById(int empId);
	public int getEmployeeIdFromRequest(int rrId);
	public ReimburseRequest getSingleRequest(int rrId);
	public List<ReimburseRequest> getEmployeeRequests(int empId);
	public List<ReimburseRequest> getAllRequests();
}
