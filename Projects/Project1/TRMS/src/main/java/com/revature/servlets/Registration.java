package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.service.Register;


/**
 * Servlet implementation class EmployeeRegistration
 */
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//create sql statement
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		if(Register.RegisterUser(request)) {
			out.println("You have succesfully registered! Return to homepage to login.");
		}else {
			out.println("User already exists; please choose another.");
		}
		
		out.println(
				"<hr>" +
				"<a href='index.html'>BACK</a>"
				);
	}

}
