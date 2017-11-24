package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CreateSession
 */
public class CreateSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Grabs an existing session object. If one does not exists, it creates one on the spot.
		HttpSession session = request.getSession();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		if(session.isNew()){
			out.println("SESSION CREATED FOR " + request.getParameter("username").toUpperCase());
			session.setAttribute("username", request.getParameter("username"));
			session.setAttribute("password", request.getParameter("password"));
			session.setAttribute("visit", 0);
		}else{
			String username = ((String)session.getAttribute("username")).toUpperCase();
			String password = ((String)session.getAttribute("password")).toUpperCase();
			out.println("CANNOT CREATE SESSION!");
			out.println("CURRENTLY LOGGED IN AS: " + username + ", WHOSE PASSWORD IS: " + password);
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
