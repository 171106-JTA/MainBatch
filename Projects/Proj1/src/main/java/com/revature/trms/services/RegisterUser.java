package com.revature.trms.services;

import javax.servlet.http.HttpServletRequest;

import com.revature.trms.dao.EmployeeDAO;
import com.revature.trms.model.Employee;

public class RegisterUser {
	public static boolean register(HttpServletRequest request){
		Employee emp = new Employee(
					request.getParameter("username"),
					request.getParameter("password"),
					request.getParameter("email"),
					request.getParameter("fname"),
					request.getParameter("lname")
				);
		EmployeeDAO empData = new EmployeeDAO();
		// Checks to see no duplicate username exists
		
		if(empData.selectEmployeeByUsername(emp.getUsername())!=null){
			return false;
		}
		empData.insertNewEmployee(emp);

		return true;
	}
}