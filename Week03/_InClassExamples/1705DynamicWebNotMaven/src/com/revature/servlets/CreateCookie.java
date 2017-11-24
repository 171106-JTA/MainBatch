package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateCookie
 */
public class CreateCookie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Create a cookie as a key/value pair for parameters.
		Cookie cookie = new Cookie(request.getParameter("username"), request.getParameter("password"));
		
		//Determine how long a cookie is active for (Done so in seconds)
		cookie.setMaxAge(30);
		
		response.addCookie(cookie);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("Cookie Created!");
		
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
