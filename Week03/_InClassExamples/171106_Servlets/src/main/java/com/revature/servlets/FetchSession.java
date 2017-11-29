package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FetchSession
 */
public class FetchSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		
		
		if(session.isNew()){
			out.println("Please create a session first!");
			session.invalidate(); //Clears the session entirely
		}else{
			String username = (String)session.getAttribute("username");
			session.setAttribute("visit", ((Integer)session.getAttribute("visit")) + 1);
			
			out.println("<h2>Welcome, " + username + "!</h2><br>");
			
			out.println("Below are the session specs: <br> <table border='2px'><tr><th>Field Name</th><th>Field Value</th></tr>");
			out.println("<tr><td>SESSION HOLDER</td><td>" + username + "</td></tr>");
			out.println("<tr><td>SESSION ID</td><td>" + session.getId() + "</td></tr>");
			out.println("<tr><td>CREATION TIME</td><td>" + new Date(session.getCreationTime()) + "</td></tr>");
			out.println("<tr><td>LAST ACCESS</td><td>" + new Date(session.getLastAccessedTime()) + "</td></tr>");
			out.println("<tr><td>VISIT COUNT</td><td>" + session.getAttribute("visit") + "</td></tr>");
			out.println("</table>");
		}
		
		
		out.println(
				"<hr>" +
				"<a href='index.html'>BACK</a>"
				);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
