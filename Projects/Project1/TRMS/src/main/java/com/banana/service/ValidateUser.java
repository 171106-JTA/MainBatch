package com.banana.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.banana.bean.Employee;
import com.banana.dao.EmployeeDAOImpl;
import com.revature.log.Logging;

public class ValidateUser {
	/**
	 * This method validates the user for login
	 * 
	 * @param request object that contains parameter values from the HTML
	 * @param response object that will contain any message to be returned
	 * 
	 * @return Employee if one exists in the database, otherwise null
	 * @throws IOException
	 * @throws ServletException
	 */
	public static Employee validate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		EmployeeDAOImpl empdao = new EmployeeDAOImpl();
		String requestUsername = request.getParameter("username");
		String requestPassword = request.getParameter("password");

		Employee emp = empdao.getEmployeeByUsername(requestUsername);
		
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
