package com.revature.controller.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetAndPost
 */
public class GetAndPost extends HttpServlet {
       
	/*
	 * Never, EVER, override the service() method.
	 * It is already implemented in the background and aims to call the proper DO method
	 * for whatever HTTP method is used. If you override service, doGet, doPost, doEtc will
	 * not execute and you will just get a blank page every time.
	 */
	
	
	/*
	 * doGet is the method that service will call when your form sends a GET request.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<h3>GET REQUEST</h3>");
		out.println(
					"<table border='2px'>"	+
							"<tr><th>USERNAME</th><th>PASSWORD</th></tr>" +
							"<tr><td>" + username + "</td><td>" + password + "</td></tr></table>"
				);
		out.println(
				"<hr>" +
				"<a href='index.html'>BACK</a>"
				);
	}

	/*
	 * doPost is the method that service will cal when your form sends a POST request.
	 * Note in the auto implemented method, it just simply calls doGet().
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<h3>POST REQUEST</h3>");
		out.println(
					"<table border='2px'>"	+
							"<tr><th>USERNAME</th><th>PASSWORD</th></tr>" +
							"<tr><td>" + username + "</td><td>" + password + "</td></tr></table>"
				);
		out.println(
				"<hr>" +
				"<a href='index.html'>BACK</a>"
				);
		
	}

}
