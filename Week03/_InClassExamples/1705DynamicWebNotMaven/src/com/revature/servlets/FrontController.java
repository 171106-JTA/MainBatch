package com.revature.servlets;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.services.Login;

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
		System.out.println("url: " + url);
		
		String[] tokens = url.split("/");
		System.out.println(Arrays.toString(tokens));
		
		String action = tokens[tokens.length-1];
		System.out.println("Action: " + action);
		
		action = action.substring(0, action.length()-3).toLowerCase();
		System.out.println("action: " + action);
		
		RequestDispatcher rd = null;
		HttpSession session = null;
	
		switch(action){
		case "login":
			request.setAttribute("issue", null);
			String username = request.getParameter("user");
			String password = request.getParameter("pass");
			Login vl = new Login();
			if(vl.validateLogin(username, password)){
				session = request.getSession();
				session.setAttribute("user", "bobbert");
				session.setAttribute("fname", "bobbert");
				session.setAttribute("lname", "bobson");
			}else{
				request.setAttribute("issue", "INVALID CRENDENTIALS!");
			}
			rd = request.getRequestDispatcher("indexJSTL.jsp");
			rd.forward(request, response);
			break;
		case "logout":
			request.getSession().invalidate();
			rd = request.getRequestDispatcher("indexJSTL.jsp");
			rd.forward(request, response);
			break;
		default:
			response.sendError(404);
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
