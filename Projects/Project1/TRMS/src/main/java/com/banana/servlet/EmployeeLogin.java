package com.banana.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.banana.bean.Employee;
import com.banana.service.ValidateUser;
import com.revature.log.Logging;

/**
 * Servlet implementation class UserAuthentication
 */
public class EmployeeLogin extends HttpServlet {
	
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
		RequestDispatcher rd = null;
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Employee emp = ValidateUser.validate(request, response);
		
		if(emp != null) {
			if(emp.getRoleId() == 0) {
				rd = request.getRequestDispatcher("EmployeePage.html");
				Logging.startLogging(emp.getUsername() + " has logged in as Employee");
			}
			else {
				rd = request.getRequestDispatcher("AdminPage.html");
				Logging.startLogging(emp.getUsername() + " has logged in as Admin");
			}
			
			rd.forward(request, response);
		}
		else {
			out.println("<script type='text/javascript'>");
			out.println("alert('Wrong Credentials');");
			out.println("</script>");
			rd = request.getRequestDispatcher("index.html");
			Logging.startLogging("Login has failed");
			rd.include(request, response);
		}
	}

	
}
