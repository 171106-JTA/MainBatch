package com.banana.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.banana.service.LogOut;

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
	 * Directs actions to certain servlets; used as the intial contact point of all requests
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url = request.getRequestURI();

		System.out.println(url);
		String[] split = url.split("/");

		String action = split[split.length-1].substring(0, split[split.length-1].length()-4).toLowerCase();

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
			case "submit_info":
				rd = request.getRequestDispatcher("SubmitAdditionalInfo");
				rd.include(request, response);
				break;
			case "logout":
				LogOut.logout(request);
				rd = request.getRequestDispatcher("index.html");
				rd.forward(request, response);
				break;
			default: 
				response.sendError(404);
		}
	}
	
	

}
