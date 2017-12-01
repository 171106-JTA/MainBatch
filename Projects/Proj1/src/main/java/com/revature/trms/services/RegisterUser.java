package com.revature.trms.services;

import javax.servlet.http.HttpServletRequest;

import com.revature.trms.dao.EmployeeDAO;
import com.revature.trms.model.Employee;

public class RegisterUser {
	public static boolean register(HttpServletRequest request) {
		Employee emp = new Employee(
				request.getParameter("username"),
				request.getParameter("password"),
				request.getParameter("email"), 
				request.getParameter("firstname"), 
				request.getParameter("lastname"));
		EmployeeDAO td = new EmployeeDAO();
		if (td.selectEmployeeByUsername(emp.getUsername()) != null) {
			return false;
		}
		td.insertNewEmployee(emp);

		return true;
	}
}
