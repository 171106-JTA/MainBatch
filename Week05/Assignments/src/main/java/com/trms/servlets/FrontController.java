package com.trms.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.trms.services.Services;

/**
 * Servlet implementation class FrontController
 */
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURI();
		String splits[] = url.split("/");
		String action = splits[splits.length - 1];
		action = action.substring(0, action.indexOf('.'));

		response.setContentType("text/html");
		PrintWriter out = response.getWriter(); 
		RequestDispatcher rd = null; 

		if(action.equals("login")) {
			out.println("<p>Login Action</p>");
			out.println("<a href='landing.html'>GO BACK</a>");
		}

		else if(action.equals("requestform")) {
			if(userLoggedIn(request, response)) {
				rd = request.getRequestDispatcher("request.html"); 
				rd.include(request, response);
			}
		}

		else if(action.equals("viewrequests")) {
			System.out.println("redirecting viewrequests");
			if(userLoggedIn(request, response)) {
				rd = request.getRequestDispatcher("ViewRequests");
				rd.include(request, response);				
			}
		}

		else if(action.equals("home")) {
			if(userLoggedIn(request, response)) {
				rd = request.getRequestDispatcher("home.html"); 
				rd.include(request, response);
			}
		}




	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURI();
		String splits[] = url.split("/");
		String action = splits[splits.length - 1];
		action = action.substring(0, action.indexOf('.'));
		System.out.println("action = " + action); 

		response.setContentType("text/html");
		PrintWriter out = response.getWriter(); 
		RequestDispatcher rd = null; 

		if(action.equals("login")) {
			rd = request.getRequestDispatcher("Login"); 
			rd.include(request, response);
		}
		else if(action.equals("submitRequest")) {
			if(userLoggedIn(request, response)) {
				rd = request.getRequestDispatcher("ReimbursementRequest");
				rd.include(request, response);
			}
		}



	}

	private boolean userLoggedIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(); 
		String username = (String) session.getAttribute("username"); 
		String password = (String) session.getAttribute("password");
		boolean loggedIn = (username != null && password != null && 
				Services.verifyCredentials(username, password));
		if(!loggedIn)
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		return loggedIn; 

	}

}
