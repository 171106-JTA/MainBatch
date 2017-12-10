package com.banana.service;

import com.banana.bean.Employee;
import com.banana.bean.ReimburseRequest;
import com.banana.dao.EmployeeDAOImpl;
import com.banana.dao.ReimbursementDAOImpl;
import com.banana.dao.UpdateDAOImpl;
import com.revature.log.Logging;

public class UpdateEmployeeAmount {
	public static boolean updateAmount(int empId, int rrId) {
		EmployeeDAOImpl empdao = new EmployeeDAOImpl();
		ReimbursementDAOImpl rdao = new ReimbursementDAOImpl();
		UpdateDAOImpl udao = new UpdateDAOImpl();
		boolean status = false;
		
		Employee emp = empdao.getEmployeeById(empId);
		
		ReimburseRequest rr = rdao.getSingleRequest(rrId);
		
		if(emp != null) {
			double amount = emp.getCurrAmount();
			double newAmount = amount - rr.getCost();
			
			if(newAmount < 0) {
				return false;
			}
			status = udao.setNewAmount(empId, newAmount);
			if(status) {
				Logging.startLogging("Request of " + rr.getCost() + " has been approved for " + emp.getUsername());
			}
			else {
				Logging.startLogging("Issue updating amount");
			}
		}
		
		if(!status) {
			Logging.startLogging("Issue updating amount");
		}
		
		return status;
	}
	
	public static boolean denyAmount(int rrId) {
		EmployeeDAOImpl empdao = new EmployeeDAOImpl();
		ReimbursementDAOImpl rdao = new ReimbursementDAOImpl();
		UpdateDAOImpl udao = new UpdateDAOImpl();
		boolean status = false;
		
		int empId = empdao.getEmployeeIdFromRequest(rrId);
		
		Employee emp = empdao.getEmployeeById(empId);
		
		ReimburseRequest rr = rdao.getSingleRequest(rrId);
		
		if(emp != null) {
			double amount = emp.getCurrAmount();
			double newAmount = amount + rr.getCost();
			
			if(newAmount > 1000) {
				return false;
			}
			status = udao.setNewAmount(empId, newAmount);
			if(status) {
				Logging.startLogging("Request of " + rr.getCost() + " has been denied for " + emp.getUsername());
			}
			else {
				Logging.startLogging("Issue updating amount");
			}
		}
		
		if(!status) {
			Logging.startLogging("Issue: Employee not found");
		}
		return status;
	}
}
