package com.trms.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FrontController
 */
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

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
		
		if(action.equals("login")) {
			out.println("<p>Login Action</p>");
			out.println("<a href='landing.html'>GO BACK</a>");
		}
		else if(action.equals("requestform")) {
			out.println("<head><title>Redirecting</title></head><body>");
			out.println("<p>Request Action</p>");
			out.println("<a href='other.html'>GO TO HERE</a>");
			out.println("</body>");
		}
		else if(action.equals("viewrequests")) {
			
			
		}
		else if(action.equals("redirectdirect")) {
			response.sendRedirect("other.html");
			
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
		
		if(action.equals("login")) {
			response.sendRedirect("index.html");
		}
		else if(action.equals("requestform")) {
			out.println("<head><title>Redirecting</title></head><body>");
			out.println("<p>Request Action</p>");
			out.println("<a href='other.html'>GO TO HERE</a>");
			out.println("</body>");
		}
		else if(action.equals("viewrequests")) {
			
			
		}
		else if(action.equals("registerUser")) {
			System.out.println("Redirecting to CreateNewUser servlet");
			RequestDispatcher rd = request.getRequestDispatcher("CreateUserRequest");
			rd.include(request, response); 
		}
		else if(action.equals("redirectdirect")) {
			response.sendRedirect("other.html");
			
		}
		
	}

}
