package com.banana.service;

import com.banana.bean.Employee;
import com.banana.bean.ReimburseRequest;
import com.banana.dao.SystemDAOImpl;
import com.banana.dao.SystemUpdateDAOImpl;

public class UpdateEmployeeAmount {
	public static boolean updateAmount(int rrId) {
		SystemDAOImpl dao = new SystemDAOImpl();
		SystemUpdateDAOImpl udao = new SystemUpdateDAOImpl();
		boolean status = false;
		
		int empId = dao.getEmployeeIdFromRequest(rrId);
		
		Employee emp = dao.getEmployeeById(empId);
		
		ReimburseRequest rr = dao.getSingleRequest(rrId);
		
		if(emp != null) {
			double amount = emp.getCurrAmount();
			double newAmount = amount - rr.getCost();
			status = udao.setNewAmount(empId, newAmount);
		}
		
		return status;
	}
}
