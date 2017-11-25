package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloWorld
 */
public class HelloWorld extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//	method called first and only once throughout lifecycle
	public void init() throws ServletException
	{
		System.out.println("Servlet instantiated!");
	}
	
//	method called first and only once throughout lifecycle
	public void destroy()
	{
		
	}
//	service() is the method that actually performs the actions with the data
//	it takes the request and response objects (were wrapped by the web container)
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException
	{
		System.out.println("I am now executing a service method.");
		
		res.setContentType("text/html");
		// get the writer of the response to utilize it to send back dynamic webpage.
		PrintWriter out = res.getWriter();
		
		out.println(
				"<h1>HELLO BOBBERT!</h1>" + 
				"<hr>" + 
				"<a>text</a>"
				);
	}
}
