package com.revature.controller.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

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
		
		out.print("<h1>HELLO BOBBERT!</h1>");
		
		//Config parameters
		out.print("<table border='2px'><tr><th>INIT PARAM NAME</th><th>INIT PARAM VALUE</th></tr>");
		
		Enumeration names = 
		this.getInitParameterNames();
		
		while(names.hasMoreElements()){
			String name = (String)names.nextElement();
			out.println("<tr><td>" + name + "</td><td>" + this.getInitParameter(name) + "</td></tr>");
		}
		out.println("</table>");
		
		//Context Parameters
		out.print("<hr><table border='1px'><tr><th>CONTEXT PARAM NAME</th><th>CONTEXT PARAM VALUE</th></tr>");
		
		names = 
		this.getServletContext().getInitParameterNames();
		
		
		while(names.hasMoreElements()){
			String name = (String)names.nextElement();
			out.println("<tr><td>" + name + "</td><td>" + this.getServletContext().getInitParameter(name) + "</td></tr>");
		}
		out.println("</table>");
		
		out.println(
				"<hr>" +
				"<a href='index.html'>BACK</a>"
				);
	}
	
	//Method called last and only one throughout life cycle.
	public void destroy(){
		System.out.println("Servlet Destroyed");
	}

}
