package com.revature.trms.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.trms.services.RegisterUser;

/**
 * Servlet implementation class UserRegistration
 */
public class UserRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		if (RegisterUser.register(request)) {
			out.println("<h1>USER REGISTERED</h1><p> Thank you " + request.getParameter("fname") + "</p>");
		} else {
			out.println("<h1>REGISTRATION FAILED</h1>");
		}
		out.println("<hr>" + "<a href='index.html'>BACK</a>");
	}

}
