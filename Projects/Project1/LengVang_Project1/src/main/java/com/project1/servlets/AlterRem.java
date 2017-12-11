package com.project1.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project1.classes.Employee;

/**
 * Servlet implementation class AlterRem
 */
public class AlterRem extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Employee currEmp = (Employee) session.getAttribute("currEmp");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		QuickFunction.insertBoostrap(response);
		QuickFunction.insertNavBar(response);
		
		out.println("<h3> Alter Reimbursement</h3>");
		out.println("<form action='AlterFinish'>"
				+ "<input type='number' name='alterAmount'>"
				+ "<input type='submit' name='Submit'>"
				+ "</form>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
