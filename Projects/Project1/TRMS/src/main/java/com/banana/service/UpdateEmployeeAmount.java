package com.banana.service;

import com.banana.bean.Employee;
import com.banana.dao.SystemDAOImpl;
import com.banana.dao.SystemUpdateDAOImpl;

public class UpdateEmployeeAmount {
	public static void updateAmount(int empId, double cost) {
		SystemDAOImpl dao = new SystemDAOImpl();
		SystemUpdateDAOImpl udao = new SystemUpdateDAOImpl();
		
		Employee emp = dao.getEmployeeById(empId);
		
		if(emp != null) {
			double amount = emp.getCurrAmount();
			double newAmount = amount - cost;
			udao.setNewAmount(empId, newAmount);
		}
	}
}
