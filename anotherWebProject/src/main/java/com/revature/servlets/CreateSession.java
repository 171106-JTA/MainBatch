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
     * @see HttpServlet#HttpServlet()
     */
    public CreateSession() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	response.setContentType("text/html");
    	PrintWriter out = response.getWriter();
    	
    	
		if (session.isNew()) {
			out.println("SESSION CREATED FOR " + request.getParameter("username").toUpperCase());
			session.setAttribute("username", request.getParameter("username"));
			session.setAttribute("visit", 0);
		}
		else
		{
			String username = ((String)session.getAttribute("username")).toUpperCase(),
					password = ((String)session.getAttribute("password")).toUpperCase();
			out.println("Cannot create session!");
			out.println("Currently logged in as : " + username);
			
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
