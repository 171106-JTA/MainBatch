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

		//Grab existing session object, or create a new one if it doesnt exist
		HttpSession session = request.getSession();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		if(session.isNew()){
			out.println("SESSION CREATED FOR " + request.getParameter("fname").toUpperCase() + " " + request.getParameter("lname").toUpperCase());
			session.setAttribute("fname", request.getParameter("fname"));
			session.setAttribute("lname", request.getParameter("lname"));
			session.setAttribute("visit", 0);
		}else{
			String fname = ((String)session.getAttribute("fname")).toUpperCase();
			String lname = ((String)session.getAttribute("lname")).toUpperCase();
			out.println("CANNOT CREATE SESSION!");
			out.println("CURRENTLY LOGGED IN AS: " + fname + " " + lname);
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
