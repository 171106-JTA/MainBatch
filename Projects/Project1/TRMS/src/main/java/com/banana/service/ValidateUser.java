package com.banana.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.banana.bean.Employee;
import com.banana.dao.SystemDAOImpl;

public class ValidateUser {
	public static boolean validate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		SystemDAOImpl dao = new SystemDAOImpl();
		String requestUsername = request.getParameter("username");
		String requestPassword = request.getParameter("password");
		
		Employee emp = dao.getEmployeeByUsername(requestUsername);
		System.out.println(emp);
		if(emp != null) {
			String realPassword = emp.getPassword(); 
			if(requestPassword.equals(realPassword)) {
				CreateSession.makeSession(request, emp);
				return true;
			}
		}
		return false;
	}
}
