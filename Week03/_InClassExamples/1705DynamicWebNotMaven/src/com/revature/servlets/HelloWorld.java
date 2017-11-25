package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HelloWorld extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public void init() throws ServletException {
		System.out.println(this.getServletConfig().getServletName() + ":I am not alive yet! Web container has called me!");
		
		
		Enumeration params = this.getServletConfig().getInitParameterNames();
		
		while(params.hasMoreElements()){
			String param = (String)params.nextElement();
			System.out.println(param + ": " + this.getServletConfig().getInitParameter(param));
		}
		
		Enumeration Globalparams = this.getServletContext().getInitParameterNames();
		
		
		while(Globalparams.hasMoreElements()){
			String param = (String)Globalparams.nextElement();
			System.out.println(param + ": " + this.getServletContext().getInitParameter(param));
		}
		
	}


	public void destroy() {
		System.out.println("Web container considers dormant, shutting me down.");
	}


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("I am now executing actions.");
		
		response.setContentType("text/html");
		//Grab the writer of the response to utilize it to send back a dynamic webpage
		PrintWriter out = response.getWriter();
		
		out.println("Hello world!");
		
		
		out.println(
				"<hr>" +
				"<a href='index.html'>BACK</a>"
				);
		
	}

}
