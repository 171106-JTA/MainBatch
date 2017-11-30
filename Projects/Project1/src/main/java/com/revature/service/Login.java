package com.revature.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.bean.User;
import com.revature.dao.TrmsDaoImplement;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private TrmsDaoImplement dao;
	
	 /**
	 * Default constructor.
	 */
	 public Login() {
		 dao = new TrmsDaoImplement(); 
	 }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		String username = request.getParameter("username");
//		String password = request.getParameter("password");
//
		response.setContentType("text/html");
//
		PrintWriter out = response.getWriter();
		out.println("Don't Use GET for login!");
//		out.println("<h3>Hello From Get</h3>");
//		out.println("<table>" + "<tr><th>Username</th><th>Password</th></tr>" + "<tr><th>" + username + "</th><th>"
//				+ password + "</th></tr></table>");
//		out.println("<hr><a href='index.html'>Back</a>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		out.println("<h3>Hello From Post</h3>");
		out.println("<table>" + "<tr><th>Username</th><th>Password</th></tr>" + "<tr><th>" + username + "</th><th>"
				+ password + "</th></tr></table>");
		out.println("<hr><a href='index.html'>Back</a>");
		
		ArrayList<User> all_users = dao.login(username, password);
		
		//Print a table of all users. 
		out.println("<table><tr><th>Username</th><th>Password</th><th>Permission</th></tr>");
		for(User user : all_users) {
			out.println("<tr><th>" + user.getUsername() + "</th><th>" + user.getPassword() + "</th><th>" + 
		user.getPermission() + "</th></tr>");
		}
		out.println("</table>");
	}
}
