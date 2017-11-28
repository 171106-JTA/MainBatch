package com.revature.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.service.RegisterEmployee;

/**
 * Servlet implementation class MyFrontController
 */
public class MyFrontController extends HttpServlet {
	private static final long serialVersionUID = 2470658858713525902L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String url = request.getRequestURI();
		final String[] url_parts = url.split("/");
		final String action = url_parts[url_parts.length - 1].split("\\.")[0].toLowerCase(); //grab the action
		
		RequestDispatcher rd = null;
		HttpSession session = null;		
		
		if (action.equals("login")) { //A user is logging in
			login(request, response);
		} else if (action.equals("register")) { //A user is making a new account
			register(request, response);
		} else if (action.equals("submit_tuition_request")) {
			submitTuitionRequest(request, response);
		}
			
		else {
			response.sendError(404);
		}
	}

	private void login(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 * @param request
	 * @param response
	 */
	private void submitTuitionRequest(HttpServletRequest request, HttpServletResponse response) {
		
		
		
	}

	/**
	 * 
	 * @param request
	 * @param response
	 */
	private void register(HttpServletRequest request, HttpServletResponse response) {
		final boolean successfully_registered = RegisterEmployee.register(request);
		
		if (successfully_registered) {
			
		} else {
			
			
		}
		
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}