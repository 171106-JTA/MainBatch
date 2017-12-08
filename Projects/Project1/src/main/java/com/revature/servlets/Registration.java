package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.service.RegisterEmployee;

/**
 * Servlet implementation class Registration
 */
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Registration");
		boolean success = RegisterEmployee.register(request);
		RequestDispatcher rd = null;
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		if (success) {
			out.println("<h3>Your account has been successfully made.</h3>");
			rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);
		} else {
			out.println("<h3>There was an error creating your account.</h3>");
			rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);
		}
	}
}