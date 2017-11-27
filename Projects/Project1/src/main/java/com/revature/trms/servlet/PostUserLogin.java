package com.revature.trms.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PostUserLogin
 */
public class PostUserLogin extends HttpServlet {

//	/**
//	 * 
//	 */
	private static final long serialVersionUID = -7397521266355425122L;
	
	public void init() throws ServletException{
		System.out.println("Servlet has been instantiated.");
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<h3>Logged in user information</h3>");
		out.println("<table border='2px'>" +"<tr><th>username</th><th>password</th></tr>"
				+ "<tr><td>"+username+"</td><td>" + password + "</td></tr></table>");
	}

}
