package com.banana.service;

import com.banana.bean.Employee;
import com.banana.bean.ReimburseRequest;
import com.banana.dao.EmployeeDAOImpl;
import com.banana.dao.ReimbursementDAOImpl;
import com.banana.dao.UpdateDAOImpl;

public class UpdateEmployeeAmount {
	public static boolean updateAmount(int rrId) {
		EmployeeDAOImpl empdao = new EmployeeDAOImpl();
		ReimbursementDAOImpl rdao = new ReimbursementDAOImpl();
		UpdateDAOImpl udao = new UpdateDAOImpl();
		boolean status = false;
		
		int empId = empdao.getEmployeeIdFromRequest(rrId);
		
		Employee emp = empdao.getEmployeeById(empId);
		
		ReimburseRequest rr = rdao.getSingleRequest(rrId);
		
		if(emp != null) {
			double amount = emp.getCurrAmount();
			double newAmount = amount - rr.getCost();
			if(newAmount < 0) {
				return false;
			}
			status = udao.setNewAmount(empId, newAmount);
		}
		
		return status;
	}
}
