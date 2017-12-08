package com.banana.dao;

import java.util.List;

import com.banana.bean.Employee;
import com.banana.bean.ReimburseRequest;

public interface EmployeeDAO {
	public Employee getEmployeeByUsername(String username);
	public Employee getEmployeeById(int empId);
	public int getEmployeeIdFromRequest(int rrId);
}
