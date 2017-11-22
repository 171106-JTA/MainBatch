package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloWorld2
 */
public class HelloWorld2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//Method called first and only once throughout life cycle.
	public void init() throws ServletException{
		System.out.println("Servlet Instantiated!");
	}
	
	//Service method is the method that actually performs the actions with the data.
	//It takes the request and response objects (Were wrapped by the web container)
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("I am now executing a service method.");
		
		response.setContentType("text/html");
		//Grab the writer of the response to utilize it to send backa  dynamic webpage.
		PrintWriter out = response.getWriter();
		
		out.println(
				"<h1>HELLO BOBBERT!</h1>" +
				"<hr>" +
				"<a href='index.html'>BACK</a>"
				);
	}
	
	//Method called last and only one throughout life cycle.
	public void destroy(){
		System.out.println("Servlet Destroyed");
	}

}
