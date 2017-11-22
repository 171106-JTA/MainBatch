package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetAndPost
 */
public class GetAndPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/*
	 * service() will call doGet in the event of the GET HTTP protocol
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstname = request.getParameter("first");
		String lastname = request.getParameter("last");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println(
					"<ul>" +
							"<li>FIRST NAME: " + firstname + "</li>" +
							"<li>LAST NAME: " + lastname + "</li>" +
					"</ul>"
				);
		
		
		out.println(
				"<hr>" +
				"<a href='index.html'>BACK</a>"
				);
	}

	/*
	 * service() will call doPost in the event of the POST HTTP Protocol
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println(
				"<ul>");
		String param;
		Enumeration params = request.getParameterNames();
		while(params.hasMoreElements()){
			param = (String)params.nextElement();
			out.println("<li>" + param + ": " + request.getParameter(param) + "</li>");
		}
		
		out.println("</ul>");
		
		
		out.println(
				"<hr>" +
				"<a href='index.html'>BACK</a>"
				);
	}

}
