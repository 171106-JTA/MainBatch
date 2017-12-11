package com.project1.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project1.services.registerEmployee;

/**
 * Servlet implementation class EmployeeRegistration
 */
public class EmployeeRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String ID = request.getParameter("employeeID");
		if(registerEmployee.registerEmp(request)){
			out.println("<h1>Employee REGISTERED</h1>");
		}else{
			out.println("<h1>REGISTRATION FAILED</h1>");
		}
		out.println(
    			"<hr>" + 
    			"<a href='index.html'>BACK</a>"
    			);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
