package com.banana.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FrontController
 */
public class FrontController extends HttpServlet {
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url = request.getRequestURI();

		System.out.println(url);

		String[] split = url.split("/");

		System.out.println(split[split.length-1]);

		String action = split[split.length-1].substring(0, split[split.length-1].length()-4).toLowerCase();

		System.out.println(action);

		response.setContentType("text/html");
		RequestDispatcher rd = null;

		

		switch(action){
			case "login":
				rd = request.getRequestDispatcher("EmployeeLogin");
				rd.forward(request, response);
				break;
			case "submit":
				rd = request.getRequestDispatcher("SubmitRequest");
				rd.include(request, response);
				break;
			case "approval":
				rd = request.getRequestDispatcher("ApproveRequest");
				rd.include(request, response);
				break;
			case "info_request":
				rd = request.getRequestDispatcher("GetInfoRequest");
				rd.include(request, response);
				break;
			default: 
				response.sendError(404);
		}
	}
	
	

}
