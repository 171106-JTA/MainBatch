package com.banana.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.banana.bean.Employee;

public class CreateSession {
	
	/**
	 * This method creates a session for the user
	 * Saves the employee id, role, username, currAmount of the employee
	 * 
	 * @param request the request object that holds session data
	 * @param emp the Employee object with data
	 */
	public static void makeSession(HttpServletRequest request, Employee emp) {
		HttpSession session = request.getSession();
	
		if(session.isNew()) {
			session.setAttribute("empId", emp.getEmpId());
			session.setAttribute("role", emp.getRoleId());
			session.setAttribute("username", emp.getUsername());
			session.setAttribute("currAmount", emp.getCurrAmount());
		}
	}
}
