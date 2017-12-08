package com.banana.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.banana.bean.Employee;

public class CreateSession {
	public static void makeSession(HttpServletRequest request, Employee emp) {
		HttpSession session = request.getSession();
		
		if(session.isNew()) {
			//session.setAttribute("username", emp.getUsername());
			session.setAttribute("empId", emp.getEmpId());
			session.setAttribute("role", emp.getRoleId());
			session.setAttribute("username", emp.getUsername());
		}
	}
}
