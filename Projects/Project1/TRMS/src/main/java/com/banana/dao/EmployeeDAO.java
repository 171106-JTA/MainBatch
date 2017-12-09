package com.banana.dao;

import java.util.List;

import com.banana.bean.Employee;
import com.banana.bean.InfoRequest;

public interface EmployeeDAO {
	public Employee getEmployeeByUsername(String username);
	public Employee getEmployeeById(int empId);
	public int getEmployeeIdFromRequest(int rrId);
	public List<InfoRequest> getInfoRequests(int requesteeId);
	public List<InfoRequest> getAllInfoRequests();
}
