package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.service.Login;

/**
 * Servlet implementation class FrontController
 */
public class FrontController extends HttpServlet {
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURI();
		System.out.println(url);
		
		String[] split = url.split("/");
		System.out.println(split[split.length-1]);
		
		String action = split[split.length-1].substring(0, split[split.length-1].length()-3).toLowerCase();
		System.out.println(action);
		
		RequestDispatcher rd = null;
		HttpSession session = null;
		
		switch(action){
		case "hello":
			rd = request.getRequestDispatcher("HeyBobbert");
			rd.forward(request, response);
			break;
		case "login":
			request.setAttribute("issue", null); //Stores any error messages that occur
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			Login login = new Login();
			if(login.validateLogin(username, password)){
				session = request.getSession();
				session.setAttribute("user", request.getAttribute("username"));
			}else{
				request.setAttribute("issue", "INVALID CREDENTIALS!");
			}
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			if(!(request.getAttribute("issue")==null)){
				out.println("<h3 style='color:red'>" + request.getAttribute("issue") + "</h3>");
			}
			
			rd = request.getRequestDispatcher("index.html");
			rd.include(request, response);

			
			
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
