package com.revature.servlets;

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
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAndPost() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username"),
				password = request.getParameter("password");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<h3>GET request</h3>");
		out.println(
					"<table>" + 
						"<tr><th>Username</th><th>Password</th></tr>" + 
						"<tr><td>" + username + "</td><td>" + password + "</td></tr>" + 
					"</table>"
		);
		out.println(
				"<hr>" + 
				"<a href='index.html'>BACK</a>"
				);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username"),
				password = request.getParameter("password");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<h3>POST request</h3>");
		out.println(
					"<table>" + 
						"<tr><th>Username</th><th>Password</th></tr>" + 
						"<tr><td>" + username + "</td><td>" + password + "</td></tr>" + 
					"</table>"
		);
		out.println(
				"<hr>" + 
				"<a href='index.html'>BACK</a>"
				);
	}

}
