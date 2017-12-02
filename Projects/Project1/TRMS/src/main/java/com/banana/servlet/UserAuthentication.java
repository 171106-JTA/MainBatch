package com.banana.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.banana.bean.Employee;
import com.banana.dao.SystemDAOImpl;

/**
 * Servlet implementation class UserAuthentication
 */
public class UserAuthentication extends HttpServlet {
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserAuthentication.validate(request, response);
	}

	private static void validate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		SystemDAOImpl dao = new SystemDAOImpl();
		String requestUsername = request.getParameter("username");
		String requestPassword = request.getParameter("password");
		RequestDispatcher rd = null;
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		Employee emp = dao.getEmployeeByUsername(requestUsername);
		System.out.println(emp);
		if(emp != null) {
			String realPassword = emp.getPassword(); 
			if(requestPassword.equals(realPassword)) {
				rd = request.getRequestDispatcher("employee.html");
				rd.forward(request, response);
			}
		}
		else {
			out.println("Try Again");
			rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);
		}
	}
}
