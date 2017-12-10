package com.banana.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.banana.bean.Employee;
import com.banana.dao.EmployeeDAOImpl;
import com.revature.log.Logging;

public class ValidateUser {
	public static Employee validate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		EmployeeDAOImpl empdao = new EmployeeDAOImpl();
		String requestUsername = request.getParameter("username");
		String requestPassword = request.getParameter("password");

		Employee emp = empdao.getEmployeeByUsername(requestUsername);
		System.out.println(emp);
		if(emp != null) {
			String realPassword = emp.getPassword(); 
			if(requestPassword.equals(realPassword)) {
				CreateSession.makeSession(request, emp);
				return emp;
			}
		}
		Logging.startLogging(requestUsername + " has attempted to login");
		return null;
	}
}
