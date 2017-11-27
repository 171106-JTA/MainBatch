package com.revature.trms.servlet;

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
		// Create cookie as a key/value pair for parameters
		// username/pw pair
		// Cookie cookie = new Cookie(request.getParameter("username"), request.getParameter("password"));
		// key name/param pair
		Cookie cookie = new Cookie("email", request.getParameter("email"));
		cookie.setMaxAge(60); // in seconds
		response.addCookie(cookie);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("Cookie has been created");
		out.print("<hr><a href='index.html'>BACK</a>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
