package com.revature.trms.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteCookie
 */
public class DeleteCookie extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void init() throws ServletException{
		System.out.println("Servlet instantiated!");
		
		// Pre-initializing a servlet
		
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCookie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
	
		Enumeration names = this.getInitParameterNames();
		while(names.hasMoreElements()) {
			String name = (String)names.nextElement();
			out.println(name+" | "+ this.getInitParameter(name));
			// use it for any static parameter
		}
		
		
		
		//getting servlet context parameter
		names = this.getServletContext().getInitParameterNames();
		while(names.hasMoreElements()) {
			String name = (String)names.nextElement();
			out.println(name+" | "+ this.getInitParameter(name));
			// use it for any static parameter
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
