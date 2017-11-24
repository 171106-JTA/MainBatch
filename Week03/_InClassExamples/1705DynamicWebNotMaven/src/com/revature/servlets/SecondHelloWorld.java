package com.revature.servlets;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SecondHelloWorld
 */
public class SecondHelloWorld extends HttpServlet {
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
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
