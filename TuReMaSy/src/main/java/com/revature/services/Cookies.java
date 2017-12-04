package com.revature.services;

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
public class Cookies extends HttpServlet {



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//This creates a cookie, with the key = username, and value = password.
		Cookie cookie = new Cookie(request.getParameter("username"), request.getParameter("password"));
		
		cookie.setMaxAge(30); //Cookie deletes after 30 seconds
		
		response.addCookie(cookie);
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		out.println("Cookie created!");
		
		out.println(
				"<hr>" +
				"<a href='index.html'>BACK</a>"
				);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}